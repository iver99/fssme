/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.registration.RegistrationManager;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.metadata.ApplicationEditionConverter;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.json.AppMappingCollection;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.json.AppMappingEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.json.DomainEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.json.DomainsEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.LogUtil.InteractionLogDirection;

/**
 * @author guobaochen
 */
public class TenantSubscriptionUtil
{
	public static class RestClient
	{
		public RestClient()
		{
		}

		public String get(String url, String tenant, String userTenant)
		{
			if (StringUtils.isEmpty(url)) {
				return null;
			}

			ClientConfig cc = new DefaultClientConfig();
			Client client = Client.create(cc);
			char[] authToken = RegistrationManager.getInstance().getAuthorizationToken();
			String auth = String.copyValueOf(authToken);
			if (StringUtil.isEmpty(auth)) {
				logger.warn("Warning: RestClient get an empty auth token when connection to url {}", url);
			}
			else {
				LogUtil.setInteractionLogThreadContext(tenant, url, InteractionLogDirection.OUT);
				itrLogger.info(
						"RestClient is connecting to get response after getting authorization token from registration manager.");
			}
			Builder builder = null;
			if (tenant != null) {
				builder = client.resource(UriBuilder.fromUri(url).build()).header(HttpHeaders.AUTHORIZATION, auth)
						.header("X-USER-IDENTITY-DOMAIN-NAME", tenant)
						//.header("X-REMOTE-USER", userTenant)
						.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			} else {
				builder = client.resource(UriBuilder.fromUri(url).build()).header(HttpHeaders.AUTHORIZATION, auth)
						.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			}
			
			return builder.get(String.class);
		}

		/**
		 * HTTP put request to send a non-empty request entity to a non-empty URL for specific URL<br>
		 * NOTE: currently empty body isn't supported
		 *
		 * @param url
		 * @param requestEntity
		 * @param tenant
		 * @return
		 */
		public String put(String url, Object requestEntity, String tenant, String userTenant)
		{
			if (StringUtils.isEmpty(url)) {
				logger.error("Unable to put to an empty URL");
				return null;
			}
			if (requestEntity == null) {
				logger.error("Unable to put an empty request entity");
				return null;
			}

			ClientConfig cc = new DefaultClientConfig();
			Client client = Client.create(cc);
			char[] authToken = RegistrationManager.getInstance().getAuthorizationToken();
			String auth = String.copyValueOf(authToken);
			if (StringUtil.isEmpty(auth)) {
				logger.warn("Warning: RestClient get an empty auth token when connection to url {}", url);
			}
			else {
				LogUtil.setInteractionLogThreadContext(tenant, url, InteractionLogDirection.OUT);
				itrLogger.info(
						"RestClient is connecting to get response after getting authorization token from registration manager.");
			}
			Builder builder = null;
			if (tenant != null) {
				builder = client.resource(UriBuilder.fromUri(url).build()).header(HttpHeaders.AUTHORIZATION, auth)
						.header("X-USER-IDENTITY-DOMAIN-NAME", tenant)
						//.header("X-REMOTE-USER", userTenant)
						.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			} else {
				builder = client.resource(UriBuilder.fromUri(url).build()).header(HttpHeaders.AUTHORIZATION, auth)
						.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			}
			String response = null;
			try {
				 response = builder.put(String.class, requestEntity);
			} catch (Exception e) {
				logger.warn(e.getLocalizedMessage());
			}
		    
			
			
			return response;
		}
	}

	private static Logger logger = LogManager.getLogger(TenantSubscriptionUtil.class);
	private static Logger itrLogger = LogUtil.getInteractionLogger();
}
