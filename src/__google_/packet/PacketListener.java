package __google_.packet;

import __google_.net.NetListener;
import __google_.util.Listener;

public class PacketListener implements NetListener{

    private final Listener<Packet> listener;

    public PacketListener(Listener<Packet> listener){
        this.listener = listener;
    }

    @Override
    public void read(String str) {
        listener.read(Packet.getPacket(str.getBytes()));
    }
}
