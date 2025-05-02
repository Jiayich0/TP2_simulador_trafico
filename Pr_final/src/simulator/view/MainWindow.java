package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

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
		// En vez de un BoxLayout un GridLayout para manejar directamente que sean 4 partes iguales
		JPanel tablesPanel = new JPanel(new GridLayout(4, 1));
		viewsPanel.add(tablesPanel);
		
		// Se crea dentor de viewsPanel el panel de los mapas (derecha)
		// Igual que tablesPanel pero en dos partes
		JPanel mapsPanel = new JPanel(new GridLayout(2, 1));
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
			tablesPanel.add(view);
		}


		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapsPanel.add(mapView);
		
		JPanel mapRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		mapsPanel.add(mapRoadView);
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		addBorder(p, title);
		
		// Forzar a que no haya horizontal scroll en los componentes que no sean MapComponent
		JScrollPane scroll;
		if (c instanceof MapComponent) {
		    scroll = new JScrollPane(c, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		} else {
		    scroll = new JScrollPane(c, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		scroll.getViewport().setBackground(MyColors.FONDO1);
		p.add(scroll);
		return p;
	}
	
	private void addBorder(JPanel p, String title) {
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MyColors.FONDO2, 2), title);
		border.setTitleFont(new Font("Tahoma", Font.BOLD, 12));
		border.setTitleColor(MyColors.FONDO2);
		p.setBorder(border);
	}
	
	private void defaultColors() {
		UIManager.put("OptionPane.background", MyColors.FONDO1);
		UIManager.put("Panel.background", MyColors.FONDO1);
		UIManager.put("OptionPane.messageForeground", MyColors.TEXTO);
		
		UIManager.put("Button.background", MyColors.ELEMENTOS);
		UIManager.put("Button.foreground", MyColors.TEXTO);
		UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 13));
	}
	
	private void setTableConfig(JTable table) {
		table.getTableHeader().setBackground(MyColors.ELEMENTOS);
		table.getTableHeader().setForeground(MyColors.TEXTO);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
		
		table.setBackground(MyColors.FONDO2);
		table.setForeground(MyColors.TEXTO);
		table.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		table.getTableHeader().setReorderingAllowed(true);		// Permitir o no mover el Header/Título
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
