package __google_.net;

import __google_.crypt.Crypt;
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
    private final Crypt crypt;

    public Client(String host, int port, Crypt crypt){
        this.host = host;
        this.port = port;
        this.crypt = crypt;
    }

    public Client(String host, int port){
        this(host, port, null);
    }

    public Response connect(Response response) {
        try{
            return new Connecter(new Socket(host, port), response).result();
        }catch (IOException ex){
            return null;
        }
    }

    private class Connecter extends CSSystem{
        private final Response response;

        public Connecter(Socket socket, Response response) throws IOException{
            super(socket);
            this.response = response;
        }

        public Response result() throws IOException{
            byte write[] = Byteable.toBytes(response);
            if(crypt != null)write = crypt.encodeByte(write);
            out.write(Coder.toBytes(write.length));
            out.write(write);
            out.flush();
            byte read[] = read(Coder.toInt(read(4)));
            if(crypt != null)read = crypt.decodeByte(read);
            socket.close();
            return Byteable.toObject(read, Response.class);
        }
    }
}
