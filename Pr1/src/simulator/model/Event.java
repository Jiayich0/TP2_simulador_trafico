package simulator.model;

public abstract class Event implements Comparable<Event> {

	private static long _counter = 0;

	protected int _time;
	protected long _time_stamp;

	Event(int time) {
		if ( time < 1 )
			throw new IllegalArgumentException("Invalid time: "+time);
	    else {
	    	_time = time;
	    	_time_stamp = _counter++;
	    }
	}
	
	int getTime() {
		return _time;
	}
	
	private long getTimeStamp() {
		return _time_stamp;
	}
  	
	
	
	/**
  	 * Propiedad de Java Comparable:
  	 * a < b -> -1
  	 * a = b -> 0
  	 * a > b -> 1
  	 * a(this) | b(o)
  	 */
  	@Override
  	public int compareTo(Event o) {
  		if(_time < o.getTime())				
  			return -1;
  		
  		if(_time > o.getTime())
  			return 1;
  		
  		if(_time_stamp < o.getTimeStamp())
  			return -1;
  		
  		if(_time_stamp > o.getTimeStamp())
  			return 1;
  		
  		return 0;
  	}
  	/**
  	 * 	Es lo mismo que:
  	 * 	if(a < b) return -1
  	 * 	elsif(a > b) return 1
  	 * 	elsif(a == b)
  	 * 		if(c < d) return -1
  	 *		elif(c > d) return 1
  	 *		else return 0
  	 */
  	
  	abstract void execute(RoadMap map);
  	
}