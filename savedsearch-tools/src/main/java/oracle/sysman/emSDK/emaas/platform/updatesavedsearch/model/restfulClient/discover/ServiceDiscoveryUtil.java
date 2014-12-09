package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.model.restfulClient.discover;

import java.util.Properties;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.UpdateUtilConstants;

public class ServiceDiscoveryUtil
{

	public static String getSsfUrlBySmUrl(String url, String version) throws Exception
	{
		Properties props = new Properties();
		props.setProperty(UpdateUtilConstants.SERVICE_URLS, url);
		props.setProperty(UpdateUtilConstants.SERVICE_NAME, UpdateUtilConstants.SAVEDSEARCH);
		props.setProperty(UpdateUtilConstants.SERVICE_VERSION, version);
		if (props.getProperty(UpdateUtilConstants.CACHE_THRESHOLD) == null) {
			props.setProperty(UpdateUtilConstants.CACHE_THRESHOLD, UpdateUtilConstants.CACHE_THRESHOLD_VALUE);
		}
		if (props.getProperty(UpdateUtilConstants.NUM_RETRIES) == null) {
			props.setProperty(UpdateUtilConstants.NUM_RETRIES, UpdateUtilConstants.RETRIES_COUNT);
		}
		IServiceDefinition properties = SavedSearchServiceDefFactory.getSavedSearchServiceDefV1(props);
		ServiceDiscoverer sd = new ServiceDiscoverer(properties);
		sd.discoverService();
		return sd.getServiceUrl();
	}

}
