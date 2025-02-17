package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	void addVehicle(Vehicle v) {
		if(_vehicleMap.containsKey(v.getId())) {
			throw new IllegalArgumentException("Ya existe un vehículo con el identificador: " + v.getId());
		}
		List<Junction> itinerary = v.getItinerary();
		for (int i = 0; i < itinerary.size() - 1; i++) {
			Junction src = itinerary.get(i);
			Junction dest = itinerary.get(i + 1);
		
			boolean roadExists = _roads.stream().anyMatch(r -> r.getSrc().equals(src) && r.getDest().equals(dest));
			
			if (!roadExists) {
				throw new IllegalArgumentException("El itinerario del vehículo " + v.getId() + " no es válido. No hay carretera entre " + src.getId() + " y " + dest.getId());
			}
		}
		
		_vehicles.add(v);
		_vehicleMap.put(v.getId(), v);
	}
	
	public Junction getJunction(String id) {
		return _junctionMap.getOrDefault(id, null);
	}
	
	public Road getRoad(String id) {
		return _roadMap.getOrDefault(id, null);
	}
	
	public Vehicle getVehicle(String id) {
		return _vehicleMap.getOrDefault(id, null);
	}
	
	public List<Junction> getJunctions() {
        return Collections.unmodifiableList(_junctions);
    }
	
	public List<Road> getRoads() {
        return Collections.unmodifiableList(_roads);
    }
	
	public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(_vehicles);
    }
	
	void reset() {
        _junctions.clear();
        _roads.clear();
        _vehicles.clear();

        _junctionMap.clear();
        _roadMap.clear();
        _vehicleMap.clear();
    }
	
	public JSONObject report() {
		JSONObject js = new JSONObject();
		
		// convertimos la lista de cruces a un JSONArray de reportes
		JSONArray junctionsArray = new JSONArray();
		for (Junction j : _junctions) {
			junctionsArray.put(j.report());
	    }
		
		// convertimos la lista de carreteras a un JSONArray de reportes
		JSONArray roadsArray = new JSONArray();
		for (Road r : _roads) {
			roadsArray.put(r.report());
		}
		
		// convertimos la lista de vehiculos a un JSONArray de reportes
		JSONArray vehiclesArray  = new JSONArray();
		for (Vehicle v : _vehicles) {
			vehiclesArray.put(v.report());
		}
		
		js.put("junctions", junctionsArray);
		js.put("roads", roadsArray);
		js.put("vehicles", vehiclesArray);
		
		return js;
	}
}
