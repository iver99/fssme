package oracle.sysman.emaas.platform.savedsearch.utils;

import java.net.URI;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.apache.http.Consts;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class RestRequestUtil {
	public static final String TENANT_HEADER = "X-USER-IDENTITY-DOMAIN-NAME";
	public static final String AUTHORIZATION_HEADER = "Authorization";

	private RestRequestUtil() {
	}

	public static String restGet(String baseUrl, String authToken) throws Exception {
		// Construct URL
		URI restUri = new URIBuilder(baseUrl).build();

		// Construct GET Request
		HttpGet request = new HttpGet(restUri);
		
		return sendRequest(request, authToken);
	}
	
	public static String restDelete(String baseUrl, String authToken) throws Exception {
		// Construct URL
		URI restUri = new URIBuilder(baseUrl).build();

		// Construct DELETE Request
		HttpDelete request = new HttpDelete(restUri);
		
		return sendRequest(request, authToken);
	}
	
	public static String restPost(String baseUrl, String json, String authToken) throws Exception {
		// Construct URL
		URI restUri = new URIBuilder(baseUrl).build();

		// Construct POST Request
		HttpPost request = new HttpPost(restUri);

		// set Request Body
		ContentType contentType = ContentType.create("application/json", Consts.UTF_8);
		StringEntity s = new StringEntity(json, contentType);
		request.setEntity(s);
		
		return sendRequest(request, authToken);
	}
	
	/**
	 * public part of request sending
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private static String sendRequest(HttpRequestBase request, String authToken) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		
		// Set Request Headers
		String tenantName = TenantContext.getContext().gettenantName();
		if (tenantName != null && !tenantName.isEmpty()) {
			request.setHeader(TENANT_HEADER, tenantName);
		}

		request.setHeader(AUTHORIZATION_HEADER, authToken);
		
		// Run request
		HttpResponse response = null;
		String responseStr = null;
		try {
			response = client.execute(request);
			responseStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new HttpException(request.getMethod() + " on " + request.getURI() + " failed.", e);
		}
		
		// Return the response as exception if the status is not 200 & 201
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK
				&& response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
			throw new EMAnalyticsFwkException(responseStr, EMAnalyticsFwkException.ERR_GENERIC, null);
		}
		
		return responseStr;
	}
}
