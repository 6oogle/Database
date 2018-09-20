package __google_.net.client;

import __google_.crypt.Crypt;
import __google_.net.CSSystem;
import __google_.net.Flags;
import __google_.net.Response;

import java.io.IOException;
import java.net.Socket;

public class Connector extends CSSystem implements NetClient {
	public Connector(Socket socket, Response response, Flags flags, Crypt crypt) throws IOException {
		super(socket, response, flags, crypt);
	}
}
