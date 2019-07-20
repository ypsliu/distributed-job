package cn.rongcapital.djob.exception;

public class DjobSchedulerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1659691892235903223L;

	/**
	 * 
	 */
	public DjobSchedulerException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DjobSchedulerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DjobSchedulerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DjobSchedulerException(Throwable cause) {
		super(cause);
	}
}
