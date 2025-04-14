package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road", "A new inter city road");
	}
	
	// Herencia
	
	// Este es el createInstance de NewCityRoadEventBuilder
	@Override
	protected Event createRoadEvent(int time, String id, String src, String dest, int length, int co2Limit, int maxSpeed, Weather weather) {
		return new NewInterCityRoadEvent(time, id, src, dest, length, co2Limit, maxSpeed, weather);
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
}
