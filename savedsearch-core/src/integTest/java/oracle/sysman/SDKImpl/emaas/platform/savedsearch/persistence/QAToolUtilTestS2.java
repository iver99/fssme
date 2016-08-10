package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.qatool.uifwk.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by xidai on 3/4/2016.
 */
@Test(groups={"s2"})
public class QAToolUtilTestS2 {
    @Mocked
    Utils utils;
    @Mocked
    Properties properties;
    @Mocked
    URL url;
    @Mocked
    HttpURLConnection httpURLConnection;
    @Mocked
    InputStream inputStream;
    @Mocked
    SchemaUtil schemaUtil;
    @Mocked
    BufferedReader bufferedReader;
    @Test
    public void testGetDbProperties() throws Exception {
        final ArrayList<String> urlList = new ArrayList<>();
        urlList.add("url");
        new Expectations(){
            {
                Utils.getProperty(anyString);
                result = "ODS_HOstName";
                url.openConnection();
                result = httpURLConnection;
                httpURLConnection.setRequestProperty(anyString,anyString);
                httpURLConnection.getInputStream();
                result = inputStream;
            }
        };
        Assert.assertNotNull(QAToolUtil.getDbProperties());
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