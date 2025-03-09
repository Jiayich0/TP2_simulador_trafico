package simulator.factories;

import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy>{
	
	public MoveAllStrategyBuilder() {
		super("move_all_dqs", "Move all dequeuing strategy");
	}
	
	// Herencia
	@Override
	protected DequeuingStrategy createInstance(JSONObject data) {
		return new MoveAllStrategy();
	}
	
	@Override
	protected void fillInData(JSONObject o) {
    }
}