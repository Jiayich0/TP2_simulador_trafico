package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import simulator.control.Controller;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 2L;
	
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
		
		this.setSize(800, 600);
	    this.setMinimumSize(new Dimension(600, 400));
	    this.setLocationRelativeTo(null); 
	}

	private void initGUI() {
		defaultColors();
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// Se añade arriba el panel de control -> ControlPanel
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		
		// Se añade abajo el panel de estados -> StatusBar
		mainPanel.add(new StatusBar(_ctrl), BorderLayout.PAGE_END);
		
		// Se crea en el centro un panel para las tablas y mapas
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		// Se crea dentro de viewsPanel el panel de las tablas (izquierda)
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		// Se crea dentor de viewsPanel el panel de los mapas (derecha)
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		
		// tables
		String[] titles = { "Events", "Vehicles", "Roads", "Junctions" };
		JTable[] tables = {
			new JTable(new EventsTableModel(_ctrl)),
			new JTable(new VehiclesTableModel(_ctrl)),
			new JTable(new RoadsTableModel(_ctrl)),
			new JTable(new JunctionsTableModel(_ctrl))
		};
		
		for (int i = 0; i < tables.length; i++) {
			setTableConfig(tables[i]);
			JPanel view = createViewPanel(tables[i], titles[i]);
			view.setPreferredSize(new Dimension(500, 200));
			tablesPanel.add(view);
		}


		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		JPanel mapRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapRoadView);
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
            // TODO add a framed border to p with title
		JScrollPane scroll = new JScrollPane(c);
		scroll.getViewport().setBackground(MyColors.GRIS_CLARO);
		p.add(scroll);
		return p;
	}
	
	private void defaultColors() {
		UIManager.put("OptionPane.background", MyColors.GRIS_CLARO);
		UIManager.put("Panel.background", MyColors.GRIS_CLARO);
		UIManager.put("OptionPane.messageForeground", MyColors.BLANCO);
		
		UIManager.put("Button.background", MyColors.AZUL);
		UIManager.put("Button.foreground", MyColors.BLANCO);
		UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 13));
	}
	
	private void setTableConfig(JTable table) {
		table.getTableHeader().setBackground(MyColors.AZUL);
		table.getTableHeader().setForeground(MyColors.BLANCO);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
		
		table.setBackground(MyColors.GRIS_CLARO);
		table.setForeground(MyColors.BLANCO);
		table.setFont(new Font("Tahoma", Font.BOLD, 11));
	}
}


/*		FIXMe - Demasiado texto -> sustituido por bucle -> borrar más tarde (aún útil para probar las tablas a medida que se vayan implementando

 		JTable eventsTable = new JTable(new EventsTableModel(_ctrl));
		JTable vehiclesTable = new JTable(new VehiclesTableModel(_ctrl));
		JTable roadsTable = new JTable(new RoadsTableModel(_ctrl));
		JTable junctionsTable = new JTable(new JunctionsTableModel(_ctrl));
		
		setTableConfig(eventsTable);
		setTableConfig(vehiclesTable);
		setTableConfig(roadsTable);
		setTableConfig(junctionsTable);
		
		JPanel eventsView = createViewPanel(eventsTable, "Events");
		JPanel vehiclesView = createViewPanel(vehiclesTable, "Vehicles");
		JPanel roadsView = createViewPanel(roadsTable, "Roads");
		JPanel junctionsView = createViewPanel(junctionsTable, "Junctions");
		
		eventsView.setPreferredSize(new Dimension(500, 200));
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		roadsView.setPreferredSize(new Dimension(500, 200));
		junctionsView.setPreferredSize(new Dimension(500, 200));
		
		tablesPanel.add(eventsView);
		tablesPanel.add(vehiclesView);
		tablesPanel.add(roadsView);
		tablesPanel.add(junctionsView);
 */
