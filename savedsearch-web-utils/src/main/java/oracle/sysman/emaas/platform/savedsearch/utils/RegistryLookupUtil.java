/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.utils;

import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil.InteractionLogContext;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author guobaochen
 */
public class RegistryLookupUtil
{
	private static final Logger logger = LogManager.getLogger(RegistryLookupUtil.class);
	private static final Logger itrLogger = LogUtil.getInteractionLogger();

	public static Link getServiceInternalLink(String serviceName, String version, String rel, String tenantName)
	{
		logger.debug(
				"/getServiceInternalLink/ Trying to retrieve service internal link for service: \"{}\", version: \"{}\", rel: \"{}\", tenant: \"{}\"",
				serviceName, version, rel, tenantName);
		InstanceInfo info = InstanceInfo.Builder.newBuilder().withServiceName(serviceName).withVersion(version).build();
		InteractionLogContext ilc = LogUtil.setInteractionLogThreadContext(tenantName, "ServiceManager:" + serviceName + "/"
				+ version, LogUtil.InteractionLogDirection.OUT);
		itrLogger.debug("Retrieved instance {}", info);
		Link lk = null;
		try {
			List<InstanceInfo> result = LookupManager.getInstance().getLookupClient().lookup(new InstanceQuery(info));
			if (result != null && result.size() > 0) {

				//find https link first
				for (InstanceInfo internalInstance : result) {
					List<Link> links = internalInstance.getLinksWithProtocol(rel, "https");
					if (links != null && links.size() > 0) {
						lk = links.get(0);
						break;
					}
				}

				if (lk != null) {
					return lk;
				}

				//https link is not found, then find http link
				for (InstanceInfo internalInstance : result) {
					List<Link> links = internalInstance.getLinksWithProtocol(rel, "http");
					if (links != null && links.size() > 0) {
						lk = links.get(0);
						return lk;
					}
				}
			}
			itrLogger.debug("Retrieved link {} for service \"{}\", version \"{}\", rel \"{}\"", lk, serviceName, version, rel);
			return lk;
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return lk;
		}
		finally {
			LogUtil.setInteractionLogThreadContext(ilc.getTenantId(), ilc.getServiceInvoked(), ilc.getDirection() == null ? null
					: LogUtil.InteractionLogDirection.valueOf(ilc.getDirection()));
		}
	}
}
