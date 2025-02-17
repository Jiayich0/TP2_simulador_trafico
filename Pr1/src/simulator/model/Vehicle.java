package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private List<Junction> _itinerary; 		// Cruces
	private int _maxSpeed;
	private int _speed;
	private VehicleStatus _status;
	private Road _road;
	private int _location;
	private int _contClass; 
	private int _totalContamination;
	private int _totalDistanceTraveled;

	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		_speed = 0;
		_status = VehicleStatus.PENDING;
		_road = null;
		_location = 0;
		_totalContamination = 0;
        _totalDistanceTraveled = 0;
		
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("por hacer en vechicle.java -> constructora -> maxSpeed");
		}
		if (contClass < 0 || contClass > 10) {
			throw new IllegalArgumentException("por hacer en vechicle.java -> constructora -> contClass");
		}
		if (itinerary.size() < 2 || itinerary == null) {
			throw new IllegalArgumentException("por hacer en vechicle.java -> constructora -> itinerary.size");
		}
		_maxSpeed = maxSpeed;
		_contClass = contClass;
		_itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
	}

	
	// Herencia
	void advance(int time) {

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
			throw new IllegalArgumentException("por hacer en vechicle.java -> setSpeed");
		}
		_speed = Math.min(s, _maxSpeed);
	}
	
	void setContaminationClass(int c) {
		if(c < 0 || c > 10) {
			throw new IllegalArgumentException("por hacer en vechicle.java -> setContaminationClass");
		}
		_contClass = c;
	}
	
	void moveToNextRoad() {
		
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
			throw new IllegalArgumentException("por hacer en vechicle.java -> setStatus");
		}
		_status = newStatus;
		if(_status != VehicleStatus.TRAVELING) {
			_speed = 0;
		}
	}
}
