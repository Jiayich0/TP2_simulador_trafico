package simulator.model;

public abstract class NewRoadEvent extends Event {
	
	protected String _id;
	protected String _srcJunc;
	protected String _destJunc;
	protected int _length;
	protected int _co2Limit;
	protected int _maxSpeed;
	protected Weather _weather;

	
	// Cuidado porque la constructora de Road y sus clases hija son de orden ...maxSpeed, co2limit, length, weather
	public NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		_id = id;
		_srcJunc = srcJunc;
		_destJunc = destJunc;
		_length = length;
		_co2Limit = co2Limit;
		_maxSpeed = maxSpeed;
		_weather = weather;
	}
	
	@Override
	abstract void execute(RoadMap map);
	// Event -> NewRoadEvent -> NewCityRoadEvent
	// 						 -> NewInterCityRoadEvent
}
