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

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;

/**
 * @author guochen
 */
public class WidgetChangeNotification
{
	private static final Logger LOGGER = LogManager.getLogger(WidgetChangeNotification.class);
	private static final String WIDGET_CHANGE_SERVICE_REL = "ssf.widget.changed";

	public List<Link> getInternalLinksByRel(String rel)
	{
		LOGGER.debug("/getInternalLinksByRel/ Trying to retrieve service internal link with rel: \"{}\"", rel);
		LookupClient lookUpClient = LookupManager.getInstance().getLookupClient();
		List<InstanceInfo> instanceList = lookUpClient.getInstancesWithLinkRelPrefix(rel, "http");
		if (instanceList == null) {
			LOGGER.warn("Found no instances with specified http rel {}", rel);
			return Collections.emptyList();
		}
		Map<String, Link> serviceLinksMap = new HashMap<String, Link>();
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
						serviceLinksMap.put(ii.getServiceName(), links.get(0));
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
			return new ArrayList<Link>(serviceLinksMap.values());
		}
	}

	public void notifyChange(Search search)
	{
		if (search == null) {
			return;
		}
		notifyChange(new WidgetNotifyEntity(search));
	}

	public void notifyChange(WidgetNotifyEntity wne)
	{
		if (wne == null) {
			LOGGER.info("Didn't notify of widget change for null widget notify entity object");
			return;
		}
		LOGGER.info("Notify to end points with rel={} of widget changes. Widget unique ID={}, widget name={}",
				WIDGET_CHANGE_SERVICE_REL, wne.getUniqueId(), wne.getName());
		long start = System.currentTimeMillis();
		List<Link> links = getInternalLinksByRel(WIDGET_CHANGE_SERVICE_REL);
		if (links == null || links.isEmpty()) {
			LOGGER.info("Didn't notify of widget change for finding no link for rel={}", WIDGET_CHANGE_SERVICE_REL);
			return;
		}
		RestClient rc = new RestClient();
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("X-USER-IDENTITY-DOMAIN-NAME", TenantContext.getContext().gettenantName());
		for (Link link : links) {
			long innerStart = System.currentTimeMillis();
			WidgetNotifyEntity rtn = (WidgetNotifyEntity) rc.post(link.getHref(), headers, wne,
					TenantContext.getContext().gettenantName());
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
