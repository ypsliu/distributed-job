package cn.rongcapital.djob.interceptor;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * @author sunxin@rongcapital.cn
 *
 */
@Provider
@Component
public class GlobalExceptionHandler implements ExceptionMapper<Throwable>{
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@Override
	public Response toResponse(Throwable exception) {
		LOGGER.error(exception.getMessage(),exception);
		final ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
		errorInfo.setErrorCode(String.valueOf(Response.Status.BAD_REQUEST.getStatusCode()));
		errorInfo.setErrorMessage(exception.getMessage());
		return Response.status(errorInfo.getStatusCode()).entity(errorInfo).type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}
	
	public static class ErrorInfo{
		private int statusCode;

		private String errorCode;

		private String errorMessage;

		public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		@Override
		public String toString() {
			return "ErrorInfo [statusCode=" + statusCode + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage
					+ "]";
		}
		
		
	}

}
