package simulator.model;

public enum VehicleStatus {
	PENDING,		// Todavı́a no ha entrado a la primera carretera de su itinerario
	TRAVELING,		// Circulando sobre una carretera
	WAITING,		// Esperando en un cruce
	ARRIVED;		// Ha completado su itinerario
}
