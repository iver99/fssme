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

import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author aduan
 */
public class RegistryLookupUtil
{
	private static final Logger LOGGER = LogManager.getLogger(RegistryLookupUtil.class);

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

	private RegistryLookupUtil()
	{
	}
}
