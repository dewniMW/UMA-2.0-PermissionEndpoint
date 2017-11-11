package org.wso2.carbon.identity.oauth.uma.endpoint.util;

import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.oauth.uma.service.PermissionService;

/**
 * This class holds Utils for permission endpoint component
 */
public class PermissionUtil {

    public static PermissionService getPermissionService() {
        return (PermissionService) PrivilegedCarbonContext.getThreadLocalCarbonContext()
                .getOSGiService(PermissionService.class, null);
    }

//    public static void hi(ResourceModelDTO resourceModelDTO){
//
//        List<Resource> resourceList = new ArrayList<>();
//
//        for (ResourceModelInnerDTO resourceModelInnerDTO : resourceModelDTO) {
//            Resource resource = new Resource();
//            resource.setResourceId(resourceModelInnerDTO.getResource_id());
//            List<String> resourceScopesList = new ArrayList<>();
//            for (String scopeString : resourceModelInnerDTO.getResource_scopes()) {
//                resourceScopesList.add(scopeString);
//            }
//            resource.setResourceScopes(resourceScopesList);
//            resourceList.add(resource);
//        }
//    }
}
