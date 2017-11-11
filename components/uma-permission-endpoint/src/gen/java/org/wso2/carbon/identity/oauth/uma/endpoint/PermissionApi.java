package org.wso2.carbon.identity.oauth.uma.endpoint;

import io.swagger.annotations.ApiParam;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.ResourceModelDTO;
import org.wso2.carbon.identity.oauth.uma.endpoint.dto.SuccessDTO;
import org.wso2.carbon.identity.oauth.uma.endpoint.factories.PermissionApiServiceFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/permission")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(value = "/permission", description = "the permission API")
public class PermissionApi  {

   private final PermissionApiService delegate = PermissionApiServiceFactory.getPermissionApi();

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Permission Endpoint.", notes = "This API is used by Resource Server to request permissions on Client's Behalf With Authorization Server.\n", response = SuccessDTO.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Created"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request") })

    public Response registerPermission(@ApiParam(value = "The resource owner ID is defined to obtain of the list of resources to be retrieved.",required=true) @QueryParam("resourceOwnerId") String resourceOwnerId,
    @ApiParam(value = "The requested permissions."  ) ResourceModelDTO requestedPermission)
    {
    return delegate.registerPermission(resourceOwnerId,requestedPermission);
    }
}

