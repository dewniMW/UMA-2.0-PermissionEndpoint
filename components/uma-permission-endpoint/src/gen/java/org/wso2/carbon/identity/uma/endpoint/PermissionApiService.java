package org.wso2.carbon.identity.uma.endpoint;

import org.wso2.carbon.identity.uma.endpoint.dto.ResourceDetailsDTO;
import org.wso2.carbon.identity.uma.endpoint.*;
import org.wso2.carbon.identity.uma.endpoint.dto.*;

import javax.ws.rs.core.Response;

public abstract class PermissionApiService {
    public abstract Response registerPermission(String PAT,ResourceDetailsDTO requestedPermission);
}

