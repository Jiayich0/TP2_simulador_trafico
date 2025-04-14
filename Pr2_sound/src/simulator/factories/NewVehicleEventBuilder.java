package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {
	
	public NewVehicleEventBuilder() {
		super("new_vehicle", "A new vehicle");
	}
	
	// Herencia
	@Override
	protected Event create_instance(JSONObject data) {
		if (!data.has("time") || !data.has("id") || !data.has("maxspeed") || !data.has("class") || !data.has("itinerary")) {
			throw new IllegalArgumentException("[E] No se encuentra disponible toda la información necesaria en data");
		}
		
		int time = data.getInt("time");
        String id = data.getString("id");
        int maxSpeed = data.getInt("maxspeed");
        int contClass = data.getInt("class");
        
        // Extrae el jsonarray y lo copia en una Lista de Strings uno a uno
        JSONArray jsArray = data.getJSONArray("itinerary");
        List<String> itinerary = new ArrayList<>();
        for(int i = 0; i < jsArray.length(); i++) {
        	itinerary.add(jsArray.getString(i));
        }
        
        return new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
}
