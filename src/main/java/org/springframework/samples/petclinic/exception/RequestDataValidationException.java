package org.springframework.samples.petclinic.exception;

public class RequestDataValidationException extends RuntimeException 
{
	private static final long serialVersionUID = 2543937423598172570L;
	
	final private String traceId;
	final private String errorCode;
	final private String errorMessage;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTraceId() {
		return traceId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public RequestDataValidationException(String traceId, String errorCode, String errorMessage) {
		super();
		this.traceId = traceId;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
