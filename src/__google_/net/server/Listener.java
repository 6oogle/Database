package __google_.net.server;

import __google_.crypt.Crypt;
import __google_.net.CSSystem;
import __google_.net.Response;
import __google_.net.server.exec.Exec;
import __google_.util.Exceptions;

import java.io.IOException;
import java.net.Socket;

public class Listener extends CSSystem implements NetServer{
	private final Server server;

	public Listener(Socket socket, Crypt crypt, Server server) throws IOException {
		super(socket, crypt);
		this.server = server;
		new Thread(this).start();
	}

	@Override
	public void execute() throws IOException {
		read();
		setResponse(getResponse());
		write();
	}

	private Response getResponse(){
		Response response = response();
		if(response == null) return nullResponse();
		Exec exec = server.getExec(response.getType());
		if(exec == null) return nullResponse();
		Exceptions.runThrowsEx(() -> exec.accept(this));
		return response();
	}

	private Response nullResponse(){
		return new Response(127);
	}
}
