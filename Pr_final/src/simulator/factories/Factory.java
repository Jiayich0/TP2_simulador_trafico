package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public interface Factory<T> {
	
	public T create_instance(JSONObject info);
	// Recibe un JSONObject y devuelve una isntancia de T
	
	public List<JSONObject> getInfo();
	// Lista de JSONObject con info de qué objetos puede crear la factoría
	
}
