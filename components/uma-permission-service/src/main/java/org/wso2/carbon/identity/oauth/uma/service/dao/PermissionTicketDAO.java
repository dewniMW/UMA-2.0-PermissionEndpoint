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

import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionAPIException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionTicketDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.ResourceIdDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.ResourceScopeDAOException;
import org.wso2.carbon.identity.oauth.uma.service.model.PermissionTicket;
import org.wso2.carbon.identity.oauth.uma.service.model.PermissionTicketImpl;
import org.wso2.carbon.identity.oauth.uma.service.model.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Data Access Layer functionality for Permission Endpoint. This includes storing requested permissions
 * (requested resource ids with their scopes).
 */
public class PermissionTicketDAO {

    /**
     * Issue a permission ticket.
     *
     * @param resourceList A list with the resource ids and the corresponding scopes.
     * @return PermissionTicket
     * @throws PermissionAPIException
     * @throws PermissionTicketDAOException
     * @throws ResourceIdDAOException
     * @throws ResourceScopeDAOException
     */
    public PermissionTicket issue(List<Resource> resourceList) throws PermissionAPIException,
            PermissionTicketDAOException, ResourceIdDAOException, ResourceScopeDAOException {

        PermissionTicket permissionTicketImpl = new PermissionTicketImpl();

        // Generate PT String.
        String ticketString = UUID.randomUUID().toString();
        permissionTicketImpl.setTicket(ticketString);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        permissionTicketImpl.setCreatedTime(timestamp);
        permissionTicketImpl.setValidityPeriod(3600000);
        permissionTicketImpl.setStatus("ACTIVE");

        // Persist ticket related details.
        try (Connection connection = IdentityDatabaseUtil.getDBConnection()) {

            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.STORE_PT_QUERY)) {
                preparedStatement.setString(1, permissionTicketImpl.getTicket());
                preparedStatement.setTimestamp(2, permissionTicketImpl.getCreatedTime());
                preparedStatement.setLong(3, permissionTicketImpl.getValidityPeriod());
                preparedStatement.setString(4, permissionTicketImpl.getStatus());

                preparedStatement.execute();

                // Checking if the PT is persisted in the db.
                long id;
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getLong(1);
                    } else {
                        connection.rollback();
                        throw new PermissionTicketDAOException("Failed to persist Permission Ticket.");
                    }
                }

                for (Resource resource : resourceList) {
                    try (PreparedStatement preparedStatement1 = connection.prepareStatement(SQLQueries.STORE_PT_RESOURCE_IDS_QUERY)) {
                        preparedStatement1.setString(1, resource.getResourceId());
                        preparedStatement1.setLong(2, id);
                        preparedStatement1.execute();

                        try (ResultSet resultSet = preparedStatement1.getGeneratedKeys()) {
                            if (resultSet.next()) {
                                try (PreparedStatement resourceScopeStatement = connection.prepareStatement(SQLQueries
                                        .STORE_PT_RESOURCE_SCOPES_QUERY)) {
                                    long resourceId = resultSet.getLong(1);
                                    for (String scope : resource.getResourceScopes()) {
                                        resourceScopeStatement.setLong(1, resourceId);
                                        resourceScopeStatement.setString(2, scope);
                                        resourceScopeStatement.addBatch();
                                    }
                                    resourceScopeStatement.executeBatch();
                                }
                            }
                        } catch (SQLException e){
                            throw new ResourceScopeDAOException("Failed to persist resource scopes.",e);
                        }
                    } catch (SQLException e){
                        throw new ResourceIdDAOException("Failed to persist resource Id.",e);
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw new PermissionAPIException("Error occurred while storing PT details.", e);
        }
        return permissionTicketImpl;
    }

}
