package __google_.net;

import __google_.crypt.Crypt;
import __google_.crypt.async.RSA;
import __google_.crypt.async.SignedRSA;
import __google_.util.ByteUnzip;
import __google_.util.ByteZip;
import __google_.util.Byteable;
import __google_.util.Coder;

import javax.swing.text.html.CSS;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private final String host;
    private final int port;
    private Crypt crypt;

    public Client(String host, int port, Crypt crypt){
        this.host = host;
        this.port = port;
        this.crypt = crypt;
    }

    public Client(String host, int port){
        this(host, port, null);
    }

    public Response connect(Response response, Flags flags) {
        try{
            return new Connecter(new Socket(host, port), response, flags, crypt).result();
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

    private class Connecter extends CSSystem{
        private Connecter(Socket socket, Response response, Flags flags, Crypt crypt) throws IOException{
            super(socket, response, flags, crypt);
        }

        private Response result() throws IOException{
            write();
            Response response = read();
            socket.close();
            return response;
        }
    }
}
