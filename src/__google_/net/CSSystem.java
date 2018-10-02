package __google_.net;

import __google_.crypt.Crypt;
import __google_.util.Coder;
import __google_.util.Exceptions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class CSSystem implements NetWorker{
    private Response response;
    private Flags flags;
    private final Crypt crypt;

    private final Socket socket;
    private final BufferedInputStream in;
    private final BufferedOutputStream out;

    protected CSSystem(Socket socket, Response response, Flags flags, Crypt crypt) throws IOException{
        this.socket = socket;
        this.in = new BufferedInputStream(socket.getInputStream());
        this.out = new BufferedOutputStream(socket.getOutputStream());
        this.response = response;
        this.flags = flags;
        this.crypt = crypt;
        socket.setSoTimeout(1000);
    }

    protected CSSystem(Socket socket, Crypt crypt) throws IOException{
        this(socket, null, null, crypt);
    }

    @Override
    public void close(){
        if(socket == null)return;
        Exceptions.runThrowsEx(() -> {
            flush();
            socket.close();
        });
    }

    @Override
    public void write() throws IOException{
        byte write[] = Coder.toBytes(response);
        if(flags.isCrypt() && crypt != null)write = crypt.encodeByte(write);
        write(Coder.toAbsoluteBytes(write.length));
        write(flags.getFlags());
        write(write);
        flush();
    }

    @Override
    public void read() throws IOException{
        byte input[] = read(5);
        int size = Coder.toInt(Coder.subBytes(input, 4));
        Flags flags = new Flags(input[4]);
        setFlags(flags);
        byte read[] = read(size);
        if(flags.isCrypt() && crypt != null)read = crypt.decodeByte(read);
        setResponse(Coder.toObject(read, Response.class));
    }

    private byte[] read(int size) throws IOException{
        byte array[] = new byte[size];
        for(int i = 0; i < array.length; i++)
            array[i] = (byte)in.read();
        return array;
    }

    private void write(byte array[]) throws IOException{
        out.write(array);
    }

    private void write(byte b) throws IOException{
        out.write(b);
    }

    private void flush() throws IOException{
        out.flush();
    }

    @Override
    public Socket socket() {
        return socket;
    }

    @Override
    public Response response(){
        return response;
    }

    @Override
    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public Flags flags() {
        return flags;
    }

    @Override
    public void setFlags(Flags flags) {
        this.flags = flags;
    }
}
