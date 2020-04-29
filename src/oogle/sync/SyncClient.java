package oogle.sync;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class SyncClient extends Thread{
    private final SocketChannel channel;
    private final Socket socket;
    private final Listener listener;

    public SyncClient(SocketAddress address, Listener listener, int timeout) {
        this.listener = listener;
        try{
            this.socket = new Socket();
            socket.connect(address, timeout);
            this.channel = new SocketChannel(socket);
            listener.open(channel);
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
        start();
    }

    public SyncClient(SocketAddress address, Listener listener) {
        this(address, listener, 400);
    }

    private volatile boolean work = true;

    public void close(){
        work = false;
        try{
            socket.close();
        }catch (IOException ex){}
    }

    @Override
    public void run() {
        while (work) {
            if (tick()) {
                work = false;
                break;
            }
            listener.tick();
        }
    }

    private boolean tick() {
        if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
            listener.close(channel);
            channel.close();
            return true;
        }
        try {
            InputStream stream = socket.getInputStream();
            byte f[] = new byte[2];
            try{
                int i = stream.read(f, 0, 2);
                if(i == 0)return false;
                if(i == 1) i = stream.read(f, 1, 1);
                if(i == -1){
                    listener.close(channel);
                    channel.close();
                    return true;
                }
            }catch (SocketTimeoutException ex){
                return false;
            }catch (SocketException ex){
                listener.close(channel);
                channel.close();
                return true;
            }
            int size = ((f[0] & 0xFF) << 8) | (f[1] & 0xFF);
            byte array[] = new byte[size];
            int i = 0;
            while (i != size)
                i = i + stream.read(array, i, size - i);
            try {
                listener.read(channel, array);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (IOException ex) {
            listener.close(channel);
            channel.close();
            return true;
        }
        return false;
    }
}
