package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private List<Junction> _itinerary; 		// Cruces
	private int _maxSpeed;
	private int _speed;
	private VehicleStatus _status;			// 4 estados
	private Road _road;
	private int _location;
	private int _contClass; 				// Clase/Grado contaminación
	private int _totalContamination;		
	private int _totalDistanceTraveled;
	
	private int _itineraryIndex;

	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		_speed = 0;
		_status = VehicleStatus.PENDING;
		_road = null;
		_location = 0;
		_totalContamination = 0;
        _totalDistanceTraveled = 0;
        _itineraryIndex = 0;
		
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("[E] La velocidad máxima del vehículo tiene que ser mayor que 0");
		}
		if (contClass < 0 || contClass > 10) {
			throw new IllegalArgumentException("[E] El grado de contaminació tiene que estar en el rango [0, 10]");
		}
		if (itinerary.size() < 2 || itinerary == null) {
			throw new IllegalArgumentException("[E] El itinerario del vehículo tiene que tener mínimo dos cruces");
		}
		_maxSpeed = maxSpeed;
		_contClass = contClass;
		_itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
	}

	
	// Herencia
	void advance(int time) {
		if(_status != VehicleStatus.TRAVELING) {
			return;
		}
		
		int oldLocation = _location;
		_location = Math.min(_location + _speed, _road.getLength());
		
		int travelDistance = _location - oldLocation;
		_totalDistanceTraveled += travelDistance;
		
		int travelContamination = _contClass * travelDistance;
		_totalContamination += travelContamination;
		_road.addContamination(travelContamination);
		
		if(_location >= _road.getLength()) {
			setStatus(VehicleStatus.WAITING);
			_speed = 0;
			_road.getDest().enter(this);
		}
	}

	public JSONObject report() {
		JSONObject js = new JSONObject();
		js.put("id", _id);
		js.put("speed", _speed);
		js.put("distance", _totalDistanceTraveled);
		js.put("co2", _totalContamination);
		js.put("class", _contClass);
		js.put("status", _status.toString());
		if(_status != VehicleStatus.PENDING && _status != VehicleStatus.ARRIVED) {
			js.put("road", _road.getId());
			js.put("location", _location);
		}
		return js;
	}
	
	
	// Métodos
	void setSpeed(int s) {
		if(s < 0) {
			throw new IllegalArgumentException("[E] La velocidad del vehículo no puede ser negativa");
		}
		
		if(this._status != VehicleStatus.TRAVELING) {
			_speed = 0;
			return;
		}

		_speed = Math.min(s, _maxSpeed);;
	}
	
	void setContClass(int c) {
		if(c < 0 || c > 10) {
			throw new IllegalArgumentException("[E] El grado de contaminació tiene que estar en el rango [0, 10]");
		}
		_contClass = c;
	}
	
	void moveToNextRoad() {
		if(_status != VehicleStatus.WAITING && _status != VehicleStatus.PENDING) {
			throw new IllegalArgumentException("[E] El vehículo no puede cambiar de carretera si no está en PENDING o WAITING");
		}
		
		if(_itineraryIndex == _itinerary.size() - 1) {					// Ha lleagdo a su último cruce
			setStatus(VehicleStatus.ARRIVED);							// También pone _speed a 0
			if (_road != null) {											// Saca a este vehiculo de la carretera en la que está
		        _road.exit(this);		// Añadido porque si no da error en los Test1 y 2 de Main
		    }
			_road = null;
			return;
		}
		
		if (_road != null) {											// Saca a este vehiculo de la carretera en la que está
	        _road.exit(this);
	    }
		
		Junction actualJunction = _itinerary.get(_itineraryIndex);  	// Cruce actual
	    _itineraryIndex++;
	    Junction nextJunction = _itinerary.get(_itineraryIndex);  		// Cruce siguiente (como se ha hecho ++, coge el siguiente de la lista)

	    Road nextRoad = actualJunction.roadTo(nextJunction);
	    
	    if (nextRoad == null) {
	        throw new IllegalArgumentException("[E] No existe una carretera entre " + actualJunction.getId() + " y " + nextJunction.getId() );
	    }
		
		_road = nextRoad;
		_location = 0;
		setStatus(VehicleStatus.TRAVELING);								// Aquí no pone _speed a 0 porque es TRAVELING
		_road.enter(this);
	}
	
	
	// Getters
	public List<Junction> getItinerary() {
		return _itinerary;
	}

	public int getMaxSpeed() {
		return _maxSpeed;
	}
	
	public int getSpeed(){
		return _speed;
	}
	
	public VehicleStatus getStatus() {
		return _status;
	}
	
	public Road getRoad() {
		return _road;
	}
	
	public int getLocation() {
		return _location;
	}
	
	public int getContClass() {
		return _contClass;
	}
	
	public int getTotalCO2() {
		return _totalContamination;
	}
	
	public int getTotalDistance() {
		return _totalDistanceTraveled;
	}
	
	
	// Setters
	private void setStatus(VehicleStatus newStatus) {
		if(newStatus == null) {
			throw new IllegalArgumentException("[E] El estado del vehículo no puede ser nulo");
		}
		_status = newStatus;
		if(_status != VehicleStatus.TRAVELING) {
			_speed = 0;
		}
	}
}
