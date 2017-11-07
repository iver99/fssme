package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.ssflifecycle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import oracle.sysman.emaas.platform.emcpdf.registry.RegistryLookupUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by guochen on 3/24/17.
 */
@Test(groups = { "s2" })
public class SSFLifeCycleNotificationTest {
    private RegistryLookupUtil.VersionedLink link;
    private List<RegistryLookupUtil.VersionedLink> links;
    @BeforeMethod
    public void beforeMethod()
    {
        link = new RegistryLookupUtil.VersionedLink();
        link.withHref("http://test.link.com");
        link.withRel("ssf.lifecycle.ntf");
        link.setAuthToken("authToken");
        links = new ArrayList<>();
        links.add(link);
    }
    
    @Test
    public void testNotifyChange(@Mocked final RegistryLookupUtil anyRegistryLookupUtil, @Mocked final RestClient anyRestClient,
                                 @Mocked final TenantContext anyTenantContext) throws IOException
    {
        final SSFLifeCycleNotification lcn = new SSFLifeCycleNotification();
        // null input
        lcn.notify(null);
        new Expectations(lcn) {
            {
                RegistryLookupUtil.getAllServicesInternalLinksByRel(anyString);
                result = links;
                anyRestClient.post(anyString, any, anyString, anyString);
                result = SSFLifeCycleNotifyEntity.SSFNotificationType.UP;
            }
        };
        lcn.notify(SSFLifeCycleNotifyEntity.SSFNotificationType.UP);
    }
}
