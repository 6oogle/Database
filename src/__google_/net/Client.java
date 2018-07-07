package __google_.net;

import __google_.crypt.Crypt;
import __google_.util.Listener;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private final String host;
    private final int port;
    private final NetListener listener;
    private final Crypt crypt;

    public Client(String host, int port, NetListener listener, Crypt crypt){
        this.host = host;
        this.port = port;
        this.listener = listener;
        this.crypt = crypt;
    }

    public Client(String host, int port, NetListener listener){
        this(host, port, listener, null);
    }

    public CSSystem connect() {
        try{
            return new CSSystem(new Socket(host, port), crypt, listener);
        }catch (IOException ex){
            return null;
        }
    }
}
