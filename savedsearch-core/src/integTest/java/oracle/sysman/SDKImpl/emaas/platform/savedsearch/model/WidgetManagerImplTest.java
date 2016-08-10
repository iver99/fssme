package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emaas.savedsearch.TestUtils;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "s2" })
public class WidgetManagerImplTest
{

	@Mocked
	TenantContext tenantContext;

	@Mocked
	TenantInfo tenantInfo;

	@Mocked
	QAToolUtil qaToolUtil;

	@Mocked
	RegistryLookupUtil regLookupUtil;

	@BeforeMethod
	public void initTenantDetails()
	{
		new Expectations() {
			{

				final Properties props = new Properties();
				props.put(QAToolUtil.TENANT_USER_NAME, "TENANT_USER_NAME");
				props.put(QAToolUtil.TENANT_NAME, "TENANT_NAME");

				QAToolUtil.getTenantDetails();
				result = props;

			}
		};
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails()
				.get(QAToolUtil.TENANT_USER_NAME).toString()), TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails()
						.get(QAToolUtil.TENANT_NAME).toString())));
	}

	//	@AfterClass
	//	public void removeTenantDetails()
	//	{
	//		TenantContext.clearContext();
	//	}

	@Test
	public void testGetSpelledJsonFromQueryResult() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				Link link = new Link();
				link.withRel("href");
				link.withHref("http://widget");
				RegistryLookupUtil.getServiceExternalLink(anyString, anyString, anyString, anyString);
				result = link;
			}

		};
		final String SEARCH_ID = "SEARCH_ID";
		final String WIDGET_NAME = "NAME";
		final String WIDGET_DESCRIPTION = "DESCRIPTION";
		final String WIDGET_OWNER = "OWNER";
		//		final String WIDGET_CREATION_TIME = "CREATION_DATE";
		//		final String WIDGET_LAST_MODIFICATION_DATE = "LAST_MODIFICATION_DATE";
		final String WIDGET_GROUP_NAME = "CATOGORY_NAME";
		//		final String WIDGET_SCREENSHOT_HREF = "WIDGET_SCREENSHOT_HREF";
		final String WIDGET_SUPPORT_TIME_CONTROL = "WIDGET_SUPPORT_TIME_CONTROL";
		final String WIDGET_KOC_NAME = "WIDGET_KOC_NAME";
		final String WIDGET_TEMPLATE = "WIDGET_TEMPLATE";
		final String WIDGET_VIEWMODEL = "WIDGET_VIEWMODEL";
		final String PROVIDER_NAME = "PROVIDER_NAME";
		final String PROVIDER_VERSION = "PROVIDER_VERSION";
		final String PROVIDER_ASSET_ROOT = "PROVIDER_ASSET_ROOT";

		List<Map<String, Object>> widgets = new ArrayList<Map<String, Object>>();
		Map<String, Object> widget1 = new HashMap<String, Object>();
		widget1.put(SEARCH_ID, 2000);
		widget1.put(WIDGET_NAME, "My_widget");
		widget1.put(WIDGET_DESCRIPTION, "my desc");
		widget1.put(WIDGET_OWNER, "emcsadmin");
		//		widget1.put(WIDGET_CREATION_TIME, "2016-07-25 04:21:02");
		//		widget1.put(WIDGET_LAST_MODIFICATION_DATE, "2016-07-25 04:21:02");
		widget1.put(WIDGET_GROUP_NAME, "Data Explorer");
		//		widget1.put(WIDGET_SCREENSHOT_HREF, "/image/001.png");
		widget1.put(WIDGET_SUPPORT_TIME_CONTROL, "1");
		widget1.put(WIDGET_KOC_NAME, "MY_WIDGET");
		widget1.put(WIDGET_TEMPLATE, "/template.html");
		widget1.put(WIDGET_VIEWMODEL, "/viewmodel.js");
		widget1.put(PROVIDER_NAME, "TargetAnalytics");
		widget1.put(PROVIDER_VERSION, "1.0");
		widget1.put(PROVIDER_ASSET_ROOT, "assetRoot");
		widgets.add(widget1);

		String json = WidgetManagerImpl.getInstance().getSpelledJsonFromQueryResult(widgets);
		System.out.println(json);
		Assert.assertEquals(
				json,
				"[{\"WIDGET_UNIQUE_ID\":\"2000\",\"WIDGET_NAME\":\"My_widget\",\"WIDGET_DESCRIPTION\":\"my desc\",\"WIDGET_OWNER\":\"emcsadmin\",\"WIDGET_CREATION_TIME\":\"null\",\"WIDGET_SOURCE\":1,\"WIDGET_GROUP_NAME\":\"Data Explorer\",\"WIDGET_SCREENSHOT_HREF\":\"null\",\"WIDGET_SUPPORT_TIME_CONTROL\":\"1\",\"WIDGET_KOC_NAME\":\"MY_WIDGET\",\"WIDGET_TEMPLATE\":\"/template.html\",\"WIDGET_VIEWMODEL\":\"/viewmodel.js\",\"PROVIDER_NAME\":\"TargetAnalytics\",\"PROVIDER_VERSION\":\"1.0\",\"PROVIDER_ASSET_ROOT\":\"assetRoot\"}]");
	}

	@Test
	public void testGetWidgetJsonStringFromWidgetList() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				Link link = new Link();
				link.withRel("href");
				link.withHref("http://widget");
				RegistryLookupUtil.getServiceExternalLink(anyString, anyString, anyString, anyString);
				result = link;
			}

		};
		WidgetImpl widget1 = new WidgetImpl();
		widget1.setId(new BigInteger("2001"));
		CategoryImpl category1 = new CategoryImpl();
		category1.setId(new BigInteger("2"));
		category1.setName("TargetAnalytics");
		category1.setDefaultFolderId(new BigInteger("3"));
		widget1.setCategory(category1);
		widget1.setDescription("my widget desc");
		widget1.setFolderId(new BigInteger("3"));
		widget1.setIsWidget(true);
		Date today = new Date(1469782680798L);
		widget1.setLastAccessDate(today);
		widget1.setCreationDate(today);
		widget1.setLastModificationDate(today);
		widget1.setLastModifiedBy("emcsadmin");
		widget1.setName("My widget");
		widget1.setQueryStr("xxx");

		SearchParameter sp1 = new SearchParameter();
		sp1.setName("WIDGET_KOC_NAME");
		sp1.setType(ParameterType.STRING);
		sp1.setValue("MY_WIDGET");
		SearchParameter sp2 = new SearchParameter();
		sp2.setName("WIDGET_TEMPLATE");
		sp2.setType(ParameterType.STRING);
		sp2.setValue("/template.js");
		SearchParameter sp3 = new SearchParameter();
		sp3.setName("WIDGET_VIEWMODEL");
		sp3.setType(ParameterType.STRING);
		sp3.setValue("/viewmodel.js");
		SearchParameter sp4 = new SearchParameter();
		sp4.setName("PROVIDER_NAME");
		sp4.setType(ParameterType.STRING);
		sp4.setValue("TargetAnalytics");
		SearchParameter sp5 = new SearchParameter();
		sp5.setName("PROVIDER_VERSION");
		sp5.setType(ParameterType.STRING);
		sp5.setValue("1.0");
		SearchParameter sp6 = new SearchParameter();
		sp6.setName("PROVIDER_ASSET_ROOT");
		sp6.setType(ParameterType.STRING);
		sp6.setValue("/assetroot");
		List<SearchParameter> sps = new ArrayList<SearchParameter>();
		sps.add(sp1);
		sps.add(sp2);
		sps.add(sp3);
		sps.add(sp4);
		sps.add(sp5);
		sps.add(sp6);
		widget1.setParameters(sps);
		List<Widget> widgets = new ArrayList<Widget>();
		widgets.add(widget1);
		String json = WidgetManagerImpl.getInstance().getWidgetJsonStringFromWidgetList(widgets);
		Assert.assertNotNull(json);
		Assert.assertNotEquals("[]", json);
		Assert.assertTrue(json.contains("WIDGET_UNIQUE_ID"));
	}

	@Test
	public void testGetWidgetListByProviderNames()
	{
		//TODO: not implmented
	}

}
