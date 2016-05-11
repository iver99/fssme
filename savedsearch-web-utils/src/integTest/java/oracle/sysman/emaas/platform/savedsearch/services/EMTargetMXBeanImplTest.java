package oracle.sysman.emaas.platform.savedsearch.services;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-22.
 */

@Test (groups = {"s1"})
public class EMTargetMXBeanImplTest {
    EMTargetMXBeanImpl emTargetMXBeanImpl;

    @BeforeMethod
    public void setUp() throws Exception {
        emTargetMXBeanImpl = new EMTargetMXBeanImpl("name1");
    }

    @Test
    public void testGetEMTargetType() throws Exception {
        Assert.assertTrue(emTargetMXBeanImpl.getEMTargetType() instanceof java.lang.String);
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(emTargetMXBeanImpl.getName(),"name1");
    }
}