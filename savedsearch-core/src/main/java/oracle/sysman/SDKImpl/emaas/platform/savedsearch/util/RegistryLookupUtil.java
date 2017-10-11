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

	private RegistryLookupUtil()
	{
	}
}
