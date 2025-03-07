package simulator.model;

import java.util.List;
import java.util.ListIterator;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	private int _timeSlot;
	
	MostCrowdedStrategy(int timeSlot){
		if (timeSlot <= 0) {
		    throw new IllegalArgumentException("[E] El tiempo mínimo tiene que ser positivo");
		}
		_timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		if(roads.isEmpty())	{return -1;}									//

		if(currGreen == -1) {return maxQueue(roads, qs);}					// No hay ningun vehículo - llama a maxQueue1
		
		if(currTime - lastSwitchingTime < _timeSlot) {return currGreen;}	// No ha pasado el timeSlot - mantiene el index
		
		return maxQueue(roads, qs, currGreen);								// Temrmina cuirrGreen y pasa al siguiente - llama a maxQueue2
	}
	
	private int maxQueue(List<Road> roads, List<List<Vehicle>> qs) {		// maxQueue1 - busca el index de la queue más grande
		int maxIndex = 0;													// realmente no se usa "roadS", parámetro omitible
		List<Vehicle> max = qs.get(0);
		
		for (int i = 1; i < qs.size(); i++) {
			if ( qs.get(i).size() > max.size()) {
				max = qs.get(i);
				maxIndex = i;
			}
		}

		return maxIndex;
	}
	
	private int maxQueue(List<Road> roads, List<List<Vehicle>> qs, int currGreen) { // maxQueue2 busca el index de lea siguiente
		int maxIndex = (currGreen + 1) % roads.size();						// queue más grande o la más grande sin contar currGeen
		List<Vehicle> max = qs.get(maxIndex);
		
		for (int i = 1; i < roads.size(); i++) {
			int index = (currGreen + 1 + i) % roads.size();
			if (qs.get(index).size() > max.size()) {
	            max = qs.get(index);
	            maxIndex = index;
	        }
		}
		
		return maxIndex;
	}
}
