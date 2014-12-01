package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.model.restfulClient.discover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;
import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.logging.UpdateSavedSearchLog;

import org.apache.log4j.Logger;

/**
 * Class to discover a RESTful service from service manager
 */
public class ServiceDiscoverer
{
	private static Logger _logger = UpdateSavedSearchLog.getLogger(ServiceDiscoverer.class);
	private static final int MAX_TOTAL_RETRY = 8;
	private static final int MAX_RETRY_ON_SAME = 4;

	private final IServiceDefinition property;

	private final LookupManager lm;
	private InstanceInfo usedInstance;
	private int retries;
	private int retriesOnSame;

	private final Set<String> failedSericeUrl;
	private final List<String> availableServiceUrl;

	public ServiceDiscoverer(IServiceDefinition property)
	{
		super();
		this.property = property;
		failedSericeUrl = new HashSet<String>();
		availableServiceUrl = new ArrayList<String>();
		lm = LookupManager.getInstance();
		try {

			Properties lookupProps = property.getSMProps();
			if (lookupProps.getProperty("cacheUpdateThreshold") == null) {
				lookupProps.setProperty("cacheUpdateThreshold", "10");
			}
			if (lookupProps.getProperty("numberOfRetries") == null) {
				lookupProps.setProperty("numberOfRetries", "1");
			}
			lm.initComponent(lookupProps);
		}
		catch (Exception e) {
			_logger.error("ServiceDiscoverer" + e);
		}
	}

	/**
	 * Discover the available Saved Search RESTful service from service registry
	 * 
	 * @return discovered service URL
	 * @throws Exception
	 */
	public void discoverService() throws Exception
	{
		internalDiscoverService(null);
	}

	/**
	 * Discover the next service instance after failure. This method retries for given total times and for maximium times on same
	 * service instance
	 * 
	 * @throws Exception
	 */
	public void discoverServiceAfterFailure() throws Exception
	{
		if (availableServiceUrl != null && !availableServiceUrl.isEmpty()) {
			String url = availableServiceUrl.remove(0);
			failedSericeUrl.add(url);
			if (!availableServiceUrl.isEmpty()) {
				return;
			}
		}
		internalDiscoverService(usedInstance);
	}

	/**
	 * Retrieves the 1st available service URL, or null if there is no service URL available
	 * 
	 * @return
	 */
	public String getServiceUrl()
	{
		if (availableServiceUrl == null || availableServiceUrl.isEmpty()) {
			return null;
		}
		return availableServiceUrl.get(0);
	}

	private InstanceInfo getServiceInstanceInfo(InstanceInfo used) throws java.lang.Exception
	{
		if (property == null || property.getGetName() == null) {
			throw new Exception("Failure: invalid service property is specified");
		}
		if (lm == null) {
			throw new Exception("Failure: Lookup Manager not initialized");
		}
		InstanceInfo.Builder builder = InstanceInfo.Builder.newBuilder().withServiceName(property.getGetName());
		if (property.getVersion() != null) {
			builder.withVersion(property.getVersion());
		}
		if (property.getCharacteristics() != null) {
			Map<String, String> characs = property.getCharacteristics();
			for (String key : characs.keySet()) {
				builder.addCharacteristic(key, characs.get(key));
			}
		}
		InstanceInfo queryInfo = builder.build();
		LookupClient lc = lm.getLookupClient();
		InstanceInfo instance = lc.getInstance(queryInfo, used);
		return instance;
	}

	/**
	 * Discover the available Saved Search RESTful service from service registry
	 * 
	 * @return discovered service URL
	 * @throws Exception
	 */
	private void internalDiscoverService(InstanceInfo used) throws Exception
	{

		final int INIT_WAIT_TIME = 80;//ms
		int waitTime = INIT_WAIT_TIME;
		while (retries++ <= MAX_TOTAL_RETRY && retriesOnSame < MAX_RETRY_ON_SAME) {
			InstanceInfo instance = getServiceInstanceInfo(used);
			if (instance == null) {
				Thread.sleep(waitTime);
				continue;
			}
			if (instance == usedInstance) {
				retriesOnSame++;
			}
			else {
				retriesOnSame = 0;
			}
			usedInstance = instance;
			List<String> canonicalEndpoints = instance.getCanonicalEndpoints();
			List<String> virtualEndpoints = instance.getVirtualEndpoints();
			if (canonicalEndpoints != null && canonicalEndpoints.size() > 0) {
				for (String serviceUrl : canonicalEndpoints) {
					if (!failedSericeUrl.contains(serviceUrl) && !availableServiceUrl.contains(serviceUrl)) {
						availableServiceUrl.add(serviceUrl);
					}
				}
			}
			if (virtualEndpoints != null && virtualEndpoints.size() > 0) {
				for (String serviceUrl : virtualEndpoints) {
					if (!failedSericeUrl.contains(serviceUrl) && !availableServiceUrl.contains(serviceUrl)) {
						availableServiceUrl.add(serviceUrl);
					}
				}
			}
			if (!availableServiceUrl.isEmpty()) {
				break;
			}
			if (waitTime < 640) {
				waitTime *= 2;
			}
			else {
				waitTime = INIT_WAIT_TIME;
			}

		}

	}
}
