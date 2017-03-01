package oracle.sysman.emaas.platform.savedsearch.comparator.targetmodel.services;

import mockit.Expectations;
import mockit.Mocked;
import org.testng.annotations.Test;

import javax.management.*;


@Test(groups = {"s2"})
public class SavedsearchComparatorStatusTest {


    @Mocked
    GlobalStatus globalStatus;
    private SavedsearchComparatorStatus SavedsearchComparatorStatus = new SavedsearchComparatorStatus();
    @Test
    public void testGetStatus(){
        new Expectations(){
            {
                globalStatus.isSavedsearchComparatorUp();
                result = false;
            }
        };
        SavedsearchComparatorStatus.getStatus();
        SavedsearchComparatorStatus.getStatusMsg();
        SavedsearchComparatorStatus.equals(SavedsearchComparatorStatus);
    }

    @Test
    public void testGetStatus2nd(){
        new Expectations(){
            {
                globalStatus.isSavedsearchComparatorUp();
                result = true;
            }
        };
        SavedsearchComparatorStatus.getStatus();
        SavedsearchComparatorStatus.getStatusMsg();
        SavedsearchComparatorStatus.equals(SavedsearchComparatorStatus);
    }
    private JMXUtil jmxUtil = JMXUtil.getInstance();
    @Test
    public void testRegisterMBeans() throws MalformedObjectNameException, InstanceAlreadyExistsException, NotCompliantMBeanException, MBeanRegistrationException, InstanceNotFoundException {
        jmxUtil.registerMBeans();
        jmxUtil.unregisterMBeans();
    }
}
