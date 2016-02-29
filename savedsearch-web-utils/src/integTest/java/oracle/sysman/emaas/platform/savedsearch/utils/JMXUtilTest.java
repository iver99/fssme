package oracle.sysman.emaas.platform.savedsearch.utils;

import mockit.Deencapsulation;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author qianqi
 * @since 16-2-24.
 */
@Test(groups = "s1")
public class JMXUtilTest {

    private static final java.lang.String SAVEDSEARCH_STATUS = "oracle.sysman.emaas.platform.savedsearch:Name=SavedSearchStatus,Type=oracle.sysman.emaas.platform.savedsearch.targetmodel.services.SavedSearchStatus";

    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(JMXUtil.getInstance());
    }

    @Test
    public void testRegisterMBeans() throws Exception {
        JMXUtil jmxUtil = JMXUtil.getInstance();
        ObjectName savedSearchStatusObjectName = new ObjectName(SAVEDSEARCH_STATUS);

        jmxUtil.registerMBeans();
        MBeanServer server = Deencapsulation.getField(jmxUtil,"server");
        Assert.assertTrue(server.isRegistered(savedSearchStatusObjectName));
    }

    @Test
    public void testUnregisterMBeans() throws Exception {
        JMXUtil jmxUtil = JMXUtil.getInstance();
        ObjectName savedSearchStatusObjectName = new ObjectName(SAVEDSEARCH_STATUS);
        jmxUtil.registerMBeans();

        jmxUtil.unregisterMBeans();
        MBeanServer server = Deencapsulation.getField(jmxUtil,"server");
        Assert.assertFalse(server.isRegistered(savedSearchStatusObjectName));
    }
}