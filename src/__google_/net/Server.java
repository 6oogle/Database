package __google_.net;

import __google_.crypt.Crypt;
import __google_.util.Listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private final int port;
    private final NetListener listener;
    private final Crypt crypt;
    private boolean close = false;

    public Server(int port, NetListener listener, Crypt crypt){
        this.port = port;
        this.listener = listener;
        this.crypt = crypt;
        start();
    }

    public Server(int port, NetListener listener){
        this(port, listener, null);
    }

    @Override
    public void run(){
        try{
            ServerSocket listn = new ServerSocket(port);
            while (!close){
                Socket socket = listn.accept();
                if(socket != null)new CSSystem(socket, crypt, listener);
            }
            listn.close();
        }catch (IOException ex){
            //Error, server closed, but not close()
            ex.printStackTrace();
        }
    }

    public void close(){
        close = true;
    }
}
