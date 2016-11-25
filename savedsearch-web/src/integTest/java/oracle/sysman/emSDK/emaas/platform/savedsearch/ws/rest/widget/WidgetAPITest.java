package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.WidgetManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotData;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;

import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups = { "s2" })
public class WidgetAPITest
{
	private static final BigInteger TEST_ID_10 = BigInteger.TEN;
	@Mocked
	UriInfo uriInfo;
	@Mocked
	URI uri;
	@Mocked
	CategoryManager categoryManager;
	@Mocked
	DependencyStatus dependencyStatus;
	private static List<Map<String, Object>> mockedWidgetObjects()
	{
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 45; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("SEARCH_ID", "1000");
			m.put("SP_WIDGET_KOC_NAME", "Test");
			m.put("SP_WIDGET_VIEWMODEL", "Test");
			m.put("SP_WIDGET_TEMPLATE", "Test");
			m.put("SP_WIDGET_LINKED_DASHBOARD", "1");
			m.put("SP_WIDGET_DEFAULT_WIDTH", "10");
			m.put("SP_WIDGET_DEFAULT_HEIGHT", "10");
			m.put("SP_DASHBOARD_INELIGIBLE", "Test");
			m.put("NAME", "Test");
			m.put("DESCRIPTION", "Test");
			m.put("OWNER", "Test");
			m.put("CREATION_DATE", "20-05-16 05.49.10.971542000 AM");
			m.put("CATOGORY_NAME", "Test");
			m.put("PROVIDER_NAME", "Test");
			m.put("PROVIDER_VERSION", "100");
			m.put("PROVIDER_ASSET_ROOT", "Test");
			map.add(m);
		}
		return map;
	}

	private final WidgetAPI widgetAPI = new WidgetAPI();

	Date now = new Date();

	@BeforeMethod
	public void setUp()
	{
		TenantContext.setContext(new TenantInfo("SYSMAN", 1L));
		widgetAPI.uri = uriInfo;

	}

	@Test
	public void testGetAllWidgets()
	{
		String userTenant = "OAM_REMOTE_USER.userName";
		String widgetGroupId = "10";
		boolean includeDashboardIneligible = true;
		final List<Category> list = new ArrayList<Category>();
		for (int i = 0; i < 3; i++) {
			CategoryImpl category = new CategoryImpl();
			category.setId(TEST_ID_10);
			list.add(new CategoryImpl());
		}
		new Expectations(){
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=11&includeDashboardIneligible=true";
			}
		};
		widgetAPI.getAllWidgets(widgetAPI.uri, userTenant, widgetGroupId, includeDashboardIneligible);
	}

	@Test
	public void testGetAllWidgets2nd(
			@Mocked final oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil anyTSU) throws IOException {
		String userTenant = "OAM_REMOTE_USER.userName";
		String widgetGroupId = "10";
		boolean includeDashboardIneligible = false;
		final List<Category> list = new ArrayList<Category>();
		for (int i = 0; i < 3; i++) {
			CategoryImpl category = new CategoryImpl();
			category.setId(TEST_ID_10);
			list.add(category);
		}
		final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
		SearchParameter searchParameter = new SearchParameter();
		searchParameter.setName("DASHBOARD_INELIGIBLE");
		searchParameter.setValue("1");
		for (int i = 0; i < 3; i++) {
			parameters.add(searchParameter);
		}
		final List<Search> searches = new ArrayList<Search>();
		for (int i = 0; i < 3; i++) {
			SearchImpl search = new SearchImpl();
			search.setParameters(parameters);
			searches.add(search);
		}
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil
				.getTenantSubscribedServiceProviders(anyString);
				result = Arrays.asList("LoganService", "emcitas-ui-apps", "TargetAnalytics", "ApmUI");
			}
		};
		//		new MockUp<CategoryManagerImpl>() {
		//			@Mock
		//			public List<Category> getAllCategories() throws EMAnalyticsFwkException
		//			{
		//				return list;
		//			}
		//		};
		/*new MockUp<SearchManagerImpl>() {
			@Mock
			public List<Search> getWidgetListByProviderNames(boolean includeDashboardIneligible, List<String> providerNames,
					String widgetGroupId) throws EMAnalyticsFwkException
			{
				return searches;
			}
		};*/
		//		new MockUp<URI>() {
		//			@Mock
		//			public String getQuery()
		//			{
		//				return "widgetGroupId=10&includeDashboardIneligible=true";
		//			}
		//		};
		new MockUp<WidgetManagerImpl>() {
			@Mock
			public List<Map<String, Object>> getWidgetListByProviderNames(List<String> providerNames, String widgetGroupId)
					throws EMAnalyticsFwkException
					{
				return WidgetAPITest.mockedWidgetObjects();
					}
		};
		new MockUp<WidgetManagerImpl>() {
			@Mock
			public String getWidgetJsonStringFromWidgetList(List<Widget> widgetList)
			{
				return "Widget Json Message";
			}
		};
		widgetAPI.getAllWidgets(widgetAPI.uri, userTenant, widgetGroupId, includeDashboardIneligible);
	}

	@Test
	public void testGetAllWidgets3th(
			@Mocked final oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil anyTSU) throws IOException {
		String userTenant = "OAM_REMOTE_USER.userName";
		String widgetGroupId = "10";
		boolean includeDashboardIneligible = false;
		final List<Category> list = new ArrayList<Category>();
		for (int i = 0; i < 3; i++) {
			CategoryImpl category = new CategoryImpl();
			category.setId(TEST_ID_10);
			list.add(category);
		}
		final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
		SearchParameter searchParameter = new SearchParameter();
		searchParameter.setName("DASHBOARD_INELIGIBLE");
		searchParameter.setValue("1");
		for (int i = 0; i < 3; i++) {
			parameters.add(searchParameter);
		}
		final List<Search> searches = new ArrayList<Search>();
		for (int i = 0; i < 3; i++) {
			SearchImpl search = new SearchImpl();
			search.setParameters(parameters);
			searches.add(search);
		}
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil
				.getTenantSubscribedServiceProviders(anyString);
				result = Arrays.asList("LoganService", "emcitas-ui-apps", "TargetAnalytics", "ApmUI");
			}
		};
		//		new MockUp<CategoryManagerImpl>() {
		//			@Mock
		//			public List<Category> getAllCategories() throws EMAnalyticsFwkException
		//			{
		//				return list;
		//			}
		//		};
		/*new MockUp<SearchManagerImpl>() {
			@Mock
			public List<Search> getWidgetListByProviderNames(boolean includeDashboardIneligible, List<String> providerNames,
					String widgetGroupId) throws EMAnalyticsFwkException
			{
				return searches;
			}
		};*/
		//		new MockUp<URI>() {
		//			@Mock
		//			public String getQuery()
		//			{
		//				return "widgetGroupId=10&includeDashboardIneligible=true";
		//			}
		//		};
		new MockUp<WidgetManagerImpl>() {
			@Mock
			public String getWidgetJsonStringFromWidgetList(List<Widget> widgetList) throws EMAnalyticsFwkException
			{
				throw new EMAnalyticsFwkException(new Exception());
			}

			@Mock
			public List<Map<String, Object>> getWidgetListByProviderNames(List<String> providerNames, String widgetGroupId)
					throws EMAnalyticsFwkException
					{
				return WidgetAPITest.mockedWidgetObjects();
					}
		};
		widgetAPI.getAllWidgets(widgetAPI.uri, userTenant, widgetGroupId, includeDashboardIneligible);
	}

	@Test
	public void testGetAllWidgets4th(
			@Mocked final oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil anyTSU) throws IOException {
		String userTenant = "OAM_REMOTE_USER.userName";
		String widgetGroupId = "10";
		boolean includeDashboardIneligible = false;
		final List<Category> list = new ArrayList<Category>();
		for (int i = 0; i < 3; i++) {
			CategoryImpl category = new CategoryImpl();
			category.setId(TEST_ID_10);
			list.add(category);
		}
		final List<SearchParameter> parameters = new ArrayList<SearchParameter>();
		SearchParameter searchParameter = new SearchParameter();
		searchParameter.setName("DASHBOARD_INELIGIBLE");
		searchParameter.setValue("1");
		for (int i = 0; i < 3; i++) {
			parameters.add(searchParameter);
		}
		final List<Search> searches = new ArrayList<Search>();
		for (int i = 0; i < 3; i++) {
			SearchImpl search = new SearchImpl();
			search.setParameters(parameters);
			searches.add(search);
		}
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil
				.getTenantSubscribedServiceProviders(anyString);
				result = Arrays.asList("LoganService", "emcitas-ui-apps", "TargetAnalytics", "ApmUI");
			}
		};
		//		new MockUp<CategoryManagerImpl>() {
		//			@Mock
		//			public List<Category> getAllCategories() throws EMAnalyticsFwkException
		//			{
		//				return list;
		//			}
		//		};
		/*new MockUp<SearchManagerImpl>() {
			@Mock
			public List<Search> getWidgetListByProviderNames(boolean includeDashboardIneligible, List<String> providerNames,
					String widgetGroupId) throws EMAnalyticsFwkException
			{
				return searches;
			}
		};*/
		//		new MockUp<URI>() {
		//			@Mock
		//			public String getQuery()
		//			{
		//				return "widgetGroupId=10&includeDashboardIneligible=true";
		//			}
		//		};
		new MockUp<WidgetManagerImpl>() {
			@Mock
			public String getWidgetJsonStringFromWidgetList(List<Widget> widgetList) throws Exception
			{
				throw new Exception();
			}

			@Mock
			public List<Map<String, Object>> getWidgetListByProviderNames(List<String> providerNames, String widgetGroupId)
					throws EMAnalyticsFwkException
					{
				return WidgetAPITest.mockedWidgetObjects();
					}
		};
		widgetAPI.getAllWidgets(widgetAPI.uri, userTenant, widgetGroupId, includeDashboardIneligible);
	}

	@Test
	public void testGetWidgetScreenshotById()
	{
		new MockUp<SearchManagerImpl>() {
			@Mock
			public ScreenshotData getWidgetScreenshotById(BigInteger widgetId) throws EMAnalyticsFwkException
			{
				return new ScreenshotData("10", now, now);
			}
		};
		widgetAPI.getWidgetScreenshotById(TEST_ID_10, "1.0", "test.png");
	}

	@Test
	public void testGetWidgetScreenshotById2nd()
	{
		new MockUp<SearchManagerImpl>() {
			@Mock
			public ScreenshotData getWidgetScreenshotById(BigInteger widgetId) throws EMAnalyticsFwkException
			{
				throw new EMAnalyticsFwkException(new Throwable());
			}
		};
		widgetAPI.getWidgetScreenshotById(TEST_ID_10, "1.0", "test.png");
	}

}
