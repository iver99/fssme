package oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services;

import org.testng.annotations.Test;

import javax.management.*;

@Test(groups = {"s2"})
public class JMXUtilTest {
    @Test
    public void testGetIns(){
        JMXUtil.getInstance();
    }
    @Test
    public void testReg() throws MalformedObjectNameException, InstanceAlreadyExistsException, NotCompliantMBeanException, MBeanRegistrationException {
        JMXUtil.getInstance().registerMBeans();
    }

    @Test
    public void testUnReg() throws MalformedObjectNameException, InstanceNotFoundException, MBeanRegistrationException {
        JMXUtil.getInstance().unregisterMBeans();
    }
}
