package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import mockit.Expectations;
import mockit.Mocked;
import mockit.StrictExpectations;
import oracle.sysman.qatool.uifwk.utils.Utils;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xidai on 3/4/2016.
 */
@Test(groups={"s2"})
public class QAToolUtilTestS2 {
    private  QAToolUtil qaToolUtil =new QAToolUtil();
    @Mocked
    Utils utils;
    @Mocked
    HttpURLConnection connection;
    @Mocked
    URL url;
    @Mocked
    InputStream inputStream;
    @Mocked
    InputStreamReader inputStreamReader;
    @Mocked
    BufferedReader bufferedReader;
    @Mocked
    SchemaUtil schemaUtil;
    @Mocked
    Logger logger;
    @Test
    public void testGetDbProperties() throws Exception {
        new StrictExpectations(){
            {
                Utils.getProperty(anyString);
                result = "http://slc08twq.us.oracle.com";
                Utils.getProperty(anyString);
                result = "7001";
                Utils.getProperty(anyString);
                result = "registry/servicemanager/registry/v1";
                Utils.getProperty(anyString);
                result = "ServiceManager";
                new URL(anyString);
                result =url;
                url.openConnection();
                result = connection;
                connection.setRequestProperty(anyString,anyString);
                connection.getInputStream();
                result = inputStream;
                new BufferedReader(withAny(inputStreamReader));
                result = bufferedReader;
                bufferedReader.readLine();
                result="http://slc04lec.us.oracle.com:7001//registry/servicemanager/registry/v1/ServiceManager/instance?servicename=LifecycleInvManager";
                bufferedReader.readLine();
                new URL(anyString);
                result =url;
                url.openConnection();
                result = connection;
                connection.setRequestProperty(anyString,anyString);
                connection.getInputStream();
                result = inputStream;
                new BufferedReader(withAny(inputStreamReader));
                result = bufferedReader;
                bufferedReader.readLine();
                result="schemaUser:username";
                bufferedReader.readLine();
            }
        };
        QAToolUtil.getDbProperties();
    }

    @Test
    public void testGetSavedSearchDeploymentDet() throws Exception {
        new Expectations(){
            {
                Utils.getProperty(anyString);
                result = "http://slc08twq.us.oracle.com:7001//registry/servicemanager/registry/v1";
                new URL(anyString);
                result =url;
                url.openConnection();
                result = connection;
                connection.setRequestProperty(anyString,anyString);
                connection.getInputStream();
                result = inputStream;
                new BufferedReader(withAny(inputStreamReader));
                result = bufferedReader;
                bufferedReader.readLine();
                result="http://slc04lec.us.oracle.com:7001//registry/servicemanager/registry/v1/ServiceManager/instance?servicename=LifecycleInvManager";
                bufferedReader.readLine();
                SchemaUtil.getSchemaUrls(anyString);
            }
        };
       Assert.assertNull(QAToolUtil.getSavedSearchDeploymentDet());
    }

    @Test
    public void testGetTenantDetails() throws Exception {
        new Expectations() {
            {
                Utils.getProperty(anyString);
                result = "10";
                Utils.getProperty(anyString);
                result = "internalTenantId";
                Utils.getProperty(anyString);
                result = "username";
                Utils.getProperty(anyString);
                result = "internalTenantId";
            }
        };
        Assert.assertNotNull(QAToolUtil.getTenantDetails());
    }
}