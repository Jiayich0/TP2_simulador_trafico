package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	
	protected Junction _srcJunc;
	protected Junction _destJunc;
	protected int _length;
	protected int _maxSpeed;
	protected int _speedLimit;
	protected int _contLimit;
	protected Weather _weather;
	protected int _totalContamination;
	protected List<Vehicle> _vehicles;
	
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		_vehicles = new ArrayList<>();
		_speedLimit = _maxSpeed;
		_totalContamination = 0;
		
		if(maxSpeed <= 0) {
			throw new IllegalArgumentException("por hacer en Road.java -> constructora -> maxSpeed");
		}
		if(contLimit < 0) {
			throw new IllegalArgumentException("por hacer en Road.java -> constructora -> contLimit");
		}
		if(length <= 0) {
			throw new IllegalArgumentException("por hacer en Road.java -> constructora -> length");
		}
		if(srcJunc == null) {
			throw new IllegalArgumentException("por hacer en Road.java -> constructora -> srcJunc");
		}
		if(destJunc == null) {
			throw new IllegalArgumentException("por hacer en Road.java -> constructora -> destJunc");
		}
		if(weather == null) {
			throw new IllegalArgumentException("por hacer en Road.java -> constructora -> weather");
		}
		_maxSpeed = maxSpeed;
		_contLimit = contLimit;
		_length = length;
		_srcJunc = srcJunc;
		_destJunc = destJunc;
		_weather = weather;
	}
	
	
	// Herencia
	void advance(int time) {

	}

	public JSONObject report() {
		JSONObject js = new JSONObject();
		js.put("id", _id);
		js.put("speedlimit", _speedLimit);
		js.put("weather", _weather.toString());
		js.put("co2", _totalContamination);
		
		JSONArray vehiclesArray = new JSONArray();
	    for (Vehicle v : _vehicles) {
	        vehiclesArray.put(v.getId());
	    }
		js.put("vehicles", vehiclesArray);
		return js;
	}
	
	
	// Métodos
	void enter(Vehicle v) {
		if(v.getLocation() != 0 || v.getSpeed() != 0) {
			throw new IllegalArgumentException("por hacer en Road.java -> enter");
		}
		_vehicles.add(v);
	}
	
	void exit(Vehicle v) {
		_vehicles.remove(v);
	}
	
	void setWeather(Weather w) {
		if(w == null) {
			throw new IllegalArgumentException("por hacer en Road.java -> setWeather");
		}
		_weather = w;
	}
	
	void addContamination(int c) {
		if(c < 0) {
			throw new IllegalArgumentException("por hacer en Road.java -> addContamination");
		}
		_totalContamination += c;
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);

	
	// Getters
	public Junction getSrc() {
		return _srcJunc;
	}
	
	public Junction getDest() {
		return _destJunc;
	}
	
	public int getMaxSpeed() {
		return _maxSpeed;
	}

	public int getSpeedLimit() {
		return _speedLimit;
	}
	
	public int getContLimit() {
		return _contLimit;
	}
	
	public Weather getWeather() {
		return _weather;
	}
	
	public int getTotalCO2() {
		return _totalContamination;
	}
	
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(_vehicles);
	}
}

