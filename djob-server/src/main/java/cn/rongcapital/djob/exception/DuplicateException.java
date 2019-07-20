package cn.rongcapital.djob.exception;

public class DuplicateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6628964433074356915L;

	/**
	 * 
	 */
	public DuplicateException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DuplicateException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DuplicateException(Throwable cause) {
		super(cause);
	}

}