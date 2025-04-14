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
	
	private Map<String, Junction> _junctionsMap;
	private Map<String, Road> _roadsMap;
	private Map<String, Vehicle> _vehiclesMap;
	
	public RoadMap() {
        _junctions = new ArrayList<>();
        _roads = new ArrayList<>();
        _vehicles = new ArrayList<>();

        _junctionsMap = new HashMap<>();
        _roadsMap = new HashMap<>();
        _vehiclesMap = new HashMap<>();
	}
	
	void addJunction(Junction j) {
		if (_junctionsMap.containsKey(j.getId())) { // Búsqueda por el id (mismo para los siguientes dos métodos)
			throw new IllegalArgumentException("[€] Ya existe un cruce con el identificador: " + j.getId());
		}
		
		_junctions.add(j);
		_junctionsMap.put(j.getId(), j);
	}
	
	void addRoad(Road r) {
		if (_roadsMap.containsKey(r.getId())) {
			throw new IllegalArgumentException("[€] Ya existe una carretera con el identificador: " + r.getId());
		}
		if (!_junctionsMap.containsKey(r.getSrc().getId()) || !_junctionsMap.containsKey(r.getDest().getId())) {
			throw new IllegalArgumentException("[E] Los cruces de la carretera noexisten en el mapa de cruces");
		}
		
		_roads.add(r);
		_roadsMap.put(r.getId(), r);
	}
	
	void addVehicle(Vehicle v) {
		if(_vehiclesMap.containsKey(v.getId())) {
			throw new IllegalArgumentException("[€] Ya existe un vehículo con el identificador: " + v.getId());
		}
		
		List<Junction> itinerary = v.getItinerary();					//
		boolean roadExists = true;										// true para entrar al while
		int i = 0;
		while(roadExists && i < itinerary.size() - 1) {					// Que exista carretera entre todos los cruces de su itinerario
			Junction src = itinerary.get(i);							// [0] [1] [2] ... [n-1]
			Junction dest = itinerary.get(i+1);							// [1] [2] [3] ... [n]
			
			roadExists = false;											// false en un principio
	        for (Road r : _roads) {
	            if (r.getSrc().equals(src) && r.getDest().equals(dest)) {
	                roadExists = true;									// true si encuentra una y sale, no busca más (no existe otra)
	                break;
	            }
	        }
	        
	        if (!roadExists) {
				throw new IllegalArgumentException("[E] El itinerario del vehículo " + v.getId() + " no es válido (no hay carretera entre " + src.getId() + " y " + dest.getId() + ")");
			}
	        
			i++;
		}
		
		_vehicles.add(v);
		_vehiclesMap.put(v.getId(), v);
	}
	
	
	public Junction getJunction(String id) {
		return _junctionsMap.get(id);
	}
	
	public Road getRoad(String id) {
		return _roadsMap.get(id);
	}
	
	public Vehicle getVehicle(String id) {
		return _vehiclesMap.get(id);
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

        _junctionsMap.clear();
        _roadsMap.clear();
        _vehiclesMap.clear();
    }
	
	public JSONObject report() {
		JSONObject js = new JSONObject();
		
		// Lista de reportes de _junctions, _roads y _vehicles
		JSONArray JiReport = new JSONArray();
		for (Junction j : _junctions) {
			JiReport.put(j.report());
	    }
		
		JSONArray RiReport = new JSONArray();
		for (Road r : _roads) {
			RiReport.put(r.report());
		}
		
		JSONArray ViReport  = new JSONArray();
		for (Vehicle v : _vehicles) {
			ViReport.put(v.report());
		}
		
		js.put("junctions", JiReport);
		js.put("roads", RiReport);
		js.put("vehicles", ViReport);
		
		return js;
	}
}
