package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.model.restfulClient.discover;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.logging.UpdateSavedSearchLog;

import org.apache.log4j.Logger;

public class SavedSearchServiceDefFactory
{

	//private static final String SERVICE_MANAGER_FILE_PATH = "/opt/ORCLemaas/Applications/SavedSearchService/init/servicemanager.properties";
	public static final String SAVEDSEARCH_SERVICE_NAME = "savedSearch.serviceName";
	public static final String SAVEDSEARCH_SERVICE_VERSION = "savedSearch.version";
	public static final String SERVICE_NAME = "serviceName";//needed by SM
	public static final String SERVICE_VERSION = "version"; //needed by SM   
	public static final String REGISTRY_URLS = "registryUrls";
	public static final String SERVICE_URLS = "serviceUrls";
	public static final String SERVICE_NAME_SAVEDSEARCH = "SavedSearch";
	public static final String SERVICE_VERSION_SAVEDSEARCH = "1.0+";
	private static Logger LOGGER = UpdateSavedSearchLog.getLogger(SavedSearchServiceDefFactory.class);

	/*   public static IServiceDefinition getSavedSearchServiceDefV1(){
	       InputStream is =
	           null; //SavedSearchServiceDefFactory.class.getClassLoader().getResourceAsStream("META-INF/saved_search_service_v1.properties");
	      try {
	           File f = new File(SERVICE_MANAGER_FILE_PATH);
	           Properties prop=new Properties();
	          if (!f.exists()){
	              String isUnitTesting = System.getProperty("IsUnitTesting", "false");
	              if ("false".equals(isUnitTesting)){
	                  throw new Exception("File not found: "+SERVICE_MANAGER_FILE_PATH);
	              }
	              /**
	               * Below is normally used by unit testing when SERVICE_MANAGER_FILE_PATH is not avail on the machine
	               
	              String serviceName = System.getProperty(SAVEDSEARCH_SERVICE_NAME,null);
	              if (serviceName==null){
	                  throw new Exception("service name is NULL");
	              }               
	              String serviceVersion= System.getProperty(SAVEDSEARCH_SERVICE_VERSION,null);
	              if (serviceVersion==null){
	                  throw new Exception("service version is NULL");
	              }                  
	              String registryUrls = System.getProperty(REGISTRY_URLS,null);
	              if (registryUrls==null){
	                  throw new Exception("registryUrls is NULL");
	              }                  
	              String serviceUrls = System.getProperty(SERVICE_URLS,null);
	              if (serviceUrls==null){
	                  throw new Exception("serviceUrls is NULL");
	              }                  

	              prop.put(SERVICE_NAME, serviceName);
	              prop.put(SERVICE_VERSION, serviceVersion);
	              prop.put(REGISTRY_URLS, registryUrls);
	              prop.put(SERVICE_URLS, serviceUrls);
	              
	          }else{
	              is =
	                  new FileInputStream(f); //SavedSearchServiceDefFactory.class.getClassLoader().getResourceAsStream("META-INF/saved_search_service_v1.properties");
	              prop.load(is);  
	              prop.setProperty(SERVICE_NAME, prop.getProperty(SAVEDSEARCH_SERVICE_NAME,SERVICE_NAME_SAVEDSEARCH ));
	              prop.setProperty(SERVICE_VERSION, prop.getProperty(SAVEDSEARCH_SERVICE_VERSION,SERVICE_VERSION_SAVEDSEARCH ));               
	          }
	           return getSavedSearchServiceDefV1(prop);
	       }catch(Exception e){
	           throw new RuntimeException("Failed to load saved search service definition!",e);
	       }finally{
	           if (is!=null){
	               try {
	                   is.close();
	               } catch (IOException e) {
	               	LOGGER.error("getSavedSearchServiceDefV1", e);
	                   	
	                   
	               }
	           }
	       }
	   }*/

	/**
	 * Returns the service definition for saved search framework RESTful API v1 NOTE: INTERNAL USE ONLY!!
	 * 
	 * @return
	 */
	public static IServiceDefinition getSavedSearchServiceDefV1(Properties prop)
	{
		if (prop == null) {
			throw new RuntimeException("Failed to load saved search service definition: NULL properties");
		}
		try {
			final String serviceName = prop.getProperty(SERVICE_NAME);
			final String serviceVersion = prop.getProperty(SERVICE_VERSION);
			final Properties ps = prop;
			IServiceDefinition sd = new IServiceDefinition() {
				@Override
				public Map<String, String> getCharacteristics()
				{
					return Collections.emptyMap();
				}

				@Override
				public String getGetName()
				{
					return serviceName;
				}

				@Override
				public Properties getSMProps()
				{
					return ps;
				}

				@Override
				public String getVersion()
				{
					return serviceVersion;
				}
			};
			return sd;
		}
		catch (Exception e) {
			LOGGER.error("getSavedSearchServiceDefV1", e);
			return null;
		}
	}
}
