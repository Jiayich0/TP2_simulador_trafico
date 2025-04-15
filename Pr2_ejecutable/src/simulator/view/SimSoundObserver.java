package simulator.view;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class SimSoundObserver implements TrafficSimObserver {
	
	/**
	 * TICK speed and sound modificator:
	 * 	- DO_TIME_SLEEP: 	interruptor de ralentización
	 * 	- TIME_SLEEP:		ralentizacion [500: medio segundo, 1000: 1segundo]
	 * 	- _tickInterval:	intervalo entre ticks
	 * ---------------------------------------------------------------------------
	 * 	Valores default: 
	 * 		DO_TIME_SLEEP = false;
	 * 		_tickInterval = 40;
	 * 	Valores recomendados para DO_TIME_SLEEP = true:
	 * 		TIME_SLEEP = 500;
	 * 		_tickInterval = 1;
	 */
	public static final boolean DO_TIME_SLEEP = false;
	public static final int TIME_SLEEP = 500;
	private final int _tickInterval = 40;
	
	private Set<String> arrivedVehicles = new HashSet<>();
	private boolean simulationCompleted = false;
	private int _tickSoundIndex = 0;
	
	private final String _arrivedSound = "/sounds/orb.wav";
	private final String _completedSound = "/sounds/levelup.wav";
	private final String[] _tickSounds = {
		    "/sounds/wood1.wav",
		    "/sounds/wood2.wav",
		    "/sounds/wood3.wav",
		    "/sounds/wood4.wav"
		};
	
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		// Para los tikcs
		if (time % _tickInterval == 0) {
			Sound.playSound(_tickSounds[_tickSoundIndex]);

			_tickSoundIndex = (_tickSoundIndex + 1) % _tickSounds.length;	// Recorrido en ciruclo
		}
		
		// Para la llegada de vehiculos
		boolean allArrived = true;
		
		for (Vehicle v : map.getVehicles()) {
	        if (v.getStatus() == VehicleStatus.ARRIVED && !arrivedVehicles.contains(v.getId())) {
	            arrivedVehicles.add(v.getId());
	            Sound.playSound(_arrivedSound);
	        }

	        if (v.getStatus() != VehicleStatus.ARRIVED) {
	            allArrived = false;
	        }
	    }

	    if (allArrived && !simulationCompleted) {
	    	// simulationCompleted para evitar que se reproduzca el sonido en bucle
	    	// despues de que hayan llegado todos cuando se sigue corriendoe l programa
	        simulationCompleted = true;
	        Sound.playSound(_completedSound);
	    }
	}

	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
	}

	public void onReset(RoadMap map, Collection<Event> events, int time) {
		arrivedVehicles.clear();
		simulationCompleted = false;
		_tickSoundIndex = 0;
	}

	public void onRegister(RoadMap map, Collection<Event> events, int time) {
	}
}
