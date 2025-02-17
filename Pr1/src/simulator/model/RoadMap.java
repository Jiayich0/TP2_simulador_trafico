package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {
	private List<Junction> _junctions;
	private List<Road> _roads;
	private List<Vehicle> _vehicles;
	
	private Map<String, Junction> _junctionMap;
	private Map<String, Road> _roadMap;
	private Map<String, Vehicle> _vehicleMap;
	
	RoadMap() {
        _junctions = new ArrayList<>();
        _roads = new ArrayList<>();
        _vehicles = new ArrayList<>();

        _junctionMap = new HashMap<>();
        _roadMap = new HashMap<>();
        _vehicleMap = new HashMap<>();
	}
	
	void addJunction(Junction j) {
		if (_junctionMap.containsKey(j.getId())) {
			throw new IllegalArgumentException("Ya existe un cruce con el identificador: " + j.getId());
		}
		
		_junctions.add(j);
		_junctionMap.put(j.getId(), j);
	}
	
	void addRoad(Road r) {
		if (_roadMap.containsKey(r.getId())) {
			throw new IllegalArgumentException("Ya existe una carretera con el identificador: " + r.getId());
		}
		if (!_junctionMap.containsKey(r.getSrc().getId()) || !_junctionMap.containsKey(r.getDest().getId())) {
			throw new IllegalArgumentException("Los cruces de la carretera no existen en el mapa de cruces.");
		}
		
		_roads.add(r);
		_roadMap.put(r.getId(), r);
	}
	
}
