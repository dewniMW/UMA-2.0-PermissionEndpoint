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
import org.wso2.carbon.identity.oauth.uma.service.dao.PermissionTicketDAO;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionTicketDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.UMAServerException;
import org.wso2.carbon.identity.oauth.uma.service.exception.UMAClientException;
import org.wso2.carbon.identity.oauth.uma.service.exception.UMAResourceException;
import org.wso2.carbon.identity.oauth.uma.service.model.PermissionTicketDO;
import org.wso2.carbon.identity.oauth.uma.service.model.PermissionTicketValues;
import org.wso2.carbon.identity.oauth.uma.service.model.Resource;
import org.wso2.carbon.identity.xacmlEXP1.impl.XACMLBasedAuthorization;

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
    public PermissionTicketDO issuePermissionTicket(List<Resource> resourceList) throws UMAClientException, UMAServerException {

        /*PermissionTicketDAO permissionTicketDAO = new PermissionTicketDAO();*/
        PermissionTicketDO permissionTicketDO = new PermissionTicketDO();

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
        } catch (UMAResourceException e){
            //throw new UMAClientException(400, e.getErrorCode(), e.getMessage(), e);
            throw new UMAClientException( e.getCode(),e.getMessage(), e);
        } catch (PermissionDAOException e) {
            //throw new UMAServerException(e.getMessage(),e);
            throw new UMAServerException(e.getCode(), e.getMessage(),e);
        } catch (PermissionTicketDAOException e) {
            throw new UMAServerException(e.getCode(), e.getMessage(),e);
        }


        //evaluating policy
        //not needed for this
        XACMLBasedAuthorization xacmlBasedAuthorization = new XACMLBasedAuthorization();
        if(xacmlBasedAuthorization.isAuthorized()){
            log.info("successful");
        } else {
            log.info("unsuccessful");
        }


        return permissionTicketDO;

    }
}
