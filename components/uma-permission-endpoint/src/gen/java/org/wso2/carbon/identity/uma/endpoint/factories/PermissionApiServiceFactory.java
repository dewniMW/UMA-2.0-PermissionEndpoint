package org.wso2.carbon.identity.uma.endpoint.factories;

import org.wso2.carbon.identity.uma.endpoint.PermissionApiService;
import org.wso2.carbon.identity.uma.endpoint.PermissionApiServiceImpl;

public class PermissionApiServiceFactory {

   private final static PermissionApiService service = new PermissionApiServiceImpl();

   public static PermissionApiService getPermissionApi()
   {
      return service;
   }
}
