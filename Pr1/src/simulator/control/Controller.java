package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	
	private TrafficSimulator _sim;
	private Factory<Event> _eventsFactory;
	
	
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		
		if(sim == null) {
			throw new IllegalArgumentException("[E] Simulador de tráfico no puede ser nulo");
		}
		if(eventsFactory == null) {
			throw new IllegalArgumentException("[E] Factory de eventos no puede ser nulo");

		}
		_sim = sim;
		_eventsFactory = eventsFactory;
	}
	
	
	
	public void loadEvents(InputStream in) {
		if (in == null) {
	        throw new IllegalArgumentException("[E] InputStream no puede ser nulo");
	    }
		
		JSONObject jo = new JSONObject(new JSONTokener(in));	// Lee input stream y lo convierte en un jsonobject
		if (!jo.has("events")) {
	        throw new IllegalArgumentException("[E] InputStream no contiene events");
	    }
		
		JSONArray eventsArray = jo.getJSONArray("events");		// De este jsonObject saca la Lista que contiene todos los eventos (de i a n)
		for (int i = 0; i < eventsArray.length(); i++) {
	        JSONObject eventJSON = eventsArray.getJSONObject(i);//
	        Event e = _eventsFactory.create_instance(eventJSON);// La factrory crea el evento
	        _sim.addEvent(e);									// Añade evento a la simulacion
	    }
	}
	
	
	public void run(int n, OutputStream out) {
		if(out == null) {
		    throw new IllegalArgumentException("[E] OutputStream no puede ser nulo");
		}
		if(n < 1) {
			throw new IllegalArgumentException("[E] El número de ticks tiene que ser mayor o igual que 1");
		}
		
		// Guía: https://github.com/genaim/TP2_2425/blob/master/practicas/P1/guide/pr1.notes.6.md
		PrintStream p = new PrintStream(out);
		
		p.println("{");
		p.println("  \"states\": ["); 
	    
		// loop for the first n-1 states (to print comma after each state)
	    for (int i = 0; i < n - 1; i++) {
	        _sim.advance();
	        p.print(_sim.report());
	        p.println(",");
	    }
	    
	    // last step, only if 'n > 0'
	    if (n > 0) {
	        _sim.advance();
	        p.print(_sim.report());
	    }
	    
	    p.println("]");
	    p.println(" }");
	}
	
	public void reset() {
		_sim.reset();
	}	
}
