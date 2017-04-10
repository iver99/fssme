package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;

public class RestClientTest
{
	@Test(groups = { "s2" })
	public void testPostNull()
	{
		RestClient rc = new RestClient();
		Object rtn = rc.post(null, null, null, null, null);
		Assert.assertNull(rtn);

		rtn = rc.post("url", null, null, null, null);
		Assert.assertNull(rtn);
	}

	@Test(groups = { "s2" })
	public void testPostNullAuthS2(@Mocked final DefaultClientConfig anyClientConfig, @Mocked final Client anyClient,
			@Mocked final Map<String, Boolean> anyMap, @Mocked final RegistrationManager anyRegistrationManager,
			@Mocked final URI anyUri, @Mocked final UriBuilder anyUriBuilder, @Mocked final MediaType anyMediaType,
			@Mocked final com.sun.jersey.api.client.WebResource.Builder anyBuilder, @Mocked final StringUtil anyStringUtil)
					throws Exception
	{
		new NonStrictExpectations() {
			{
				new DefaultClientConfig();
				anyClientConfig.getFeatures();
				result = anyMap;
				anyMap.put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
				Client.create(anyClientConfig);
				result = anyClient;
				StringUtil.isEmpty(anyString);
				result = false;
			}
		};
		new RestClient().post("http://test.link.com", null, new String("test"), "emaastesttenant1", "auth");
		new Verifications() {
			{
				UriBuilder.fromUri(anyString).build();
				anyClient.resource(anyUri).header(anyString, any);
				anyBuilder.post(String.class, any);
			}
		};
	}

	@Test(groups = { "s2" })
	public void testPostS2(@Mocked final DefaultClientConfig anyClientConfig, @Mocked final Client anyClient,
			@Mocked final Map<String, Boolean> anyMap, @Mocked final RegistrationManager anyRegistrationManager,
			@Mocked final URI anyUri, @Mocked final UriBuilder anyUriBuilder, @Mocked final MediaType anyMediaType,
			@Mocked final com.sun.jersey.api.client.WebResource.Builder anyBuilder) throws Exception
	{
		new NonStrictExpectations() {
			{
				new DefaultClientConfig();
				anyClientConfig.getFeatures();
				result = anyMap;
				anyMap.put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
				Client.create(anyClientConfig);
			}
		};
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("X-USER-IDENTITY-DOMAIN-NAME", "emaastesttenant1");
		new RestClient().post("http://test.link.com", headers, new String("test"), "emaastesttenant1", "auth");
		new Verifications() {
			{
				UriBuilder.fromUri(anyString).build();
				anyClient.resource(anyUri).header(anyString, any);
				anyBuilder.post(String.class, any);
			}
		};
	}

	@Test(groups = { "s2" })
	public void testRestClientGetNull()
	{
		String res = new RestClient().get(null, null, null);
		Assert.assertNull(res);
	}

	@Test(groups = { "s2" })
	public void testRestClientGetNullAuthS2(@Mocked final DefaultClientConfig anyClientConfig, @Mocked final Client anyClient,
			@Mocked final RegistrationManager anyRegistrationManager, @Mocked final URI anyUri,
			@Mocked final UriBuilder anyUriBuilder, @Mocked final MediaType anyMediaType,
			@Mocked final com.sun.jersey.api.client.WebResource.Builder anyBuilder, @Mocked final StringUtil anyStringUtil)
					throws Exception
	{
		new NonStrictExpectations() {
			{
				new DefaultClientConfig();
				Client.create(anyClientConfig);
				StringUtil.isEmpty(anyString);
				result = false;
			}
		};
		new RestClient().get("http://test.link.com", "emaastesttenant1", "auth");
		new Verifications() {
			{
				UriBuilder.fromUri(anyString).build();
				anyClient.resource(anyUri).header(anyString, any);
				anyBuilder.get(String.class);
			}
		};
	}

	@Test(groups = { "s2" })
	public void testRestClientGetS2(@Mocked final DefaultClientConfig anyClientConfig, @Mocked final Client anyClient,
			@Mocked final RegistrationManager anyRegistrationManager, @Mocked final URI anyUri,
			@Mocked final UriBuilder anyUriBuilder, @Mocked final MediaType anyMediaType,
			@Mocked final com.sun.jersey.api.client.WebResource.Builder anyBuilder) throws Exception
	{
		new NonStrictExpectations() {
			{
				new DefaultClientConfig();
				Client.create(anyClientConfig);
			}
		};
		new RestClient().get("http://test.link.com", "emaastesttenant1", "auth");
		new Verifications() {
			{
				UriBuilder.fromUri(anyString).build();
				anyClient.resource(anyUri).header(anyString, any);
				anyBuilder.get(String.class);
			}
		};
	}
}
