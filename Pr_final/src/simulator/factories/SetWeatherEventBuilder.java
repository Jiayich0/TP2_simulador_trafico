package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {
	
	public SetWeatherEventBuilder() {
		super("set_weather", "Set weather");
	}
	
	// Herencia
	@Override
	protected Event create_instance(JSONObject data) {
		if (!data.has("time") || !data.has("info") ) {
			throw new IllegalArgumentException("[E] No se encuentra disponible toda la información necesaria en data");
		}
		
		int time = data.getInt("time");
		
		// Parecido a vehicle, extrae jsonArray y hace una copia profunda a la lista de pares
		JSONArray jsArray = data.getJSONArray("info");
		List<Pair<String, Weather>> ws = new ArrayList<>();
		for (int i = 0; i < jsArray.length(); i++) {
			JSONObject jsObj = jsArray.getJSONObject(i);	// Obtiene cada objeto JSON(par) del JSONArray(list)
            String road = jsObj.getString("road");			// jsonobj con identificador "road" lo convierte a String
            Weather weather = Weather.valueOf(jsObj.getString("weather").toUpperCase()); // id -> String -> Weather
            ws.add(new Pair<>(road, weather));				// Añade cada par a la la lista con los datos copiados y convertidos a String y Weather respectivamente
		}
		
        return new SetWeatherEvent(time, ws);
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
}