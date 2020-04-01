package oogle.sync;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SyncServer extends Thread{
    private final ServerSocket socket;
    private final Map<Socket, SocketChannel> sockets = new HashMap<>();
    private final Listener listener;
    private boolean newConnections = true;
    private volatile boolean work = true;

    public SyncServer(SocketAddress address, Listener listener){
        super("SyncServer thread");
        this.listener = listener;
        try {
            socket = new ServerSocket();
            socket.bind(address);
            socket.setSoTimeout(1);
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
        start();
    }

    public void close(){
        work = false;
        try{
            socket.close();
        }catch (IOException ignore){}
    }

    @Override
    public void run(){
        try {
            while (work) {
                tick();
            }
        }catch (Throwable ex){
            ex.printStackTrace();
            System.err.println("In sync input thread");
        }
    }

    private void tick() throws IOException{
        if (newConnections) {
            if (socket.isClosed()) {
                System.err.println("Socket on new connections closed");
                newConnections = false;
            } else try {
                Socket socket = this.socket.accept();
                if (socket != null) {
                    socket.setSoTimeout(1);
                    SocketChannel channel = new SocketChannel(socket);
                    sockets.put(socket, channel);
                    listener.open(channel);
                }
            } catch (SocketTimeoutException ex) {
                //Ignored, nobody tried to connect
            }catch (SocketException ex){
                newConnections = false;
                ex.printStackTrace();
                System.err.println("Socket error");
            }
        }
        Iterator<Map.Entry<Socket, SocketChannel>> iterator = sockets.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Socket, SocketChannel> entry = iterator.next();
            Socket socket = entry.getKey();
            SocketChannel channel = entry.getValue();
            if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                System.out.println("Socket closed");
                listener.close(channel);
                socket.close();
                iterator.remove();
                continue;
            }
            try {
                InputStream stream = socket.getInputStream();
                byte f[] = new byte[2];
                try{
                    int a = stream.read(f, 0, 2);
                    if(a == -1){
                        listener.close(channel);
                        channel.close();
                        iterator.remove();
                        continue;
                    }
                }catch (SocketTimeoutException ex){
                    continue;
                }catch (SocketException ex){
                    ex.printStackTrace();
                    listener.close(channel);
                    channel.close();
                    iterator.remove();
                    continue;
                }
                int size = ((f[0] & 0xFF) << 8) | (f[1] & 0xFF);
                if(size == 0)continue;
                byte array[] = new byte[size];
                int i = 0;
                while (i != size)
                    i = i + stream.read(array, i, size - i);
                try{
                    listener.read(channel, array);
                }catch (Throwable throwable){
                    throwable.printStackTrace();
                    System.err.println("Inside consumer");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("In read remote socket, disconnect");
                listener.close(channel);
                channel.close();
                iterator.remove();
            }
        }
    }
}
