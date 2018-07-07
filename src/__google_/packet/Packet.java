package __google_.packet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {

    private static final Map<String, Constructor<? extends Packet>> packets = new HashMap<>();
    private final String type;

    public Packet(String type){
        this.type = type;
        if(!packets.containsKey(type))register(getClass(), type);
    }

    public String getType() {
        return type;
    }

    protected abstract String encode();

    @Override
    public String toString(){
        return type + (char)12 + encode();
    }

    public static Packet getPacket(String str){
        String split[] = str.split((char)12 + "");
        Constructor<? extends Packet> constructor = packets.get(split[0]);
        if(constructor == null)throw new IllegalArgumentException("Packet not registered");
        try{
            return constructor.newInstance(split[1]);
        }catch (IllegalAccessException | InvocationTargetException | InstantiationException ex){
            throw new IllegalArgumentException(ex);
        }
    }

    public static void register(Class<? extends Packet> clazz, String type){
        try{
            Constructor<? extends Packet> constructor = clazz.getConstructor(String.class);
            if(constructor == null)throw new NullPointerException();
            packets.put(type, constructor);
        }catch (NoSuchMethodException ex){
            throw new IllegalArgumentException(ex);
        }
    }
}
