package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

import java.math.BigInteger;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.WidgetImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.WidgetManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager;

import oracle.sysman.emaas.platform.emcpdf.cache.tool.ScreenshotData;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.testng.annotations.Test;


/**
 * Created by QIQIAN on 2016/3/28.
 */
@Test(groups = { "s1" })
public class WidgetAPIMoreTest
{

	private static final BigInteger TEST_ID_1234 = new BigInteger("1234");
	WidgetAPI widgetAPI;
	Date now = new Date();
	@Mocked
	DependencyStatus dependencyStatus;
	@Test(groups = { "s2" })
	public void testCheckQueryParamGroupIdLessThen0(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=-123&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true, "false", "false");
	}

	@Test(groups = { "s2" })
	public void testCheckQueryParamInputLengthLessThen2(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true, "false", "false");
	}

	@Test(groups = { "s2" })
	public void testCheckQueryParamInputLengthLessThen2KeyNotCorrect(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "includeDashboardIneligiblexx=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true, "false", "false");
	}

	@Test(groups = { "s2" })
	public void testCheckQueryParamKey1NotCorrect(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupIdxx=123&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true, "false", "false");
	}

	@Test(groups = { "s2" })
	public void testCheckQueryParamKey2NotCorrect(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123&includeDashboardIneligiblexx=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true, "false", "false");
	}

	@Test
	public void testGetAllWidgets()
	{
//		widgetAPI = new WidgetAPI();
//		widgetAPI.getAllWidgets(new WebApplicationContext(new WebApplicationImpl(), new ContainerRequest(
//				new WebApplicationImpl(), "method", null, null, new InBoundHeaders(), null), new ContainerResponse(
//				new WebApplicationImpl(), null, null)), "userTenant", "widgetGroupId", true);
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsEMAnalyticsFwkException(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = new EMAnalyticsFwkException(new Throwable());
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true, "false", "false");
	}
	@Mocked WidgetManager widgetManager;
	@Mocked WidgetManagerImpl widgetManagerImpl;
	@Mocked SearchManager searchManager;
	@Mocked SearchManagerImpl searchManagerImpl;
	@Mocked TenantSubscriptionUtil tenantSubscriptionUtil;
	@Mocked TenantContext tenantContext;
	@Mocked TenantInfo tenantInfo;
	@Mocked UriInfo uriInfo;
	@Mocked URI uri;
	@Test(groups = { "s2" })
	public void testGetAllWidgetsGetWigetListFromCacheExceptionWidgetGroupIdNotNull() throws IOException, EMAnalyticsFwkException {
		final List<String> providers = new ArrayList<>();
		providers.add("providerQQ");
		providers.add("providerPP");
		final List<Widget> widgetList = new ArrayList<>();
		widgetList.add(new WidgetImpl());
		widgetList.add(new WidgetImpl());
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123&includeDashboardIneligible=true";

				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.gettenantName();
				result = "tenantName";

				TenantSubscriptionUtil.getTenantSubscribedServiceProviders(anyString);
				result = providers;

				WidgetManager.getInstance();
				result = widgetManagerImpl;
				widgetManagerImpl.getWidgetListByProviderNames(providers, anyString, anyBoolean);

				WidgetManager.getInstance();
				result = widgetManagerImpl;

			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", null, false, "false", "false");
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsGroupIdLargerThen(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true, "false", "false");
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsGroupIdLargerThen0ParamLessThen2(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true, "false", "false");
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsGroupIdLessThen0(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "-123", true, "false", "false");
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsWidgetGroupIdNotNull(@Mocked WidgetManager widgetManager,
													  @Mocked final WidgetManagerImpl widgetManagerImpl, @Mocked SearchManager searchManager,
													  @Mocked final SearchManagerImpl searchManagerImpl, @Mocked TenantSubscriptionUtil tenantSubscriptionUtil,
													  @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo, @Mocked final UriInfo uriInfo,
													  @Mocked final URI uri) throws IOException, EMAnalyticsFwkException {
		final List<String> providers = new ArrayList<>();
		providers.add("providerQQ");
		providers.add("providerPP");
		final List<Widget> widgetList = new ArrayList<>();
		widgetList.add(new WidgetImpl());
		widgetList.add(new WidgetImpl());
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123&includeDashboardIneligible=true";

				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.gettenantName();
				result = "tenantName";

				TenantSubscriptionUtil.getTenantSubscribedServiceProviders(anyString);
				result = providers;

				WidgetManager.getInstance();
				result = widgetManagerImpl;
				widgetManagerImpl.getWidgetListByProviderNames(providers, anyString, anyBoolean);

				WidgetManager.getInstance();
				result = widgetManagerImpl;
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", false, "false", "false");
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsWidgetGroupIdNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
												   @Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123&includeDashboardIneligible=true";

				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.gettenantName();
				result = "tenantName";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", null, false, "false", "false");
	}

	// comment out duplicated case
	//	@Test(groups = { "s2" })
	//	public void testGetAllWidgets_widgetGroupIdNull_getWigetListFromCache_Exception(@Mocked TenantContext tenantContext,
	//			@Mocked final TenantInfo tenantInfo, @Mocked final UriInfo uriInfo, @Mocked final URI uri) throws Exception
	//	{
	//		new Expectations() {
	//			{
	//				uriInfo.getRequestUri();
	//				result = uri;
	//				uri.getQuery();
	//				result = "widgetGroupId=123&includeDashboardIneligible=true";
	//
	//				TenantContext.getContext();
	//				result = tenantInfo;
	//				tenantInfo.gettenantName();
	//				result = "tenantName";
	//			}
	//		};
	//		widgetAPI = new WidgetAPI();
	//		widgetAPI.getAllWidgets(uriInfo, "userTenant", null, false);
	//	}

	// comment out duplicated case
	//	@Test(groups = { "s2" })
	//	public void testGetAllWidgets_widgetGroupIdNull_msgNotEmpty(@Mocked TenantContext tenantContext,
	//			@Mocked final TenantInfo tenantInfo, @Mocked final UriInfo uriInfo, @Mocked final URI uri) throws Exception
	//	{
	//		new Expectations() {
	//			{
	//				uriInfo.getRequestUri();
	//				result = uri;
	//				uri.getQuery();
	//				result = "widgetGroupId=123&includeDashboardIneligible=true";
	//
	//				TenantContext.getContext();
	//				result = tenantInfo;
	//				tenantInfo.gettenantName();
	//				result = "tenantName";
	//			}
	//		};
	//		widgetAPI = new WidgetAPI();
	//		widgetAPI.getAllWidgets(uriInfo, "userTenant", null, false);
	//	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotById(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo
				) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				SearchManager.getInstance();
				result = searchManager;
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(TEST_ID_1234, "serviceVersion", "fileName");
		widgetAPI.getWidgetScreenshotById(TEST_ID_1234, "serviceVersion", "fileName2");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdB64SeNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
													 @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
//				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById((BigInteger) any);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(TEST_ID_1234, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdException(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
													 @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
//				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById((BigInteger) any);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(TEST_ID_1234, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdSeNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
												  @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
//				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById((BigInteger) any);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(TEST_ID_1234, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdSeNullEMAnalyticsFwkException(@Mocked TenantContext tenantContext,
																		 @Mocked final TenantInfo tenantInfo, @Mocked SearchManager searchManager,
																		 @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
//				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById((BigInteger) any);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(TEST_ID_1234, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdSeNullException(@Mocked TenantContext tenantContext,
														   @Mocked final TenantInfo tenantInfo, @Mocked SearchManager searchManager,
														   @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById((BigInteger) any);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(TEST_ID_1234, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdSeNullSsNull(@Mocked TenantContext tenantContext,
														@Mocked final TenantInfo tenantInfo, @Mocked SearchManager searchManager,
														@Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				dependencyStatus.isDatabaseUp();
				result = true;
//				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById((BigInteger) any);
				result = null;

			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(TEST_ID_1234, "serviceVersion", "fileName");
	}

}