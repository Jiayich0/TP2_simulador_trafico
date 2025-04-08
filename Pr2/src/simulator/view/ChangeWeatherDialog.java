package simulator.view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import simulator.misc.Pair;
import simulator.control.Controller;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;



public class ChangeWeatherDialog extends JDialog {
	
	private static final long serialVersionUID = 6L;
	
	private JComboBox<String> _roadsComboBox;
	private JComboBox<String> _weatherComboBox;
	private JSpinner _ticksSpinner;
	private Controller _ctrl;
	
	public ChangeWeatherDialog(Controller ctrl, ControlPanel controlPanel) {
		super(null, "Change CO2 Class", ModalityType.DOCUMENT_MODAL);
		_ctrl = ctrl;
		_roadsComboBox = new JComboBox<>();
		_weatherComboBox = new JComboBox<>();
		_ticksSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		
		initGUI();
		pack();
		setLocationRelativeTo(null);
		
		// TODO como el changeco2classdialog y tambien hacer el de  weather
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		// Descripcion
		JPanel msgPanel = new JPanel();
		
		msgPanel.setLayout(new BoxLayout(msgPanel, BoxLayout.Y_AXIS));
		msgPanel.add(new JLabel("Schedule an event to change the weather of a road after a given number "));
		msgPanel.add(new JLabel("of simulation ticks from now."));
		
		mainPanel.add(msgPanel, BorderLayout.NORTH);
		
		// Panel con cosas
		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		spinnerPanel.add(new JLabel("Road:"));
		spinnerPanel.add(_roadsComboBox);
		
		spinnerPanel.add(new JLabel("Weather:"));
		spinnerPanel.add(_weatherComboBox);
		
		spinnerPanel.add(new JLabel("Ticks:"));
		spinnerPanel.add(_ticksSpinner);
		
		mainPanel.add(spinnerPanel, BorderLayout.CENTER);
		
		// cancel - ok
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		JButton okButton = new JButton("OK");
		okButton(okButton);
		
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(Box.createHorizontalStrut(15));
		buttonsPanel.add(okButton);

		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);		
	}
	
	private void okButton(JButton okButton) {
		okButton.addActionListener(e -> {
			try {
				String vehicleId = (String)_roadsComboBox.getSelectedItem();
				Weather weather = (Weather)_weatherComboBox.getSelectedItem();
				int ticks = (Integer)_ticksSpinner.getValue();
				
				List<Pair<String, Weather>> cs = new ArrayList<>();
				cs.add(new Pair<>(vehicleId, weather));
				
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
}
