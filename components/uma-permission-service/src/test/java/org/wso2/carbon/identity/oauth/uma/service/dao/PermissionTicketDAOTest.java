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

package org.wso2.carbon.identity.oauth.uma.service.dao;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.oauth.uma.service.dao.utils.DAOUtils;

/**
 * Unit tests for PermissionTicketDAO.
 */
@PrepareForTest(IdentityDatabaseUtil.class)
public class PermissionTicketDAOTest extends DAOUtils {

    /*private static final String DB_NAME = "UMA_DB";

    private PermissionTicketDAO permissionTicketDAO;

    @BeforeClass
    public void setUp() throws Exception {
        initiateH2Base(DB_NAME, getFilePath("permission.sql"));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        createOauth2ScopeTable(DB_NAME, 1, "scope01", "scopeDescription01");
        createResourceTable(DB_NAME, 1, "1", "photo01", timestamp, "1",
                1234);
        createPTTable(DB_NAME, 1, "12345", timestamp, 3600000, "ACTIVE");
        createPTResourceTable(DB_NAME, 1, 1, 1);
        createPTResourceScopeTable(DB_NAME, 1, 1, 1);
        permissionTicketDAO = new PermissionTicketDAO();
    }

    @AfterClass
    public void tearDown() throws Exception {
        closeH2Base(DB_NAME);
    }

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    @Test
    public void testPersist() throws Exception {
        mockStatic(IdentityDatabaseUtil.class);
        try (Connection connection = DAOUtils.getConnection(DB_NAME)) {
            when(IdentityDatabaseUtil.getDBConnection()).thenReturn(connection);
            List<Resource> list = new ArrayList<>();
            list.add(getResource());
            permissionTicketDAO.persist(list, getPermissionTicketDO());
        }
    }

    *//**
     * Test persisting a permission with an invalid resource id.
     *//*
    @Test(expectedExceptions = ResourceIdDAOException.class)
    public void testPersistInvalidResourceId() throws Exception {
        mockStatic(IdentityDatabaseUtil.class);
        try (Connection connection = DAOUtils.getConnection(DB_NAME)) {
            when(IdentityDatabaseUtil.getDBConnection()).thenReturn(connection);
            List<Resource> list = new ArrayList<>();
            list.add(getResourceWithInvalidResourceId());
            // This should throw an exception.
            permissionTicketDAO.persist(list, getPermissionTicketDO());


        }
    }

    *//**
     * Test persisting a permission with an invalid resource scope.
     *//*
    @Test(expectedExceptions = ResourceScopeDAOException.class)
    public void testPersistInvalidResourceScope() throws Exception {
        mockStatic(IdentityDatabaseUtil.class);
        try (Connection connection = DAOUtils.getConnection(DB_NAME)) {
            when(IdentityDatabaseUtil.getDBConnection()).thenReturn(connection);
            List<Resource> list = new ArrayList<>();
            list.add(getResourceWithInvalidResourceScope());
            // This should throw an exception.
            permissionTicketDAO.persist(list, getPermissionTicketDO());
        }
    }

    *//**
     * Test persisting an empty resource or empty permission ticket
     *//*
    @Test(expectedExceptions = PermissionAPIException.class)
    public void testPersistEmptyPermission() throws Exception {
        mockStatic(IdentityDatabaseUtil.class);
        try (Connection connection = DAOUtils.getConnection(DB_NAME)) {
            when(IdentityDatabaseUtil.getDBConnection()).thenReturn(connection);
            List<Resource> list = new ArrayList<>();
            // This should throw an exception.
            permissionTicketDAO.persist(list, new PermissionTicketDO());
        }
    }


    private PermissionTicketDO getPermissionTicketDO() {
        PermissionTicketDO permissionTicketDO = new PermissionTicketDO();
        permissionTicketDO.setTicket(UUID.randomUUID().toString());
        permissionTicketDO.setStatus("ACTIVE");
        //permissionTicketDO.setTimestamp(new Timestamp(new Date().getTime()));
        permissionTicketDO.setCreatedTime(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        permissionTicketDO.setValidityPeriod(3600000);
        return permissionTicketDO;
    }

    private Resource getResource() {
        Resource resource = new Resource();
        resource.setResourceId("1");
        List<String> resourceScopeList = new ArrayList<>();
        resourceScopeList.add("scope01");
        resource.setResourceScopes(resourceScopeList);
        return resource;
    }

    private Resource getResourceWithInvalidResourceId() {
        Resource resource = new Resource();
        // Invalid resource ID.
        resource.setResourceId("10");
        List<String> resourceScopeList = new ArrayList<>();
        resourceScopeList.add("scope01");
        resource.setResourceScopes(resourceScopeList);
        return resource;
    }

    private Resource getResourceWithInvalidResourceScope() {
        Resource resource = new Resource();
        resource.setResourceId("1");
        List<String> resourceScopeList = new ArrayList<>();
        // Invalid resource scope.
        resourceScopeList.add("scope02");
        resource.setResourceScopes(resourceScopeList);
        return resource;
    }*/
}
