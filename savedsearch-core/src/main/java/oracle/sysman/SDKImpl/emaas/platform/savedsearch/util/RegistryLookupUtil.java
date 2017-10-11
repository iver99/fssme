/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CachedLink;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.SanitizedInstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;

import oracle.sysman.emaas.platform.emcpdf.cache.api.ICacheManager;
import oracle.sysman.emaas.platform.emcpdf.cache.support.CacheManagers;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.DefaultKeyGenerator;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.Keys;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.Tenant;
import oracle.sysman.emaas.platform.emcpdf.cache.util.CacheConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author aduan
 */
public class RegistryLookupUtil
{
	private static final Logger LOGGER = LogManager.getLogger(RegistryLookupUtil.class);
	private static final Logger LOGGERITR = LogUtil.getInteractionLogger();

	public static final String APM_SERVICE = "ApmUI";
	public static final String ITA_SERVICE = "emcitas-ui-apps";
	public static final String LA_SERVICE = "LoganService";
	public static final String TA_SERVICE = "TargetAnalytics";
	public static final String MONITORING_SERVICE = "MonitoringServiceUI";
	public static final String SECURITY_ANALYTICS_SERVICE = "SecurityAnalyticsUI";
	public static final String COMPLIANCE_SERVICE = "ComplianceUIService";
	public static final String ORCHESTRATION_SERVICE = "CosUIService";

	public static List<VersionedLink> getAllServicesInternalLinksByRel(String rel) throws IOException
	{
		LOGGER.debug("/getInternalLinksByRel/ Trying to retrieve service internal link with rel: \"{}\"", rel);
		//.initComponent() reads the default "looup-client.properties" file in class path
		//.initComponent(List<String> urls) can override the default Registry urls with a list of urls
		if (LookupManager.getInstance().getLookupClient() == null) {
			// making sure the initComponent is only called once during the client lifecycle
			LookupManager.getInstance().initComponent();
		}
		List<InstanceInfo> instanceList = LookupManager.getInstance().getLookupClient().getInstancesWithLinkRelPrefix(rel,
				"http");
		if (instanceList == null) {
			LOGGER.warn("Found no instances with specified http rel {}", rel);
			return Collections.emptyList();
		}
		Map<String, VersionedLink> serviceLinksMap = new HashMap<String, VersionedLink>();
		for (InstanceInfo ii : instanceList) {
			List<Link> links = null;
			try {
				links = ii.getLinksWithRelPrefix(rel);
				if (links == null || links.isEmpty()) {
					LOGGER.warn("Found no links for InstanceInfo for service {}", ii.getServiceName());
					continue;
				}
				LOGGER.debug("Retrieved {} links for service {}. Links list: {}", links == null ? 0 : links.size(),
						ii.getServiceName(), StringUtil.arrayToCommaDelimitedString(links.toArray()));
				for (Link link : links) {
					if (link.getHref().startsWith("http://")) {
						serviceLinksMap.put(ii.getServiceName(), new VersionedLink(links.get(0), getAuthorizationToken(ii)));
					}
				}
			}
			catch (Exception e) {
				LOGGER.error("Error to get links!", e);
			}
		}
		if (serviceLinksMap.isEmpty()) {
			LOGGER.warn("Found no internal widget notification links for rel {}", rel);
			return Collections.emptyList();
		}
		else {
			LOGGER.info("Widget notification links: {}", serviceLinksMap);
			return new ArrayList<VersionedLink>(serviceLinksMap.values());
		}
	}

	public static VersionedLink getServiceExternalLink(String serviceName, String version, String rel, String tenantName)
	{
		Link link = RegistryLookupUtil.getServiceExternalLink(serviceName, version, rel, false, tenantName);
		String authToken = RegistryLookupUtil.getAuthorizationToken(serviceName, version);
		return new VersionedLink(link, authToken);
	}

	public static VersionedLink getServiceInternalHttpLink(String serviceName, String version, String rel, String tenantName)
	{
		Link link = RegistryLookupUtil.getServiceInternalLink(serviceName, version, rel, false, tenantName, true);
		String authToken = RegistryLookupUtil.getAuthorizationToken(serviceName, version);
		if(link == null) return null;
		return new VersionedLink(link, authToken);
	}

	public static VersionedLink getServiceInternalLink(String serviceName, String version, String rel, String tenantName)
	{
		Link link = RegistryLookupUtil.getServiceInternalLink(serviceName, version, rel, false, tenantName, false);
		String authToken = RegistryLookupUtil.getAuthorizationToken(serviceName, version);
		return new VersionedLink(link, authToken);
	}

    public static VersionedLink getServiceInternalLinkHttp(String serviceName, String version, String rel, String tenantName)
    {
		Link link = RegistryLookupUtil.getServiceInternalLink(serviceName, version, rel, false, tenantName, true);
		String authToken = RegistryLookupUtil.getAuthorizationToken(serviceName, version);
		return new VersionedLink(link, authToken);
    }

	public static Link replaceWithVanityUrl(Link lk, String tenantName, String serviceName)
	{
		if (lk == null || StringUtil.isEmpty(serviceName)) {
			return lk;
		}
		Map<String, String> vanityBaseUrls = RegistryLookupUtil.getVanityBaseURLs(tenantName);
		if (vanityBaseUrls != null && vanityBaseUrls.containsKey(serviceName)) {
			lk = RegistryLookupUtil.replaceVanityUrlDomainForLink(vanityBaseUrls.get(serviceName), lk, tenantName);
			LOGGER.debug("Completed to (try to) replace URL with vanity URL. Updated url is {}", lk.getHref());
		}
		return lk;
	}
	
	private static String getAuthorizationToken(InstanceInfo instanceInfo) {
		char[] authToken = LookupManager.getInstance().getAuthorizationAccessToken(instanceInfo);
		return new String(authToken);
	}
	
	private static String getAuthorizationToken(String serviceName, String version) {
		InstanceInfo instanceInfo = RegistryLookupUtil.getInstanceInfo(serviceName, version);
		return getAuthorizationToken(instanceInfo);
	}
	
	private static InstanceInfo getInstanceInfo (String serviceName, String version) {
		InstanceInfo.Builder builder = InstanceInfo.Builder.newBuilder().withServiceName(serviceName);
		if (!StringUtil.isEmpty(version)) {
			builder = builder.withVersion(version);
		}
		return builder.build();
	}

	private static List<Link> getLinksWithProtocol(String protocol, List<Link> links)
	{
		if (protocol == null || links == null || protocol.isEmpty() || links.isEmpty()) {
			if (links == null) {
				return new ArrayList<Link>();
			}
			return links;
		}
		List<Link> protocoledLinks = new ArrayList<Link>();
		for (Link link : links) {
			try {
				URI uri = URI.create(link.getHref());
				if (protocol.equalsIgnoreCase(uri.getScheme())) {
					protocoledLinks.add(link);
				}
			}
			catch (Throwable thr) {
				LOGGER.error(thr.getLocalizedMessage(), thr);
				return protocoledLinks;
			}
		}

		return protocoledLinks;
	}

	private static List<Link> getLinksWithRelPrefixWithProtocol(String protocol, String relPrefix, List<Link> links)
	{
		if (protocol == null || relPrefix == null || links == null || protocol.isEmpty() || links.isEmpty()) {
			if (links == null) {
				return new ArrayList<Link>();
			}
			return links;
		}
		List<Link> protocoledLinks = new ArrayList<Link>();
		for (Link link : links) {
			try {
				LOGGER.debug("Checks link on protocol {} with expected rel prefix {} against retrieved link (rel={}, href={})",
						protocol, relPrefix, link.getRel(), link.getHref());
				URI uri = URI.create(link.getHref());
				if (protocol.equalsIgnoreCase(uri.getScheme()) && link.getRel() != null && link.getRel().indexOf(relPrefix) == 0) {
					protocoledLinks.add(link);
				}
			}
			catch (Throwable thr) {
				LOGGER.error(thr.getLocalizedMessage(), thr);
				return protocoledLinks;
			}
		}

		return protocoledLinks;
	}

	private static Link getServiceExternalLink(String serviceName, String version, String rel, boolean prefixMatch,
			String tenantName)
	{
		LOGGER.debug(
				"/getServiceExternalLink/ Trying to retrieve service external link for service: \"{}\", version: \"{}\", rel: \"{}\", tenant: \"{}\"",
				serviceName, version, rel, tenantName);

		InstanceInfo info = RegistryLookupUtil.getInstanceInfo(serviceName, version);
		LogUtil.setInteractionLogThreadContext(tenantName, "Retristry lookup client", LogUtil.InteractionLogDirection.OUT);
		Link lk = null;
		try {
			List<InstanceInfo> result = null;

			if (!StringUtil.isEmpty(tenantName)) {
				InstanceInfo ins = LookupManager.getInstance().getLookupClient().getInstanceForTenant(info, tenantName);
				LOGGERITR.debug("Retrieved INSTANCE {} by using getInstanceForTenant for tenant {}", ins, tenantName);
				if (ins == null) {
					LOGGER.warn(
							"retrieved null INSTANCE info with getInstanceForTenant. Details: serviceName={}, version={}, tenantName={}",
							serviceName, version, tenantName);
					result = LookupManager.getInstance().getLookupClient().lookup(new InstanceQuery(info));
					LOGGERITR.debug("Retrieved InstanceInfo list {} by using LookupClient.lookup for InstanceInfo {}", result,
							info);
				}
				else {
					result = new ArrayList<InstanceInfo>();
					result.add(ins);
				}

			}
			else {
				result = LookupManager.getInstance().getLookupClient().lookup(new InstanceQuery(info));
				LOGGERITR.debug("Retrieved InstanceInfo list {} by using LookupClient.lookup for InstanceInfo {}", result, info);
			}
			if (result != null && !result.isEmpty()) {

				//find https link first
				for (InstanceInfo internalInstance : result) {
					List<Link> links = null;
					if (prefixMatch) {
						links = internalInstance.getLinksWithRelPrefixWithProtocol(rel, "https");
					}
					else {
						links = internalInstance.getLinksWithProtocol(rel, "https");
					}

					try {
						SanitizedInstanceInfo sanitizedInstance = null;
						if (!StringUtil.isEmpty(tenantName)) {
							sanitizedInstance = LookupManager.getInstance().getLookupClient()
									.getSanitizedInstanceInfo(internalInstance, tenantName);
							LOGGERITR.debug("Retrieved sanitizedInstance {} by using getSanitizedInstanceInfo for tenant {}",
									sanitizedInstance, tenantName);
						}
						else {
							LOGGER.debug("Failed to retrieve tenant when getting external link. Using tenant non-specific APIs to get sanitized INSTANCE");
							sanitizedInstance = LookupManager.getInstance().getLookupClient()
									.getSanitizedInstanceInfo(internalInstance);
							LOGGERITR.debug("Retrieved sanitizedInstance {} by using getSanitizedInstanceInfo without tenant id",
									sanitizedInstance);
						}
						if (sanitizedInstance != null) {
							if (prefixMatch) {
								links = RegistryLookupUtil.getLinksWithRelPrefixWithProtocol("https", rel,
										sanitizedInstance.getLinks());
							}
							else {
								links = RegistryLookupUtil.getLinksWithProtocol("https", sanitizedInstance.getLinks(rel));
							}
						}
					}
					catch (Exception e) {
						LOGGER.error(e.getLocalizedMessage(), e);
					}
					if (links != null && !links.isEmpty()) {
						lk = links.get(0);
						break;
					}
				}

				if (lk != null) {
					LOGGER.debug(
							"[branch 1] Retrieved link: \"{}\" for service: \"{}\", version: \"{}\", rel: \"{}\", tenant: \"{}\"",
							lk.getHref(), serviceName, version, rel, tenantName);
					return lk;
				}

				//https link is not found, then find http link
				for (InstanceInfo internalInstance : result) {
					List<Link> links = null;
					if (prefixMatch) {
						links = internalInstance.getLinksWithRelPrefixWithProtocol(rel, "http");
					}
					else {
						links = internalInstance.getLinksWithProtocol(rel, "http");
					}
					try {
						SanitizedInstanceInfo sanitizedInstance = null;
						if (!StringUtil.isEmpty(tenantName)) {
							sanitizedInstance = LookupManager.getInstance().getLookupClient()
									.getSanitizedInstanceInfo(internalInstance, tenantName);
							LOGGERITR.debug("Retrieved sanitizedInstance {} by using getSanitizedInstanceInfo for tenant {}",
									sanitizedInstance, tenantName);
						}
						else {
							LOGGER.debug("Failed to retrieve tenant when getting external link. Using tenant non-specific APIs to get sanitized INSTANCE");
							sanitizedInstance = LookupManager.getInstance().getLookupClient()
									.getSanitizedInstanceInfo(internalInstance);
							LOGGERITR.debug("Retrieved sanitizedInstance {} by using getSanitizedInstanceInfo without tenant id",
									sanitizedInstance);
						}
						if (sanitizedInstance != null) {
							if (prefixMatch) {
								links = RegistryLookupUtil.getLinksWithRelPrefixWithProtocol("http", rel,
										sanitizedInstance.getLinks());
							}
							else {
								links = RegistryLookupUtil.getLinksWithProtocol("http", sanitizedInstance.getLinks(rel));
							}

						}
					}
					catch (Exception e) {
						LOGGER.error(e.getLocalizedMessage(), e);
					}
					if (links != null && !links.isEmpty()) {
						lk = links.get(0);
						LOGGER.debug(
								"[branch 2] Retrieved link: \"{}\" for service: \"{}\", version: \"{}\", rel: \"{}\", tenant: \"{}\"",
								lk == null ? null : lk.getHref(), serviceName, version, rel, tenantName);
						return lk;
					}
				}
			}
			LOGGER.debug("[branch 3] Retrieved link: \"{}\" for service: \"{}\", version: \"{}\", rel: \"{}\", tenant: \"{}\"",
					lk == null ? null : lk.getHref(), serviceName, version, rel, tenantName);
			return lk;
		}
		catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			return lk;
		}
	}

	private static Link getServiceInternalLink(String serviceName, String version, String rel, boolean prefixMatch,
			String tenantName, boolean httpOnly)
	{
		LOGGER.debug(
				"/getServiceInternalLink/ Trying to retrieve service internal link for service: \"{}\", version: \"{}\", rel: \"{}\", prefixMatch: \"{}\", tenant: \"{}\"",
				serviceName, version, rel, prefixMatch, tenantName);

		LogUtil.setInteractionLogThreadContext(tenantName, "Retristry lookup client", LogUtil.InteractionLogDirection.OUT);
		InstanceInfo info = RegistryLookupUtil.getInstanceInfo(serviceName, version);
		Link lk = null;
		try {
			List<InstanceInfo> result = LookupManager.getInstance().getLookupClient().lookup(new InstanceQuery(info));
			if (result != null && !result.isEmpty()) {

				//find https link first
				if (!httpOnly) {
					for (InstanceInfo internalInstance : result) {
						List<Link> links = null;
						if (prefixMatch) {
							links = internalInstance.getLinksWithRelPrefixWithProtocol(rel, "https");
						}
						else {
							links = internalInstance.getLinksWithProtocol(rel, "https");
						}

						if (links != null && !links.isEmpty()) {
							lk = links.get(0);
							break;
						}
					}
				}

				if (lk != null) {
					return lk;
				}
				//https link is not found, then find http link
				for (InstanceInfo internalInstance : result) {
					List<Link> links = null;
					if (prefixMatch) {
						links = internalInstance.getLinksWithRelPrefixWithProtocol(rel, "http");
					}
					else {
						links = internalInstance.getLinksWithProtocol(rel, "http");
					}
					if (links != null && !links.isEmpty()) {
						lk = links.get(0);
						return lk;
					}
				}
			}
		}
		catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return lk;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getVanityBaseURLs(String tenantName)
	{
		LOGGER.debug("/getVanityBaseURLs/ Trying to retrieve service internal link for tenant: \"{}\"", tenantName);
		Map<String, String> map = null;
		ICacheManager cm = CacheManagers.getInstance().build();
		Tenant cacheTenant = new Tenant(tenantName);
		Object cacheKey = DefaultKeyGenerator.getInstance().generate(cacheTenant,new Keys(CacheConstants.LOOKUP_CACHE_KEY_VANITY_BASE_URL));
		try {
			map = (Map<String,String>) cm.getCache(CacheConstants.CACHES_VANITY_BASE_URL_CACHE).get(cacheKey);
			if (map != null) {
				return map;
			}
		}
		catch (Exception e) {
			LOGGER.error(e);
		}
		InstanceInfo info = RegistryLookupUtil.getInstanceInfo("OHS", null);
		Link lk = null;
		map = new HashMap<String, String>();
		try {
			List<InstanceInfo> result = LookupManager.getInstance().getLookupClient().lookup(new InstanceQuery(info));
			if (result != null && !result.isEmpty()) {
				for (InstanceInfo internalInstance : result) {
					if (map.containsKey(APM_SERVICE) && map.containsKey(ITA_SERVICE) && map.containsKey(LA_SERVICE)
							&& map.containsKey(MONITORING_SERVICE) && map.containsKey(SECURITY_ANALYTICS_SERVICE)
							&& map.containsKey(COMPLIANCE_SERVICE) && map.containsKey(ORCHESTRATION_SERVICE)) {
						break;
					}
					if (!map.containsKey(APM_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/apm", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && !links.isEmpty()) {
							lk = links.get(0);
							LOGGER.debug("Retrieved base vanity URL for apm: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							LOGGER.debug("Tenant id is inserted into the base vanity URL for apm. The URL is {}", url);
							map.put(APM_SERVICE, url);
						}
					}
					if (!map.containsKey(ITA_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/ita", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && !links.isEmpty()) {
							lk = links.get(0);
							LOGGER.debug("Retrieved base vanity URL for ita: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							LOGGER.debug("Tenant id is inserted into the base vanity URL for ita. The URL is {}", url);
							map.put(ITA_SERVICE, url);
							// ta/ita has the same URL pattern
							map.put(TA_SERVICE, url);
						}
					}
					if (!map.containsKey(LA_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/la", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && !links.isEmpty()) {
							lk = links.get(0);
							LOGGER.debug("Retrieved base vanity URL for la: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							LOGGER.debug("Tenant id is inserted into the base vanity URL for la. The URL is {}", url);
							map.put(LA_SERVICE, url);
						}
					}
					if (!map.containsKey(MONITORING_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/monitoring", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && !links.isEmpty()) {
							lk = links.get(0);
							LOGGER.debug("Retrieved base vanity URL for monitoring service: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							LOGGER.debug("Tenant id is inserted into the base vanity URL for monitoring service. The URL is {}",
									url);
							map.put(MONITORING_SERVICE, url);
						}
					}
					if (!map.containsKey(SECURITY_ANALYTICS_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/security", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && !links.isEmpty()) {
							lk = links.get(0);
							LOGGER.debug("Retrieved base vanity URL for Security Analytics service: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							LOGGER.debug(
									"Tenant id is inserted into the base vanity URL for Security Analytics service. The URL is {}",
									url);
							map.put(SECURITY_ANALYTICS_SERVICE, url);
						}
					}
					if (!map.containsKey(COMPLIANCE_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/compliance", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && !links.isEmpty()) {
							lk = links.get(0);
							LOGGER.debug("Retrieved base vanity URL for Compliance service: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							LOGGER.debug("Tenant id is inserted into the base vanity URL for Compliance service. The URL is {}",
									url);
							map.put(COMPLIANCE_SERVICE, url);
						}
					}
					if (!map.containsKey(ORCHESTRATION_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/ocs", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && !links.isEmpty()) {
							lk = links.get(0);
							LOGGER.debug("Retrieved base vanity URL for Orchestration service: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							LOGGER.debug(
									"Tenant id is inserted into the base vanity URL for Orchestration service. The URL is {}",
									url);
							map.put(ORCHESTRATION_SERVICE, url);
						}
					}
				}
			}
		}
		catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}

		if (LOGGER.isDebugEnabled() && !map.isEmpty()) {
			LOGGER.debug("Printing out vanity URLs map:");
			for (String service : map.keySet()) {
				String url = map.get(service);
				LOGGER.debug("service name is {}, and url is {}", service, url);
			}
		}
		cm.getCache(CacheConstants.CACHES_VANITY_BASE_URL_CACHE).put(cacheKey, map);
		return map;
	}

	private static String insertTenantIntoVanityBaseUrl(String tenantName, String vanityBaseUrl)
	{
		LOGGER.debug("/insertTenantIntoVanityBaseUrl/ Trying to insert tenant \"{}\" to base vanity url \"{}\"", tenantName,
				vanityBaseUrl);
		if (StringUtil.isEmpty(tenantName) || StringUtil.isEmpty(vanityBaseUrl)) {
			return vanityBaseUrl;
		}

		if (vanityBaseUrl.indexOf("://") != -1) {
			String[] splittedProtocol = vanityBaseUrl.split("://");
			StringBuilder sb = new StringBuilder();
			sb.append(splittedProtocol[0]);
			sb.append("://");
			sb.append(tenantName);
			sb.append(".");
			for (int i = 1; i < splittedProtocol.length; i++) {
				sb.append(splittedProtocol[i]);
				if (i != splittedProtocol.length - 1) {
					sb.append("://");
				}
			}
			LOGGER.debug("/insertTenantIntoVanityBaseUrl/ URL \"{}\" is updated to \"{}\"", vanityBaseUrl, sb.toString());
			return sb.toString();
		}
		return vanityBaseUrl;
	}

	private static Link replaceVanityUrlDomainForLink(String domainPort, Link lk, String tenantName)
	{
		LOGGER.debug("/replaceDomainForLink/ Trying to replace link url \"{}\" with domain \"{}\"", lk != null ? lk.getHref()
				: null, domainPort);
		if (StringUtil.isEmpty(domainPort) || lk == null || StringUtil.isEmpty(lk.getHref())) {
			return lk;
		}
		String replacedHref = RegistryLookupUtil.replaceVanityUrlDomainForUrl(domainPort, lk.getHref(), tenantName);
		LOGGER.debug("/replaceDomainForLink/ Link \"{}\" URL (after replaced) is \"{}\"", lk.getHref(), replacedHref);
		lk.withHref(replacedHref);
		return lk;
	}

	private static String replaceVanityUrlDomainForUrl(String vanityBaseUrl, String targetUrl, String tenantName)
	{
		if (StringUtil.isEmpty(vanityBaseUrl) || StringUtil.isEmpty(targetUrl) || targetUrl.indexOf("://") == -1) {
			return targetUrl;
		}
		// replace URLs started with tenant only
		String[] splittedProtocol = targetUrl.split("://");
		if (splittedProtocol == null || splittedProtocol.length < 2) {
			LOGGER.warn("Specified url \"{}\" is invalid, can't splitted into multiple parts by '://'", targetUrl);
			return targetUrl;
		}
		if (splittedProtocol[1] == null || !splittedProtocol[1].startsWith(tenantName)) {
			LOGGER.debug(
					"Do not need to replace the url with vanity URL, because the URL \"{}\" doesn't start with opc tenant id",
					targetUrl);
			return targetUrl;
		}
		LOGGER.info("Replacing with vanity base URL for target url. Vanity url is {}, url is {}", vanityBaseUrl, targetUrl);
		String domainToReplace = vanityBaseUrl;
		if (domainToReplace.indexOf("://") != -1) {
			String[] splittedDomain = domainToReplace.split("://");
			if (splittedDomain != null && splittedDomain.length > 1) {
				domainToReplace = splittedDomain[1];
			}
		}
		LOGGER.info("Replacing with vanity base url for url. Vanity url w/o protocol is {}", vanityBaseUrl);
		StringBuilder sb = new StringBuilder();

		sb.append(splittedProtocol[0]);
		sb.append("://");
		sb.append(domainToReplace);

		if (splittedProtocol[1].indexOf("/") != -1) {
			String[] splitted = splittedProtocol[1].split("/");
			if (splitted.length > 1) {
				for (int i = 1; i < splitted.length; i++) {
					sb.append("/");
					sb.append(splitted[i]);
				}
			}
		}
		LOGGER.info("After replacing with vanity url, the target url is: \"{}\"", sb.toString());
		return sb.toString();
	}

	private RegistryLookupUtil()
	{
	}
}
