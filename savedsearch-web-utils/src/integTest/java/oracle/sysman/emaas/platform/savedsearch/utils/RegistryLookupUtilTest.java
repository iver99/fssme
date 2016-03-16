package oracle.sysman.emaas.platform.savedsearch.utils;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-24.
 */
@Test(groups = "s2")
public class RegistryLookupUtilTest {
    RegistryLookupUtil registryLookupUtil;

    @Test
    public void testGetServiceInternalLink() throws Exception {
        registryLookupUtil = new RegistryLookupUtil();
        registryLookupUtil.getServiceInternalLink("a","b","c","d");
    }

    @Test
    public void testGetServiceInternalLink_Mocked(@Mocked final LookupManager lookupManager, @Mocked final LookupClient lookupClient) throws Exception {
        new Expectations(){
            {
                LookupManager.getInstance();
                result = lookupManager;
                lookupManager.getLookupClient();
                result = lookupClient;
                lookupClient.lookup((InstanceQuery)any);
                result = null;
            }
        };

        registryLookupUtil = new RegistryLookupUtil();
        registryLookupUtil.getServiceInternalLink("a","b","c","d");
    }
}