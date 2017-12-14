package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.AppMappingEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.DomainEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.DomainsEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;

import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Created by xidai on 2/29/2016.
 */
@Test(groups = { "s2" })
public class TenantSubscriptionUtilTest
{
	@Mocked
	VersionedLink linkInfo;
	@Mocked
	StringUtil stringUtil;
	@Mocked
	UriBuilder uriBuilder;
	@Mocked
	WebResource.Builder builder;
	@Mocked
	URI uri;
	@Mocked
	Client client;
	@Mocked
	WebResource webSource;
	@Mocked
	JSONUtil jsonUtil;
	@Mocked
	ObjectMapper mapper;
	@Mocked
	DomainsEntity domainsEntity;
	@Mocked
	DomainEntity domainEntity;
//	@Mocked
//    ServiceRequestCollection serviceRequestCollection;
	@Mocked
	AppMappingEntity appMappingEntity;
	@Mocked
	Category category;
	@Mocked
    CategoryManager categoryManager;
    @Mocked
    AppMappingEntity.AppMappingValue appMappingValue;
	@Test
	public void testGetTenantSubscribedCategories() throws Exception {
        final ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);
        final ArrayList<String> cachedApps = new ArrayList<>();
        cachedApps.add("TargetAnalytics");
        new Expectations(){
            {
                CategoryManager.getInstance();
                result =categoryManager;
                categoryManager.getAllCategories();
                result = categories;
//                cacheManager.getCacheable(withAny(tenant), anyString, anyString);
                result = cachedApps;
            }
        };
        TenantSubscriptionUtil.getTenantSubscribedCategories("testtenant", true);
	}

	@Test
	public void testGetProviderNameFromServiceName(){
		TenantSubscriptionUtil.getProviderNameFromServiceName("ITAnalytics");
		TenantSubscriptionUtil.getProviderNameFromServiceName("LogAnalytics");
		TenantSubscriptionUtil.getProviderNameFromServiceName("APM");
		TenantSubscriptionUtil.getProviderNameFromServiceName(null);
		TenantSubscriptionUtil.getProviderNameFromServiceName("APMUI");
	}

	@Test
	public void tesGetTenantSubscribedServiceProviders() throws IOException {
		final List<DomainEntity> list = new ArrayList<>();
		list.add(domainEntity);
		final List<AppMappingEntity> amecList = new ArrayList<>();
		amecList.add(appMappingEntity);
		final List<AppMappingEntity.AppMappingValue>  appMappingValues = new ArrayList<>();
		appMappingValues.add(appMappingValue);
		TenantSubscriptionUtil.getTenantSubscribedServiceProviders("testtenant");
	}
}