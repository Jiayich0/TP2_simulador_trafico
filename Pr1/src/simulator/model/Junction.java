package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	private List<Road> _incomingRoads;
	private List<List<Vehicle>> _queues;
	private Map<Road, List<Vehicle>> _queueByRoad;
	private Map<Junction,Road> _outgoingRoads;
	private	int _greenLightIndex;
	private int _lastSwitchingTime;
	private LightSwitchingStrategy _lsStrategy;
	private DequeuingStrategy _dqStrategy;
	private int _x;
	private int _y;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		_incomingRoads = new ArrayList<>();
		_outgoingRoads = new HashMap<>();
		_queues = new ArrayList<>();
		_queueByRoad = new HashMap<>();
		_greenLightIndex = -1;
		_lastSwitchingTime = 0;
		
		if (lsStrategy == null) {
			throw new IllegalArgumentException("[E] La estrategia lightswitch no puede ser nula");
		}
		if (dqStrategy == null) {
			throw new IllegalArgumentException("[E] La estrategia dequeue no puede ser nula");
		}
		if (xCoor < 0 || yCoor < 0) {
			throw new IllegalArgumentException("[E] Las coordenadas del cruce no pyueden ser negativas");
		}
		
		_lsStrategy = lsStrategy;
		_dqStrategy = dqStrategy;
		_x = xCoor;
		_y = yCoor;
	}
	// fixme solucionado - para qué son los coords si no se usan
	
	// Herencia
	void advance(int time) {
		if (_greenLightIndex != -1 && !_incomingRoads.isEmpty() ) {
			Road greenRoad = _incomingRoads.get(_greenLightIndex);
			List<Vehicle> vehiclesToMove = _dqStrategy.dequeue(_queueByRoad.get(greenRoad));
			
			for (Vehicle v : vehiclesToMove) {
				v.moveToNextRoad();
				_queueByRoad.get(greenRoad).remove(v);										// No se si hace falta borrar de ambos, map y list
				_queues.get(_incomingRoads.indexOf(greenRoad)).remove(v);
			}
		}
		
		int nextGreen = _lsStrategy.chooseNextGreen(_incomingRoads, _queues, _greenLightIndex, _lastSwitchingTime, time);

	    if (nextGreen != _greenLightIndex) {
	        _greenLightIndex = nextGreen;
	        _lastSwitchingTime = time;
	    }
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
		for (Road road : _incomingRoads) {
			JSONObject queueObj = new JSONObject();
			queueObj.put("road", road.getId());

			JSONArray vehiclesArray = new JSONArray();
			List<Vehicle> queue = _queueByRoad.get(road);
			if (queue != null) {
				for (Vehicle v : queue) {
					vehiclesArray.put(v.getId());
				}
				queueObj.put("vehicles", vehiclesArray);
			}
			queuesArray.put(queueObj);
		}

		js.put("queues", queuesArray);			// Lista queuues tiene road y otra lista vechicles
		return js;
	}

	
	// Métodos
	void addIncomingRoad(Road r) {				// Coches que entran
		if(!r.getDest().equals(this)) {
			throw new IllegalArgumentException("[E] La carretera " + r.getId() + " no tiene este cruce como destino");
		}
		
		// fixme solucionado - verificación: pasan los tests sin esta verificación, pero tiene sentido para no duplicar. Preguntar al profe
		//if (_incomingRoads.contains(r)) {
	    //    throw new IllegalArgumentException("[E] La carretera " + r.getId() + " ya está registrada como entrante");
	    //}

		
		_incomingRoads.add(r);					// Añade a la lista
		List<Vehicle> queue = new ArrayList<>();
		_queues.add(queue);						// Añade a la cola
		_queueByRoad.put(r, queue);				// Añade al map (lista, cola) para buscar más fácil
	}
	
	void addOutgoingRoad(Road r) {				// Coches que salen
		if(r.getSrc() != this) {
			throw new IllegalArgumentException("[E] La carretera " + r.getId() + " no tiene este cruce como origen");
		}
		
		if(_outgoingRoads.containsKey(r.getDest())) {
			throw new IllegalArgumentException("[E] Ya existe una carretera saliente hacia el cruce " + r.getDest().getId());
		}
		
		_outgoingRoads.put(r.getDest(), r);
	}
	
	void enter(Vehicle v) {
		Road road = v.getRoad();
		
		if (!_queueByRoad.containsKey(road)) {
			throw new IllegalArgumentException("[E] El vehículo " + v.getId() + "no pertenece a una carretera válida");
		}
		
		// fixme solucionado - TEST1. Si se descomenta la siguiente línea el test1 de Main no va porque se está añadiendo dos veces, entonces
		// por qué en addIncomingRoad sí se añade a lista y mapa y va bien??
		//_queues.get(_incomingRoads.indexOf(road)).add(v);
		_queueByRoad.get(road).add(v);				// Añade el vehiculo  a la cola de la carretera
													// lo mismo que añadir a _queeus add(v) pero por eficiencia se usa el map _queueByRoad
	}
	
	Road roadTo(Junction j) {
		return _outgoingRoads.get(j);
	}

	
	// Getters
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
}
