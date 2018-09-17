package __google_.net;

import __google_.crypt.Crypt;
import __google_.util.Coder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

abstract class CSSystem extends Thread{
    Response response;
    Flags flags;
    final Crypt crypt;

    final Socket socket;
    private final BufferedInputStream in;
    private final BufferedOutputStream out;

    CSSystem(Socket socket, Response response, Flags flags, Crypt crypt) throws IOException{
        this.socket = socket;
        this.in = new BufferedInputStream(socket.getInputStream());
        this.out = new BufferedOutputStream(socket.getOutputStream());
        this.response = response;
        this.flags = flags;
        this.crypt = crypt;
        setPriority(MIN_PRIORITY);
    }

    CSSystem(Socket socket, Crypt crypt) throws IOException{
        this(socket, null, null, crypt);
    }

    void close(){
        if(socket == null)return;
        try{
            socket.close();
        }catch (IOException ex){}
    }

    boolean connected(){
        return socket != null && socket.isConnected();
    }

    byte[] read(int size) throws IOException{
        byte array[] = new byte[size];
        if(in.read(array) == -1)throw new IllegalArgumentException();
        return array;
    }

    void write(byte array[]) throws IOException{
        out.write(array);
    }

    void write(byte b) throws IOException{
        out.write(b);
    }

    void flush() throws IOException{
        out.flush();
    }

    void write() throws IOException{
        byte write[] = Coder.toBytes(response);
        if(flags.isCrypt() && crypt != null)write = crypt.encodeByte(write);
        write(Coder.toBytes(write.length));
        write(flags.getFlags());
        write(write);
        flush();
    }

    Response read() throws IOException{
        byte input[] = read(5);
        int size = Coder.toInt(Coder.subBytes(input, 4));
        Flags flags = new Flags(input[4]);
        if(this.flags == null)this.flags = flags;
        byte read[] = read(size);
        if(flags.isCrypt() && crypt != null)read = crypt.decodeByte(read);
        return Coder.toObject(read, Response.class);
    }
}
