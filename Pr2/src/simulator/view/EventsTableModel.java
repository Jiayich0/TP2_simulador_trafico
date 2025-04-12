package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.TrafficSimObserver;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;


public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private static final long serialVersionUID = 8L;
	
	private Controller _ctrl;
	private List<Event> _events = new ArrayList<>();
	
	EventsTableModel(Controller ctrl){
		_ctrl = ctrl;
		_ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _events.size();
	}

	@Override
	public int getColumnCount() { 
		return 2; 
	}
	
	public String getColumnName(int column) {
	    return column == 0 ? "Time" : "Desc.";
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
		//_events = new ArrayList<>(events);;
		// TODO
		// Imprime todos los eventos, en cambio, en el ejemplo de la demo solo muestra de los changeCO2 
		// y changeWeather events. Preguntar al profe si tiene o no que imprimir todos. Tambien
		// preguntar si se puede usar instanceof, a diferencia de TP1

		
		_events = new ArrayList<>();;
		
		for (Event e : events) {
	        if (e instanceof SetWeatherEvent || e instanceof SetContClassEvent) {
	            _events.add(e);
	        }
	    }
		
		fireTableStructureChanged();
	}
}
