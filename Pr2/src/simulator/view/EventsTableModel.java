package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 8L;
	
	private Controller _ctrl;
	private List<Event> _events = new ArrayList<>();
	
	EventsTableModel(Controller ctrl){
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}
	
	public String getColumnName(int column) {
	    return column == 0 ? "Time" : "Desc.";
	}
	
	@Override
	public int getRowCount() {
		return _events.size();
	}

	@Override
	public int getColumnCount() { 
		return 2; 
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	    Event e = _events.get(rowIndex);
	    if (columnIndex == 0) return e.getTime();
	    else return e.toString();
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		onUpdate(events);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		onUpdate(events);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		onUpdate(events);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		onUpdate(events);
	}
	
	private void onUpdate(Collection<Event> events) {
		_events = new ArrayList<>(events);;
		fireTableStructureChanged();
	}
}
