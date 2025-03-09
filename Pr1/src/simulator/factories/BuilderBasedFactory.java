package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;

	
	
	// Constructores
	public BuilderBasedFactory() {
		_builders = new HashMap<>();
		_buildersInfo = new LinkedList<>();
	}

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		for(Builder<T> b : builders) {
			addBuilder(b);
		}
	}
	
	// Método
	public void addBuilder(Builder<T> b) {
		_builders.put(b.getTypeTag(), b);
		_buildersInfo.add(b.getInfo());			//getInfo de Builder, no de BuilderBasedFactory
	}

	
	// Herencia
	@Override
	public T create_instance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException("’info’ cannot be null");
		}
		
		String type = info.getString("type");
		Builder<T> builder = _builders.get(type);
		if(builder != null) {
			JSONObject data = info.has("data")? info.getJSONObject("data"): new JSONObject();
			T instance = builder.create_instance(data);
			if (instance!= null) {
                return instance;
            }
		}
		// info.has("data") ? info.getJSONObject("data") : new getJSONObject()
		// condición ? valor_si_verdadero : valor_si_falso; -> es como un if-else, igual que en python y c
		
		throw new IllegalArgumentException("Unrecognized ‘info’:" + info.toString());
	}

	@Override
  	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}
}