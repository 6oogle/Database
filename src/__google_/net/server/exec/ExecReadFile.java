package __google_.net.server.exec;

import __google_.io.FileIO;
import __google_.net.Response;
import __google_.net.server.NetServer;
import __google_.util.ByteUnzip;

import java.io.IOException;

public class ExecReadFile implements Exec{
	@Override
	public void accept(NetServer server) throws IOException {
		server.setResponse(new Response(0, FileIO.readBytes(
				new ByteUnzip(server.response().getContent()).getString())));
	}
}
