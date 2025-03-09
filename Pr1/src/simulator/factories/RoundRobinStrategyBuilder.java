package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss", "Round robin light swithcing strategy");
	}
	
	// Herencia
	@Override
	protected LightSwitchingStrategy createInstance(JSONObject data) {
		int timeSlot = 1;	// Default
		
		if(data.has("timeslot")) {
			timeSlot = data.getInt("timeslot");
		}
		
		return new RoundRobinStrategy(timeSlot);
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
}