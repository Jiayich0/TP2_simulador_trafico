package simulator.model;

import java.util.List;
import java.util.ListIterator;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	private int _timeSlot;
	
	MostCrowdedStrategy(int timeSlot){
		if (timeSlot <= 0) {
		    throw new IllegalArgumentException("ERROR: El tiempo mínimo debe ser positivo.");
		}
		_timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		if(roads.isEmpty())	{return -1;}

		if(currGreen == -1) {return maxQueue(roads, qs);}
		
		if(currTime - lastSwitchingTime < _timeSlot) {return currGreen;}
		
		return maxQueue(roads, qs, currGreen);
	}
	
	private int maxQueue(List<Road> roads, List<List<Vehicle>> qs) {
		int maxIndex = 0;
		List<Vehicle> max = qs.get(0);
		
		for (int i = 1; i < qs.size(); i++) {
			if ( qs.get(i).size() > max.size()) {
				max = qs.get(i);
				maxIndex = i;
			}
		}

		return maxIndex;
	}
	
	private int maxQueue(List<Road> roads, List<List<Vehicle>> qs, int currGreen) {
		ListIterator<List<Vehicle>> it = qs.listIterator();
		List<Vehicle> max1 = qs.get((currGreen + 1) % roads.size());
		it.set(max1);
		int j = currGreen + 1;
		
		while(j != currGreen){
			List<Vehicle> max2 = null;
			if(it.hasNext()) max2 = it.next();
			else{
				it.set(qs.get(0));
			} 
			if(max1.size() < it.next().size()) max1 = max2;
		}
		
		return qs.indexOf(max1);
	}
}
