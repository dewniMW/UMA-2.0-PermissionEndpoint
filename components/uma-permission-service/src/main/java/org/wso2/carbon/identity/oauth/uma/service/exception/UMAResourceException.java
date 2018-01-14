/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.oauth.uma.service.exception;

import org.wso2.carbon.identity.oauth.uma.service.UMAConstants;

/**
 * Custom exception to be thrown when there is an invalid resource id or resource scope in the requested permission(s).
 */
public class UMAResourceException extends UMAException {

    public UMAResourceException(String message) {
        super(message);
//        this.setErrorCode(getDefaultErrorCode());
    }

    public UMAResourceException(String message, Throwable throwable) {
        super(message, throwable);
//        this.setErrorCode(getDefaultErrorCode());
    }

    /*public UMAResourceException(String errorCode, String message) {
        super (errorCode, message);

    }*/

    /*public UMAResourceException(String errorCode, String message, Throwable throwable){
        super(message, throwable);
        this.setErrorCode(errorCode);
    }

    public UMAResourceException (String errorCode, UMAConstants.ErrorMessages errorMessages, Throwable throwable){
        super(errorMessages.getMessage(), throwable);
        this.setErrorCode(errorCode);
    }*/

    public UMAResourceException (UMAConstants.ErrorMessages errorMessage, Throwable throwable){
        super(errorMessage.getMessage(), throwable);
        this.setCode(errorMessage.getCode());

    }



    /*public static DCRMClientException generateClientException(DCRMConstants.ErrorMessages error,
                                                              String data)
            throws DCRMClientException {

        String errorDescription;
        if (StringUtils.isNotBlank(data)) {
            errorDescription = String.format(error.getMessage(), data);
        } else {
            errorDescription = error.getMessage();
        }

        return IdentityException.error(DCRMClientException.class, error.toString(), errorDescription);
    }
    */


    /*private String getDefaultErrorCode() {

        String errorCode = super.getErrorCode();
        if (StringUtils.isEmpty(errorCode)) {
            //TODO define error codes for DCRM
//            errorCode = Oauth2ScopeConstants.ErrorMessages.ERROR_CODE_UNEXPECTED.getCode();
        }
        return errorCode;
    }

    private String getDefaultStatusCode() {

        String errorCode = super.getErrorCode();
        if (StringUtils.isEmpty(errorCode)) {
            //TODO define error codes for DCRM
//            errorCode = Oauth2ScopeConstants.ErrorMessages.ERROR_CODE_UNEXPECTED.getCode();
        }
        return errorCode;
    }*/

}
