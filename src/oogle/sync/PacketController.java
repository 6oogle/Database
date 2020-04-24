package oogle.sync;

import oogle.util.byteable.BEncoder;
import oogle.util.byteable.Deserializer;
import oogle.util.byteable.FastDecoder;
import oogle.util.byteable.FastEncoder;

import java.util.LinkedList;
import java.util.List;

public final class PacketController {

    private final Class<?>[] classes;
    private final Deserializer<?>[] deserializers;
    private final int packets;

    private PacketController(Class<?>[] classes, Deserializer<?>[] deserializers) {
        this.classes = classes;
        this.deserializers = deserializers;
        this.packets = classes.length;
    }

    @SuppressWarnings("unchecked")
    public <T> T decode(byte[] array) {
        FastDecoder decoder = new FastDecoder(array);
        return decoder.read((Deserializer<T>) deserializers[decoder.readInt()]);
    }

    public <T extends Packet> byte[] encode(T packet) {
        BEncoder encoder = new FastEncoder();
        Class<?> clazz = packet.getClass();
        int id = 0;
        for (; id < packets; id++) if (classes[id] == clazz) break;
        encoder.writeInt(id);
        encoder.write(packet);
        return encoder.generate();
    }

    public int indexOf(Class<?> packet){
        for(int i = 0; i < classes.length; i++)
            if(classes[i] == packet)return i;
        return -1;
    }

    public static final class Builder{

        private final List<Class<?>> classes = new LinkedList<>();
        private final List<Deserializer<?>> deserializers = new LinkedList<>();

        public <T> Builder add(Class<T> packet, Deserializer<T> deserializer) {
            classes.add(packet);
            deserializers.add(deserializer);
            return this;
        }

        public PacketController end(){
            return new PacketController(classes.toArray(new Class[]{}), deserializers.toArray(new Deserializer[]{}));
        }
    }
}
