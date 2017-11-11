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
public final class UMAConstants {

    public static final String UMA_PERMISSION_ENDPOINT_CONFIG_PATH = "ptDetails.properties";

    /**
     * Contains error messages to be logged when an invalid body parameter is sent in the request body.
     */
    public static class ErrorMessages {
        public static final String ERROR_MESSAGE_INVALID_RESOURCE_ID = "Invalid resource id.";
        public static final String ERROR_MESSAGE_INVALID_RESOURCE_SCOPE = "Invalid resource scope.";
        public static final String ERROR_CODE_UNEXPECTED = "Unexpected error.";
    }

    /**
     * Contains the values to be sent in body of the response as a JSON Result as specified in spec when an error
     * occurs.
     */
    public static class ErrorCodes {
        public static final String INVALID_RESOURCE_ID = "invalid_resource_id";
        public static final String INVALID_SCOPE = "invalid_scope";
    }
}
