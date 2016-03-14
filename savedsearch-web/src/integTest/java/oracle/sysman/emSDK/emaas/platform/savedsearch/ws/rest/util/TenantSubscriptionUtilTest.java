package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json.AppMappingCollection;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json.AppMappingEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json.DomainEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json.DomainsEntity;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;
import org.testng.annotations.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/29/2016.
 */
@Test(groups={"s2"})
public class TenantSubscriptionUtilTest {
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
    DomainsEntity domainsEntity;
    @Mocked
    DomainEntity domainEntity;
    @Mocked
    AppMappingCollection appMappingCollection;
    @Mocked
    AppMappingEntity appMappingEntity;
    @Mocked
    CategoryManager categoryManager;
    @Mocked
    Category category;
    @Mocked
    TenantSubscriptionUtil.RestClient restClient;
    @Mocked
    JsonUtil jsonUtil;
    @Mocked
    AppMappingEntity.AppMappingValue appMappingValue;

    @Test
    public void testGetTenantSubscribedCategories() throws Exception {
        final List<Category> categories = new ArrayList<>();
        categories.add(category);
        final List<DomainEntity> domainEntities = new ArrayList<>();
        domainEntities.add(domainEntity);
        final List<AppMappingEntity> appMappingEntities = new ArrayList<>();
        appMappingEntities.add(appMappingEntity);
        final List<AppMappingEntity.AppMappingValue> appMappingValues = new ArrayList<>();
        appMappingValues.add(appMappingValue);
        new Expectations(){
            {
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getAllCategories();
                result = categories;
                restClient.get(anyString);
                result = "url";
                JsonUtil.buildNormalMapper();
                result = jsonUtil;
                jsonUtil.fromJson(anyString,DomainsEntity.class);
                result = domainsEntity;
                domainsEntity.getItems();
                result = domainEntities;
                domainEntity.getDomainName();
                result = "TenantApplicationMapping";
                domainEntity.getCanonicalUrl();
                result = "url";
                jsonUtil.fromJson(anyString, AppMappingCollection.class);
                result = appMappingCollection;
                appMappingCollection.getItems();
                result =appMappingEntities;
                appMappingEntity.getValues();
                result = appMappingValues;
                appMappingValue.getOpcTenantId();
                result = "";
                appMappingValue.getApplicationNames();
                result ="name";
            }
        };
        TenantSubscriptionUtil.getTenantSubscribedCategories("",true);
    }

    @Test
    public void testGetTenantSubscribedServices() throws Exception {
        final char[] authToken = {'a','b','c'};
        final List<DomainEntity> list = new ArrayList<>();
        list.add(domainEntity);
        final List<AppMappingEntity> amecList = new ArrayList<>();
        amecList.add(appMappingEntity);
        new Expectations(){
            {
                RegistryLookupUtil.getServiceInternalLink(anyString,anyString,anyString,anyString);
                result = link;
                StringUtil.isEmpty(anyString);
                result = false;
                RegistrationManager.getInstance();
                result =registrationManager;
                registrationManager.getAuthorizationToken();
                result = authToken;
                UriBuilder.fromUri(anyString);
                result = uriBuilder;
                uriBuilder.build();
                result = uri;
                client.resource(withAny(uri));
                result = webSource;
                webSource.header(anyString,anyString);
                result = builder;
                builder.type(anyString);
                result = builder;
                builder.accept(anyString);
                result = builder;
                builder.get(String.class);
                result = "uri.uri";
                jsonUtil.fromJson(anyString,DomainsEntity.class);
                result = domainsEntity;
                domainsEntity.getItems();
                result = list;
                domainEntity.getDomainName();
                result ="TenantApplicationMapping";
                domainEntity.getCanonicalUrl();
                result = "httyp://";
                jsonUtil.fromJson(anyString,AppMappingCollection.class);
                result = appMappingCollection;
                appMappingCollection.getItems();
                result = amecList;
            }
        };
        TenantSubscriptionUtil.getTenantSubscribedServices("");
    }
}