package simulator.model;

import java.util.PriorityQueue;
import java.util.Queue;
import org.json.JSONObject;

public class TrafficSimulator {
	
	private RoadMap _roadMap;
	Queue<Event> _events;
	int _time;
	
	
	
	public TrafficSimulator() {
		_roadMap = new RoadMap();
		_events = new PriorityQueue<>();
		_time = 0;
	}
	
	
	public void addEvent(Event e) {
		_events.add(e);
	}
	
	public void advance() {
		_time++;
		
		/* FIXME - dos alternativas: elegir uno
		funcionar funciona, habría que añadirle que se elimine una vez extraída, pero teniendo la Estructura de Datos Queue...
		Si está bien el while, borrar este. Conusltar con el profe
		for(Event e : _events) {
			if(e.getTime() == _time) {
				e.execute(_roadMap);
			}
		}
		*/
		
		// Oracle.com "poll() methods remove and return the head of the queue"
		while (!_events.isEmpty() && _events.peek().getTime() == _time) {
			Event e = _events.poll();						
			e.execute(_roadMap);
		}
		/**
		 * RECORDATORIO:
		 * 	1. No puedes hacer e.getTime() == _time antes del _events.poll() porque no está creada e
		 * 	2. No puedes comparar e.getTime() == _time después de hacerlo porque ya estaría extraído y eliminado sin pasar por el if
		 * 	   Solución -> _events.peek()
		 * 		peek(): devuelve el primer elemento de la cola sin eliminarlo
		 */
		
		for(Junction j : _roadMap.getJunctions()) {
			j.advance(_time);
		}
		
		for(Road r : _roadMap.getRoads()) {
			r.advance(_time);
		}
	}
	
	public void reset() {
		_roadMap.reset();
		_events.clear();
		_time = 0;
	}
	
	public JSONObject report() {
		JSONObject js = new JSONObject();
		js.put("time", _time);
		js.put("state", _roadMap.report());
		
		return js;
	}
}
