package oracle.sysman.emaas.platform.savedsearch.services;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.AbstractApplicationLifecycleService;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by jishshi on 2016/2/29.
 */
@Test(groups = {"s1"})
public class ApplicationLifecycleServiceTest {

    @Test
    public void testConstructor() {
//        ApplicationLifecycleService applicationLifecycleService = new ApplicationLifecycleService();
//
//        List<ApplicationServiceManager> registeredServices =  Deencapsulation.getField(applicationLifecycleService,"registeredServices");
//        Assert.assertEquals(registeredServices.size(),4);
    }

    @Test(groups = {"s2"})
    public void testConstructor(@Mocked final AbstractApplicationLifecycleService applicationLifecycleService1) {
        new Expectations(){
            {
                withAny(applicationLifecycleService1).addApplicationServiceManager((ApplicationServiceManager)any);
            }
        };
                new ApplicationLifecycleService();

    }
}