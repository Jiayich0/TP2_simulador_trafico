package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public interface Factory<T> {
	
	public T create_instance(JSONObject info);
	// Recibe un JSONObject y devuelve una isntancia de T
	
	public List<JSONObject> getInfo();
	// Lista de JSONObject con info de qué objetos puede crear la factoría
	
	/**
	 * fixme solucionado - nomenclatura
	 * Hay un problema con la nomenclatura de Factory, Builder y sus clases hija. Uso en toda la practica camelCase
	 * pero la llamada de los tests a createInstance es en snake_case (create_instance). No mantiene consistencia.
	 * Consultar con el profesor. No es un problema a nivel funcional
	 */
}
