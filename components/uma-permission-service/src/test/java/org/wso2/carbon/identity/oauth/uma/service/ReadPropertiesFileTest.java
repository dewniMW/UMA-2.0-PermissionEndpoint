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

package org.wso2.carbon.identity.oauth.uma.service;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.wso2.carbon.utils.CarbonUtils;

import java.nio.file.Paths;

@PrepareForTest({CarbonUtils.class, Paths.class})
public class ReadPropertiesFileTest {
    /*@BeforeMethod
    public void setUp() throws Exception {
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }


    @Test(expectedExceptions = IOException.class)
    public void testReadFileConfigValues() throws Exception {

        PermissionTicketValues permissionTicketValues = new PermissionTicketValues();
        mockStatic(CarbonUtils.class);
        when(CarbonUtils.getCarbonConfigDirPath()).thenReturn(System.getProperty("carbon.config.dir.path"));
        //when(Paths.get(anyString(), anyString()).toString()).thenReturn("a");
        ReadPropertiesFile.readFileConfigValues(permissionTicketValues);


//        mockStatic(CarbonUtils.class);
//        when(CarbonUtils.getCarbonConfigDirPath()).thenReturn("a");
//
//        mockStatic(Paths.class);
//        when(Paths.get(anyString(), anyString()).toString()).thenReturn("a");

        //String configDirPath = CarbonUtils.getCarbonConfigDirPath();
//        String confPath = Paths.get(configDirPath, "uma", UMAConstants.UMA_PERMISSION_ENDPOINT_CONFIG_PATH)
//                .toString();
//
//        Properties prop = new Properties();
//        InputStream input = new FileInputStream(confPath);
//
//            prop.load(input);
//        assertNotNull(prop);
//        assertFalse(prop.isEmpty());


    }
*/
}
