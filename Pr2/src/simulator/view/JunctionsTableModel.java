package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 11L;
	
	private Controller _ctrl;
	
	
	JunctionsTableModel(Controller ctrl){
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _vehicles.size();
	}

	@Override
	public int getColumnCount() { 
		
	}
	
	public String getColumnName(int column) {
	    return column == 0 ? "Time" : "Desc.";
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

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

		fireTableStructureChanged();
	}
}
