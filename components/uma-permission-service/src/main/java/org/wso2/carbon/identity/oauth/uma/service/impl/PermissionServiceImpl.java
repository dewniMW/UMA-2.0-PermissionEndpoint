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

package org.wso2.carbon.identity.oauth.uma.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth.uma.service.PermissionService;
import org.wso2.carbon.identity.oauth.uma.service.ReadPropertiesFile;
import org.wso2.carbon.identity.oauth.uma.service.UMAConstants;
import org.wso2.carbon.identity.oauth.uma.service.dao.PermissionTicketDAO;
import org.wso2.carbon.identity.oauth.uma.service.dao.PermissionTicketDO;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionAPIException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionServiceException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionServiceRuntimeException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionTicketDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.ResourceIdDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.ResourceScopeDAOException;
import org.wso2.carbon.identity.oauth.uma.service.model.PermissionTicketValues;
import org.wso2.carbon.identity.oauth.uma.service.model.Resource;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

/**
 * PermissionServiceImpl service is used for permission registration.
 */
public class PermissionServiceImpl implements PermissionService {

    private static Log log = LogFactory.getLog(PermissionServiceImpl.class);
    private static final String UTC = "UTC";
    private PermissionTicketDAO permissionTicketDAO = new PermissionTicketDAO();

    @Override
    public PermissionTicketDO issuePermissionTicket(List<Resource> resourceList) throws PermissionServiceException,
            PermissionServiceRuntimeException {

        /*PermissionTicketDAO permissionTicketDAO = new PermissionTicketDAO();*/
        PermissionTicketDO permissionTicketDO = new PermissionTicketDO();
        PermissionServiceException permissionServiceException;

        PermissionTicketValues permissionTicketValues = new PermissionTicketValues();

        ReadPropertiesFile.readFileConfigValues(permissionTicketValues);

        String ticketString = UUID.randomUUID().toString();
        permissionTicketDO.setTicket(ticketString);
        //permissionTicketDO.setTimestamp(new Timestamp(new Date().getTime()));
        permissionTicketDO.setCreatedTime(Calendar.getInstance(TimeZone.getTimeZone(UTC)));
        permissionTicketDO.setValidityPeriod(permissionTicketValues.getValidityPeriod());
        permissionTicketDO.setStatus(permissionTicketValues.getStatus());

        try {
            permissionTicketDAO.persist(resourceList, permissionTicketDO);
        } catch (PermissionAPIException e) {
            log.error("Internal server error occurred. ", e);
            throw new PermissionServiceRuntimeException("Error occurred while persisting permission ticket and " +
                    "requested permissions.", e);
        } catch (PermissionTicketDAOException e) {
            log.error("Internal server error occurred. ", e);
            throw new PermissionServiceRuntimeException("Error occurred while persisting permission ticket and " +
                    "requested permissions.", e);
        } catch (ResourceIdDAOException e) {
            log.error(UMAConstants.ErrorMessages.ERROR_MESSAGE_INVALID_RESOURCE_ID, e);
            permissionServiceException = new PermissionServiceException("Error occurred while persisting permission " +
                    "ticket and requested permissions.", e);
            permissionServiceException.setErrorCode(UMAConstants.ErrorCodes.INVALID_RESOURCE_ID);
            throw permissionServiceException;
        } catch (ResourceScopeDAOException e) {
            log.error(UMAConstants.ErrorMessages.ERROR_MESSAGE_INVALID_RESOURCE_SCOPE, e);
            permissionServiceException = new PermissionServiceException("Error occurred while persisting permission " +
                    "ticket and requested permissions.", e);
            permissionServiceException.setErrorCode(UMAConstants.ErrorCodes.INVALID_SCOPE);
            throw permissionServiceException;
        }

        return permissionTicketDO;

    }
}
