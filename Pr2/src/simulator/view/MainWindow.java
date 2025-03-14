package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import simulator.control.Controller;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 2L;
	
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
		
		this.setSize(800, 600); // Puedes ajustar el tamaño
	    this.setMinimumSize(new Dimension(600, 400)); // Para evitar que se haga demasiado pequeña
	    this.setLocationRelativeTo(null); 
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		//mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		//mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);

		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);

		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		//tablesPanel.add(createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events"));
	    //tablesPanel.add(createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles"));
	    //tablesPanel.add(createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads"));
	    //tablesPanel.add(createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions"));

		
		// tables
		JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		// TODO add other tables
		// ...

		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		// TODO add a map for MapByRoadComponent
		// ...
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
            // TODO add a framed border to p with title
		p.add(new JScrollPane(c));
		return p;
	}
}