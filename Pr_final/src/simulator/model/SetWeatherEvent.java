package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	private List<Pair<String, Weather>> _ws;
	
	
	
	public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
		super(time);
		
		if(ws == null) {
			throw new IllegalArgumentException("[E] La lista de clima no puede ser nula");
		}
		_ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		for(Pair<String, Weather> w : _ws) {
			Road r = map.getRoad(w.getFirst());
			if (r == null) {
			    throw new IllegalArgumentException("[E] La carretera " + w.getFirst() + " no existe en el mapa de carreteras");
			}
			r.setWeather(w.getSecond());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Change Weather: [");
		Pair<String, Weather> p;
		for (int i = 0; i < _ws.size(); i++) {
			p = _ws.get(i);
			sb.append("(").append(p.getFirst()).append(",").append(p.getSecond()).append(")");
			if (i < _ws.size() - 1) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
}