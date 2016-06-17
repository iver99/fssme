package oracle.sysman.emaas.platform.savedsearch.utils;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicStatusLine;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = {"s2"})
public class RestRequestUtilTest {
	public static final String MOCKED_RESPONSE = "{\"meClass\":\"TARGET\", \"meId\":\"odsentityid\"}";
	public static HttpResponse response = null;
	public static HttpEntity entity = null;
	public static StatusLine statusLine = null;
	
	@BeforeClass
	public static void setUp() throws Exception {
		response = new MyHttpResponseProxy();
		entity = new StringEntity(MOCKED_RESPONSE);
		statusLine = new BasicStatusLine(new ProtocolVersion("HTTP", 0, 0), 200, null);
	}
	
	public void testRestGet(@Mocked final CloseableHttpClient client, @Mocked final LookupManager lm, 
			@Mocked final MyHttpResponseProxy proxy, @Mocked final TenantContext context,
			@Mocked final TenantInfo info) throws Exception {
		
		new Expectations() {
			{
				TenantContext.getContext();
				result = info;
				info.gettenantName();
				result = "tenant";
				lm.getAuthorizationToken();
				result = new char[]{'a', 'b'};
				client.execute((HttpUriRequest) any);
				result = response;
				proxy.getEntity();
				result = entity;
				proxy.getStatusLine();
				result = statusLine;
			}
		};
		
		Assert.assertEquals(RestRequestUtil.restGet("http://xxx"), MOCKED_RESPONSE);
	}
	
	public void testRestPost(@Mocked final CloseableHttpClient client, @Mocked final LookupManager lm, 
			@Mocked final MyHttpResponseProxy proxy, @Mocked final TenantContext context,
			@Mocked final TenantInfo info) throws Exception {
		
		new Expectations() {
			{
				TenantContext.getContext();
				result = info;
				info.gettenantName();
				result = "tenant";
				lm.getAuthorizationToken();
				result = new char[]{'a', 'b'};
				client.execute((HttpUriRequest) any);
				result = response;
				proxy.getEntity();
				result = entity;
				proxy.getStatusLine();
				result = statusLine;
			}
		};
		
		Assert.assertEquals(RestRequestUtil.restPost("http://xxx", MOCKED_RESPONSE), MOCKED_RESPONSE);
	}
	
	public void testRestDelete(@Mocked final CloseableHttpClient client, @Mocked final LookupManager lm, 
			@Mocked final MyHttpResponseProxy proxy, @Mocked final TenantContext context,
			@Mocked final TenantInfo info) throws Exception {
		
		new Expectations() {
			{
				TenantContext.getContext();
				result = info;
				info.gettenantName();
				result = "tenant";
				lm.getAuthorizationToken();
				result = new char[]{'a', 'b'};
				client.execute((HttpUriRequest) any);
				result = response;
				proxy.getEntity();
				result = entity;
				proxy.getStatusLine();
				result = statusLine;
			}
		};
		
		Assert.assertEquals(RestRequestUtil.restDelete("http://xxx"), MOCKED_RESPONSE);
	}
}
