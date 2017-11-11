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

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.base.IdentityConstants;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.ErrorDTO;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.ResourceModelDTO;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.ResourceModelInnerDTO;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.SuccessDTO;
import org.wso2.carbon.identity.oauth.uma.service.PermissionService;
import org.wso2.carbon.identity.oauth.uma.service.dao.PermissionTicketDO;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionServiceException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionServiceRuntimeException;
import org.wso2.carbon.identity.oauth.uma.service.model.Resource;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

/**
 * PermissionApiServiceImpl is used to register permissions.
 */
public class PermissionApiServiceImpl extends PermissionApiService {


    private static Log log = LogFactory.getLog(PermissionApiServiceImpl.class);
    //private List<Resource> resourceList = new ArrayList<>();

    /**
     * Register permissions requested by the resource server on client's behalf.
     *
     * @param resourceOwnerId     Identifies the owner of the requested resource(s).
     * @param requestedPermission Permissions requested.
     * @return Response with the created permission ticket.
     */
    @Override
    public Response registerPermission(String resourceOwnerId, ResourceModelDTO requestedPermission) {
        PermissionService permissionService = (PermissionService) PrivilegedCarbonContext.getThreadLocalCarbonContext()
                .getOSGiService(PermissionService.class, null);

        List<Resource> resourceList = new ArrayList<>();

        try {
            for (ResourceModelInnerDTO resourceModelInnerDTO : requestedPermission) {
                Resource resource = new Resource();
                resource.setResourceId(resourceModelInnerDTO.getResource_id());
                List<String> resourceScopesList = new ArrayList<>();
                for (String scopeString : resourceModelInnerDTO.getResource_scopes()) {
                    resourceScopesList.add(scopeString);
                }
                resource.setResourceScopes(resourceScopesList);
                resourceList.add(resource);
            }
        } catch (Exception e) {
            log.error("Invalid request. ", e);
            return Response.serverError().build();
        }
        /*try {
            getRequestedPermission(requestedPermission);
        } catch (Exception e) {
            log.error("Invalid request. ", e);
            return Response.serverError().build();
        }
*/
        PermissionTicketDO permissionTicketDO = null;

        try {
            permissionTicketDO = permissionService.issuePermissionTicket(resourceList);
        } catch (PermissionServiceException e) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError(e.getErrorCode());
            Gson gson = new Gson();
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(errorDTO)).build();
            /*handleErrorResponse(Response.Status.BAD_REQUEST,
                    Response.Status.BAD_REQUEST.getReasonPhrase(), e, false, log);*/
        } catch (PermissionServiceRuntimeException e) {
            log.error("Internal server error occurred. ", e);
            return Response.serverError().build();
            /*handleErrorResponse(Response.Status.INTERNAL_SERVER_ERROR,
                    Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e, true, log);*/
        } catch (Throwable throwable) {
            log.error("Internal server error occurred. ", throwable);
            return Response.serverError().build();
        }

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setTicket(permissionTicketDO.getTicket());

        if (log.isDebugEnabled()) {
            if (IdentityUtil.isTokenLoggable(IdentityConstants.IdentityTokens.ACCESS_TOKEN)) {
                log.debug("Permission Ticket created: " + successDTO.getTicket());
            } else {
                // Avoid logging token since its a sensitive information.
                log.debug("Permission Ticket created.");
            }
        }

        return Response.status(Response.Status.CREATED).entity(successDTO).build();
    }

    /**
     * @param resourceModelDTO An object array containing the requested permissions
     */
    /*private void getRequestedPermission(ResourceModelDTO resourceModelDTO) {

        for (ResourceModelInnerDTO resourceModelInnerDTO : resourceModelDTO) {
            Resource resource = new Resource();
            resource.setResourceId(resourceModelInnerDTO.getResource_id());
            List<String> resourceScopesList = new ArrayList<>();
            for (String scopeString : resourceModelInnerDTO.getResource_scopes()) {
                resourceScopesList.add(scopeString);
            }
            resource.setResourceScopes(resourceScopesList);
            resourceList.add(resource);
        }
    }
*/
    /*public static void handleErrorResponse(Response.Status status, String message, Throwable throwable,
                                           boolean isServerException, Log log)
            throws PermissionEndpointException {

        String errorCode = null;
        if (throwable instanceof PermissionServiceException) {
            errorCode = ((PermissionServiceException) throwable).getErrorCode();
        } else {
            errorCode = UMAConstants.ErrorMessages.ERROR_CODE_UNEXPECTED;
        }

        if (isServerException) {
            if (throwable == null) {
                log.error(message);
            } else {
                log.error(message, throwable);
            }
        }
        throw buildScopeEndpointException(status, errorCode, isServerException);
    }*/

    /*private static PermissionEndpointException buildScopeEndpointException(Response.Status status, String error,
                                                                           boolean isServerException) {
        ErrorDTO errorDTO = getErrorDTO(error);
        if (isServerException) {
            return new PermissionEndpointException(status);
        } else {
            return new PermissionEndpointException(status, errorDTO);
        }
    }

    public static ErrorDTO getErrorDTO(String error) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError(error);
//        errorDTO.setErrorDescription(errorDescription);
//        errorDTO.setErrorUri(errorUri);
        return errorDTO;
    }*/
}
