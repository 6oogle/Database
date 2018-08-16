package __google_.packet;

import java.util.function.Function;

public class PacketListener implements Function<byte[], byte[]> {
    private final Function<Packet, byte[]> listener;

    public PacketListener(Function<Packet, byte[]> listener){
        this.listener = listener;
    }

    @Override
    public byte[] apply(byte[] bytes) {
        return listener.apply(Packet.getPacket(bytes));
    }
}
