package oracle.sysman.emaas.platform.savedsearch.utils;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianqi
 * @since 16-2-24.
 */
@Test(groups = {"s2"})
public class RegistryLookupUtilTest {
    RegistryLookupUtil registryLookupUtil;

    @Test(groups = {"s1"})
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

    @Test
    public void testGetServiceInternalLink_IfBranchforHttps(@Mocked final Link link, @Mocked final InstanceInfo instanceInfo, @Mocked final LookupManager lookupManager, @Mocked final LookupClient lookupClient) throws Exception {
        final List<InstanceInfo> instanceInfos = new ArrayList<>();
        instanceInfos.add(instanceInfo);
        instanceInfos.add(instanceInfo);
        final List<Link> links = new ArrayList<>();
        new Expectations(){
            {
                LookupManager.getInstance();
                result = lookupManager;
                lookupManager.getLookupClient();
                result = lookupClient;
                lookupClient.lookup((InstanceQuery)any);
                result = instanceInfos;
                instanceInfo.getLinksWithProtocol(anyString,anyString);
                result = links;
            }
        };
        registryLookupUtil = new RegistryLookupUtil();
        registryLookupUtil.getServiceInternalLink("a","b","c","d");

        links.add(link);
        links.add(link);
        registryLookupUtil.getServiceInternalLink("a","b","c","d");
    }

    @Test
    public void testGetServiceInternalLink_IfBranchforHttp(@Mocked final Link link, @Mocked final InstanceInfo instanceInfo, @Mocked final LookupManager lookupManager, @Mocked final LookupClient lookupClient) throws Exception {
        final List<InstanceInfo> instanceInfos = new ArrayList<>();
        instanceInfos.add(instanceInfo);
        instanceInfos.add(instanceInfo);
        final List<Link> links = new ArrayList<>();
        new Expectations(){
            {
                LookupManager.getInstance();
                result = lookupManager;
                lookupManager.getLookupClient();
                result = lookupClient;
                lookupClient.lookup((InstanceQuery)any);
                result = instanceInfos;
                instanceInfo.getLinksWithProtocol(anyString,"http");
                result = links;
            }
        };
        registryLookupUtil = new RegistryLookupUtil();
        registryLookupUtil.getServiceInternalLink("a","b","c","d");

        links.add(link);
        links.add(link);
        registryLookupUtil.getServiceInternalLink("a","b","c","d");
    }
}