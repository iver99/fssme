package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

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
	AppMappingCollection appMappingCollection;
	@Mocked
	AppMappingEntity appMappingEntity;

	@Test
	public void testGetTenantSubscribedCategories() throws Exception
	{

	}

	@Test
	public void testGetTenantSubscribedServices() throws Exception
	{
		final char[] authToken = { 'a', 'b', 'c' };
		final List<DomainEntity> list = new ArrayList<>();
		list.add(domainEntity);
		final List<AppMappingEntity> amecList = new ArrayList<>();
		amecList.add(appMappingEntity);
		new Expectations() {
			{
				RegistryLookupUtil.getServiceInternalLink(anyString, anyString, anyString, anyString);
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
				JSONUtil.fromJson(mapper, anyString, DomainsEntity.class);
				result = domainsEntity;
				domainsEntity.getItems();
				result = list;
				domainEntity.getDomainName();
				result = "TenantApplicationMapping";
				domainEntity.getCanonicalUrl();
				result = "httyp://";
				JSONUtil.fromJson(mapper, anyString, AppMappingCollection.class);
				result = appMappingCollection;
				appMappingCollection.getItems();
				result = amecList;
			}
		};
		TenantSubscriptionUtil.getTenantSubscribedServices("");
	}
}