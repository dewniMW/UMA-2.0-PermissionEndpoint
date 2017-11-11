package org.wso2.carbon.identity.uma.endpoint;

import org.wso2.carbon.identity.uma.endpoint.dto.PTSuccessDTO;
import org.wso2.carbon.identity.uma.endpoint.dto.ResourceDetailsDTO;
import org.wso2.carbon.identity.uma.endpoint.factories.PermissionApiServiceFactory;
import org.wso2.carbon.identity.uma.endpoint.dto.*;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/permission")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(value = "/permission", description = "the permission API")
public class PermissionApi  {

   private final PermissionApiService delegate = PermissionApiServiceFactory.getPermissionApi();

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Permission Endpoint.", notes = "This API is used by Resource Server to request permissions on Client's Behalf With Authorization Server.\n", response = PTSuccessDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Created"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request") })

    public Response registerPermission(@ApiParam(value = "An access token with the scope uma_protection, used by the resource server as a client of the authorization server's protection API." ,required=true )@HeaderParam("PAT") String PAT,
    @ApiParam(value = "The requested permissions."  ) ResourceDetailsDTO requestedPermission)
    {
    return delegate.registerPermission(PAT,requestedPermission);
    }
}

