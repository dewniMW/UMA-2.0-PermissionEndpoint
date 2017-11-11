package org.wso2.carbon.identity.oauth.uma.service.exception;

/**
 * Custom exception to be thrown inside permission registration related functionality.
 */
public class PermissionServiceRuntimeException extends Exception {

    public PermissionServiceRuntimeException(String message) {
        super(message);
    }

    public PermissionServiceRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
