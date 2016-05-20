package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emaas.platform.savedsearch.utils.RestRequestUtil;

import org.codehaus.jettison.json.JSONObject;

public class OdsDataServiceImpl implements OdsDataService {
	private static final OdsDataServiceImpl instance = new OdsDataServiceImpl();
	
	public static OdsDataServiceImpl getInstance() {
		return instance;
	}
	
	
	@Override
	public String createOdsEntity(String searchId, String searchName, String tenantName) throws EMAnalyticsFwkException {
		// get ODS entity
		String odsEntity = generateOdsEntityJson(searchId, searchName, ENTITY_CLASS);
		
		// send to ODS
		String baseUrl = retriveEndpoint(tenantName, REL_DATA_RESOURCE, DATA_MES);
		String meId = null;
		// see if there is already an ODS entity created
		try {
			String result = 
					RestRequestUtil.restGet(baseUrl+"?entityType=" + ENTITY_TYPE_NAME + "&entityName=" + searchId, tenantName);
			JSONObject jsonObj = new JSONObject(result);
			if (jsonObj.getInt("count") > 0) {
				JSONObject entity = (JSONObject) jsonObj.getJSONArray("items").get(0);
				meId = entity.getString(ENTITY_ID);
			}
		} catch (Exception e) {
			// do nothing
		}
		// no existing ODS entity then create one
		if (meId == null || meId.isEmpty()) {
			try {
				String result = RestRequestUtil.restPost(baseUrl, tenantName, odsEntity);
				JSONObject jsonObj = new JSONObject(result);
				meId = jsonObj.getString(ENTITY_ID);
			} catch (Exception e) {
				throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
			}
		}
		
		return meId;
	}
	
	@Override
	public void deleteOdsEntity(long searchId, String tenantName) throws EMAnalyticsFwkException {
		// get ODS entity id from search parameters
		SearchManager sman = SearchManager.getInstance();
		String meid = sman.getSearchParamByName(searchId, ENTITY_ID);
		
		// no ods entity need to be deleted
		if(meid == null)
			return;
		
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
		} catch(Exception e) {
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
		Link link = RegistryLookupUtil.getServiceInternalHttpLink(SERVICE_NAME, VERSION, rel, tenantName);
		StringBuffer mesUrl = new StringBuffer();
		mesUrl.append(link.getHref()).append(HTTP_DELIMITER).append(resource);
		return mesUrl.toString();
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

}
