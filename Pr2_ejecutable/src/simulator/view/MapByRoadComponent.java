package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	
	private static final long serialVersionUID = 4L;
	
	private static final int _JRADIUS = 10;
	
	private RoadMap _map;
	private Image _car;
	private Map<Weather, Image> _weatherIcons;
	private Image[] _co2Icons;
	
	MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		_car = loadImage("car.png");
		
		_weatherIcons = new HashMap<>();
		_weatherIcons.put(Weather.SUNNY, loadImage("sun.png"));
		_weatherIcons.put(Weather.CLOUDY, loadImage("cloud.png"));
		_weatherIcons.put(Weather.RAINY, loadImage("rain.png"));
		_weatherIcons.put(Weather.WINDY, loadImage("wind.png"));
		_weatherIcons.put(Weather.STORM, loadImage("storm.png"));
		
		_co2Icons = new Image[6];
		for(int i = 0; i < _co2Icons.length; i++) {		//es lo mimso que poner un 6 -> buenas prácticas .lenght 
			_co2Icons[i] = loadImage("cont_" + i + ".png");
		}
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.setColor(MyColors.FONDO1);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(MyColors.RED);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMapByRoad(g);
		}
	}

	private void drawMapByRoad(Graphics g) {
		for(int i = 0; i < _map.getRoads().size(); i++) {
			int x1 = 50;
			int x2 = getWidth() - 100;
			int y = (i + 1) * 50;
			
			Road r = _map.getRoads().get(i);
			Junction src = r.getSrc();
			Junction dest = r.getDest();
			
			drawRoad(g, x1, x2, y, r);						// Road
			drawJunction(g, x1, x2, y, r, src, dest);		// Junction

			for(int j = 0; j < r.getVehicles().size(); j++) {
				Vehicle v = r.getVehicles().get(j);
				int A = v.getLocation();
				int B = r.getLength();
				int x = x1 + (int) ((x2 - x1) * ((double)A / (double)B));
				
				drawVehicle(g, x, y, v);					// Vehicle
			}
		}
	}
	
	private void drawRoad(Graphics g, int x1, int x2, int y, Road r) {
		g.setColor(MyColors.GRAY);
		g.drawLine(x1, y, x2, y);
		drawId(g, MyColors.BLACK, r.getId(), x1 - 25 , y + 3);
		
		Image weather = _weatherIcons.get(r.getWeather());
		g.drawImage(weather, x2 + 10, y - 16, 32, 32, this);
		
		int A = r.getTotalCO2();
		int B = r.getContLimit();
		
		int c = (int) Math.floor(Math.min((double)A / (1.0 + (double)B), 1.0) / 0.19);
		Image co2 = _co2Icons[c];
		g.drawImage(co2, x2 + 50, y - 16, 32, 32, this);
	}
	
	private void drawJunction(Graphics g, int x1, int x2, int y, Road r, Junction src, Junction dest){
		// Cruce origin
		g.setColor(MyColors.JUNCTION_COLOR);
		g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		drawId(g, MyColors.JUNCTION_LABEL_COLOR, src.getId(), x1 - 5 , y + 20);

		// Cruce dest
		int idx = dest.getGreenLightIndex();
		boolean green = isGreen(idx, r, dest);
		g.setColor(green ? MyColors.GREEN: MyColors.RED);
		g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		drawId(g, MyColors.JUNCTION_LABEL_COLOR, dest.getId(), x2 - 5 , y + 20);
	}
	
	private void drawVehicle(Graphics g, int x, int y, Vehicle v) {
		int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
		g.setColor(new Color(0, vLabelColor, 0));
		
		g.drawImage(_car, x, y - 11, 16, 16, this);
		drawId(g, new Color(0, vLabelColor, 0), v.getId(), x + 1, y - 15);
	}
	
	private void drawId(Graphics g, Color color, String id, int x, int y) {
		g.setFont(new Font("Tahoma", Font.BOLD, 10));
		g.setColor(color);
		g.drawString(id, x , y);
	}
	
	private boolean isGreen(int idx, Road r, Junction dest) {
		if (idx != -1) {
			Road greenRoad = dest.getInRoads().get(idx);
			return r.equals(greenRoad);
		}
		return false;
	}
	
	private void updatePrefferedSize() {
		int numRoads = _map.getRoads().size();	// num Carreteras
		int height = (numRoads + 1) * 50; 		// 50 pixeles para cada carretera (con 1 extra como margen)
		// Si no hay mapa -> anchura 400 -> alienado con mapComponent
		// Si hay mapa -> anchura w-10 -> para el scrollBar vertical
		int width = _map.getRoads().isEmpty() ? 220 : getWidth() - 1;
		
		setPreferredSize(new Dimension(width, height));
		setSize(new Dimension(width, height));
	}

	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(getClass().getResourceAsStream("/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			updatePrefferedSize();
			repaint();
		});
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}
	
}
