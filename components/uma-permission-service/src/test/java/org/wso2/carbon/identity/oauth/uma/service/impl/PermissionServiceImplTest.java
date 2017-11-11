package org.wso2.carbon.identity.oauth.uma.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.oauth.uma.service.ReadPropertiesFile;
import org.wso2.carbon.identity.oauth.uma.service.dao.PermissionTicketDAO;
import org.wso2.carbon.identity.oauth.uma.service.dao.PermissionTicketDO;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionAPIException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionServiceException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionServiceRuntimeException;
import org.wso2.carbon.identity.oauth.uma.service.exception.PermissionTicketDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.ResourceIdDAOException;
import org.wso2.carbon.identity.oauth.uma.service.exception.ResourceScopeDAOException;

import java.lang.reflect.Field;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PrepareForTest({ReadPropertiesFile.class, PermissionTicketDAO.class})
public class PermissionServiceImplTest {

    @Mock
    private PermissionTicketDAO mockedPermissionTicketDAO;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @BeforeMethod
    public void setUp() throws Exception {
        permissionService = new PermissionServiceImpl();

        Field field = PermissionServiceImpl.class.getDeclaredField("permissionTicketDAO");
        field.setAccessible(true);
        field.set(permissionService, mockedPermissionTicketDAO);
        field.setAccessible(false);
    }

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    @Test
    public void testIssuePermissionTicket() throws Exception {
        mockStatic(ReadPropertiesFile.class);
        whenNew(PermissionTicketDAO.class).withNoArguments().thenReturn(mockedPermissionTicketDAO);
        doNothing().when(mockedPermissionTicketDAO).persist(anyList(), any(PermissionTicketDO.class));
        permissionService.issuePermissionTicket(anyList());
    }

    @Test(expectedExceptions = PermissionServiceRuntimeException.class)
    public void testIssuePermissionTicketWithPermissionAPIException() throws Exception {
        mockStatic(ReadPropertiesFile.class);
        whenNew(PermissionTicketDAO.class).withNoArguments().thenReturn(mockedPermissionTicketDAO);
        doThrow(new PermissionAPIException("dummyException")).when(mockedPermissionTicketDAO).
                persist(anyList(), any(PermissionTicketDO.class));
        permissionService.issuePermissionTicket(anyList());
    }

    @Test(expectedExceptions = PermissionServiceRuntimeException.class)
    public void testIssuePermissionTicketWithPermissionTicketDAOException() throws Exception {
        mockStatic(ReadPropertiesFile.class);
        whenNew(PermissionTicketDAO.class).withNoArguments().thenReturn(mockedPermissionTicketDAO);
        doThrow(new PermissionTicketDAOException("dummyException")).when(mockedPermissionTicketDAO).
                persist(anyList(), any(PermissionTicketDO.class));
        permissionService.issuePermissionTicket(anyList());
    }

    @Test(expectedExceptions = PermissionServiceException.class)
    public void testIssuePermissionTicketWithResourceIdDAOException() throws Exception {
        mockStatic(ReadPropertiesFile.class);
        whenNew(PermissionTicketDAO.class).withNoArguments().thenReturn(mockedPermissionTicketDAO);
        doThrow(new ResourceIdDAOException("dummyException")).when(mockedPermissionTicketDAO).
                persist(anyList(), any(PermissionTicketDO.class));
        permissionService.issuePermissionTicket(anyList());
    }

    @Test(expectedExceptions = PermissionServiceException.class)
    public void testIssuePermissionTicketWithResourceScopeDAOException() throws Exception {
        mockStatic(ReadPropertiesFile.class);
        whenNew(PermissionTicketDAO.class).withNoArguments().thenReturn(mockedPermissionTicketDAO);
        doThrow(new ResourceScopeDAOException("dummyException")).when(mockedPermissionTicketDAO).
                persist(anyList(), any(PermissionTicketDO.class));
        permissionService.issuePermissionTicket(anyList());
    }
}
