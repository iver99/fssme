package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emaas.platform.savedsearch.utils.RegistryLookupUtil;
import oracle.sysman.emaas.platform.savedsearch.utils.RestRequestUtil;

import org.codehaus.jettison.json.JSONObject;

public class OdsDataServiceImpl implements OdsDataService {
	private static final OdsDataServiceImpl instance = new OdsDataServiceImpl();
	private static final Map<String, String> cache = new ConcurrentHashMap<String, String>();
	
	public static OdsDataServiceImpl getInstance() {
		return instance;
	}
	
	
	@Override
	public String createOdsEntity(String searchId, String searchName, String tenantName) throws EMAnalyticsFwkException {
		// get entity type
		String meClass = getMeClassFromCache();
		if (meClass == null) {
			try {
				String entityType = createOdsEntityType(generateOdsEntityTypeJson(), tenantName);
				JSONObject json = new JSONObject(entityType);
				meClass = json.getString(ENTITY_CLASS);
			} catch(Exception e) {
				throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
			}
			putMeClassToCache(meClass);
		}
		
		// get ODS entity
		String odsEntity = generateOdsEntityJson(searchId, searchName, meClass);
		
		// send to ODS
		String baseUrl = retriveEndpoint(tenantName, REL_DATA_RESOURCE, DATA_MES);
		String meId = null;
		try {
			String result = RestRequestUtil.restPost(baseUrl, tenantName, odsEntity);
			JSONObject jsonObj = new JSONObject(result);
			meId = jsonObj.getString(ENTITY_ID);
		} catch (Exception e) {
			throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
		}
		
		return meId;
	}
	
	@Override
	public void deleteOdsEntity(long searchId, String tenantName) throws EMAnalyticsFwkException {
		// get ODS entity id from search parameters
		SearchManager sman = SearchManager.getInstance();
		String meid = sman.getSearchParamByName(searchId, ENTITY_ID);
		
		// send the meid to ODS for deleting
		StringBuffer baseUrl = new StringBuffer();
		baseUrl.append(retriveEndpoint(tenantName, REL_DATA_RESOURCE, DATA_MES)).append(HTTP_DELIMITER).append(meid);
		try {
			RestRequestUtil.restDelete(baseUrl.toString(), tenantName);
		} catch (Exception e) {
			throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
		}
	}
	
	/**
	 * create ODS entity type if the type doesn't exist
	 */
	@Override
	public String createOdsEntityType(String entityType, String tenantName) throws EMAnalyticsFwkException {
		String baseUrl = retriveEndpoint(tenantName, REL_METADATA_RESOURCE, METADATA_METYPES);
		String result = null;
		try {
			result = RestRequestUtil.restGet(baseUrl + HTTP_DELIMITER + ENTITY_TYPE_NAME, tenantName);
		} catch(EMAnalyticsFwkException emExc) {
			try {
				result = RestRequestUtil.restPost(baseUrl, tenantName, entityType);
			}catch (Exception e) {
				throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
			}
		}catch(Exception e) {
			throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param tenantName
	 * @param rel
	 * @param resource
	 * @return http://host:port/targetmodel/api/v1/rel/resource
	 */
	private String retriveEndpoint(String tenantName, String rel, String resource) {
		Link link = RegistryLookupUtil.getServiceInternalLink(SERVICE_NAME, VERSION, rel, tenantName);
		StringBuffer mesUrl = new StringBuffer();
		mesUrl.append(link.getHref()).append(HTTP_DELIMITER).append(resource);
		return mesUrl.toString();
	}
	
	private String getMeClassFromCache() {
		return cache.get(ENTITY_CLASS);
	}
	
	private void putMeClassToCache(String meClass) {
		cache.put(ENTITY_CLASS, meClass);
	}
	
	static private String generateOdsEntityJson(String searchId, String searchName,String meClass) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"entityType\" : \"").append(ENTITY_TYPE_NAME).append("\",");
		sb.append("\"entityName\" : \"").append(searchId).append("\",");
		sb.append("\"displayName\" : \"").append(searchName).append("\",");
		sb.append("\"timezoneRegion\" : \"").append(getTimeZone()).append("\",");
		sb.append("\"meClass\" : \"").append(meClass).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	static private String getTimeZone() {
		return "UTC";
	}

	static private String generateOdsEntityTypeJson() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"entityType\":\"").append(ENTITY_TYPE_NAME).append("\",");
		sb.append("\"facts\":[");
		sb.append("{");
		sb.append("\"factType\":\"").append(FACT_TYPE_NAME).append("\",");
		sb.append("\"displayName\":\"").append(FACT_DISPLAY_NAME).append("\",");
		sb.append("\"fields\":[");
		sb.append("{");
		sb.append("\"name\":\"ruleRef\",");
		sb.append("\"isKey\":true,");
		sb.append("\"displayName\":\"Rule Reference\",");
		sb.append("\"type\":\"STR\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"column1\",");
		sb.append("\"isKey\":true,");
		sb.append("\"displayName\":\"Column 1\",");
		sb.append("\"type\":\"STR\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"column2\",");
		sb.append("\"isKey\":true,");
		sb.append("\"displayName\":\"Column 2\",");
		sb.append("\"type\":\"STR\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"column3\",");
		sb.append("\"isKey\":true,");
		sb.append("\"displayName\":\"Column 3\",");
		sb.append("\"type\":\"STR\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"count\",");
		sb.append("\"isKey\":false,");
		sb.append("\"displayName\":\"Result Count\",");
		sb.append("\"type\":\"NUM\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"sourceURL\",");
		sb.append("\"isKey\":false,");
		sb.append("\"displayName\":\"Event Source URL\",");
		sb.append("\"type\":\"STR\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"status\",");
		sb.append("\"isKey\":false,");
		sb.append("\"displayName\":\"Status\",");
		sb.append("\"type\":\"STR\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"statusMessage\",");
		sb.append("\"isKey\":false,");
		sb.append("\"displayName\":\"Status Message\",");
		sb.append("\"type\":\"STR\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"execTime\",");
		sb.append("\"isKey\":false,");
		sb.append("\"displayName\":\"Execution Time\",");
		sb.append("\"type\":\"STR\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"evalTimeWindow\",");
		sb.append("\"isKey\":false,");
		sb.append("\"displayName\":\"Evaluation Time Window\",");
		sb.append("\"type\":\"STR\"");
		sb.append("}");
		sb.append("]");
		sb.append("}");
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}
}
