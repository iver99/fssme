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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author guochen
 */
public class WidgetChangeNotification implements IWidgetNotification
{
	private static final Logger LOGGER = LogManager.getLogger(WidgetChangeNotification.class);
	public static final String WIDGET_CHANGE_SERVICE_REL = "ssf.widget.changed";

	@Override
	public void notify(Search search, Date notifyTime)
	{
		if (search == null) {
			return;
		}
		notify(new WidgetNotifyEntity(search, notifyTime, WidgetNotificationType.UPDATE_NAME));
	}

	@Override
	public void notify(WidgetNotifyEntity wne)
	{
		if (wne == null) {
			LOGGER.info("Didn't notify of widget change for null widget notify entity object");
			return;
		}
		LOGGER.info("Notify to end points with rel={} of widget changes. Widget unique ID={}, widget name={}",
				WIDGET_CHANGE_SERVICE_REL, wne.getUniqueId(), wne.getName());
		long start = System.currentTimeMillis();
		List<VersionedLink> links = null;
		try {
			links = RegistryLookupUtil.getAllServicesInternalLinksByRel(WIDGET_CHANGE_SERVICE_REL);
		}
		catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		if (links == null || links.isEmpty()) {
			LOGGER.info("Didn't notify of widget change for finding no link for rel={}", WIDGET_CHANGE_SERVICE_REL);
			return;
		}
		RestClient rc = new RestClient();
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("X-USER-IDENTITY-DOMAIN-NAME", TenantContext.getContext().gettenantName());
		for (VersionedLink link : links) {
			long innerStart = System.currentTimeMillis();
			WidgetNotifyEntity rtn = (WidgetNotifyEntity) rc.post(link.getHref(), headers, wne,
					TenantContext.getContext().gettenantName(), link.getAuthToken());
			long innerEnd = System.currentTimeMillis();
			if (rtn != null) {
				LOGGER.info(
						"Notification of widget change to link {} affacted {} objects (might always be 1 for eclipse 2.4). It takes {} ms",
						link.getHref(), rtn.getAffactedObjects(), innerEnd - innerStart);
			}
		}
		LOGGER.info("Completed notify of widget change for widget unique ID={} and widget name={}, totally it takes {} ms",
				wne.getUniqueId(), wne.getName(), System.currentTimeMillis() - start);
	}
}
