package org.wso2.carbon.identity.oauth.uma.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.base.IdentityConstants;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.ErrorResponseDTO;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.PermissionTicketResponseDTO;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.ResourceModelDTO;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.ResourceModelInnerDTO;
import org.wso2.carbon.identity.oauth.uma.endpoint.exception.PermissionEndpointException;
import org.wso2.carbon.identity.oauth.uma.service.PermissionService;
import org.wso2.carbon.identity.oauth.uma.service.UMAConstants;
import org.wso2.carbon.identity.oauth.uma.service.exception.UMAClientException;
import org.wso2.carbon.identity.oauth.uma.service.exception.UMAException;
import org.wso2.carbon.identity.oauth.uma.service.exception.UMAServerException;
import org.wso2.carbon.identity.oauth.uma.service.model.PermissionTicketDO;
import org.wso2.carbon.identity.oauth.uma.service.model.Resource;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

/**
 * PermissionApiServiceImpl is used to request permission ticket.
 */
public class PermissionApiServiceImpl extends PermissionApiService {

    private static Log log = LogFactory.getLog(org.wso2.carbon.identity.oauth.uma.endpoint.PermissionApiServiceImpl.class);
    private List<Resource> resourceList;

    @Override
    public Response requestPermission(String PAT, ResourceModelDTO requestedPermission) {
        PermissionService permissionService = (PermissionService) PrivilegedCarbonContext.getThreadLocalCarbonContext()
                .getOSGiService(PermissionService.class, null);

        if (requestedPermission == null) {
            log.error("Empty request body.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        getPermissionTicketRequest(requestedPermission);

        PermissionTicketDO permissionTicketDO = null;

        try {
            permissionTicketDO = permissionService.issuePermissionTicket(resourceList);
        } catch (UMAClientException e) {
            if (log.isDebugEnabled()) {
                log.debug("Client error while requesting permission ticket.", e);
            }
            handleErrorResponse(e, log);
        } catch (UMAServerException e) {
            handleErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, e, true, log);
        } catch (Throwable throwable) {
            handleErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, throwable, true, log);
        }

        PermissionTicketResponseDTO permissionTicketResponseDTO = new PermissionTicketResponseDTO();
        permissionTicketResponseDTO.setTicket(permissionTicketDO.getTicket());

        if (log.isDebugEnabled()) {
            if (IdentityUtil.isTokenLoggable(IdentityConstants.IdentityTokens.ACCESS_TOKEN)) {
                log.debug("Permission Ticket created: " + permissionTicketResponseDTO.getTicket());
            } else {
                // Avoid logging token since its a sensitive information.
                log.debug("Permission Ticket created.");
            }
        }
        return Response.status(Response.Status.CREATED).entity(permissionTicketResponseDTO).build();
    }

    private void getPermissionTicketRequest(ResourceModelDTO requestedPermission) {
        resourceList = new ArrayList<>();
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
    }

    private static void handleErrorResponse(UMAException umaException, Log log) throws PermissionEndpointException {

        String code = umaException.getCode();

        String statusCode;
        String errorCode = null;
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        boolean isStatusOnly = true;

        if (code != null) {
            org.wso2.carbon.identity.oauth.uma.endpoint.PermissionEndpointConstants.getResponseDataMap();
            if (org.wso2.carbon.identity.oauth.uma.endpoint.PermissionEndpointConstants.responseDataMap.containsKey(code)) {
                statusCode = org.wso2.carbon.identity.oauth.uma.endpoint.PermissionEndpointConstants.responseDataMap.get(code)[0];
                errorCode = org.wso2.carbon.identity.oauth.uma.endpoint.PermissionEndpointConstants.responseDataMap.get(code)[1];
                status = Response.Status.fromStatusCode(Integer.parseInt(statusCode));
                isStatusOnly = false;
            }
        }

        throw buildPermissionEndpointException(status, errorCode, umaException.getMessage(), isStatusOnly);
    }

    /**
     * Logs the error, builds a PermissionEndpointException with specified details and throws it.
     *
     * @param status    response status
     * @param throwable throwable
     * @throws PermissionEndpointException
     */
    private static void handleErrorResponse(Response.Status status, Throwable throwable,
                                            boolean isServerException, Log log)
            throws PermissionEndpointException {

        String code;
        if (throwable instanceof UMAException) {
            code = ((UMAException) throwable).getCode();
        } else {
            code = UMAConstants.ErrorMessages.ERROR_UNEXPECTED.getCode();
        }

        if (isServerException) {
            if (throwable == null) {
                log.error(status.getReasonPhrase());
            } else {
                log.error(status.getReasonPhrase(), throwable);
            }
        }
        throw buildPermissionEndpointException(status, code, throwable == null ? "" : throwable.getMessage(),
                isServerException);
    }

    private static PermissionEndpointException buildPermissionEndpointException(Response.Status status,
                                                                                String errorCode, String description,
                                                                                boolean isStatusOnly) {
        if (isStatusOnly) {
            return new PermissionEndpointException(status);
        } else {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
            errorResponseDTO.setError(errorCode);
            errorResponseDTO.setErrorDescription(description);
            return new PermissionEndpointException(status, errorResponseDTO);
        }
    }
}
