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

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CachedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Keys;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.SanitizedInstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author aduan
 */
public class RegistryLookupUtil
{
	private static final Logger logger = LogManager.getLogger(RegistryLookupUtil.class);
	private static final Logger itrLogger = LogUtil.getInteractionLogger();

	public static final String APM_SERVICE = "ApmUI";
	public static final String ITA_SERVICE = "emcitas-ui-apps";
	public static final String LA_SERVICE = "LoganService";
	public static final String TA_SERVICE = "TargetAnalytics";
	public static final String MONITORING_SERVICE = "MonitoringServiceUI";
	public static Link getServiceExternalLink(String serviceName, String version, String rel, String tenantName)
	{
		return RegistryLookupUtil.getServiceExternalLink(serviceName, version, rel, false, tenantName);
	}

	public static Link getServiceInternalLink(String serviceName, String version, String rel, String tenantName)
	{
		return RegistryLookupUtil.getServiceInternalLink(serviceName, version, rel, false, tenantName, false);
	}
	
	public static Link getServiceInternalHttpLink(String serviceName, String version, String rel, String tenantName)
	{
		return RegistryLookupUtil.getServiceInternalLink(serviceName, version, rel, false, tenantName, true);
	}

	private static List<Link> getLinksWithProtocol(String protocol, List<Link> links)
	{
		if (protocol == null || links == null || protocol.length() == 0 || links.size() == 0) {
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
				logger.error(thr.getLocalizedMessage(), thr);
				return protocoledLinks;
			}
		}

		return protocoledLinks;
	}

	private static List<Link> getLinksWithRelPrefixWithProtocol(String protocol, String relPrefix, List<Link> links)
	{
		if (protocol == null || relPrefix == null || links == null || protocol.length() == 0 || links.size() == 0) {
			if (links == null) {
				return new ArrayList<Link>();
			}
			return links;
		}
		List<Link> protocoledLinks = new ArrayList<Link>();
		for (Link link : links) {
			try {
				logger.debug("Checks link on protocol {} with expected rel prefix {} against retrieved link (rel={}, href={})",
						protocol, relPrefix, link.getRel(), link.getHref());
				URI uri = URI.create(link.getHref());
				if (protocol.equalsIgnoreCase(uri.getScheme()) && link.getRel() != null
						&& link.getRel().indexOf(relPrefix) == 0) {
					protocoledLinks.add(link);
				}
			}
			catch (Throwable thr) {
				logger.error(thr.getLocalizedMessage(), thr);
				return protocoledLinks;
			}
		}

		return protocoledLinks;
	}

	private static Link getServiceExternalLink(String serviceName, String version, String rel, boolean prefixMatch,
			String tenantName)
	{
		logger.debug(
				"/getServiceExternalLink/ Trying to retrieve service external link for service: \"{}\", version: \"{}\", rel: \"{}\", tenant: \"{}\"",
				serviceName, version, rel, tenantName);
		Tenant cacheTenant = new Tenant(tenantName);
		try {
			logger.debug("Try to retrieve from cache");
			CachedLink cl = (CachedLink) CacheManager.getInstance().getCacheable(cacheTenant, CacheManager.CACHES_LOOKUP_CACHE,
					new Keys(CacheManager.LOOKUP_CACHE_KEY_EXTERNAL_LINK, serviceName, version, rel, prefixMatch));
			if (cl != null) {
				logger.debug(
						"Retrieved exteral link {} from cache, serviceName={}, version={}, rel={}, prefixMatch={}, tenantName={}",
						cl.getHref(), serviceName, version, rel, prefixMatch, tenantName);
				return cl.getLink();
			}
			logger.debug("Fail not retrieve from cache,try to load data from persistence layer");
		}
		catch (Exception e) {
			logger.error("Error to retrieve external link from cache. Try to lookup the link", e);
		}
		
		InstanceInfo.Builder builder = InstanceInfo.Builder.newBuilder().withServiceName(serviceName);
		if (!StringUtil.isEmpty(version)) {
			builder = builder.withVersion(version);
		}
		InstanceInfo info = builder.build();
		LogUtil.setInteractionLogThreadContext(tenantName, "Retristry lookup client", LogUtil.InteractionLogDirection.OUT);
		Link lk = null;
		try {
			List<InstanceInfo> result = null;

			if (!StringUtil.isEmpty(tenantName)) {
				InstanceInfo ins = LookupManager.getInstance().getLookupClient().getInstanceForTenant(info, tenantName);
				itrLogger.debug("Retrieved instance {} by using getInstanceForTenant for tenant {}", ins, tenantName);
				if (ins == null) {
					logger.warn(
							"retrieved null instance info with getInstanceForTenant. Details: serviceName={}, version={}, tenantName={}",
							serviceName, version, tenantName);
					result = LookupManager.getInstance().getLookupClient().lookup(new InstanceQuery(info));
					itrLogger.debug("Retrieved InstanceInfo list {} by using LookupClient.lookup for InstanceInfo {}", result,
							info);
				}
				else {
					result = new ArrayList<InstanceInfo>();
					result.add(ins);
				}

			}
			else {
				result = LookupManager.getInstance().getLookupClient().lookup(new InstanceQuery(info));
				itrLogger.debug("Retrieved InstanceInfo list {} by using LookupClient.lookup for InstanceInfo {}", result, info);
			}
			if (result != null && result.size() > 0) {

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
							itrLogger.debug("Retrieved sanitizedInstance {} by using getSanitizedInstanceInfo for tenant {}",
									sanitizedInstance, tenantName);
						}
						else {
							logger.debug(
									"Failed to retrieve tenant when getting external link. Using tenant non-specific APIs to get sanitized instance");
							sanitizedInstance = LookupManager.getInstance().getLookupClient()
									.getSanitizedInstanceInfo(internalInstance);
							itrLogger.debug("Retrieved sanitizedInstance {} by using getSanitizedInstanceInfo without tenant id",
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
						logger.error(e.getLocalizedMessage(), e);
					}
					if (links != null && links.size() > 0) {
						lk = links.get(0);
						CacheManager.getInstance().putCacheable(cacheTenant, CacheManager.CACHES_LOOKUP_CACHE,
								new Keys(CacheManager.LOOKUP_CACHE_KEY_EXTERNAL_LINK, serviceName, version, rel, prefixMatch),
								new CachedLink(lk));
						break;
					}
				}

				if (lk != null) {
					logger.debug(
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
							itrLogger.debug("Retrieved sanitizedInstance {} by using getSanitizedInstanceInfo for tenant {}",
									sanitizedInstance, tenantName);
						}
						else {
							logger.debug(
									"Failed to retrieve tenant when getting external link. Using tenant non-specific APIs to get sanitized instance");
							sanitizedInstance = LookupManager.getInstance().getLookupClient()
									.getSanitizedInstanceInfo(internalInstance);
							itrLogger.debug("Retrieved sanitizedInstance {} by using getSanitizedInstanceInfo without tenant id",
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
						logger.error(e.getLocalizedMessage(), e);
					}
					if (links != null && links.size() > 0) {
						lk = links.get(0);
						CacheManager.getInstance().putCacheable(cacheTenant, CacheManager.CACHES_LOOKUP_CACHE,
								new Keys(CacheManager.LOOKUP_CACHE_KEY_EXTERNAL_LINK, serviceName, version, rel, prefixMatch),
								new CachedLink(lk));
						logger.debug(
								"[branch 2] Retrieved link: \"{}\" for service: \"{}\", version: \"{}\", rel: \"{}\", tenant: \"{}\"",
								lk == null ? null : lk.getHref(), serviceName, version, rel, tenantName);
						return lk;
					}
				}
			}
			logger.debug("[branch 3] Retrieved link: \"{}\" for service: \"{}\", version: \"{}\", rel: \"{}\", tenant: \"{}\"",
					lk == null ? null : lk.getHref(), serviceName, version, rel, tenantName);
			return lk;
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return lk;
		}
	}

	private static Link getServiceInternalLink(String serviceName, String version, String rel, boolean prefixMatch,
			String tenantName, boolean httpOnly)
	{
		logger.debug(
				"/getServiceInternalLink/ Trying to retrieve service internal link for service: \"{}\", version: \"{}\", rel: \"{}\", prefixMatch: \"{}\", tenant: \"{}\"",
				serviceName, version, rel, prefixMatch, tenantName);

		Tenant cacheTenant = new Tenant(tenantName);
		try {
			CachedLink cl = (CachedLink) CacheManager.getInstance().getCacheable(cacheTenant, CacheManager.CACHES_LOOKUP_CACHE,
					new Keys(CacheManager.LOOKUP_CACHE_KEY_INTERNAL_LINK, serviceName, version, rel, prefixMatch));
			if (cl != null) {
				logger.debug(
						"Retrieved internal link {} from cache, serviceName={}, version={}, rel={}, prefixMatch={}, tenantName={}",
						cl.getHref(), serviceName, version, rel, prefixMatch, tenantName);
				return cl.getLink();
			}
		}
		catch (Exception e) {
			logger.error("Error to retrieve internal link from cache. Try to lookup the link", e);
		}
		LogUtil.setInteractionLogThreadContext(tenantName, "Retristry lookup client", LogUtil.InteractionLogDirection.OUT);
		InstanceInfo info = InstanceInfo.Builder.newBuilder().withServiceName(serviceName).withVersion(version).build();
		Link lk = null;
		try {
			List<InstanceInfo> result = LookupManager.getInstance().getLookupClient().lookup(new InstanceQuery(info));
			if (result != null && result.size() > 0) {

				//find https link first
				if(!httpOnly) {
					for (InstanceInfo internalInstance : result) {
						List<Link> links = null;
						if (prefixMatch) {
							links = internalInstance.getLinksWithRelPrefixWithProtocol(rel, "https");
						}
						else {
							links = internalInstance.getLinksWithProtocol(rel, "https");
						}

						if (links != null && links.size() > 0) {
							lk = links.get(0);
							CacheManager.getInstance().putCacheable(cacheTenant, CacheManager.CACHES_LOOKUP_CACHE,
									new Keys(CacheManager.LOOKUP_CACHE_KEY_INTERNAL_LINK, serviceName, version, rel, prefixMatch), new CachedLink(lk));
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
					if (links != null && links.size() > 0) {
						lk = links.get(0);
						CacheManager.getInstance().putCacheable(cacheTenant, CacheManager.CACHES_LOOKUP_CACHE,
								new Keys(CacheManager.LOOKUP_CACHE_KEY_INTERNAL_LINK, serviceName, version, rel, prefixMatch), new CachedLink(lk));
						return lk;
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return lk;
	}

	public static Link replaceWithVanityUrl(Link lk, String tenantName, String serviceName)
	{
		if (lk == null || StringUtil.isEmpty(serviceName)) {
			return lk;
		}
		Map<String, String> vanityBaseUrls = RegistryLookupUtil.getVanityBaseURLs(tenantName);
		if (vanityBaseUrls != null && vanityBaseUrls.containsKey(serviceName)) {
			lk = RegistryLookupUtil.replaceVanityUrlDomainForLink(vanityBaseUrls.get(serviceName), lk, tenantName);
			logger.debug("Completed to (try to) replace URL with vanity URL. Updated url is {}", lk.getHref());
		}
		return lk;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getVanityBaseURLs(String tenantName)
	{
		logger.debug("/getVanityBaseURLs/ Trying to retrieve service internal link for tenant: \"{}\"", tenantName);
		Tenant cacheTenant = new Tenant(tenantName);
		Map<String, String> map = null;
		try {
			map = (Map<String, String>) CacheManager.getInstance().getCacheable(cacheTenant, CacheManager.CACHES_LOOKUP_CACHE,
					CacheManager.LOOKUP_CACHE_KEY_VANITY_BASE_URL);
			if (map != null) {
				return map;
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
		InstanceInfo info = InstanceInfo.Builder.newBuilder().withServiceName("OHS").build();
		Link lk = null;
		map = new HashMap<String, String>();
		try {
			List<InstanceInfo> result = LookupManager.getInstance().getLookupClient().lookup(new InstanceQuery(info));
			if (result != null && result.size() > 0) {
				for (InstanceInfo internalInstance : result) {
					if (map.containsKey(APM_SERVICE) && map.containsKey(ITA_SERVICE) && map.containsKey(LA_SERVICE)
							&& map.containsKey(MONITORING_SERVICE)) {
						break;
					}
					if (!map.containsKey(APM_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/apm", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && links.size() > 0) {
							lk = links.get(0);
							logger.debug("Retrieved base vanity URL for apm: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							logger.debug("Tenant id is inserted into the base vanity URL for apm. The URL is {}", url);
							map.put(APM_SERVICE, url);
						}
					}
					if (!map.containsKey(ITA_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/ita", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && links.size() > 0) {
							lk = links.get(0);
							logger.debug("Retrieved base vanity URL for ita: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							logger.debug("Tenant id is inserted into the base vanity URL for ita. The URL is {}", url);
							map.put(ITA_SERVICE, url);
							// ta/ita has the same URL pattern
							map.put(TA_SERVICE, url);
						}
					}
					if (!map.containsKey(LA_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/la", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && links.size() > 0) {
							lk = links.get(0);
							logger.debug("Retrieved base vanity URL for la: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							logger.debug("Tenant id is inserted into the base vanity URL for la. The URL is {}", url);
							map.put(LA_SERVICE, url);
						}
					}
					if (!map.containsKey(MONITORING_SERVICE)) {
						List<Link> links = internalInstance.getLinksWithProtocol("vanity/monitoring", "https");
						links = RegistryLookupUtil.getLinksWithProtocol("https", links);

						if (links != null && links.size() > 0) {
							lk = links.get(0);
							logger.debug("Retrieved base vanity URL for monitoring service: {} ", lk.getHref());
							String url = RegistryLookupUtil.insertTenantIntoVanityBaseUrl(tenantName, lk.getHref());
							logger.debug("Tenant id is inserted into the base vanity URL for monitoring service. The URL is {}",
									url);
							map.put(MONITORING_SERVICE, url);
						}
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}

		if (logger.isDebugEnabled() && !map.isEmpty()) {
			logger.debug("Printing out vanity URLs map:");
			for (String service : map.keySet()) {
				String url = map.get(service);
				logger.debug("service name is {}, and url is {}", service, url);
			}
		}
		CacheManager.getInstance().putCacheable(cacheTenant, CacheManager.CACHES_LOOKUP_CACHE,
				CacheManager.LOOKUP_CACHE_KEY_VANITY_BASE_URL, map);
		return map;
	}

	private static Link replaceVanityUrlDomainForLink(String domainPort, Link lk, String tenantName)
	{
		logger.debug("/replaceDomainForLink/ Trying to replace link url \"{}\" with domain \"{}\"", lk != null ? lk.getHref()
				: null, domainPort);
		if (StringUtil.isEmpty(domainPort) || lk == null || StringUtil.isEmpty(lk.getHref())) {
			return lk;
		}
		String replacedHref = RegistryLookupUtil.replaceVanityUrlDomainForUrl(domainPort, lk.getHref(), tenantName);
		logger.debug("/replaceDomainForLink/ Link \"{}\" URL (after replaced) is \"{}\"", lk.getHref(), replacedHref);
		lk.withHref(replacedHref);
		return lk;
	}

	private static String insertTenantIntoVanityBaseUrl(String tenantName, String vanityBaseUrl)
	{
		logger.debug("/insertTenantIntoVanityBaseUrl/ Trying to insert tenant \"{}\" to base vanity url \"{}\"", tenantName,
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
			logger.debug("/insertTenantIntoVanityBaseUrl/ URL \"{}\" is updated to \"{}\"", vanityBaseUrl, sb.toString());
			return sb.toString();
		}
		return vanityBaseUrl;
	}

	private static String replaceVanityUrlDomainForUrl(String vanityBaseUrl, String targetUrl, String tenantName)
	{
		if (StringUtil.isEmpty(vanityBaseUrl) || StringUtil.isEmpty(targetUrl) || targetUrl.indexOf("://") == -1) {
			return targetUrl;
		}
		// replace URLs started with tenant only
		String[] splittedProtocol = targetUrl.split("://");
		if (splittedProtocol == null || splittedProtocol.length < 2) {
			logger.warn("Specified url \"{}\" is invalid, can't splitted into multiple parts by '://'", targetUrl);
			return targetUrl;
		}
		if (splittedProtocol[1] == null || !splittedProtocol[1].startsWith(tenantName)) {
			logger.debug(
					"Do not need to replace the url with vanity URL, because the URL \"{}\" doesn't start with opc tenant id",
					targetUrl);
			return targetUrl;
		}
		logger.info("Replacing with vanity base URL for target url. Vanity url is {}, url is {}", vanityBaseUrl, targetUrl);
		String domainToReplace = vanityBaseUrl;
		if (domainToReplace.indexOf("://") != -1) {
			String[] splittedDomain = domainToReplace.split("://");
			if (splittedDomain != null && splittedDomain.length > 1) {
				domainToReplace = splittedDomain[1];
			}
		}
		logger.info("Replacing with vanity base url for url. Vanity url w/o protocol is {}", vanityBaseUrl);
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
		logger.info("After replacing with vanity url, the target url is: \"{}\"", sb.toString());
		return sb.toString();
	}
}
