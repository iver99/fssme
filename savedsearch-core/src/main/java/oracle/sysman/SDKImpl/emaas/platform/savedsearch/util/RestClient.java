/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil.InteractionLogDirection;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * @author guochen
 */
public class RestClient
{
	private static final Logger LOGGER = LogManager.getLogger(RestClient.class);
	private static final Logger LOGGERITR = LogUtil.getInteractionLogger();
	private static final String HTTP_HEADER_X_USER_IDENTITY_DOMAIN_NAME = "X-USER-IDENTITY-DOMAIN-NAME";

	public RestClient()
	{
	}

	public String get(String url)
	{
		return get(url, null);
	}

	public String get(String url, String tenant)
	{
		if (StringUtil.isEmpty(url)) {
			return null;
		}

		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		char[] authToken = RegistrationManager.getInstance().getAuthorizationToken();
		String auth = String.copyValueOf(authToken);
		if (StringUtil.isEmpty(auth)) {
			LOGGER.warn("Warning: RestClient get an empty auth token when connection to url {}", url);
		}
		else {
			LogUtil.setInteractionLogThreadContext(tenant, url, InteractionLogDirection.OUT);
			LOGGERITR
			.info("RestClient is connecting to {} after getting authorization token from registration manager. HTTP method is get.",
					url);
		}
		Builder builder = client.resource(UriBuilder.fromUri(url).build()).header(HttpHeaders.AUTHORIZATION, auth)
				.header(HTTP_HEADER_X_USER_IDENTITY_DOMAIN_NAME, tenant).type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		return builder.get(String.class);
	}

	/**
	 * HTTP post request to send a non-empty request entity to a non-empty URL for specific URL<br>
	 * NOTE: currently empty body isn't supported
	 *
	 * @param url
	 * @param headers
	 * @param requestEntity
	 * @param tenant
	 * @return
	 */
	public Object post(String url, Map<String, Object> headers, Object requestEntity, String tenant)
	{
		if (StringUtil.isEmpty(url)) {
			LOGGER.error("Unable to post to an empty URL for requestEntity: \"{}\", tenant: \"{}\"", requestEntity, tenant);
			return null;
		}
		if (requestEntity == null || "".equals(requestEntity)) {
			LOGGER.error("Unable to post an empty request entity");
			return null;
		}

		ClientConfig cc = new DefaultClientConfig();
		cc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(cc);
		char[] authToken = RegistrationManager.getInstance().getAuthorizationToken();
		String auth = String.copyValueOf(authToken);
		if (StringUtil.isEmpty(auth)) {
			LOGGER.warn("Warning: RestClient get an empty auth token when connection to url {}", url);
		}
		else {
			LogUtil.setInteractionLogThreadContext(tenant, url, InteractionLogDirection.OUT);
			LOGGERITR
			.info("RestClient is connecting to {} after getting authorization token from registration manager. HTTP method is post.",
					url);
		}
		Builder builder = client.resource(UriBuilder.fromUri(url).build()).header(HttpHeaders.AUTHORIZATION, auth)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		// tenant could be null and needs to handle the header then
		if (!StringUtil.isEmpty(tenant)) {
			builder.header(HTTP_HEADER_X_USER_IDENTITY_DOMAIN_NAME, tenant);
		}
		if (headers != null) {
			for (String key : headers.keySet()) {
				if (HttpHeaders.AUTHORIZATION.equals(key) || HTTP_HEADER_X_USER_IDENTITY_DOMAIN_NAME.equals(key)) {
					continue;
				}
				Object value = headers.get(key);
				if (value == null) {
					continue;
				}
				builder.header(key, value);
			}
		}
		return builder.post(requestEntity.getClass(), requestEntity);
	}

	/**
	 * HTTP post request to send a non-empty request entity to a non-empty URL for specific URL<br>
	 * NOTE: currently empty body isn't supported
	 *
	 * @param url
	 * @param requestEntity
	 * @param tenant
	 * @return
	 */
	public Object post(String url, Object requestEntity, String tenant)
	{
		return post(url, null, requestEntity, tenant);
	}
}
