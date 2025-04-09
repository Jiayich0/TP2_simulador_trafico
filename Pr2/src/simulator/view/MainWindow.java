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
		
		// Se crea un panel principal
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
		//JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		//eventsView.setPreferredSize(new Dimension(500, 200));
		//tablesPanel.add(eventsView);
		// TODO add other tables
		// ...
		tablesPanel.add(createViewPanel(new JTable(), "Events"));
	    tablesPanel.add(createViewPanel(new JTable(), "Vehicles"));
	    tablesPanel.add(createViewPanel(new JTable(), "Roads"));
	    tablesPanel.add(createViewPanel(new JTable(), "Junctions"));

		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		JPanel mapRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map");
		mapsPanel.add(mapRoadView);
		
		
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
	
	private void defaultColors() {
		UIManager.put("OptionPane.background", MyColors.GRIS_CLARO);
		UIManager.put("Panel.background", MyColors.GRIS_CLARO);
		UIManager.put("OptionPane.messageForeground", MyColors.BLANCO);
		
		UIManager.put("Button.background", MyColors.AZUL);
		UIManager.put("Button.foreground", MyColors.BLANCO);
		UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 13));
	}
}