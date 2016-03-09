package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups = {"s2"})
public class RegistryLookupUtilTest {
    private RegistryLookupUtil registryLookupUtil;
    @Mocked
    LookupManager lookupManager;
    @Mocked
    LookupClient lookupClient;
    @Mocked
    InstanceInfo instanceInfo;
    @Mocked
    InstanceQuery instanceQuery;
    @Test
    public void testGetServiceInternalLink() throws Exception {
        final List<InstanceInfo> list = new ArrayList<>();
        new Expectations(){
            {
                LookupManager.getInstance();
                result = lookupManager;
                lookupManager.getLookupClient();
                result = lookupClient;
                lookupClient.lookup(withAny(instanceQuery));
                new InstanceQuery(withAny(instanceInfo));
                result = instanceQuery;
            }
        };
        RegistryLookupUtil.getServiceInternalLink("","","","");
    }

    @Test
    public void testGetServiceInternalLink2nd() throws Exception {
        final List<InstanceInfo> list = new ArrayList<>();
        list.add(instanceInfo);
        new Expectations(){
            {
                LookupManager.getInstance();
                result = lookupManager;
                lookupManager.getLookupClient();
                result = lookupClient;
                lookupClient.lookup(withAny(instanceQuery));
                result = list;
                new InstanceQuery(withAny(instanceInfo));
                result = instanceQuery;
            }
        };
        RegistryLookupUtil.getServiceInternalLink("","","","");
    }
}