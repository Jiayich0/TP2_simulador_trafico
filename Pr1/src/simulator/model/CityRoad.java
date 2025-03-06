package simulator.model;

public class CityRoad extends Road {
	
	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	
	void reduceTotalContamination() {
		_totalContamination = _totalContamination - getWeatherCondition(_weather);
		
		if(_totalContamination < 0) {
			 _totalContamination = 0;
		}
	}
	
	private int getWeatherCondition(Weather w) {
	    if (w == Weather.WINDY || w == Weather.STORM) {
	        return 10;
	    } else {
	        return 2;
	    }
	}
	
	void updateSpeedLimit() {
		_speedLimit = _maxSpeed;
	}
	
	int calculateVehicleSpeed(Vehicle v) {
		return ((11-v.getContClass())*_speedLimit)/11;
	}

}
