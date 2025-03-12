package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import org.json.JSONObject;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	
	private RoadMap _roadMap;
	private Queue<Event> _events;
	private int _time;
	private List<TrafficSimObserver> _observers;
	
	
	public TrafficSimulator() {
		_roadMap = new RoadMap();
		_events = new PriorityQueue<>();
		_time = 0;
		_observers = new ArrayList<>();
	}
	
	
	public void addEvent(Event e) {
		_events.add(e);
		
		for (TrafficSimObserver o : _observers) {
			o.onEventAdded(_roadMap, _events, e, _time);
		}
	}
	
	public void advance() {
		_time++;
		
		// poll(): "remove and return the head of the queue"
		// peek(): devuelve el primer elemento de la cola sin eliminarlo
		while (!_events.isEmpty() && _events.peek().getTime() == _time) {
			Event e = _events.poll();						
			e.execute(_roadMap);
		}
		
		for(Junction j : _roadMap.getJunctions()) {
			j.advance(_time);
		}
		
		for(Road r : _roadMap.getRoads()) {
			r.advance(_time);
		}
		
		for (TrafficSimObserver o : _observers) {
			o.onAdvance(_roadMap, _events, _time);
		}
	}
	
	public void reset() {
		_roadMap.reset();
		_events.clear();
		_time = 0;
		
		for (TrafficSimObserver o : _observers) {
			o.onReset(_roadMap, _events, _time);
		}
	}
	
	public JSONObject report() {
		JSONObject js = new JSONObject();
		js.put("time", _time);
		js.put("state", _roadMap.report());
		
		return js;
	}

	// Interfaz
	@Override
	public void addObserver(TrafficSimObserver o) {
		_observers.add(o);
		o.onRegister(_roadMap, _events, _time);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		_observers.remove(o);		
	}
}
