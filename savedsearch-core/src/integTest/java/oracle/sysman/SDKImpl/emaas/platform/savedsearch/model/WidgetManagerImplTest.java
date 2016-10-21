package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
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

import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;
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
		final String searchId = "SEARCH_ID";
		final String widgetName = "NAME";
		final String widgetDescription = "DESCRIPTION";
		final String widgetOwner = "OWNER";
		//		final String WIDGET_CREATION_TIME = "CREATION_DATE";
		//		final String WIDGET_LAST_MODIFICATION_DATE = "LAST_MODIFICATION_DATE";
		final String catogoryName = "CATOGORY_NAME";
		//		final String WIDGET_SCREENSHOT_HREF = "WIDGET_SCREENSHOT_HREF";
		final String widgetSupportTimeControl = "WIDGET_SUPPORT_TIME_CONTROL";
		final String widgetKocName = "WIDGET_KOC_NAME";
		final String widgetTemplate = "WIDGET_TEMPLATE";
		final String widgetViewmodel = "WIDGET_VIEWMODEL";
		final String providerName = "PROVIDER_NAME";
		final String providerVersion = "PROVIDER_VERSION";
		final String providerAssetRoot = "PROVIDER_ASSET_ROOT";

		List<Map<String, Object>> widgets = new ArrayList<Map<String, Object>>();
		Map<String, Object> widget1 = new HashMap<String, Object>();
		widget1.put(searchId, 2000);
		widget1.put(widgetName, "My_widget");
		widget1.put(widgetDescription, "my desc");
		widget1.put(widgetOwner, "emcsadmin");
		//		widget1.put(WIDGET_CREATION_TIME, "2016-07-25 04:21:02");
		//		widget1.put(WIDGET_LAST_MODIFICATION_DATE, "2016-07-25 04:21:02");
		widget1.put(catogoryName, "Data Explorer");
		//		widget1.put(WIDGET_SCREENSHOT_HREF, "/image/001.png");
		widget1.put(widgetSupportTimeControl, "1");
		widget1.put(widgetKocName, "MY_WIDGET");
		widget1.put(widgetTemplate, "/template.html");
		widget1.put(widgetViewmodel, "/viewmodel.js");
		widget1.put(providerName, "TargetAnalytics");
		widget1.put(providerVersion, "1.0");
		widget1.put(providerAssetRoot, "assetRoot");
		widgets.add(widget1);

		String json = WidgetManagerImpl.getInstance().getSpelledJsonFromQueryResult(widgets);
		Assert.assertEquals(
				json,
				"[{\"WIDGET_UNIQUE_ID\":2000,\"WIDGET_NAME\":\"My_widget\",\"WIDGET_DESCRIPTION\":\"my desc\",\"WIDGET_OWNER\":\"emcsadmin\",\"WIDGET_SOURCE\":1,\"WIDGET_GROUP_NAME\":\"Data Explorer\",\"WIDGET_SUPPORT_TIME_CONTROL\":\"1\",\"WIDGET_KOC_NAME\":\"MY_WIDGET\",\"WIDGET_TEMPLATE\":\"/template.html\",\"WIDGET_VIEWMODEL\":\"/viewmodel.js\",\"PROVIDER_NAME\":\"TargetAnalytics\",\"PROVIDER_VERSION\":\"1.0\",\"PROVIDER_ASSET_ROOT\":\"assetRoot\"}]");
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
		widget1.setId(2001);
		CategoryImpl category1 = new CategoryImpl();
		category1.setId(2);
		category1.setName("TargetAnalytics");
		category1.setDefaultFolderId(3);
		widget1.setCategory(category1);
		widget1.setDescription("my widget desc");
		widget1.setFolderId(3);
		widget1.setIsWidget(true);
		Date today = new Date(1469782680798L);
		widget1.setLastAccessDate(today);
		widget1.setCreatedOn(today);
		widget1.setLastModifiedOn(today);
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
	@Mocked
	PersistenceManager persistenceManager;
	@Mocked
	EntityManager entityManager;
	@Mocked
	Session session;
	@Mocked
	JpaEntityManager jpaEntityManager;
	@Mocked
	EJBQueryImpl ejbQuery;
	@Mocked
	DatabaseQuery databaseQuery;
	@Test
	public void testGetWidgetListByProviderNames() throws EMAnalyticsFwkException {
		WidgetManagerImpl widgetManager = WidgetManagerImpl.getInstance();
		List<String> providerNames = new ArrayList<>();
		providerNames.add("name1");
		new Expectations(){
			{
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getTenantInternalId();
				result = 1L;
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo)any);
				result = entityManager;
				ejbQuery.setHint(anyString, anyString);
				ejbQuery.setParameter(anyString, anyLong);
				result = ejbQuery;
				entityManager.unwrap(JpaEntityManager.class);
				result = jpaEntityManager;
				jpaEntityManager.getActiveSession();
				result =session;
				ejbQuery.getDatabaseQuery();
				result =databaseQuery;
				databaseQuery.prepareCall(session, (DatabaseRecord)any);
				databaseQuery.getSQLString();
				result = "sqlString";
				ejbQuery.getResultList();
			}
		};
		widgetManager.getWidgetListByProviderNames(providerNames, "1");
	}

	@Mocked
	Throwable throwable;
	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetWidgetListByProviderNamesException() throws EMAnalyticsFwkException {
		WidgetManagerImpl widgetManager = WidgetManagerImpl.getInstance();
		List<String> providerNames = new ArrayList<>();
		providerNames.add("name1");
		new Expectations(){
			{
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getTenantInternalId();
				result = 1L;
				PersistenceManager.getInstance();
				result = new Exception(throwable);
			}
		};
		widgetManager.getWidgetListByProviderNames(providerNames, "1");
	}
	@Test
	public void testGetWidgetListByProviderNameSessionException() throws EMAnalyticsFwkException {
		WidgetManagerImpl widgetManager = WidgetManagerImpl.getInstance();
		List<String> providerNames = new ArrayList<>();
		providerNames.add("name1");
		new Expectations(){
			{
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getTenantInternalId();
				result = 1L;
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo)any);
				result = entityManager;
				ejbQuery.setHint(anyString, anyString);
				ejbQuery.setParameter(anyString, anyLong);
				result = ejbQuery;
				entityManager.unwrap(JpaEntityManager.class);
				result = jpaEntityManager;
				jpaEntityManager.getActiveSession();
				result = new Exception(throwable);			}
		};
		widgetManager.getWidgetListByProviderNames(providerNames, "1");
	}
	@Test
	public void testGetWidgetListByProviderNamesNULL() throws EMAnalyticsFwkException {
		WidgetManagerImpl widgetManager = WidgetManagerImpl.getInstance();
		widgetManager.getWidgetListByProviderNames(null, "1");
	}
	@Test
	public void testGetWidgetJsonStringFromWidgetListNull() throws EMAnalyticsFwkException {
		WidgetManagerImpl widgetManager = WidgetManagerImpl.getInstance();
		widgetManager.getWidgetJsonStringFromWidgetList(null);
	}
}
