package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.WidgetImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.WidgetManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/25.
 */
@Test(groups = {"s1"})
public class WidgetCacheManagerTest {

    WidgetCacheManager widgetCacheManager = WidgetCacheManager.getInstance();
    @Test
    public void testClearCachedKeys() throws Exception {
        widgetCacheManager.clearCachedKeys();
    }

    @Test(groups = {"s2"})
    public void testGetWigetListFromCache_wgtListNull(@Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userNamexx";
            }
        };
        widgetCacheManager.getWigetListFromCache(new Tenant("tenantxx"));
    }

    @Test(groups = {"s2"})
    public void testGetWigetListFromCache(@Mocked final CacheManager cacheManager, @Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                CacheManager.getInstance();
                result = cacheManager;
                cacheManager.getCacheable((Tenant)any,anyString,anyString);
                result = "wgtList";
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userNamexx";
            }
        };
        widgetCacheManager.getWigetListFromCache(new Tenant("tenantxx"));
        widgetCacheManager.reloadRefreshableCaches();

    }

    @Test
    public void testReloadRefreshableCaches_cachedKeysEmpty() throws Exception {
        widgetCacheManager.clearCachedKeys();
        widgetCacheManager.reloadRefreshableCaches();
    }

    @Test(groups = {"s2"})
    public void testReloadRefreshableCaches_Mocked(@Mocked WidgetManager widgetManager,@Mocked final WidgetManagerImpl widgetManagerImpl,
                                                   @Mocked final SearchManager searchManager,
                                                   @Mocked TenantSubscriptionUtil tenantSubscriptionUtil,
                                                   @Mocked final CacheManager cacheManager,
                                                   @Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {

        final List<String> providers = new ArrayList<>();
        providers.add(new String("provider1"));
        providers.add(new String("provider2"));

        final List<Widget> widgetList = new ArrayList<>();
        widgetList.add(new WidgetImpl());
        widgetList.add(new WidgetImpl());

        new Expectations(){
            {
                CacheManager.getInstance();
                result = cacheManager;
                cacheManager.getCacheable((Tenant)any,anyString,anyString);
                result = "wgtList";
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userNamexx";
                TenantSubscriptionUtil.getTenantSubscribedServiceProviders(anyString);
                result = providers;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getWidgetListByProviderNames(false,providers,null);
                result = widgetList;
                WidgetManager.getInstance();
                result = widgetManagerImpl;
                widgetManagerImpl.getWidgetJsonStringFromWidgetList(widgetList);
                result = "astring";
            }
        };
        widgetCacheManager.getWigetListFromCache(new Tenant("tenantxx"));
        widgetCacheManager.reloadRefreshableCaches();
    }

    @Test(groups = {"s2"})
    public void testReloadRefreshableCaches_MockedWithException(@Mocked final CacheManager cacheManager,
                                                   @Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {

        new Expectations(){
            {
                CacheManager.getInstance();
                result = cacheManager;
                cacheManager.getCacheable((Tenant)any,anyString,anyString);
                result = "wgtList";
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userNamexx";
            }
        };
        widgetCacheManager.getWigetListFromCache(new Tenant("tenantxx"));
        widgetCacheManager.reloadRefreshableCaches();
    }

    @Test(groups = {"s2"})
    public void testReloadRefreshableCaches_notCachedKeys(@Mocked final DefaultKeyGenerator defaultKeyGenerator,
                                                          @Mocked final CacheManager cacheManager,
                                                          @Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {

        new Expectations(){
            {
                CacheManager.getInstance();
                result = cacheManager;
                cacheManager.getCacheable((Tenant)any,anyString,anyString);
                result = "wgtList";
                withAny(defaultKeyGenerator).generate((Tenant)any,(Keys)any);
                result = new Object();
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userNamexx";
            }
        };
        widgetCacheManager.getWigetListFromCache(new Tenant("tenantxx"));
        widgetCacheManager.reloadRefreshableCaches();
    }

    @Test(groups = {"s2"})
    public void testStoreWidgetListToCache(@Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userNamexx";
            }
        };
        widgetCacheManager.storeWidgetListToCache(new Tenant("tenantxx"),"widgetList");
        widgetCacheManager.storeWidgetListToCache(new Tenant("tenantxx"),null);

    }
}