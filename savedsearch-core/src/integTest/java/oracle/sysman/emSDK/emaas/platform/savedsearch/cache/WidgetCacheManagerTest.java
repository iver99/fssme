package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.WidgetImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;

public class WidgetCacheManagerTest
{
	@Test
	public void testWidgetListPutGet() throws Exception
	{
		List<Widget> wgtList = new ArrayList<Widget>();

		Folder folder = new FolderImpl();
		folder.setName("FolderTest" + System.currentTimeMillis());
		folder.setDescription("TestFolderDescription");
		folder.setUiHidden(false);

		Category cat = new CategoryImpl();
		cat.setName("cat name");
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		cat.setOwner(currentUser);
		cat.setProviderName("TestProviderName");
		cat.setProviderVersion("TestProviderVersion");
		cat.setProviderDiscovery("TestProviderDiscovery");
		cat.setProviderAssetRoot("TestProviderAssetRoot");
		if (folder != null) {
			cat.setDefaultFolderId(folder.getParentId());
		}

		List<String> providers = new ArrayList<String>();
		providers.add(cat.getProviderName());

		Widget widget1 = new WidgetImpl();
		widget1.setDescription("testing purpose");
		widget1.setName("widget 1");
		widget1.setFolderId(folder.getId());
		widget1.setCategoryId(cat.getId());
		widget1.setIsWidget(true);

		List<SearchParameter> widgetParams = new ArrayList<SearchParameter>();
		SearchParameter wp1 = new SearchParameter();
		SearchParameter wp2 = new SearchParameter();
		SearchParameter wp3 = new SearchParameter();
		SearchParameter wp4 = new SearchParameter();
		SearchParameter wp5 = new SearchParameter();
		SearchParameter wp6 = new SearchParameter();
		wp1.setName("WIDGET_VIEWMODEL");
		wp1.setType(ParameterType.STRING);
		wp1.setValue("dependencies/widgets/iFrame/js/widget-iframe");
		wp2.setName("WIDGET_KOC_NAME");
		wp2.setType(ParameterType.STRING);
		wp2.setValue("DF_V1_WIDGET_IFRAME");
		wp3.setName("WIDGET_TEMPLATE");
		wp3.setType(ParameterType.STRING);
		wp3.setValue("dependencies/widgets/iFrame/widget-iframe.html");
		wp4.setName("PROVIDER_VERSION");
		wp4.setType(ParameterType.STRING);
		wp4.setValue("0.1");
		wp5.setName("PROVIDER_NAME");
		wp5.setType(ParameterType.STRING);
		wp5.setValue("DB Analytics");
		wp6.setName("PROVIDER_ASSET_ROOT");
		wp6.setType(ParameterType.STRING);
		wp6.setValue("home");
		widgetParams.add(wp1);
		widgetParams.add(wp2);
		widgetParams.add(wp3);
		widgetParams.add(wp4);
		widgetParams.add(wp5);
		widgetParams.add(wp6);

		widget1.setParameters(widgetParams);
		wgtList.add(widget1);

		Widget widget2 = new WidgetImpl();
		widget2.setDescription("testing purpose");
		widget2.setName("widget 1");
		widget2.setFolderId(folder.getId());
		widget2.setCategoryId(cat.getId());
		widget2.setIsWidget(true);
		wgtList.add(widget2);

		WidgetCacheManager wcm = WidgetCacheManager.getInstance();
		Tenant tenant = new Tenant(1L, "tenant1");
		wcm.storeWidgetListToCache(tenant, wgtList);

		List<Widget> queried = wcm.getWigetListFromCache(new Tenant(1L, "tenant1"));
		Widget wgt1 = queried.get(0);
		Assert.assertEquals(wgt1.getId(), widget1.getId());
		Assert.assertEquals(wgt1.getName(), widget1.getName());
		Assert.assertEquals(wgt1.getDescription(), widget1.getDescription());
		Assert.assertEquals(wgt1.getOwner(), widget1.getOwner());
		Assert.assertEquals(wgt1.getCategoryId(), widget1.getCategoryId());

		Widget wgt2 = queried.get(1);
		Assert.assertEquals(wgt2.getId(), widget2.getId());
		Assert.assertEquals(wgt2.getName(), widget2.getName());
		Assert.assertEquals(wgt2.getDescription(), widget2.getDescription());
		Assert.assertEquals(wgt2.getOwner(), widget2.getOwner());
		Assert.assertEquals(wgt2.getCategoryId(), widget2.getCategoryId());
	}
}
