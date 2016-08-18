package oracle.sysman.emaas.platform.savedsearch.targetmodel.services;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emaas.platform.savedsearch.utils.RestRequestUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

public class OdsDataServiceImpl implements OdsDataService
{

	private static final Logger LOGGER = LogManager.getLogger(OdsDataServiceImpl.class);
	private static final OdsDataServiceImpl INSTANCE = new OdsDataServiceImpl();

	public static OdsDataServiceImpl getInstance()
	{
		return INSTANCE;
	}

	static private String generateOdsEntityJson(String searchId, String searchName, String meClass)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"entityType\" : \"").append(ENTITY_TYPE_NAME).append("\",");
		sb.append("\"entityName\" : \"").append(searchId).append("\",");
		sb.append("\"displayName\" : \"").append(searchName).append("\",");
		sb.append("\"timezoneRegion\" : \"").append(OdsDataServiceImpl.getTimeZone()).append("\",");
		sb.append("\"meClass\" : \"").append(meClass).append("\"");
		sb.append("}");
		return sb.toString();
	}

	static private String getTimeZone()
	{
		return "UTC";
	}

	@Override
	public String createOdsEntity(String searchId, String searchName) throws EMAnalyticsFwkException
	{
		// get ODS entity
		String odsEntity = OdsDataServiceImpl.generateOdsEntityJson(searchId, searchName, ENTITY_CLASS);
		LOGGER.info("ODS Entity's string value is:" + odsEntity);
		// send to ODS
		String baseUrl = retriveEndpoint(REL_DATA_RESOURCE, DATA_MES);
		LOGGER.info("base URL is:" + baseUrl);
		String meId = null;
		// see if there is already an ODS entity created
		try {
			String result = RestRequestUtil.restGet(baseUrl + "?entityType=" + ENTITY_TYPE_NAME + "&entityName=" + searchId);
			LOGGER.info("result is" + result);
			JSONObject jsonObj = new JSONObject(result);
			if (jsonObj.getInt("count") > 0) {
				JSONObject entity = (JSONObject) jsonObj.getJSONArray("items").get(0);
				meId = entity.getString(ENTITY_ID);
			}
		}
		catch (Exception e) {
			// do nothing
		}
		// no existing ODS entity then create one
		if (meId == null || meId.isEmpty()) {
			try {
				String result = RestRequestUtil.restPost(baseUrl, odsEntity);
				LOGGER.info("RestRequestUtil.restPost(baseUrl, odsEntity) is executed,and result is " + result);
				JSONObject jsonObj = new JSONObject(result);
				meId = jsonObj.getString(ENTITY_ID);
			}
			catch (Exception e) {
				throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
			}
		}
		LOGGER.info("meId before return is " + meId);
		return meId;
	}

	/**
	 * create ODS entity type if the type doesn't exist
	 */
	@Override
	public String createOdsEntityType(String entityType) throws EMAnalyticsFwkException
	{
		String baseUrl = retriveEndpoint(REL_METADATA_RESOURCE, METADATA_METYPES);
		String result = null;
		try {
			result = RestRequestUtil.restGet(baseUrl + HTTP_DELIMITER + ENTITY_TYPE_NAME);
		}
		catch (EMAnalyticsFwkException emExc) {
			try {
				result = RestRequestUtil.restPost(baseUrl, entityType);
			}
			catch (Exception e) {
				throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
			}
		}
		catch (Exception e) {
			throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
		}

		return result;
	}

	@Override
	public void deleteOdsEntity(long searchId) throws EMAnalyticsFwkException
	{
		// get ODS entity id from search parameters
		SearchManager sman = SearchManager.getInstance();
		String meid = sman.getSearchParamByName(searchId, ENTITY_ID);

		// no ods entity need to be deleted
		if (meid == null) {
			return;
		}

		// send the meid to ODS for deleting
		StringBuilder baseUrl = new StringBuilder();
		baseUrl.append(retriveEndpoint(REL_DATA_RESOURCE, DATA_MES)).append(HTTP_DELIMITER).append(meid);
		try {
			RestRequestUtil.restDelete(baseUrl.toString());
		}
		catch (Exception e) {
			throw new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC, e);
		}
	}

	/**
	 * @param rel
	 * @param resource
	 * @return http://host:port/targetmodel/api/v1/rel/resource
	 */
	private String retriveEndpoint(String rel, String resource)
	{
		Link link = RegistryLookupUtil.getServiceInternalHttpLink(SERVICE_NAME, VERSION, rel, TenantContext.getContext()
				.gettenantName());
		StringBuilder mesUrl = new StringBuilder();
		mesUrl.append(link.getHref()).append(HTTP_DELIMITER).append(resource);
		return mesUrl.toString();
	}

}
