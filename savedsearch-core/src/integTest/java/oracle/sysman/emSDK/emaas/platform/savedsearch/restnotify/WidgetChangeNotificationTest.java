package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;

@Test(groups = { "s2" })
public class WidgetChangeNotificationTest
{
	private Link link;
	private List<Link> links;

	@BeforeMethod
	public void beforeMethod()
	{
		link = new Link();
		link.withHref("http://test.link.com");
		link.withRel("ssf.widget.changed");
		links = new ArrayList<Link>();
		links.add(link);
	}

	@Test
	public void testGetInternalLinksByRel(@Mocked final LookupManager anyLookupManager,
			@Mocked final LookupClient anyLookupClient, @Mocked final InstanceInfo anyInstanceInfo,
			@Mocked final List<InstanceInfo> anyInstanceInfoList)
	{
		final List<InstanceInfo> iiList = new ArrayList<InstanceInfo>();
		iiList.add(anyInstanceInfo);
		final String serviceName = "Test Service";
		new Expectations() {
			{
				LookupManager.getInstance().getLookupClient();
				result = anyLookupClient;
				anyLookupClient.getInstancesWithLinkRelPrefix(anyString, anyString);
				result = iiList;
				anyInstanceInfo.getLinksWithRelPrefix(anyString);
				result = links;
				anyInstanceInfo.getServiceName();
				result = serviceName;
			}
		};
		List<Link> linkList = new WidgetChangeNotification().getInternalLinksByRel("ssf.widget.changed");
		Assert.assertNotNull(linkList);
		Assert.assertEquals(linkList.size(), 1);
		Assert.assertEquals(linkList.get(0), link);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testNotifyChange(@Mocked final RestClient anyRestClient, @Mocked final TenantContext anyTenantContext)
	{
		final WidgetChangeNotification wcn = new WidgetChangeNotification();
		// null input
		wcn.notifyChange((WidgetNotifyEntity) null);

		final WidgetNotifyEntity wne = new WidgetNotifyEntity();
		wne.setUniqueId(1L);
		wne.setName("Test Widget");
		wne.setAffactedObjects(2);

		new Expectations(wcn) {
			{
				wcn.getInternalLinksByRel(anyString);
				result = links;
				TenantContext.getContext().gettenantName();
				result = "emaastesttenant1";
				anyRestClient.post(anyString, (Map<String, Object>) any, any, anyString);
				result = wne;
			}
		};
		wcn.notifyChange(wne);
	}
}
