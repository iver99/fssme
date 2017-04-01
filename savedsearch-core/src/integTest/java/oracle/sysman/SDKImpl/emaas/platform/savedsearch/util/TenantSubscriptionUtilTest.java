package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.subscription2.ServiceRequestCollection;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.subscription2.TenantSubscriptionInfo;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.AppMappingCollection;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.AppMappingEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.DomainEntity;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.DomainsEntity;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;

/**
 * Created by xidai on 2/29/2016.
 */
@Test(groups = { "s2" })
public class TenantSubscriptionUtilTest
{
	@Mocked
	Link link;
	@Mocked
	RegistryLookupUtil registryLookupUtil;
	@Mocked
	StringUtil stringUtil;
	@Mocked
	RegistrationManager registrationManager;
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
	@Mocked
    ServiceRequestCollection serviceRequestCollection;
	@Mocked
	AppMappingEntity appMappingEntity;
	@Mocked
	Category category;
	@Mocked
    CategoryManager categoryManager;
    @Mocked
    CacheManager cacheManager;
    @Mocked
    Tenant tenant;
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
                cacheManager.getCacheable(withAny(tenant), anyString, anyString);
                result = cachedApps;
            }
        };
        TenantSubscriptionUtil.getTenantSubscribedCategories("testtenant", true);
	}

	@Test
	public void testGetTenantSubscribedServices() throws IOException {
		final char[] authToken = { 'a', 'b', 'c' };
		final List<DomainEntity> list = new ArrayList<>();
		list.add(domainEntity);
		final List<AppMappingEntity> amecList = new ArrayList<>();
		amecList.add(appMappingEntity);
        final List<AppMappingEntity.AppMappingValue>  appMappingValues = new ArrayList<>();
        appMappingValues.add(appMappingValue);
        new Expectations() {
			{
				RegistryLookupUtil.getServiceInternalLinkHttp(anyString, anyString, anyString, anyString);
				result = link;
				StringUtil.isEmpty(anyString);
				result = false;
				RegistrationManager.getInstance();
				result = registrationManager;
				registrationManager.getAuthorizationToken();
				result = authToken;
				UriBuilder.fromUri(anyString);
				result = uriBuilder;
				uriBuilder.build();
				result = uri;
				client.resource(withAny(uri));
				result = webSource;
				webSource.header(anyString, anyString);
				result = builder;
				builder.type(anyString);
				result = builder;
				builder.accept(anyString);
				result = builder;
				builder.get(String.class);
				result = "uri.uri";
				/*JSONUtil.fromJsonToList(anyString, ServiceRequestCollection.class);
				result = serviceRequestCollection;*/
				/*domainsEntity.getItems();
				result = list;
				domainEntity.getDomainName();
				result = "TenantApplicationMapping";
				domainEntity.getCanonicalUrl();
				result = "httyp://";*/
				JSONUtil.fromJsonToList(anyString, ServiceRequestCollection.class);
				result = serviceRequestCollection;
				/*appMappingCollection.getItems();
				result = amecList;
                appMappingEntity.getValues();
                result = appMappingValues;
                appMappingValue.getOpcTenantId();
                result = "testtenant";
                appMappingValue.getApplicationNames();
                result = "LogAnalytics,ITAnalytics,APM";*/
			}
		};
		TenantSubscriptionUtil.getTenantSubscribedServices("testtenant",new TenantSubscriptionInfo());
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
		final char[] authToken = { 'a', 'b', 'c' };
		final List<DomainEntity> list = new ArrayList<>();
		list.add(domainEntity);
		final List<AppMappingEntity> amecList = new ArrayList<>();
		amecList.add(appMappingEntity);
		final List<AppMappingEntity.AppMappingValue>  appMappingValues = new ArrayList<>();
		appMappingValues.add(appMappingValue);
		new Expectations() {
			{
				RegistryLookupUtil.getServiceInternalLinkHttp(anyString, anyString, anyString, anyString);
				result = link;
				StringUtil.isEmpty(anyString);
				result = false;
				RegistrationManager.getInstance();
				result = registrationManager;
				registrationManager.getAuthorizationToken();
				result = authToken;
				UriBuilder.fromUri(anyString);
				result = uriBuilder;
				uriBuilder.build();
				result = uri;
				client.resource(withAny(uri));
				result = webSource;
				webSource.header(anyString, anyString);
				result = builder;
				builder.type(anyString);
				result = builder;
				builder.accept(anyString);
				result = builder;
				builder.get(String.class);
				result = "uri.uri";
				/*domainEntity.getDomainName();
				result = "TenantApplicationMapping";
				domainEntity.getCanonicalUrl();
				result = "httyp://";*/
				JSONUtil.fromJsonToList(anyString, ServiceRequestCollection.class);
				result = serviceRequestCollection;
				/*appMappingCollection.getItems();
				result = amecList;
				appMappingEntity.getValues();
				result = appMappingValues;
				appMappingValue.getOpcTenantId();
				result = "testtenant";
				appMappingValue.getApplicationNames();
				result = "LogAnalytics,ITAnalytics,APM";*/
			}
		};
		TenantSubscriptionUtil.getTenantSubscribedServiceProviders("testtenant");
	}
}