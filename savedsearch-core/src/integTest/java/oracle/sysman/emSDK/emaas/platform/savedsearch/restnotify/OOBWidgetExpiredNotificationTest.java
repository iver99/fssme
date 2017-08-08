package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "s2" })
public class OOBWidgetExpiredNotificationTest {
    private VersionedLink link;
    private List<VersionedLink> links;

    @BeforeMethod
    public void beforeMethod() {
        link = new VersionedLink();
        link.withHref("http://test.link.com");
        link.withRel("expire/widgetcache");
        link.setAuthToken("authToken");
        links = new ArrayList<VersionedLink>();
        links.add(link);
    }

    @Test
    public void testInform(@Mocked final RegistryLookupUtil anyRegistryLookupUtil, @Mocked final RestClient anyRestClient)
            throws IOException {
        final OOBWidgetExpiredNotification notification = new OOBWidgetExpiredNotification();
        new Expectations() {
            {
                RegistryLookupUtil.getAllServicesInternalLinksByRel(anyString);
                result = links;
                anyRestClient.post(anyString, any, anyString, anyString);
                result = "Something";
            }
        };
        notification.notify("SSF");
    }

}
