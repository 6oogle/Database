package oogle.sync;

import oogle.util.byteable.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class PacketController {
    private final Class<?>[] classes;
    private final Serializable<?>[] serializables;
    private final int packets;

    private PacketController(Class<?>[] classes, Serializable<?>[] serializables) {
        this.classes = classes;
        this.serializables = serializables;
        this.packets = classes.length;
    }

    @SuppressWarnings("unchecked")
    public <T> T decode(byte array[]){
        FastDecoder decoder = new FastDecoder(array);
        int id = decoder.readInt();
        Serializable<T> serializable = (Serializable<T>) serializables[id];
        if(serializable == null)return null;
        return serializable.decode(decoder);
    }

    @SuppressWarnings("unchecked")
    public <T> byte[] encode(T packet){
        BEncoder encoder = new FastEncoder();
        Class<?> clazz = packet.getClass();
        int id = 0;
        for(; id < packets; id++) if(classes[id] == clazz)break;
        encoder.writeInt(id);
        ((Serializable<T>) serializables[id]).encode(packet, encoder);
        return encoder.generate();
    }

    public int indexOf(Class<?> packet){
        for(int i = 0; i < classes.length; i++)
            if(classes[i] == packet)return i;
        return -1;
    }

    public static final class Builder{
        private final List<Class<?>> classes = new LinkedList<>();
        private final List<Serializable<?>> serializables = new LinkedList<>();

        public <T> Builder add(Class<T> packet, Serializable<T> serializable){
            classes.add(packet);
            serializables.add(serializable);
            return this;
        }

        public <T> Builder add(Class<T> packet, BiConsumer<T, Encoder> encoder, Function<Decoder, T> decoder){
            return add(packet, Serializable.get(encoder, decoder));
        }

        public PacketController end(){
            return new PacketController(classes.toArray(new Class[]{}), serializables.toArray(new Serializable[]{}));
        }
    }
}
