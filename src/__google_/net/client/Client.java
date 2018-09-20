package __google_.net.client;

import __google_.crypt.Crypt;
import __google_.crypt.async.SignedRSA;
import __google_.net.CSSystem;
import __google_.net.Flags;
import __google_.net.Response;
import __google_.util.Byteable;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final String host;
    private final int port;
    private final NetClientCreator worker;
    private Crypt crypt;

    public Client(String host, int port, Crypt crypt, NetClientCreator worker){
        this.host = host;
        this.port = port;
        this.worker = worker;
        this.crypt = crypt;
    }

    public Client(String host, int port, Crypt crypt){
        this(host, port, crypt, Connector::new);
    }

    public Client(String host, int port){
        this(host, port, null);
    }

    public Response connect(Response response, Flags flags) {
        try{
            return worker.create(new Socket(host, port), response, flags, crypt).apply();
        }catch (IOException ex){
            return null;
        }
    }

    public Response connect(Response response){
        return connect(response, new Flags());
    }

    public Response connect(int type, byte content[]){
        return connect(new Response(type, content));
    }

    public boolean getCertificate(boolean always){
        if(crypt != null)throw new IllegalArgumentException(":/");
        Response response = connect(new Response(0), new Flags(false));
        if(response.getType() == 127)return false;
        SignedRSA rsa = Byteable.toByteable(response.getContent(), SignedRSA.class);
        if(!always && !rsa.checkCertificate())return false;
        crypt = rsa.getRSA();
        return true;
    }

    public boolean getCertificate(){
        return getCertificate(false);
    }
}
