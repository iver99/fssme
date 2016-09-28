package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-29.
 */
@Test(groups={"s1"})
public class SchemaVersionTest {

    SchemaVersion schemaVersion;
    @Test
    public void testGetMajorVersion()  {
        schemaVersion = new SchemaVersion(1234,5678);
        Assert.assertEquals(schemaVersion.getMajorVersion(),1234);
    }

    @Test
    public void testGetMinorVersion()  {
        schemaVersion = new SchemaVersion(1234,5678);
        Assert.assertEquals(schemaVersion.getMinorVersion(),5678);
    }

    @Test
    public void testGetName()  {
        Assert.assertEquals(schemaVersion.getName(),"Schema Version");
    }
}