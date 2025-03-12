package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {
	
	public SetContClassEventBuilder() {
		super("set_cont_class", "Set contamination class");
	}
	
	// Herencia
	@Override
	protected Event create_instance(JSONObject data) {
		if (!data.has("time") || !data.has("info") ) {
			throw new IllegalArgumentException("[E] No se encuentra disponible toda la información necesaria en data");
		}
		
		int time = data.getInt("time");
		
		// Análogo a SetWeatherEventBuilder
		JSONArray jsArray = data.getJSONArray("info");
		List<Pair<String, Integer>> cs = new ArrayList<>();
		for (int i = 0; i < jsArray.length(); i++) {
			JSONObject jsObj = jsArray.getJSONObject(i);
            String vehicle = jsObj.getString("vehicle");
            int contClass = jsObj.getInt("class");
            cs.add(new Pair<>(vehicle, contClass));
		}
		
        return new SetContClassEvent(time, cs);
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
}
