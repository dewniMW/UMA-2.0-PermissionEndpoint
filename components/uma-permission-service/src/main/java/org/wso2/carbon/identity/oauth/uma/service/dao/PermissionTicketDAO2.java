package org.wso2.carbon.identity.oauth.uma.service.dao;

import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.oauth.uma.service.UMAConstants;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.UMAResourceException;
import org.wso2.carbon.identity.oauth.uma.service.model.PermissionTicketDO;
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
public class PermissionTicketDAO2 {

    private static final String STORE_PT_QUERY = "INSERT INTO IDN_PERMISSION_TICKET " +
            "(PT, TIME_CREATED, VALIDITY_PERIOD, TICKET_STATE, TENANT_ID) VALUES (?,?,?,?,?)";
    private static final String STORE_PT_RESOURCE_IDS_QUERY = "INSERT INTO IDN_PT_RESOURCE " +
            "(PT_RESOURCE_ID, PT_ID) VALUES " +
            "((SELECT ID FROM IDN_RESOURCE WHERE RESOURCE_ID = ?), ?)";
    private static final String STORE_PT_RESOURCE_SCOPES_QUERY = "INSERT INTO IDN_PT_RESOURCE_SCOPE " +
            "(PT_RESOURCE_ID, PT_SCOPE_ID) VALUES (?, " +
            "(SELECT SCOPE_ID FROM IDN_OAUTH2_SCOPE WHERE NAME = ?))";
    private static final String VALIDATE_REQUESTED_RESOURCE_IDS_WITH_REGISTERED_RESOURCE_IDS = "SELECT ID " +
            "FROM IDN_RESOURCE WHERE RESOURCE_ID = ?";
    private static final String VALIDATE_REQUESTED_RESOURCE_SCOPES_WITH_REGISTERED_RESOURCE_SCOPES = "SELECT SCOPE_ID" +
            " FROM IDN_OAUTH2_SCOPE WHERE NAME = ?";

    public static void persistPTandRequestedPermissions(List<Resource> resourceList,
                                                        PermissionTicketDO permissionTicketDO) throws
            UMAResourceException, PermissionDAOException {
        try (Connection connection = IdentityDatabaseUtil.getDBConnection()) {
            checkResourceIdsExistence(resourceList);
            checkResourceScopesExistence(resourceList);
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(STORE_PT_QUERY)) {
                preparedStatement.setString(1, permissionTicketDO.getTicket());
                preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()),
                        permissionTicketDO.getCreatedTime());
                preparedStatement.setLong(3, permissionTicketDO.getValidityPeriod());
                preparedStatement.setString(4, permissionTicketDO.getStatus());
                preparedStatement.setString(5, permissionTicketDO.getTenentId());
                preparedStatement.execute();

                // Checking if the PT is persisted in the db.
                long id;
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getLong(1);
                    } else {
                        throw new PermissionDAOException(UMAConstants.ErrorMessages
                                .ERROR_INTERNAL_SERVER_ERROR_FAILED_TO_PERSIST_PT);
                    }
                }
                for (Resource resource : resourceList) {
                    try (PreparedStatement resourceIdStatement = connection.prepareStatement(
                            STORE_PT_RESOURCE_IDS_QUERY)) {
                        resourceIdStatement.setString(1, resource.getResourceId());
                        resourceIdStatement.setLong(2, id);
                        resourceIdStatement.execute();

                        try (ResultSet resultSet = resourceIdStatement.getGeneratedKeys()) {
                            if (resultSet.next()) {
                                try (PreparedStatement resourceScopeStatement = connection.prepareStatement
                                        (STORE_PT_RESOURCE_SCOPES_QUERY)) {
                                    long resourceId = resultSet.getLong(1);
                                    for (String scope : resource.getResourceScopes()) {
                                        resourceScopeStatement.setLong(1, resourceId);
                                        resourceScopeStatement.setString(2, scope);
                                        resourceScopeStatement.addBatch();
                                    }
                                    resourceScopeStatement.executeBatch();
                                }
                            }
                        }
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw new PermissionDAOException(UMAConstants.ErrorMessages
                    .ERROR_INTERNAL_SERVER_ERROR_FAILED_TO_PERSIST_REQUESTED_PERMISSIONS, e);
        }
    }


    private static void checkResourceIdsExistence(List<Resource> resourceList) throws UMAResourceException,
            PermissionDAOException {
        try (Connection connection = IdentityDatabaseUtil.getDBConnection()) {
            for (Resource resource : resourceList) {
                try (PreparedStatement resourceIdStatement = connection.prepareStatement(
                        VALIDATE_REQUESTED_RESOURCE_IDS_WITH_REGISTERED_RESOURCE_IDS)) {
                    resourceIdStatement.setString(1, resource.getResourceId());
                    try (ResultSet resultSet = resourceIdStatement.executeQuery()) {
                        if (!resultSet.next()) {
                            throw new UMAResourceException(UMAConstants.ErrorMessages
                                    .ERROR_BAD_REQUEST_INVALID_RESOURCE_ID);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new PermissionDAOException(UMAConstants.ErrorMessages
                    .ERROR_INTERNAL_SERVER_ERROR_FAILED_TO_PERSIST_REQUESTED_PERMISSIONS, e);
        }
    }

    private static void checkResourceScopesExistence(List<Resource> resourceList) throws UMAResourceException,
            PermissionDAOException {
        try (Connection connection = IdentityDatabaseUtil.getDBConnection()) {
            for (Resource resource : resourceList) {
                try (PreparedStatement resourceScopeStatement = connection.prepareStatement(
                        VALIDATE_REQUESTED_RESOURCE_SCOPES_WITH_REGISTERED_RESOURCE_SCOPES)) {
                    for (String scope : resource.getResourceScopes()) {
                        resourceScopeStatement.setString(1, scope);
                        try (ResultSet resultSet = resourceScopeStatement.executeQuery()) {
                            if (!resultSet.next()) {
                                throw new UMAResourceException(UMAConstants.ErrorMessages
                                        .ERROR_BAD_REQUEST_INVALID_RESOURCE_SCOPE);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new PermissionDAOException(UMAConstants.ErrorMessages
                    .ERROR_INTERNAL_SERVER_ERROR_FAILED_TO_PERSIST_REQUESTED_PERMISSIONS, e);
        }
    }
}
