package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	private List<Road> _incomingRoads;
	private Map<Junction,Road> _outgoingRoads;
	private List<List<Vehicle>> _queues;
	private	int _greenLightIndex;
	private int _lastSwitchingTime;
	private LightSwitchingStrategy _lightSwitchingStrategy;
	private DequeuingStrategy _dequeuingStrategy;
	private int _x;
	private int _y;
	
	
	Junction(String id, LightSwitchStrategy lsStrategy, DequeingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		_incomingRoads = new ArrayList<>();
		_outgoingRoads = new HashMap<>();
		_queues = new ArrayList<>();
		_greenLightIndex = -1;
		_lastSwitchingTime = 0;
		
		if (lsStrategy == null) {
			throw new IllegalArgumentException("por hacer en Junction.java -> constructora -> lsStrategy");
		}
		if (dqStrategy == null) {
			throw new IllegalArgumentException("por hacer en Junction.java -> constructora -> dqStrategy");
		}
		if (xCoor < 0 || yCoor < 0) {
			throw new IllegalArgumentException("por hacer en Junction.java -> constructora -> coords");
		}
		_lightSwitchingStrategy = lsStrategy;
		_dequeuingStrategy = dqStrategy;
		_x = xCoor;
		_y = yCoor;
	}

	
	// Herencia
	void advance(int time) {

	}

	public JSONObject report() {
		JSONObject js = new JSONObject();
		js.put("id", _id);
		
		if (_greenLightIndex == -1) {
            js.put("green", "none");
        } else {
            js.put("green", _incomingRoads.get(_greenLightIndex).getId());
        }
		
		JSONArray queuesArray = new JSONArray();
		for (int i = 0; i < _incomingRoads.size(); i++) {
			JSONObject queueObj = new JSONObject();
			
			Road road = _incomingRoads.get(i);
	        List<Vehicle> vehicleQueue = _queues.get(i);
	        
	        queueObj.put("road", road.getId());
	        
	        JSONArray vehiclesArray = new JSONArray();
	        for (Vehicle v : vehicleQueue) {
	            vehiclesArray.put(v.getId());
	        }
	        
	        queueObj.put("vehicles", vehiclesArray);
	        queuesArray.put(queueObj);
		}
		
		js.put("queues", queuesArray);
		return js;
	}

	
	// Métodos
	void addIncommingRoad(Road r) {
		
	}
	
	void addOutGoingRoad(Road r) {
		
	}
	
	void enter(Vehicle v) {
		
	}
	
	Road roadTo(Junction j) {
		return null;
	}
	
}
