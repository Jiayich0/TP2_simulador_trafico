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
		_totalContamination = 0;
		
		if(maxSpeed <= 0) {
			throw new IllegalArgumentException("[E] La velocidad máxima tiene que ser mayor que 0");
		}
		if(contLimit < 0) {
			throw new IllegalArgumentException("[E] El límite de contaminación no puede ser negativo");
		}
		if(length <= 0) {
			throw new IllegalArgumentException("[E] La longitud de road tiene que ser mayor que 0");
		}
		if(srcJunc == null) {
			throw new IllegalArgumentException("[E] El cruce origen no puede ser nulo");
		}
		if(destJunc == null) {
			throw new IllegalArgumentException("[E] El cruce destino no puede ser nulo");  
		}
		if(weather == null) {
			throw new IllegalArgumentException("[E] El clima de la carretera no puede ser nulo");
		}
		_maxSpeed = maxSpeed;
		_speedLimit = _maxSpeed;
		_contLimit = contLimit;
		_length = length;
		_srcJunc = srcJunc;
		_destJunc = destJunc;
		_weather = weather;
		
		_srcJunc.addOutgoingRoad(this);
		_destJunc.addIncomingRoad(this);
	}
	
	
	// Herencia
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		
		for(Vehicle v: _vehicles) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
		_vehicles.sort((v1, v2) -> Integer.compare(v2.getLocation(), v1.getLocation()));		// Ordena la lista de vehiculos - DESCENDENTE (de más cerca a la salida a menos)
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
			throw new IllegalArgumentException("[E] Un vehículo solo puede entrar a la carretera si su localización y velocidad son 0");
		}
		_vehicles.add(v);
	}
	
	void exit(Vehicle v) {
		_vehicles.remove(v);
	}
	
	void setWeather(Weather w) {
		if(w == null) {
			throw new IllegalArgumentException("[E] No se puede asignar un clima nulo a la carretera");
		}
		_weather = w;
	}
	
	void addContamination(int c) {
		if(c < 0) {
			throw new IllegalArgumentException("[E] No se puede añadir contaminación negativa");
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
	
	public int getLength() {
		return _length;
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
	
	public List<Vehicle> getVehicles(){								// No se usa? Revisar más tarde
		return Collections.unmodifiableList(_vehicles);
	}
}

