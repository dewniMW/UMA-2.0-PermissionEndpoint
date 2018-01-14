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

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.oauth.uma.service.PermissionService;

@PrepareForTest({BundleContext.class, ServiceTracker.class, PrivilegedCarbonContext.class, PermissionService.class})
public class PermissionApiServiceImplTest extends PowerMockTestCase {

   /* @Mock
    BundleContext mockBundleContext;

    @Mock
    ServiceTracker mockServiceTracker;

    @Mock
    private PermissionService mockPermissionService;

    @Mock
    private PermissionTicketDO mockPermissionTicketDO;

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    @BeforeMethod
    public void setUp() throws Exception {
        //Get OSGIservice by starting the tenant flow.
        whenNew(ServiceTracker.class).withAnyArguments().thenReturn(mockServiceTracker);
        TestUtil.startTenantFlow("carbon.super");
        Object[] services = new Object[1];
        services[0] = mockPermissionService;
        when(mockServiceTracker.getServices()).thenReturn(services);
        OSGiDataHolder.getInstance().setBundleContext(mockBundleContext);
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testRegisterPermission() throws Exception {
        PermissionTicketDO permissionTicketDO = new PermissionTicketDO();
        when(mockPermissionService.issuePermissionTicket(anyList())).thenReturn(permissionTicketDO);
        when(mockPermissionTicketDO.getTicket()).thenReturn(anyString());
        PermissionApiServiceImpl permissionApiService = new PermissionApiServiceImpl();
        ResourceModelDTO resourceModelDTO = new ResourceModelDTO();
        Assert.assertEquals(permissionApiService.registerPermission("1", resourceModelDTO).getStatus(),
                Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void testRegisterPermissionWithPermissionServiceException() throws Exception {
        when(mockPermissionService.issuePermissionTicket(anyList())).thenThrow(new PermissionServiceException
                ("dummyException"));
        PermissionApiServiceImpl permissionApiService = new PermissionApiServiceImpl();
        ResourceModelDTO resourceModelDTO = new ResourceModelDTO();
        Assert.assertEquals(permissionApiService.registerPermission("1", resourceModelDTO).getStatus(),
                Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testRegisterPermissionWithPermissionServiceRuntimeException() throws Exception {
        when(mockPermissionService.issuePermissionTicket(anyList())).thenThrow(new PermissionServiceRuntimeException
                ("dummyException"));
        PermissionApiServiceImpl permissionApiService = new PermissionApiServiceImpl();
        ResourceModelDTO resourceModelDTO = new ResourceModelDTO();
        Assert.assertEquals(permissionApiService.registerPermission("1", resourceModelDTO).getStatus(),
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }*/
}
