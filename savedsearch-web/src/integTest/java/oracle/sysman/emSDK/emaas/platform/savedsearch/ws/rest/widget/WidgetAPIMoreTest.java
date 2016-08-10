package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

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
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotData;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager;

import org.testng.annotations.Test;


/**
 * Created by QIQIAN on 2016/3/28.
 */
@Test(groups = { "s1" })
public class WidgetAPIMoreTest
{

	WidgetAPI widgetAPI;
	Date now = new Date();

	@Test(groups = { "s2" })
	public void testCheckQueryParamGroupIdLessThen0(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=-123&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true);
	}

	@Test(groups = { "s2" })
	public void testCheckQueryParamInputLengthLessThen2(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true);
	}

	@Test(groups = { "s2" })
	public void testCheckQueryParamInputLengthLessThen2KeyNotCorrect(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "includeDashboardIneligiblexx=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true);
	}

	@Test(groups = { "s2" })
	public void testCheckQueryParamKey1NotCorrect(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupIdxx=123&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true);
	}

	@Test(groups = { "s2" })
	public void testCheckQueryParamKey2NotCorrect(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123&includeDashboardIneligiblexx=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true);
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
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = new EMAnalyticsFwkException(new Throwable());
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true);
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
				widgetManagerImpl.getWidgetListByProviderNames(providers, anyString);

				WidgetManager.getInstance();
				result = widgetManagerImpl;

			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", null, false);
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsGroupIdLargerThen(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true);
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsGroupIdLargerThen0ParamLessThen2(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", true);
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsGroupIdLessThen0(@Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
				uriInfo.getRequestUri();
				result = uri;
				uri.getQuery();
				result = "widgetGroupId=123&includeDashboardIneligible=true";
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "-123", true);
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
				widgetManagerImpl.getWidgetListByProviderNames(providers, anyString);

				WidgetManager.getInstance();
				result = widgetManagerImpl;
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getAllWidgets(uriInfo, "userTenant", "123", false);
	}

	@Test(groups = { "s2" })
	public void testGetAllWidgetsWidgetGroupIdNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
												   @Mocked final UriInfo uriInfo, @Mocked final URI uri)
	{
		new Expectations() {
			{
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
		widgetAPI.getAllWidgets(uriInfo, "userTenant", null, false);
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
				SearchManager.getInstance();
				result = searchManager;
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(1234L, "serviceVersion", "fileName");
		widgetAPI.getWidgetScreenshotById(1234L, "serviceVersion", "fileName2");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdB64SeNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
													 @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById(anyLong);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(1234L, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdException(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
													 @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById(anyLong);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(1234L, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdSeNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
												  @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById(anyLong);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(1234L, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdSeNullEMAnalyticsFwkException(@Mocked TenantContext tenantContext,
																		 @Mocked final TenantInfo tenantInfo, @Mocked SearchManager searchManager,
																		 @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById(anyLong);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(1234L, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdSeNullException(@Mocked TenantContext tenantContext,
														   @Mocked final TenantInfo tenantInfo, @Mocked SearchManager searchManager,
														   @Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById(anyLong);
				result = new ScreenshotData("screenShot", now, now);
			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(1234L, "serviceVersion", "fileName");
	}

	@Test(groups = { "s2" })
	public void testGetWidgetScreenshotByIdSeNullSsNull(@Mocked TenantContext tenantContext,
														@Mocked final TenantInfo tenantInfo, @Mocked SearchManager searchManager,
														@Mocked final SearchManagerImpl searchManagerImpl) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				CacheManager.getInstance().getCache(CacheManager.CACHES_SCREENSHOT_CACHE).clearCache();
				SearchManager.getInstance();
				result = searchManagerImpl;
				searchManagerImpl.getWidgetScreenshotById(anyLong);
				result = null;

			}
		};
		widgetAPI = new WidgetAPI();
		widgetAPI.getWidgetScreenshotById(1234L, "serviceVersion", "fileName");
	}

}