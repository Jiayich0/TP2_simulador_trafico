package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetContClassEvent extends Event {

	private List<Pair<String, Integer>> _cs;
	
	
	
	public SetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		
		if(cs == null) {
			throw new IllegalArgumentException("[E] La lista de contaminación no puede ser nula");
		}
		_cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		for(Pair<String, Integer> c : _cs) {
			Vehicle v = map.getVehicle(c.getFirst());
			if (v == null) {
			    throw new IllegalArgumentException("[E] El vehículo " + c.getFirst() + " no existe en el mapa de carreteras");
			}
			v.setContClass(c.getSecond());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Change CO2 class: [");
		Pair<String, Integer> p;
		for (int i = 0; i < _cs.size(); i++) {
			p = _cs.get(i);
			sb.append("(").append(p.getFirst()).append(",").append(p.getSecond()).append(")");
			if (i < _cs.size() - 1) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
}