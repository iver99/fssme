package oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.ssflifecycle;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RestClient;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.WidgetNotifyEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.ssflifecycle.SSFLifeCycleNotifyEntity.SSFNotificationType;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guochen on 3/16/17.
 */
public class SSFLifeCycleNotification {
	private static final Logger LOGGER = LogManager.getLogger(SSFLifeCycleNotification.class);
	public static final String SSF_LIFECYCLE_REL = "ssf.lifecycle.notify";

	public void notify(SSFNotificationType type) {
		if (type == null) {
			LOGGER.info("Didn't notify of ssf lifecycle for null ssf notify type");
			return;
		}
		LOGGER.info("Starts to notify an SSF lifecycle event. The entity type is {}", type);
		long start = System.currentTimeMillis();
		List<Link> links = null;
		try {
			links = RegistryLookupUtil.getAllServicesInternalLinksByRel(SSF_LIFECYCLE_REL);
		}
		catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		if (links == null || links.isEmpty()) {
			LOGGER.info("Didn't notify of ssf lifecycle for finding no link for rel={}", SSF_LIFECYCLE_REL);
			return;
		}
		RestClient rc = new RestClient();
		SSFLifeCycleNotifyEntity lcne = new SSFLifeCycleNotifyEntity(type);
		for (Link link : links) {
			long innerStart = System.currentTimeMillis();
			// TODO: will be replaced by the RestClient impl from dashboards-sdk once it could be used by SSF
			rc.post(link.getHref(), lcne, "CloudServices");
			long innerEnd = System.currentTimeMillis();
			LOGGER.info(
						"Notification of SSF lifecycle notification to link {} . It takes {} ms",
						link.getHref(), innerEnd - innerStart);
		}
		LOGGER.info("Completed notify of SSF lifecycle, totally it takes {} ms", System.currentTimeMillis() - start);
	}
}
