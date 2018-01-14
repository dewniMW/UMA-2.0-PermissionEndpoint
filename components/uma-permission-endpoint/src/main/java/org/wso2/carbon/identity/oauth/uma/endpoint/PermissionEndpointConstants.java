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

package org.wso2.carbon.identity.oauth.uma.endpoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains the http status codes and error codes to be sent in body of the response as a JSON Result as specified in
 * spec when an error occurs.
 */
public final class PermissionEndpointConstants {

    public static final Map<String, String[]> responseDataMap = new HashMap<String, String[]>();

    public static void getResponseDataMap() {

        String id;

        String[] invalidResourceIdArray = new String[]{"400", "invalid_resource_id"};
        id = "6001";
        responseDataMap.put(id, invalidResourceIdArray);

        String[] invalidResourceScopeArray = new String[]{"400", "invalid_scope"};
        id = "6002";
        responseDataMap.put(id, invalidResourceScopeArray);
    }

}
