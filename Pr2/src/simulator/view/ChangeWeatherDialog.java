package simulator.view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import simulator.misc.Pair;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;



public class ChangeWeatherDialog extends JDialog implements TrafficSimObserver {
	
	private static final long serialVersionUID = 6L;
	
	private JComboBox<String> _roadsComboBox;
	private JComboBox<String> _weatherComboBox;
	private JSpinner _ticksSpinner;
	private Controller _ctrl;
	
	public ChangeWeatherDialog(Controller ctrl, ControlPanel controlPanel) {
		super(null, "Change CO2 Class", ModalityType.DOCUMENT_MODAL);
		_ctrl = ctrl;
		_roadsComboBox = new JComboBox<>();
		_weatherComboBox = createWeatherBox();
		_ticksSpinner = createSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		
		initGUI();
		pack();
		setLocationRelativeTo(null);
		
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		// Descripcion
		JPanel msgPanel = new JPanel();
		
		msgPanel.setLayout(new BoxLayout(msgPanel, BoxLayout.Y_AXIS));
		msgPanel.add(createLabel("Schedule an event to change the weather of a road after a given number "));
		msgPanel.add(createLabel("of simulation ticks from now."));
		
		msgPanel.setBackground(MyColors.GRIS_CLARO);
		mainPanel.add(msgPanel, BorderLayout.NORTH);
		
		// Panel con cosas
		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		spinnerPanel.add(createLabel("Road:"));
		spinnerPanel.add(_roadsComboBox);
		
		spinnerPanel.add(createLabel("Weather:"));
		spinnerPanel.add(_weatherComboBox);
		
		spinnerPanel.add(createLabel("Ticks:"));
		spinnerPanel.add(_ticksSpinner);
		
		spinnerPanel.setBackground(MyColors.GRIS_CLARO);
		mainPanel.add(spinnerPanel, BorderLayout.CENTER);
		
		// cancel - ok
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JButton cancelButton = createButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		
		JButton okButton = createButton("OK");
		okButton(okButton);
		
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(Box.createHorizontalStrut(15));
		buttonsPanel.add(okButton);

		buttonsPanel.setBackground(MyColors.GRIS_CLARO);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);		
	}
	
	private JButton createButton(String buttonName) {
		JButton button = new JButton(buttonName);
		
	    button.setBackground(MyColors.AZUL);
	    button.setForeground(MyColors.BLANCO);
	    button.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		return button;
	}
	
	private JLabel createLabel(String buttonName) {
		JLabel label = new JLabel(buttonName);
		
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setForeground(MyColors.BLANCO);
		
		return label;
	}
	
	private JSpinner createSpinner(SpinnerNumberModel spinnerNM) {
		JSpinner spinner = new JSpinner(spinnerNM);
 
		JFormattedTextField txt = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		txt.setBackground(MyColors.AZUL);
		txt.setForeground(MyColors.BLANCO);
		txt.setFont(new Font("Tahoma", Font.BOLD, 12));

		return spinner;
	}
	
	private JComboBox<String> createWeatherBox() {
		JComboBox<String> weatherBox = new JComboBox<>();;
		
		for (Weather w: Weather.values()) {
			weatherBox.addItem(w.toString());
		}
		
		return weatherBox;
	}

	private void okButton(JButton okButton) {
		okButton.addActionListener(e -> {
			try {
				String roadId = (String)_roadsComboBox.getSelectedItem();				
				Weather weather = Weather.valueOf((String)_weatherComboBox.getSelectedItem());
				int ticks = (Integer)_ticksSpinner.getValue();
				
				if (roadId == null) {
					JOptionPane.showMessageDialog(null, "Please select a road", "Input Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				List<Pair<String, Weather>> cs = new ArrayList<>();
				cs.add(new Pair<>(roadId, weather));
				
				SetWeatherEvent weatherE = new SetWeatherEvent(_ctrl.getCurrentTime() + ticks, cs);
				_ctrl.addEvent(weatherE);

				dispose();
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(
						this,
						"[ERROR] Failed to create Weather change event",
						"Error",
						JOptionPane.ERROR_MESSAGE
					);
			}
		});
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		_roadsComboBox.removeAllItems();
		for (Road r: map.getRoads()) {
			_roadsComboBox.addItem(r.getId());
		}
	}
}
