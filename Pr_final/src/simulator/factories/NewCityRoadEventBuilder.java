package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {
	
	public NewCityRoadEventBuilder() {
		super("new_city_road", "A new city road");
	}
	
	// Herencia
	
	// Este es el createInstance de NewCityRoadEventBuilder
	@Override
	protected Event createRoadEvent(int time, String id, String src, String dest, int length, int co2Limit, int maxSpeed, Weather weather) {
		return new NewCityRoadEvent(time, id, src, dest, length, co2Limit, maxSpeed, weather);
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
}