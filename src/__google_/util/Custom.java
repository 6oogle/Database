package __google_.util;

import java.util.HashMap;
import java.util.Map;

public class Custom {
	private static final Map<Class, Object> map = new HashMap<>();

	private Custom(){}

	public static <T> void setInstance(Class<T> clazz, T object){
		map.put(clazz, object);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazz){
		return (T)map.get(clazz);
	}
}
