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

package org.wso2.carbon.identity.oauth.uma.service;

/**
 * This class holds the constants used by Permission Endpoint.
 */
public class UMAConstants {

    public static final String UMA_PERMISSION_ENDPOINT_CONFIG_PATH = "ptDetails.properties";


    /**
     * Contains the values to be sent in body of the response as a JSON Result as specified in spec when an error
     * occurs.
     */
    public enum ErrorCodes {
        invalid_resource_id,
        invalid_scope;
    }

    /**
     * Contains error messages to be logged when an invalid body parameter is sent in the request body.
     */
    public enum ErrorMessages {

        ERROR_MESSAGE_INVALID_RESOURCE_ID("Permission request failed with bad resource ID."),
        ERROR_MESSAGE_INVALID_RESOURCE_SCOPE("Permission request failed with bad resource scope."),
        ERROR_MESSAGE_PERSISTING_PT_DETAILS("Error occurred while storing PT details."),
        ERROR_MESSAGE_PERSISTING_PT("Error occurred while persisting PT.");


        private final String message;

        ErrorMessages(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }


    public enum ErrorMessage {
        ERROR_BAD_REQUEST_INVALID_RESOURCE_ID("6001","Permission request failed with bad resource ID."),
        ERROR_BAD_REQUEST_INVALID_RESOURCE_SCOPE("6002", "Permission request failed with bad resource scope."),
        ERROR_INTERNAL_SERVER_ERROR_FAILED_TO_PERSIST_PT("6003", "Error occurred while persisting PT."),
        ERROR_INTERNAL_SERVER_ERROR_FAILED_TO_PERSIST_PT_DETAILS("6004", "Error occurred while storing PT details."),
        ERROR_UNEXPECTED("6005", "Unexpected error.");

        private final String code;
        private final String messageyo;


        ErrorMessage(String code, String messageyo) {

            this.code = code;
            this.messageyo = messageyo;
        }

        public String getCode() {

            return this.code;
        }

        public String getMessageyo() {

            return this.messageyo;
        }

        @Override
        public String toString() {

            return code + " - " + messageyo;
        }

    }
}
