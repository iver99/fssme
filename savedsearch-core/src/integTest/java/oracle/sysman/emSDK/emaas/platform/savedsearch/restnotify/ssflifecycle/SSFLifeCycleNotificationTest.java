package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.ssflifecycle;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by guochen on 3/24/17.
 */
@Test(groups = { "s2" })
public class SSFLifeCycleNotificationTest {
	private VersionedLink link;
	private List<VersionedLink> links;

	@BeforeMethod
	public void beforeMethod()
	{
		link = new VersionedLink();
		link.withHref("http://test.link.com");
		link.withRel("ssf.lifecycle.ntf");
		link.setAuthToken("authToken");
		links = new ArrayList<VersionedLink>();
		links.add(link);
	}

	@SuppressWarnings("unchecked")
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
