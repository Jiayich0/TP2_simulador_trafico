package simulator.view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import simulator.misc.Pair;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;



public class ChangeCO2ClassDialog extends JDialog implements TrafficSimObserver {
	
	private static final long serialVersionUID = 5L;
	
	private JComboBox<String> _vehiclesComboBox;
	private JSpinner _co2ClassSpinner;
	private JSpinner _ticksSpinner;
	private Controller _ctrl;
	
	public ChangeCO2ClassDialog(Controller ctrl, ControlPanel controlPanel) {
		super(null, "Change CO2 Class", ModalityType.DOCUMENT_MODAL); // Document modal bloquea ventana padre y sus hijos
		// pasarle null para que lo centre en el medio y no respecto al 'parent'
		_ctrl = ctrl;
		_vehiclesComboBox = new JComboBox<>();
		_co2ClassSpinner = createSpinner(new SpinnerNumberModel(0, 0, 10, 1));
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
		msgPanel.add(createLabel("Schedule an event to change the CO2 class of a vehicle after a given     "));
		msgPanel.add(createLabel("number of simulation ticks from now."));
		
		msgPanel.setBackground(MyColors.FONDO2);
		mainPanel.add(msgPanel, BorderLayout.NORTH);
		
		// Panel con cosas
		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		spinnerPanel.add(createLabel("Vehicle:"));
		spinnerPanel.add(_vehiclesComboBox);
		
		spinnerPanel.add(createLabel("CO2 Class:"));
		spinnerPanel.add(_co2ClassSpinner);
		
		spinnerPanel.add(createLabel("Ticks:"));
		spinnerPanel.add(_ticksSpinner);
		
		spinnerPanel.setBackground(MyColors.FONDO2);
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

		buttonsPanel.setBackground(MyColors.FONDO2);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);		
	}
	
	private JButton createButton(String buttonName) {
		JButton button = new JButton(buttonName);
		
	    button.setBackground(MyColors.ELEMENTOS);
	    button.setForeground(MyColors.TEXTO);
	    button.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		return button;
	}
	
	private JLabel createLabel(String buttonName) {
		JLabel label = new JLabel(buttonName);
		
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setForeground(MyColors.TEXTO);
		
		return label;
	}
	
	private JSpinner createSpinner(SpinnerNumberModel spinnerNM) {
		JSpinner spinner = new JSpinner(spinnerNM);
 
		JFormattedTextField txt = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		txt.setBackground(MyColors.ELEMENTOS);
		txt.setForeground(MyColors.TEXTO);
		txt.setFont(new Font("Tahoma", Font.BOLD, 12));

		return spinner;
	}
	
	private void okButton(JButton okButton) {
		okButton.addActionListener(e -> {
			try {
				String vehicleId = (String)_vehiclesComboBox.getSelectedItem();				
				int co2Class = (Integer)_co2ClassSpinner.getValue();
				int ticks = (Integer)_ticksSpinner.getValue();

				if (vehicleId == null) {
					JOptionPane.showMessageDialog(null, "Please select a vehicle", "Input Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				List<Pair<String, Integer>> cs = new ArrayList<>();
				cs.add(new Pair<>(vehicleId, co2Class));
				
				SetContClassEvent contClassE = new SetContClassEvent(_ctrl.getCurrentTime() + ticks, cs);
				_ctrl.addEvent(contClassE);
				
				dispose();
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(
						this,
						"[ERROR] Failed to create CO2 change event",
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
		_vehiclesComboBox.removeAllItems();
		for (Vehicle v: map.getVehicles()) {
			_vehiclesComboBox.addItem(v.getId());
		}
		// FIXME: borrar despues de probar
		_vehiclesComboBox.addItem("v1");
		_vehiclesComboBox.addItem("v2");
		_vehiclesComboBox.addItem("v3");
	}
}