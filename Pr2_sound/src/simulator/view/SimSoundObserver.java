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
	
	private Set<String> arrivedVehicles = new HashSet<>();
	private boolean simulationCompleted = false;
	private final int _tickInterval = 50;	// intervalo entre ticks por si suena demasiado con 1 sonido por tick
	private int _tickSoundIndex = 0;
	
	private final String _arrivedSound = "resources/sounds/orb.wav";
	private final String _completedSound = "resources/sounds/levelup.wav";
	private final String[] _tickSounds = {
		    "resources/sounds/wood1.wav",
		    "resources/sounds/wood2.wav",
		    "resources/sounds/wood3.wav",
		    "resources/sounds/wood4.wav"
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
