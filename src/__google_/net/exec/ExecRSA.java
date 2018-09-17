package __google_.net.exec;

import __google_.crypt.async.SignedRSA;
import __google_.net.Response;

public class ExecRSA implements Exec{
	private final byte signedRSA[];

	public ExecRSA(SignedRSA signed){
		this.signedRSA = signed.toBytes();
	}

	@Override
	public Response apply(Response response) {
		return new Response(0, signedRSA);
	}
}
