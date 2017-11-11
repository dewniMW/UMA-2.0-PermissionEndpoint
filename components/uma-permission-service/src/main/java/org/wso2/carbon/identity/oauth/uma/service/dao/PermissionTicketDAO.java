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
import org.wso2.carbon.identity.oauth.uma.service.model.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Data Access Layer functionality for Permission Endpoint. This includes storing requested permissions
 * (requested resource ids with their scopes).
 */
public class PermissionTicketDAO {

    /**
     * Issue a permission ticket. Permission ticket represents the resources requested by the resource server on
     * client's behalf
     *
     * @param resourceList A list with the resource ids and the corresponding scopes.
     * @throws PermissionAPIException       Exception thrown when there is an issue with the database connection.
     * @throws PermissionTicketDAOException Exception thrown when there is an issue persisting the permission ticket.
     * @throws ResourceIdDAOException       Exception thrown when there is an invalid resource id.
     * @throws ResourceScopeDAOException    Exception thrown when there is an invalid resource scope.
     */

    public void persist(List<Resource> resourceList, PermissionTicketDO permissionTicketDO) throws
            PermissionAPIException, PermissionTicketDAOException, ResourceIdDAOException, ResourceScopeDAOException {

        // Persist ticket related details.
        try (Connection connection = IdentityDatabaseUtil.getDBConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "IDN_PERMISSION_TICKET (PT, TIME_CREATED, VALIDITY_PERIOD, TICKET_STATE) VALUES (?,?,?,?)")) {
                preparedStatement.setString(1, permissionTicketDO.getTicket());
                /*preparedStatement.setTimestamp(2, permissionTicketDO.getTimestamp(),
                        permissionTicketDO.getCreatedTime());*/
                preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()),
                        permissionTicketDO.getCreatedTime());
                preparedStatement.setLong(3, permissionTicketDO.getValidityPeriod());
                preparedStatement.setString(4, permissionTicketDO.getStatus());

                preparedStatement.execute();

                // Checking if the PT is persisted in the db.
                long id;
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getLong(1);
                    } else {
                        throw new PermissionTicketDAOException("Failed to persist Permission Ticket.");
                    }
                }

                for (Resource resource : resourceList) {
                    try (PreparedStatement resourceIdStatement = connection.prepareStatement("INSERT INTO " +
                            "IDN_PT_RESOURCE (PT_RESOURCE_ID, PT_ID) VALUES ((SELECT ID FROM IDN_RESOURCE WHERE " +
                            "RESOURCE_ID = ?), ?)")) {
                        resourceIdStatement.setString(1, resource.getResourceId());
                        resourceIdStatement.setLong(2, id);
                        resourceIdStatement.execute();

                        try (ResultSet resultSet = resourceIdStatement.getGeneratedKeys()) {
                            if (resultSet.next()) {
                                try (PreparedStatement resourceScopeStatement = connection.prepareStatement
                                        ("INSERT INTO IDN_PT_RESOURCE_SCOPE (PT_RESOURCE_ID, PT_SCOPE_ID) VALUES " +
                                                "(?, (SELECT SCOPE_ID FROM IDN_OAUTH2_SCOPE WHERE NAME = ?))")) {
                                    long resourceId = resultSet.getLong(1);
                                    for (String scope : resource.getResourceScopes()) {
                                        resourceScopeStatement.setLong(1, resourceId);
                                        resourceScopeStatement.setString(2, scope);
                                        resourceScopeStatement.addBatch();
                                    }
                                    resourceScopeStatement.executeBatch();
                                }
                            }
                        } catch (SQLException e) {
                            throw new ResourceScopeDAOException("Failed to persist resource scopes.", e);
                        }
                    } catch (SQLException e) {
                        throw new ResourceIdDAOException("Failed to persist resource Id.", e);
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw new PermissionAPIException("Error occurred while storing PT details.", e);
        }
    }
}
