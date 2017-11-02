package bg.ittalents.efficientproject.model.exception;

public class EffProjectException extends Exception {

	private static final long serialVersionUID = 6859026503206545784L;

	public EffProjectException() {
	}

	public EffProjectException(String arg0) {
		super(arg0);
	}

	public EffProjectException(Throwable arg0) {
		super(arg0);
	}

	public EffProjectException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EffProjectException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
