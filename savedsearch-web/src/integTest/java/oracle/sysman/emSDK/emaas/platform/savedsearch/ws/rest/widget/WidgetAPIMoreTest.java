package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.server.impl.application.WebApplicationContext;
import com.sun.jersey.server.impl.application.WebApplicationImpl;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.tangosol.util.Binary;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.WidgetImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.WidgetManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.WidgetCacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotCacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotData;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotElement;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import org.testng.annotations.Test;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by QIQIAN on 2016/3/28.
 */
@Test(groups = {"s1"})
public class WidgetAPIMoreTest {

    WidgetAPI widgetAPI;
    @Test
    public void testGetAllWidgets() throws Exception {
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(new WebApplicationContext(new WebApplicationImpl(),new ContainerRequest(new WebApplicationImpl(), "method",null,null,new InBoundHeaders(),null),new ContainerResponse(new WebApplicationImpl(),null, null)),"userTenant","widgetGroupId",true);
    }

    @Test(groups = {"s2"})
    public void testGetAllWidgets_groupIdLessThen0(@Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=123&includeDashboardIneligible=true";
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","-123",true);
    }

    @Test(groups = {"s2"})
    public void testGetAllWidgets_groupIdLargerThen0(@Mocked final WidgetCacheManager widgetCacheManager, @Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=123&includeDashboardIneligible=true";
                WidgetCacheManager.getInstance();
                result = widgetCacheManager;
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","123",true);
    }

    @Test(groups = {"s2"})
    public void testGetAllWidgets_EMAnalyticsFwkException(@Mocked final WidgetCacheManager widgetCacheManager, @Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = new EMAnalyticsFwkException(new Throwable());
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","123",true);
    }

    @Test(groups = {"s2"})
    public void testGetAllWidgets_groupIdLargerThen0_paramLessThen2(@Mocked final WidgetCacheManager widgetCacheManager, @Mocked final UriInfo uriInfo, @Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=123";
                WidgetCacheManager.getInstance();
                result = widgetCacheManager;
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","123",true);
    }

    @Test(groups = {"s2"})
    public void testCheckQueryParam_key1NotCorrect(@Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupIdxx=123&includeDashboardIneligible=true";
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","123",true);
    }

    @Test(groups = {"s2"})
    public void testCheckQueryParam_key2NotCorrect(@Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=123&includeDashboardIneligiblexx=true";
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","123",true);
    }

    @Test(groups = {"s2"})
    public void testCheckQueryParam_groupIdLessThen0(@Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=-123&includeDashboardIneligible=true";
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","123",true);
    }

    @Test(groups = {"s2"})
    public void testCheckQueryParam_inputLengthLessThen2(@Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId&includeDashboardIneligible=true";
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","123",true);
    }

    @Test(groups = {"s2"})
    public void testCheckQueryParam_inputLengthLessThen2_keyNotCorrect(@Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "includeDashboardIneligiblexx=true";
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","123",true);
    }

    @Test(groups = {"s2"})
    public void testGetWidgetScreenshotById(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                            @Mocked final ScreenshotCacheManager screenshotCacheManager) throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";

                ScreenshotCacheManager.getInstance();
                result = screenshotCacheManager;
                screenshotCacheManager.getScreenshotFromCache((Tenant)any,anyLong,anyString);
                result = new ScreenshotElement("fileName",new Binary());
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getWidgetScreenshotById(1234L,"serviceVersion","fileName");
        widgetAPI.getWidgetScreenshotById(1234L,"serviceVersion","fileName2");
    }

    @Test(groups = {"s2"})
    public void testGetWidgetScreenshotById_Exception(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                   @Mocked final ScreenshotCacheManager screenshotCacheManager,
                                                   @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";

                ScreenshotCacheManager.getInstance();
                result = screenshotCacheManager;
                screenshotCacheManager.getScreenshotFromCache((Tenant)any,anyLong,anyString);
                result = new Exception();

                SearchManager.getInstance();
                result = searchManagerImpl;
                searchManagerImpl.getWidgetScreenshotById(anyLong);
                result = new ScreenshotData("screenShot",new Date(),new Date());
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getWidgetScreenshotById(1234L,"serviceVersion","fileName");
    }

    @Test(groups = {"s2"})
    public void testGetWidgetScreenshotById_seNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                   @Mocked final ScreenshotCacheManager screenshotCacheManager,
                                                   @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";

                ScreenshotCacheManager.getInstance();
                result = screenshotCacheManager;
                screenshotCacheManager.getScreenshotFromCache((Tenant)any,anyLong,anyString);
                result = null;

                SearchManager.getInstance();
                result = searchManagerImpl;
                searchManagerImpl.getWidgetScreenshotById(anyLong);
                result = new ScreenshotData("screenShot",new Date(),new Date());
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getWidgetScreenshotById(1234L,"serviceVersion","fileName");
    }

    @Test(groups = {"s2"})
    public void testGetWidgetScreenshotById_seNull_ssNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                   @Mocked final ScreenshotCacheManager screenshotCacheManager,
                                                   @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";

                ScreenshotCacheManager.getInstance();
                result = screenshotCacheManager;
                screenshotCacheManager.getScreenshotFromCache((Tenant)any,anyLong,anyString);
                result = null;

                SearchManager.getInstance();
                result = searchManagerImpl;
                searchManagerImpl.getWidgetScreenshotById(anyLong);
                result = null;

            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getWidgetScreenshotById(1234L,"serviceVersion","fileName");
    }

    @Test(groups = {"s2"})
    public void testGetWidgetScreenshotById_B64seNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                   @Mocked final ScreenshotCacheManager screenshotCacheManager,
                                                   @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";

                ScreenshotCacheManager.getInstance();
                result = screenshotCacheManager;
                screenshotCacheManager.getScreenshotFromCache((Tenant)any,anyLong,anyString);
                result = null;
                screenshotCacheManager.storeBase64ScreenshotToCache((Tenant)any,anyLong,(ScreenshotData)any);
                result = null;

                SearchManager.getInstance();
                result = searchManagerImpl;
                searchManagerImpl.getWidgetScreenshotById(anyLong);
                result = new ScreenshotData("screenShot",new Date(),new Date());
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getWidgetScreenshotById(1234L,"serviceVersion","fileName");
    }

    @Test(groups = {"s2"})
    public void testGetWidgetScreenshotById_seNull_EMAnalyticsFwkException(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                      @Mocked final ScreenshotCacheManager screenshotCacheManager,
                                                      @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";

                ScreenshotCacheManager.getInstance();
                result = screenshotCacheManager;
                screenshotCacheManager.getScreenshotFromCache((Tenant)any,anyLong,anyString);
                result = null;
                screenshotCacheManager.storeBase64ScreenshotToCache((Tenant)any,anyLong,(ScreenshotData)any);
                result = new EMAnalyticsFwkException(new Throwable());

                SearchManager.getInstance();
                result = searchManagerImpl;
                searchManagerImpl.getWidgetScreenshotById(anyLong);
                result = new ScreenshotData("screenShot",new Date(),new Date());
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getWidgetScreenshotById(1234L,"serviceVersion","fileName");
    }

    @Test(groups = {"s2"})
    public void testGetWidgetScreenshotById_seNull_Exception(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                                           @Mocked final ScreenshotCacheManager screenshotCacheManager,
                                                                           @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl) throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";

                ScreenshotCacheManager.getInstance();
                result = screenshotCacheManager;
                screenshotCacheManager.getScreenshotFromCache((Tenant)any,anyLong,anyString);
                result = null;
                screenshotCacheManager.storeBase64ScreenshotToCache((Tenant)any,anyLong,(ScreenshotData)any);
                result = new Exception();

                SearchManager.getInstance();
                result = searchManagerImpl;
                searchManagerImpl.getWidgetScreenshotById(anyLong);
                result = new ScreenshotData("screenShot",new Date(),new Date());
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getWidgetScreenshotById(1234L,"serviceVersion","fileName");
    }

    @Test(groups = {"s2"})
    public void testGetAllWidgetsFromCache_widgetGroupIdNull(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                              @Mocked final WidgetCacheManager widgetCacheManager, @Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=123&includeDashboardIneligible=true";
                WidgetCacheManager.getInstance();
                result = widgetCacheManager;

                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant",null,false);
    }

    @Test(groups = {"s2"})
    public void testGetAllWidgetsFromCache_widgetGroupIdNull_msgNotEmpty(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                           @Mocked final WidgetCacheManager widgetCacheManager, @Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=123&includeDashboardIneligible=true";
                WidgetCacheManager.getInstance();
                result = widgetCacheManager;
                widgetCacheManager.getWigetListFromCache((Tenant)any);
                result = "hahaha";

                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant",null,false);
    }

    @Test(groups = {"s2"})
    public void testGetAllWidgetsFromCache_widgetGroupIdNull_getWigetListFromCache_Exception(@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                       @Mocked final WidgetCacheManager widgetCacheManager, @Mocked final UriInfo uriInfo,@Mocked final URI uri) throws Exception {
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=123&includeDashboardIneligible=true";
                WidgetCacheManager.getInstance();
                result = widgetCacheManager;
                widgetCacheManager.getWigetListFromCache((Tenant)any);
                result = new Exception();

                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant",null,false);
    }

    @Test(groups = {"s2"})
    public void testGetAllWidgetsFromCache_getWigetListFromCache_Exception_widgetGroupIdNotNull(@Mocked WidgetManager widgetManager,@Mocked final WidgetManagerImpl widgetManagerImpl,
                                                                @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl,
                                                                @Mocked TenantSubscriptionUtil tenantSubscriptionUtil,
                                                                @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                                @Mocked final WidgetCacheManager widgetCacheManager, @Mocked final UriInfo uriInfo, @Mocked final URI uri) throws Exception {
        final List<String> providers = new ArrayList<>();
        providers.add("providerQQ");
        providers.add("providerPP");
        final List<Widget> widgetList = new ArrayList<>();
        widgetList.add(new WidgetImpl());
        widgetList.add(new WidgetImpl());
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=123&includeDashboardIneligible=true";
                WidgetCacheManager.getInstance();
                result = widgetCacheManager;
                widgetCacheManager.getWigetListFromCache((Tenant)any);
                result = new Exception();

                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";

                TenantSubscriptionUtil.getTenantSubscribedServiceProviders(anyString);
                result = providers;

                SearchManager.getInstance();
                result = searchManagerImpl;
                searchManagerImpl.getWidgetListByProviderNames(anyBoolean,providers,anyString);

                WidgetManager.getInstance();
                result = widgetManagerImpl;

                widgetCacheManager.storeWidgetListToCache((Tenant)any,anyString);

            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant",null,false);
    }

    @Test(groups = {"s2"})
    public void testGetAllWidgetsFromCache_widgetGroupIdNotNull(@Mocked WidgetManager widgetManager,@Mocked final WidgetManagerImpl widgetManagerImpl,
                                                                @Mocked SearchManager searchManager, @Mocked final SearchManagerImpl searchManagerImpl,
                                                                @Mocked TenantSubscriptionUtil tenantSubscriptionUtil,
                                                                @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
                                                                @Mocked final WidgetCacheManager widgetCacheManager, @Mocked final UriInfo uriInfo, @Mocked final URI uri) throws Exception {
        final List<String> providers = new ArrayList<>();
        providers.add("providerQQ");
        providers.add("providerPP");
        final List<Widget> widgetList = new ArrayList<>();
        widgetList.add(new WidgetImpl());
        widgetList.add(new WidgetImpl());
        new Expectations(){
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "widgetGroupId=123&includeDashboardIneligible=true";
                WidgetCacheManager.getInstance();
                result = widgetCacheManager;

                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1234L;
                tenantInfo.gettenantName();
                result = "tenantName";

                TenantSubscriptionUtil.getTenantSubscribedServiceProviders(anyString);
                result = providers;

                SearchManager.getInstance();
                result = searchManagerImpl;
                searchManagerImpl.getWidgetListByProviderNames(anyBoolean,providers,anyString);

                WidgetManager.getInstance();
                result = widgetManagerImpl;
            }
        };
        widgetAPI = new WidgetAPI();
        widgetAPI.getAllWidgets(uriInfo,"userTenant","123",false);
    }

}