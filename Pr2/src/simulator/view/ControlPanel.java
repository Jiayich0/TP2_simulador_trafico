package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.FileInputStream;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 3L;
	
	private Controller _controller;
	private JButton _loadEventsButton;
	private JButton _changeCO2Button;
	private JButton _changeWeatherButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JSpinner _ticksSpinner;
	private JButton _exitButton;

	
	ControlPanel(Controller ctrl) {
		_controller = ctrl;
		initGUI();
		_controller.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);
		initializeAllButtons();
		
        
        loadEventsButton(toolBar);
        toolBar.addSeparator(new Dimension(12, 36));
        changeCO2Button(toolBar);
        //changeWeatherButton(toolBar);
		toolBar.addSeparator(new Dimension(12, 36));
		toolBar.add(_runButton);
		toolBar.add(_stopButton);
		toolBar.addSeparator(new Dimension(4, 36));
		toolBar.add(new JLabel("Ticks: "));
		toolBar.add(_ticksSpinner);
		toolBar.add(Box.createHorizontalGlue()); // Empuja el botón a la derecha
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
	    
	    // Para que los iconos no se vean tan pixelados (y poder escalar) 
	    ImageIcon icon = new ImageIcon(iconPath);
	    Image img = icon.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
	    button.setIcon(new ImageIcon(img));
	    
	    button.setIcon(new ImageIcon(iconPath));
	    
	    button.setBorderPainted(true);		// Quita el borde negro 
	    button.setFocusPainted(false);		// Quita el recuadro de la imagen al ser seleccionado
	    button.setContentAreaFilled(true);	// Color de fondo
	    button.setBackground(Color.MAGENTA);
	    
	    return button;
	}
	 
	private JSpinner createSpinner() {
		 // valor incial, minimo, maximo, paso(cuantos ticks aumentas/disminuyes por click)
		 SpinnerNumberModel model = new SpinnerNumberModel(10, 1, 10000, 1);
		 JSpinner spinner = new JSpinner(model);
		 spinner.setMaximumSize(new Dimension(60, 40));
		 
		 return spinner;
	}
	
	
	private void loadEventsButton(JToolBar toolBar) {
		JFileChooser fileChooser = new JFileChooser();
		
		_loadEventsButton.addActionListener(e -> {
			int select = fileChooser.showOpenDialog(null);
			
			if (select == JFileChooser.APPROVE_OPTION) {
				if (fileChooser.getSelectedFile() != null) {
					try (FileInputStream fileInput = new FileInputStream(fileChooser.getSelectedFile())) {
						_controller.reset();
				        _controller.loadEvents(fileInput);
					} catch (Exception except) {
			            JOptionPane.showMessageDialog(this, "[Error: Loading events file]", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
	                JOptionPane.showMessageDialog(this, "[Warning: No file selected]", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		toolBar.add(_loadEventsButton);
	}
	
	private void changeCO2Button(JToolBar toolBar) {
		_changeCO2Button.addActionListener(e -> {
			ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog(_controller, this);
	        dialog.setVisible(true);
		});
		toolBar.add(_changeCO2Button);
	}
	
	/*private void changeWeatherButton(JToolBar toolBar) {
		_changeWeatherButton.addActionListener(e -> {
			ChangeWeatherDialog dialog = new ChangeWeatherDialog(_controller, this);
	        dialog.setVisible(true);
		});
	}*/
	
	private void algoButton(JToolBar toolBar) {
		
	}
	
	private void exitButton(JToolBar toolBar) {
		_exitButton.addActionListener(e -> {
			int result = JOptionPane.showConfirmDialog(
				null,		// Centra el dialogo
				"Are you sure you want to exit the simulator?",
				"Exit Confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE
			);
			
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}			
		});
		
		toolBar.add(_exitButton);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
	}

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
