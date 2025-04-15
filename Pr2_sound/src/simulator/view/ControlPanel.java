package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileInputStream;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.JLabel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 3L;
	
	private Controller _ctrl;
	private JButton _loadEventsButton;
	private JButton _changeCO2Button;
	private JButton _changeWeatherButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JSpinner _ticksSpinner;
	private JButton _exitButton;
	private boolean _stopped;
	
	private String _openSound = "resources/sounds/chest_open.wav";
	private String _closeSound = "resources/sounds/chest_close.wav";
	private String _clickSound = "resources/sounds/click.wav";
	private String _spinnerSound = "resources/sounds/strong2.wav"; 

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ticksSpinner.addChangeListener(e -> {
			Sound.playSound(_spinnerSound);
		});
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);
		toolBar.setBackground(MyColors.FONDO2);
		initializeAllButtons();
		
        loadEventsButton(toolBar);
        toolBar.addSeparator(new Dimension(12, 36));
        changeCO2Button(toolBar);
        changeWeatherButton(toolBar);
		toolBar.addSeparator(new Dimension(12, 36));
		runButton(toolBar);
		stopButton(toolBar);
		toolBar.addSeparator(new Dimension(4, 36));
		ticksLabel(toolBar);
		toolBar.add(_ticksSpinner);
		toolBar.add(Box.createHorizontalGlue()); 			// Empuja el botón a la derecha
		exitButton(toolBar);
        
        this.add(toolBar, BorderLayout.NORTH);
    }
	
	private void initializeAllButtons() {
		_loadEventsButton = createButton("resources/icons/open.png");
        _changeCO2Button = createButton("resources/icons/co2class.png");
        _changeWeatherButton = createButton("resources/icons/weather.png");
        _runButton = createButton("resources/icons/run.png");
        _stopButton = createButton("resources/icons/stop.png");
        _ticksSpinner = createSpinner();
        _exitButton = createButton("resources/icons/exit.png");
	}
	
	private JButton createButton(String iconPath) {
	    JButton button = new JButton();
	    
	    button.setIcon(new ImageIcon(iconPath));
	    
	    button.setBorderPainted(true);		// Pone el borde
	    button.setFocusPainted(false);		// Quita el recuadro de la imagen al ser seleccionado
	    button.setContentAreaFilled(true);	// Color de fondo
	    button.setBackground(MyColors.ELEMENTOS);
	    
	    return button;
	}
	 
	private JSpinner createSpinner() {
		 // valor incial, minimo, maximo, paso(cuantos ticks aumentas/disminuyes por click)
		SpinnerNumberModel model = new SpinnerNumberModel(10, 1, 10000, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setMaximumSize(new Dimension(60, 40));
		 
		JFormattedTextField txt = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		txt.setBackground(MyColors.ELEMENTOS);
		txt.setForeground(MyColors.TEXTO);
		txt.setFont(new Font("Tahoma", Font.BOLD, 12));
		 
		return spinner;
	}
	
	
	private void loadEventsButton(JToolBar toolBar) {
		JFileChooser fileChooser = new JFileChooser();
		
		_loadEventsButton.addActionListener(e -> {
			Sound.playSound(_openSound);
			int select = fileChooser.showOpenDialog(null);
			Sound.playSound(_closeSound);
					
			if (select == JFileChooser.APPROVE_OPTION) {
				if (fileChooser.getSelectedFile() != null) {
					try (FileInputStream fileInput = new FileInputStream(fileChooser.getSelectedFile())) {
						_ctrl.reset();
						_ctrl.loadEvents(fileInput);
					} catch (Exception except) {
						except.printStackTrace();
			            JOptionPane.showMessageDialog(this, "[Error] Failed loading events file", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
	                JOptionPane.showMessageDialog(this, "[Warning] No file selected", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		toolBar.add(_loadEventsButton);
	}
	
	private void changeCO2Button(JToolBar toolBar) {
		_changeCO2Button.addActionListener(e -> {
			Sound.playSound(_clickSound);
			ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog(_ctrl, SwingUtilities.getWindowAncestor(this));	//Pasa el padre para bloquear
	        dialog.setVisible(true);
	        Sound.playSound(_clickSound);
		});
		toolBar.add(_changeCO2Button);
	}
	
	private void changeWeatherButton(JToolBar toolBar) {
		_changeWeatherButton.addActionListener(e -> {
			Sound.playSound(_clickSound);
			ChangeWeatherDialog dialog = new ChangeWeatherDialog(_ctrl, SwingUtilities.getWindowAncestor(this));
	        dialog.setVisible(true);
	        Sound.playSound(_clickSound);
		});
		toolBar.add(_changeWeatherButton);
	}
	
	private void exitButton(JToolBar toolBar) {
		_exitButton.addActionListener(e -> {
			Sound.playSound(_clickSound);
			UIManager.put("OptionPane.background", MyColors.FONDO2);
			UIManager.put("Panel.background", MyColors.FONDO2);
			int result = JOptionPane.showConfirmDialog(
				null,		// Centra el dialogo
				"Are you sure you want to exit the simulator?",
				"Exit Confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE
			);
			Sound.playSound(_clickSound);
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			
		});
		
		toolBar.add(_exitButton);
	}
	
	private void stopButton(JToolBar toolBar) {
		_stopButton.addActionListener(e -> {
			Sound.playSound(_clickSound);
			_stopped = true;
		});
		toolBar.add(_stopButton);
	}
	
	private void runButton(JToolBar toolBar) {
		_runButton.addActionListener(e -> {
			Sound.playSound(_clickSound);
			_stopped = false;
			disableButtons();
			run_sim((Integer)_ticksSpinner.getValue());
		});
		
		toolBar.add(_runButton);
	}
	
	private void ticksLabel(JToolBar toolBar) {
		JLabel ticksLabel = new JLabel("Ticks: ");
		ticksLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		ticksLabel.setForeground(MyColors.TEXTO);
		
		toolBar.add(ticksLabel);
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
				if(SimSoundObserver.DO_TIME_SLEEP) Thread.sleep(SimSoundObserver.TIME_SLEEP); // Para hacer pruebas FIXME
	         	SwingUtilities.invokeLater(() -> run_sim(n - 1));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "[ERROR] An error occurred during simulation", "Simulation Error", JOptionPane.ERROR_MESSAGE);
				_stopped = true;
				enableButtons();
			}
		} else {
			_stopped = true;
			enableButtons();
		}
	}
	
	private void enableButtons() {
		_loadEventsButton.setEnabled(true);
	    _changeCO2Button.setEnabled(true);
	    _changeWeatherButton.setEnabled(true);
	    _runButton.setEnabled(true);
	    _exitButton.setEnabled(true);
	    _ticksSpinner.setEnabled(true);
	}
	
	private void disableButtons() {
		_loadEventsButton.setEnabled(false);
		_changeCO2Button.setEnabled(false);
		_changeWeatherButton.setEnabled(false);
		_runButton.setEnabled(false);
		_exitButton.setEnabled(false);
		_ticksSpinner.setEnabled(false);
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {}

}

/* TODO: borrar initGUI provisional
 * initgui para poner botones sin accion, para porbar la estructura, borrar cuando se haya terminado la practica
private void initGUI() {
	this.setLayout(new BorderLayout());
	JToolBar toolBar = new JToolBar();
	toolBar.setFloatable(true);
	

    _loadEventsButton = createButton("resources/icons/open.png");
    _changeCO2Button = createButton("resources/icons/co2class.png");
    _changeWeatherButton = createButton("resources/icons/weather.png");
    _runButton = createButton("resources/icons/run.png");
    _stopButton = createButton("resources/icons/stop.png");
    _ticksSpinner = createSpinner();
    _exitButton = createButton("resources/icons/exit.png");
    
    toolBar.add(_loadEventsButton);
    toolBar.addSeparator(new Dimension(12, 36));
	toolBar.add(_changeCO2Button);
	toolBar.add(_changeWeatherButton);
	toolBar.addSeparator(new Dimension(12, 36));
	toolBar.add(_runButton);
	toolBar.add(_stopButton);
	toolBar.addSeparator(new Dimension(4, 36));
	toolBar.add(new JLabel("Ticks: "));
	toolBar.add(_ticksSpinner);
	toolBar.add(Box.createHorizontalGlue()); // Empuja el botón a la derecha
	toolBar.add(_exitButton);
    
    this.add(toolBar, BorderLayout.NORTH);

}*/
