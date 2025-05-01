package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;


public class NewJunctionEventBuilder extends Builder<Event> {
	
	private Factory<LightSwitchingStrategy> _lssFactory;
	private Factory<DequeuingStrategy> _dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction", "A new Junction");
		_lssFactory = lssFactory;
		_dqsFactory = dqsFactory;
	}
	
	// Herencia
	@Override
	protected Event create_instance(JSONObject data) {
		if (!data.has("time") || !data.has("id") || !data.has("coor") || !data.has("ls_strategy") || !data.has("dq_strategy")) {
			throw new IllegalArgumentException("[E] No se encuentra disponible toda la infromaciópn necesaria en data");
		}
		
		int time = data.getInt("time");
        String id = data.getString("id");
        
        JSONArray coor = data.getJSONArray("coor");
        int xCoor = coor.getInt(0);
        int yCoor = coor.getInt(1);
        
        // Como es un JsonObject dentro de otro JsonObject se puede usar la propia factoría de los LightSwitchingStrategys
        // para eso hay que pasarle un JSONObject data
        JSONObject lssSData = data.getJSONObject("ls_strategy");
        LightSwitchingStrategy lsStrategy = _lssFactory.create_instance(lssSData);
        
        JSONObject dqsData = data.getJSONObject("dq_strategy");
        DequeuingStrategy dqStrategy = _dqsFactory.create_instance(dqsData);

        return new NewJunctionEvent(time, id, lsStrategy, dqStrategy, xCoor, yCoor);
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
}