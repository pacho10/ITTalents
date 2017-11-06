package bg.ittalents.efficientproject.model.exception;

public class EfficientProjectDAOException extends Exception {
	private static final long serialVersionUID = -7474244310022540656L;

	public EfficientProjectDAOException() {
		super();
	}

	public EfficientProjectDAOException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public EfficientProjectDAOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EfficientProjectDAOException(String arg0) {
		super(arg0);
	}

	public EfficientProjectDAOException(Throwable arg0) {
		super(arg0);
	}


}
