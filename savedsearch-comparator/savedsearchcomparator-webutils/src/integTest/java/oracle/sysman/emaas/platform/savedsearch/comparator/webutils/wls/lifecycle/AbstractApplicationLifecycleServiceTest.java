package oracle.sysman.emaas.platform.savedsearch.comparator.webutils.wls.lifecycle;

import mockit.Mocked;
import org.testng.annotations.Test;
import weblogic.application.ApplicationException;
import weblogic.application.ApplicationLifecycleEvent;

@Test(groups = {"s2"})
public class AbstractApplicationLifecycleServiceTest {
    @Test
    public void testAddApplicationServiceManager(final @Mocked ApplicationServiceManager manager, final @Mocked ApplicationLifecycleEvent evt) throws ApplicationException {
        AbstractApplicationLifecycleService service =new AbstractApplicationLifecycleService();

        service.addApplicationServiceManager(manager);

        service.preStart(evt);

        service.postStart(evt);

        service.preStop(evt);

        service.postStop(evt);
    }
}
