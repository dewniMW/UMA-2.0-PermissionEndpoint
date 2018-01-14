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

/**
 * UMA custom exception for client errors.
 */
public class UMAClientException extends UMAException {

    public UMAClientException(String message) {
        super(message);
    }

    public UMAClientException(Throwable throwable) {
        super(throwable);
    }

    public UMAClientException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.setCode(code);
    }

    public UMAClientException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /*public UMAClientException(int statusCode, String errorCode, String errorMessage,
                              Throwable throwable){
        super(errorMessage, throwable);
        this.setErrorCode(errorCode);
        this.setStatusCode(statusCode);
        this.setErrorDescription(errorMessage);
    }*/

    /*public UMAClientException( String code, String message,
                              Throwable throwable){
        super(message, throwable);

        this.setCode(code);
        this.setErrorDescription(message);
    }*/

}

