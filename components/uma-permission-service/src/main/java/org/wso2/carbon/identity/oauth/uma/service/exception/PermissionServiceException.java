package org.wso2.carbon.identity.oauth.uma.service.exception;

/**
 * Custom exception to be thrown when there is an invalid resource id or resource scope in the requested permission(s).
 */
public class PermissionServiceException extends Exception {

    // errorCode represents whether it is a request containing an invalid resource id or resource scope.
    private String errorCode;

    public PermissionServiceException(String message) {
        super(message);
    }

    public PermissionServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
