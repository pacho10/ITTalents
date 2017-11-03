package bg.ittalents.efficientproject.model.exception;

public class DBException extends Exception {

	private static final long serialVersionUID = -78845582715591618L;

	public DBException() {
	}

	public DBException(String message) {
		super(message);
	}

	public DBException(Throwable cause) {
		super(cause);
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
