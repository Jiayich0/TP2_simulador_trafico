package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Road;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 10L;
	
	private Controller _ctrl;
	private List<Road> _roads = new ArrayList<>();
	private final String[] _colNames = {"Id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	RoadsTableModel(Controller ctrl){
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _roads.size();
	}

	@Override
	public int getColumnCount() { 
		return _colNames.length;
	}
	
	public String getColumnName(int column) {
	    return _colNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Road r = _roads.get(rowIndex);
		
		switch(columnIndex) {
			case 0: return r.getId();
			case 1: return r.getLength();
			case 2: return r.getWeather().toString();
			case 3: return r.getMaxSpeed();
			case 4: return r.getSpeedLimit();
			case 5: return r.getTotalCO2();
			case 6: return r.getContLimit();
			default: return null;
		}
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		onUpdate(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		onUpdate(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		onUpdate(map);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		onUpdate(map);
	}
	
	private void onUpdate(RoadMap map) {
		_roads = map.getRoads();
		fireTableStructureChanged();
	}
}
