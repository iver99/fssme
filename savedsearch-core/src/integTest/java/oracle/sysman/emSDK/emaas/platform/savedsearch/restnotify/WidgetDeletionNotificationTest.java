/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

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

/**
 * @author guochen
 */
@Test(groups = { "s2" })
public class WidgetDeletionNotificationTest
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
		final WidgetDeletionNotification wcn = new WidgetDeletionNotification();
		// null input
		wcn.notify((WidgetNotifyEntity) null);

		final WidgetNotifyEntity wne = new WidgetNotifyEntity();
		wne.setUniqueId(1L);
		wne.setName("Test Widget");
		wne.setNotifyTime(new Date());
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
	public void testNotifyDeleteSearch(@Mocked final LookupManager anyLookupManager, @Mocked final LookupClient anyLookupClient)
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
		final WidgetDeletionNotification wcn = new WidgetDeletionNotification();
		search.setId(1);
		search.setName("name");
		wcn.notify(search, new Date());
	}

	@Test
	public void testNotifyDeleteSearchNull()
	{
		final WidgetDeletionNotification wcn = new WidgetDeletionNotification();
		wcn.notify((Search) null, (Date) null);
	}
}
