package simulator.model;

public enum VehicleStatus {
	PENDING,		// Todavı́a no ha entrado a la primera carretera de su itinerario
	TRAVELING,		// Circulando sobre una carretera
	WAITING,		// Esperando en un cruce
	ARRIVED;		// Ha completado su itinerario
	
	// Cuando haga falta
	/*public static VehicleStatus stringToVechicleStatus(String str) {
		if (str.equals("PENDING"))
			return PENDING;
		else if (str.equals("TRAVELING"))
			return TRAVELING;
		else if (str.equals("WAITING"))
			return WAITING;
		else if (str.equals("ARRIVED"))
			return ARRIVED;
		else
			return null;
	}
	
	public static VehicleStatus stringToVehicleStatus(String str) {
	    try {
	        return VehicleStatus.valueOf(str.toUpperCase());
	    } catch (IllegalArgumentException | NullPointerException e) {
	        return null;
	    }
	}*/
}
