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
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CachedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Keys;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.CacheManager;
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

}
