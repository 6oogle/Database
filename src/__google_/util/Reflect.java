package __google_.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Reflect {
	public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... args) {
		return Exceptions.getThrowsEx(() -> clazz.getConstructor(args), true);
	}

	public static <T> T create(Constructor<T> constructor, Object... args) {
		return Exceptions.getThrowsEx(() -> constructor.newInstance(args));
	}

	public static Object getFromField(Field field, Object invoke) {
		return Exceptions.getThrowsEx(() -> field.get(invoke));
	}

	public static void setToField(Field field, Object invoke, Object set){
		Exceptions.runThrowsEx(() -> field.set(invoke, set), false);
	}

	public static byte[] getPrimitiveBytes(Field field, Object object) {
		if(object == null)return null;
		Class clazz = field.getType();
		return Exceptions.getThrowsEx(() -> {
			if(clazz == String.class) return Coder.toBytes(field.get(object).toString());
			if(clazz == boolean.class) return Coder.toBytes(field.getBoolean(object));
			if(clazz == long.class) return Coder.toBytes(field.getLong(object));
			if(clazz == int.class) return Coder.toBytes(field.getInt(object));
			if(clazz == short.class) return Coder.toBytes(field.getShort(object));
			if(clazz == byte.class) return Coder.toBytes(field.getByte(object));
			return null;
		}, false);
	}

	public static boolean isFinal(Field field){
		return Modifier.isFinal(field.getModifiers());
	}

	public static boolean isStatic(Field field){
		return Modifier.isStatic(field.getModifiers());
	}

	public static boolean isTransient(Field field){
		return Modifier.isTransient(field.getModifiers());
	}

	public static boolean isEditable(Field field){
		return !isFinal(field) && !isStatic(field) && !isTransient(field);
	}
}
