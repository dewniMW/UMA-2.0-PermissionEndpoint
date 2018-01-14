package org.wso2.carbon.identity.oauth.uma.service.impl;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.wso2.carbon.identity.oauth.uma.service.ReadPropertiesFile;
import org.wso2.carbon.identity.oauth.uma.service.dao.PermissionTicketDAO;

@PrepareForTest({ReadPropertiesFile.class, PermissionTicketDAO.class})
public class PermissionServiceImplTest {
/*
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
    }*/
}
