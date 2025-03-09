package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;

public class SetContClassEventBuilder extends Builder<Event> {
	
	public aaaaaaaaaaaaaaaaaaaaaaaaaaaa() {
		super("aaaaaaaaaaaaaaa", "A new aaaaaaaaaaaaaaaaa");
	}
	
	// Herencia
	@Override
	protected Event createInstance(JSONObject data) {
		if (!data.has("time") || !data.has("id") ) {
			throw new IllegalArgumentException("[E] No se encuentra disponible toda la infromaciópn necesaria en data");
		}
		
		int time = data.getInt("time");
        String id = data.getString("id");

        return new aaaaaaaaaaaaaaaaaaaaaaa(time, id);
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
}
