package simulator.view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import simulator.misc.Pair;
import simulator.control.Controller;
import simulator.model.SetContClassEvent;



public class ChangeCO2ClassDialog extends JDialog {
	
	private static final long serialVersionUID = 10L;
	
	private JComboBox<String> _vehiclesComboBox;
	private JSpinner _co2ClassSpinner;
	private JSpinner _ticksSpinner;
	private Controller _ctrl;
	
	public ChangeCO2ClassDialog(Controller ctrl, ControlPanel controlPanel) {
		super(null, "Change CO2 Class", ModalityType.DOCUMENT_MODAL); // Document modal bloquea ventana padre y sus hijos
		// pasarle null para que lo centre en el medio y no respecto al 'parent'
		_ctrl = ctrl;
		_vehiclesComboBox = new JComboBox<>();
		_co2ClassSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		_ticksSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		
		initGUI();
		pack();
		setLocationRelativeTo(null);
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		// Descripcion
		JPanel msgPanel = new JPanel();
		
		msgPanel.setLayout(new BoxLayout(msgPanel, BoxLayout.Y_AXIS));
		msgPanel.add(new JLabel("Schedule an event to change the CO2 class of a vehicle after a given     "));
		msgPanel.add(new JLabel("number of simulation ticks from now."));
		
		mainPanel.add(msgPanel, BorderLayout.NORTH);
		
		// Panel con cosas
		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		spinnerPanel.add(new JLabel("Vehicle:"));
		spinnerPanel.add(_vehiclesComboBox);
		
		spinnerPanel.add(new JLabel("CO2 Class:"));
		spinnerPanel.add(_co2ClassSpinner);
		
		spinnerPanel.add(new JLabel("Ticks:"));
		spinnerPanel.add(_ticksSpinner);
		
		mainPanel.add(spinnerPanel, BorderLayout.CENTER);
		
		// cancel - ok
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		JButton okButton = new JButton("OK");
		okButtonAction(okButton);
		
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(Box.createHorizontalStrut(15));
		buttonsPanel.add(okButton);

		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);		
	}
	
	private void okButtonAction(JButton okButton) {
		okButton.addActionListener(e -> {
			try {
				String vehicleId = (String)_vehiclesComboBox.getSelectedItem();
				int co2Class = (Integer)_co2ClassSpinner.getValue();
				int ticks = (Integer)_ticksSpinner.getValue();
				
				List<Pair<String, Integer>> cs = new ArrayList<>();
				cs.add(new Pair<>(vehicleId, co2Class));

				SetContClassEvent e1 = new SetContClassEvent(_ctrl.getCurrentTime() + ticks, cs));
				_ctrl.addEvent(e1);

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
}