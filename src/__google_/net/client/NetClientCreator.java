package __google_.net.client;

import __google_.crypt.Crypt;
import __google_.net.Flags;
import __google_.net.Response;

import java.io.IOException;
import java.net.Socket;

public interface NetClientCreator {
	NetClient create(Socket socket, Response response, Flags flags, Crypt crypt) throws IOException;
}
