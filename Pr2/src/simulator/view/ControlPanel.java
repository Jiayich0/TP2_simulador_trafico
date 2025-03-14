package simulator.view;

import java.io.FileInputStream;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 3L;
	
	private Controller _controller;
	
	ControlPanel(Controller controller) {
		_controller = controller;
	}
	
	private void initGUI() {
		JButton openFileButton = new JButton("Loads Event");
		openFileButton.addActionListener(e -> loadFile()); // Asigna la acción al botón
		this.add(openFileButton);
	}
	
	
	private void loadFile() {
		JFileChooser fileChooser = new JFileChooser();
		int selection = fileChooser.showOpenDialog(this);
		
		if (selection == JFileChooser.APPROVE_OPTION) {
			try {
	            _controller.reset();
	            _controller.loadEvents(new FileInputStream(fileChooser.getSelectedFile()));
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, "Error loading events", "Error", JOptionPane.ERROR_MESSAGE);
	        }
		}
	}

}
