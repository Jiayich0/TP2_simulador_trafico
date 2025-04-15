package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {
	
	protected NewRoadEventBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}
	
	// Herencia
	@Override
	protected Event create_instance(JSONObject data) {
		if (!data.has("time") || !data.has("id") || !data.has("src") || !data.has("dest") || !data.has("length") || !data.has("co2limit") 
				|| !data.has("maxspeed") || !data.has("weather")) {
			throw new IllegalArgumentException("[E] No se encuentra disponible toda la infromaciópn necesaria en data");
		}
		
		int time = data.getInt("time");
        String id = data.getString("id");
        String src = data.getString("src");
        String dest = data.getString("dest");
        int lenght = data.getInt("length");
        int co2Limit = data.getInt("co2limit");
        int maxSpeed = data.getInt("maxspeed");
        
        String s = data.getString("weather");
        Weather weather = Weather.valueOf(s.toUpperCase());
        // Existe getEnum pero me da error Weather
        // Weather weather = data.getEnum(Weather, "weather");
        
        return createRoadEvent(time, id, src, dest, lenght, co2Limit, maxSpeed, weather);
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
	
	protected abstract Event createRoadEvent(int time, String id, String src, String dest, int length, int co2Limit, int maxSpeed, Weather weather);
}



