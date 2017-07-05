package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread;

import mockit.Mocked;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;

import org.testng.annotations.Test;

@Test(groups={"s1"})
public class NlsRefreshRunnableTest {
    
    @Test
    public void testRun(@Mocked final DependencyStatus dependencyStatus) {
        MetadataRefreshRunnable nlsRunnable = new NlsRefreshRunnable();
        nlsRunnable.setServiceName("testService");
        Thread thread = new Thread(nlsRunnable, "Refresh testService resource bundles.");
        thread.start();
    }
}
