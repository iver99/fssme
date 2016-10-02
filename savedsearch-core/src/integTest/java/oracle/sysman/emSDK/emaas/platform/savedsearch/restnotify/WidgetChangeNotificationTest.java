package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
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

	@SuppressWarnings("unchecked")
	@Test
	public void testNotifyChange(@Mocked final RegistryLookupUtil anyRegistryLookupUtil, @Mocked final RestClient anyRestClient,
			@Mocked final TenantContext anyTenantContext) throws IOException
	{
		final WidgetChangeNotification wcn = new WidgetChangeNotification();
		// null input
		wcn.notify((WidgetNotifyEntity) null);

		final WidgetNotifyEntity wne = new WidgetNotifyEntity();
		wne.setUniqueId(1L);
		wne.setName("Test Widget");
		wne.setType(WidgetNotificationType.UPDATE_NAME);
		wne.setAffactedObjects(2);

		new Expectations(wcn) {
			{
				RegistryLookupUtil.getAllServicesInternalLinksByRel(anyString);
				result = links;
				TenantContext.getContext().gettenantName();
				result = "emaastesttenant1";
				anyRestClient.post(anyString, (Map<String, Object>) any, any, anyString);
				result = wne;
			}
		};
		wcn.notify(wne);
	}

	@Test
	public void testNotifyChangeSearch(@Mocked final LookupManager anyLookupManager, @Mocked final LookupClient anyLookupClient)
	{

		new Expectations() {
			{
				LookupManager.getInstance().getLookupClient();
				result = anyLookupClient;
				anyLookupClient.getInstancesWithLinkRelPrefix(anyString, anyString);
				result = null;
			}
		};
		SearchImpl search = new SearchImpl();
		final WidgetChangeNotification wcn = new WidgetChangeNotification();
		search.setId(1);
		search.setName("name");
		wcn.notify(search, new Date());
	}

	@Test
	public void testNotifyChangeSearchNull()
	{
		final WidgetChangeNotification wcn = new WidgetChangeNotification();
		wcn.notify((Search) null, (Date) null);
	}
}
