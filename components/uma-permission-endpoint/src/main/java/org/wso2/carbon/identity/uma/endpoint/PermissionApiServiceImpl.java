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

package org.wso2.carbon.identity.uma.endpoint;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.oauth.uma.service.PermissionService;
import org.wso2.carbon.identity.oauth.uma.service.UMAErrorCodes;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionAPIException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionTicketDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.ResourceIdDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.ResourceScopeDAOException;
import org.wso2.carbon.identity.oauth.uma.service.model.PermissionTicket;
import org.wso2.carbon.identity.oauth.uma.service.model.Resource;
import org.wso2.carbon.identity.uma.endpoint.dto.PTFailDTO;
import org.wso2.carbon.identity.uma.endpoint.dto.PTSuccessDTO;
import org.wso2.carbon.identity.uma.endpoint.dto.ResourceDetailsDTO;
import org.wso2.carbon.identity.uma.endpoint.dto.ResourceDetailsInnerDTO;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

public class PermissionApiServiceImpl extends PermissionApiService {

    private static Logger logger = LoggerFactory.getLogger(PermissionApiServiceImpl.class);
    private Gson gson = new Gson();
    private PTFailDTO ptFailDTO = new PTFailDTO();

    @Override
    public Response registerPermission(String PAT, ResourceDetailsDTO requestedPermission) {

        PermissionService permissionService = (PermissionService) PrivilegedCarbonContext.getThreadLocalCarbonContext()
                .getOSGiService(PermissionService.class, null);

        List<Resource> resourceList = new ArrayList<>();
        for (ResourceDetailsInnerDTO resourceDetailsInnerDTO : requestedPermission) {
            Resource resource = new Resource();
            resource.setResourceId(resourceDetailsInnerDTO.getResource_id());
            List<String> resourceScopesList = new ArrayList<>();
            for (String scopeString : resourceDetailsInnerDTO.getResource_scopes()) {
                resourceScopesList.add(scopeString);
            }
            resource.setResourceScopes(resourceScopesList);
            resourceList.add(resource);
        }

        PermissionTicket permissionTicket = null;
        try {
            permissionTicket = permissionService.issuePermissionTicket(resourceList);
        } catch (PermissionTicketDAOException e) {
            return Response.serverError().build();
        } catch (ResourceScopeDAOException e) {
            ptFailDTO.setError(UMAErrorCodes.INVALID_SCOPE);
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(ptFailDTO)).build();
        } catch (ResourceIdDAOException e) {
            ptFailDTO.setError(UMAErrorCodes.INVALID_RESOURCE_ID);
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(ptFailDTO)).build();
        } catch (PermissionAPIException e) {
            return Response.serverError().build();
        }

        logger.info("Permission Ticket created.");
        PTSuccessDTO ptSuccessDTO = new PTSuccessDTO();
        ptSuccessDTO.setTicket(permissionTicket.getTicket());
        return Response.status(Response.Status.CREATED).entity(gson.toJson(ptSuccessDTO)).build();

    }

}
