package __google_.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class CSSystem extends Thread{
    protected final Socket socket;
    protected final BufferedInputStream in;
    protected final BufferedOutputStream out;

    protected CSSystem(Socket socket) throws IOException{
        this.socket = socket;
        this.in = new BufferedInputStream(socket.getInputStream());
        this.out = new BufferedOutputStream(socket.getOutputStream());
        setPriority(MIN_PRIORITY);
    }

    public void close(){
        if(socket == null)return;
        try{
            socket.close();
        }catch (IOException ex){}
    }

    public boolean connected(){
        return socket != null && socket.isConnected();
    }

    protected byte[] read(int size) throws IOException{
        byte array[] = new byte[size];
        if(in.read(array) == -1)throw new IllegalArgumentException();
        return array;
    }
}
