/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify;

import java.io.IOException;
import java.util.*;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.ZDTContext;
import oracle.sysman.emaas.platform.emcpdf.registry.RegistryLookupUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author guochen
 */
public class WidgetDeletionNotification implements IWidgetNotification
{
	private static final Logger LOGGER = LogManager.getLogger(WidgetDeletionNotification.class);
	// please note that widget update share the same rel, but will call different REST APIs with different http method
	private static final String WIDGET_DELETION_SERVICE_REL = WidgetChangeNotification.WIDGET_CHANGE_SERVICE_REL;

	@Override
	public void notify(Search search, Date deletionTime)
	{
		if (search == null) {
			return;
		}
		notify(new WidgetNotifyEntity(search, deletionTime, WidgetNotificationType.DELETE));
	}

	@Override
	public void notify(WidgetNotifyEntity wne)
	{
		if (wne == null) {
			LOGGER.info("Didn't notify of widget deletion for null widget notify entity object");
			return;
		}
		LOGGER.info("Notify to end points with rel={} of widget deletion. Widget unique ID={}, widget name={}",
				WIDGET_DELETION_SERVICE_REL, wne.getUniqueId(), wne.getName());
		long start = System.currentTimeMillis();
		List<RegistryLookupUtil.VersionedLink> links = null;
		try {
			links = RegistryLookupUtil.getAllServicesInternalLinksByRel(WIDGET_DELETION_SERVICE_REL);
		}
		catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		if (links == null || links.isEmpty()) {
			LOGGER.info("Didn't notify of widget deletion for finding no link for rel={}", WIDGET_DELETION_SERVICE_REL);
			return;
		}
		RestClient rc = new RestClient();
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("X-USER-IDENTITY-DOMAIN-NAME", TenantContext.getContext().gettenantName());
		UUID reqId = ZDTContext.getRequestId();
		if (reqId != null) {
			headers.put("X-ORCL-OMC-APIGW-REQGUID", reqId);
		}
		Long reqTime = ZDTContext.getRequestTime();
		if (reqTime != null) {
			headers.put("X-ORCL-OMC-APIGW-REQTIME", reqTime);
		}
		LOGGER.info("Notify widget deletion, ZDT request ID is {}, ZDT request time is {}", reqId, reqTime);
		for (RegistryLookupUtil.VersionedLink link : links) {
			long innerStart = System.currentTimeMillis();
			WidgetNotifyEntity rtn = (WidgetNotifyEntity) rc.post(link.getHref(), headers, wne,
					TenantContext.getContext().gettenantName(), link.getAuthToken());
			long innerEnd = System.currentTimeMillis();
			if (rtn != null) {
				LOGGER.info(
						"Notification of widget deletion to link {} affacted {} objects (might always be 1 for eclipse 2.4). It takes {} ms",
						link.getHref(), rtn.getAffactedObjects(), innerEnd - innerStart);
			}
		}
		LOGGER.info("Completed notify of widget deletion for widget unique ID={} and widget name={}, totally it takes {} ms",
				wne.getUniqueId(), wne.getName(), System.currentTimeMillis() - start);
	}
}
