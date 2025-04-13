package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 9L;
	
	private Controller _ctrl;
	private List<Vehicle> _vehicles = new ArrayList<>();
	private final String[] _colNames = {"Id", "Status", "Itinerary", "CO2 Class", "Max Speed", "Speed", "Total CO2", "Distance"};
	
	VehiclesTableModel(Controller ctrl){
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _vehicles.size();
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
		Vehicle v = _vehicles.get(rowIndex);
		
		switch (columnIndex) {
			case 0: return v.getId();
			case 1: return createStatus(v);
			case 2: return v.getItinerary();
			case 3: return v.getContClass();
			case 4: return v.getMaxSpeed();
			case 5: return v.getSpeed();
			case 6: return v.getTotalCO2();
			case 7: return v.getTotalDistance();
			default: return null;
		}
	}
	
	private String createStatus(Vehicle v) {
		VehicleStatus status = v.getStatus();
		
		switch(status) {
			case PENDING: return "Pending";
			case TRAVELING: return v.getRoad().getId() + ":" + v.getLocation();
			case WAITING: return "Waiting:" + v.getRoad().getDest().getId();
			case ARRIVED: return "Arrived";
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
		_vehicles = map.getVehicles();
		fireTableStructureChanged();
	}
} 