package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {
	
	public NewCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) {
		CityRoad cR = new CityRoad(_id, map.getJunction(_srcJunc), map.getJunction(_destJunc), _maxSpeed, _co2Limit, _length, _weather);
		map.addRoad(cR);
	}
	
	@Override
	public String toString() {
	    return "New CityRoad '" + _id + "'";
	}
}




	
	
	
	