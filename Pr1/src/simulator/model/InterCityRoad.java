package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	
	void reduceTotalContamination() {
		_totalContamination = ((100 - _weather.getValue()) * _totalContamination) / 100;
	}
	
	void updateSpeedLimit() {
		if(_totalContamination > _contLimit) {
			_speedLimit = _maxSpeed/2;
		}
		else {
			_speedLimit = _maxSpeed;
		}
	}

	int calculateVehicleSpeed(Vehicle v) {
	    if (_weather == Weather.STORM) {
	        return (_speedLimit * 8) / 10;
	    }
	    return _speedLimit;
	}

	
}
