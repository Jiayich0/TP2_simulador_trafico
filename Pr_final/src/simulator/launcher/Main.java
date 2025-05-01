package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.Event;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;


public class Main {

	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static int _timeLimit = 10;
	private static String _mode = "gui";

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseModeOption(line);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTimeLimitOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator's main loop (default value is 10).").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Execution mode: 'gui' or 'console'").build());
		
		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			System.out.print("main -> parseHelpOption -> MainForStudents no existe");
			//formatter.printHelp(MainForStudents.class.getCanonicalName(), cmdLineOptions, true);
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}
	
	private static void parseModeOption(CommandLine line) throws ParseException {
		if (line.hasOption("m")) {
			_mode = line.getOptionValue("m");
			if (!(_mode.equalsIgnoreCase("gui") || _mode.equalsIgnoreCase("console"))) {
				throw new ParseException("Modo inválido: " + _mode + ". Usar 'gui' o 'console'");
			}
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_mode.equals("console") && _inFile == null) {
	        throw new ParseException("El archivo de eventos es obligatorio en modo 'console'");
	    }
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
		if (_mode.equals("gui")) {
			_outFile = null;
		}
	}
	
	private static void parseTimeLimitOption(CommandLine line) throws ParseException {
	    if (line.hasOption("t")) {
	    	try {
	            _timeLimit = Integer.parseInt(line.getOptionValue("t"));
	            if (_timeLimit < 1) {
	                throw new ParseException("Ticks tiene que ser mayor o igual que 1");
	            }
	        } catch (NumberFormatException e) {
	            throw new ParseException("Número de ticks inválido: " + line.getOptionValue("t"));
	        }
        }
	}
	
	private static void initFactories() {
		 List<Builder<Event>> eventBuilders = new ArrayList<>();
		 
		 // NewJunctionEventBuilder necesita dos parametros para construirse (LightSwitchingStrategy y DequeuingStrategy)
		 // pero para eso se crean una lista con los dos builders de LightSwitchingStrategy (lo mismo con DequeuingStrategy)
		 eventBuilders.add(new NewJunctionEventBuilder(
		            	new BuilderBasedFactory<>(List.of(new RoundRobinStrategyBuilder(), new MostCrowdedStrategyBuilder())),
		            	new BuilderBasedFactory<>(List.of(new MoveFirstStrategyBuilder(), new MoveAllStrategyBuilder()))));
		 eventBuilders.add(new NewCityRoadEventBuilder());
		 eventBuilders.add(new NewInterCityRoadEventBuilder());
		 eventBuilders.add(new NewVehicleEventBuilder());
		 eventBuilders.add(new SetWeatherEventBuilder());
		 eventBuilders.add(new SetContClassEventBuilder());
	    
		_eventsFactory = new BuilderBasedFactory<>(eventBuilders);
	}
	
	private static void startBatchMode() throws IOException {
		InputStream input = new FileInputStream(_inFile);
		
		OutputStream output;
		if((_outFile == null)){
		    output = System.out;
		}
		else {
			output = new FileOutputStream(_outFile);
		}
		
		TrafficSimulator sim = new TrafficSimulator();
		Controller controller = new Controller(sim, _eventsFactory);
		controller.loadEvents(input);
		input.close();
		controller.run(_timeLimit, output);
		
		if (_outFile != null) {
            output.close();
        }
	}
	
	private static void startGUIMode() throws IOException {
		TrafficSimulator sim = new TrafficSimulator();
		Controller controller = new Controller(sim, _eventsFactory);
		
		if (_inFile != null) {
			try (InputStream input = new FileInputStream(_inFile)) {
	            controller.loadEvents(input);
	        }
		}
	
		SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            new MainWindow(controller);
	        }
		});
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		
		if(_mode.equalsIgnoreCase("gui")) {
			startGUIMode();
		}
		else {
			startBatchMode();
		}

	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
