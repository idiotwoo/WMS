package com.ken.wms.security.Service.execption;

/**
 * 
 * @author Ken
 *
 */
public class AccountServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String USERID_ERROR = "userIDError";
	public static final String PASSWORD_ERROR = "passwordError";
	public static final String PASSWORD_UNMATCH = "passwordUnmatch";
	public static final String AUTHORIATION_ERROR = "authorizationError";
	
	public static final String ENCRYING_ERROR = "encryingError";
	
	private boolean operationStatus;
	private String errorType;

	public AccountServiceException(boolean operationStatus, String errorType) {
		super();
		this.operationStatus = operationStatus;
		this.errorType = errorType;
	}

	public boolean isSuccess() {
		return operationStatus;
	}

	public String getErrorType() {
		return errorType;
	}

}
