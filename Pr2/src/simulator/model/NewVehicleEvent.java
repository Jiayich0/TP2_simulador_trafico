package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	private String _id;
	private int _maxSpeed;
	private int _contClass;
	private List<String> _itinerary;
	
	
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
	  	_id = id;
	  	_maxSpeed = maxSpeed;
	  	_contClass = contClass;
	  	_itinerary = itinerary;
	}

	@Override
	void execute(RoadMap map) {
		List<Junction> itinerary = new ArrayList<>();
		for(String stringToJunction : _itinerary) {
			Junction j = map.getJunction(stringToJunction);
			itinerary.add(j);
		}
		
		Vehicle v = new Vehicle(_id, _maxSpeed, _contClass, itinerary);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
	
	@Override
	public String toString() {
	    return "New Vehicle '" + _id + "'";
	}
}