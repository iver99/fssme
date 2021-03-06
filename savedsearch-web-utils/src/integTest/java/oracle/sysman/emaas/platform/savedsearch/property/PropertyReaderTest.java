package oracle.sysman.emaas.platform.savedsearch.property;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author qianqi
 * @since 16-2-18.
 */
@Test (groups = {"s2"})
public class PropertyReaderTest {

    @Mocked
    IOException ioEx;

    @Test
    public void testLoadProperty(@Injectable FileInputStream fileInputStream) throws IOException {

        Properties properties = PropertyReader.loadProperty("MockFile");
        Assert.assertEquals(properties.size(),0);

//        properties = PropertyReader.loadProperty("serviceregistry-client");
        properties = PropertyReader.loadProperty("servicemanager.properties");
//        Assert.assertNotEquals(properties.size(),0);
    }

    @Test
    public void testGetInstallDirTrue(){
        new Expectations(PropertyReader.class){
            {
                Deencapsulation.setField(PropertyReader.class,"RUNNING_IN_CONTAINER",true);
            }
        };
        PropertyReader.getInstallDir();
    }

    @Test
    public void testGetInstallDirFalse(){
        new Expectations(PropertyReader.class){
            {
                Deencapsulation.setField(PropertyReader.class,"RUNNING_IN_CONTAINER",false);
            }
        };
        PropertyReader.getInstallDir();
    }


    @Test
    public void testGetRunningInContainer(){
        PropertyReader.getRunningInContainer();
    }
}