package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 11L;
	
	private Controller _ctrl;
	private List<Junction> _junctions = new ArrayList<>();
	private final String[] _colNames = {"Id", "Green", "Queues"};
	
	JunctionsTableModel(Controller ctrl){
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _junctions.size();
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
		Junction j = _junctions.get(rowIndex);
		
		switch(columnIndex) {
			case 0: return j.getId();
			case 1: return createGreen(j);
			case 2:	return createQueues(j);
			default: return null;
		}
	}
	
	private String createGreen(Junction j) {
		int greenIndex = j.getGreenLightIndex();
		if (greenIndex == -1) return "NONE";
		else return j.getInRoads().get(greenIndex).getId();
	}
	
	private String createQueues(Junction j) {
		StringBuilder sb = new StringBuilder();
		for (Road r : j.getInRoads()) {
			List<Vehicle> vehicles = r.getVehicles();
			sb.append(r.getId()).append(":[");
			for (int i = 0; i < vehicles.size(); i++) {
				sb.append(vehicles.get(i).getId());
				if (i < vehicles.size() - 1) sb.append(",");
			}
			sb.append("] ");
		}
		
		return sb.toString().trim();
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
		_junctions = map.getJunctions();
		fireTableStructureChanged();
	}
}
