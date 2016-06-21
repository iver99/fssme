package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.targetcard;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.validationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 6/21/2016.
 */
@Path("targetcardlinks")
public class TargetCardLinksFilterAPI {
    private static final Logger _logger = LogManager.getLogger(TargetCardLinksFilterAPI.class);
    @Context
    UriInfo uri;

    @DELETE
    @Path("{id: [0-9]*}")
    public Response deleteTargetCard(@PathParam("id") long searchId)
    {
        LogUtil.getInteractionLogger().info("Service calling to (DELETE) /savedsearch/v1/targetcardlinks/{}", searchId);
        int statusCode = 204;
        String message = null;
        SearchManager sman = SearchManager.getInstance();
        try {
            sman.deleteTargetCard(searchId, false);
            message = "Delete TargetCard with id: " + searchId +" Successfully!";
        } catch(EMAnalyticsFwkException e) {
            return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
        }
        return Response.status(statusCode).entity(message).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerTargertCard(JSONObject inputJsonObj){
        LogUtil.getInteractionLogger().info("Service calling to (POST) /savedsearch/v1/targetcardlinks");
        String message = "";
        int statusCode = 201;
        JSONObject jsonObj;
        SearchManager searchManager =  SearchManager.getInstance();
        try{
            Search targetObject = createSearchObjectForAdd(inputJsonObj);
            Search savedTarget = searchManager.saveTargetCard(targetObject);
            jsonObj = EntityJsonUtil.getTargetCardJsonObj(uri.getBaseUri(),savedTarget);
            message = jsonObj.toString();
        }
        catch (EMAnalyticsFwkException e) {
            message = e.getMessage();
            statusCode = e.getStatusCode();
            _logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
        }
        catch (EMAnalyticsWSException e) {
            message = e.getMessage();
            statusCode = e.getStatusCode();
            _logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "") + message, e);
        }
        return Response.status(statusCode).entity(message).build();
    }

    private Search createSearchObjectForAdd(JSONObject json) throws EMAnalyticsWSException
    {
        //copy from the SearchAPI xidai 4/13/2016
        Search searchObj = new SearchImpl();
        // Data population !
        try {
            String name = json.getString("name");

            //			if (name.trim() == null) {
            //				throw new EMAnalyticsWSException("The name key for search can not be null in the input JSON Object",
            //						EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
            //			}
            if (name != null && name.trim().equals("")) {
                throw new EMAnalyticsWSException("The name key for search can not be empty in the input JSON Object",
                        EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING);
            }

            if (StringUtil.isSpecialCharFound(name)) {
                throw new EMAnalyticsWSException(
                        "The search name contains at least one invalid character ('<' or '>'), please correct search name and retry",
                        EMAnalyticsWSException.JSON_INVALID_CHAR);
            }

            try {
                validationUtil.validateLength("name", name, 64);
            }
            catch (EMAnalyticsWSException e) {
                throw e;
            }

            searchObj.setName(name);
        }
        catch (JSONException je) {
            throw new EMAnalyticsWSException("The name key for search is missing in the input JSON Object",
                    EMAnalyticsWSException.JSON_SEARCH_NAME_MISSING, je);
        }
        String desc = "";
        try {
            desc = json.getString("description");
            if (StringUtil.isSpecialCharFound(desc)) {
                throw new EMAnalyticsWSException(
                        "The search description contains at least one invalid character ('<' or '>'), please correct search description and retry",
                        EMAnalyticsWSException.JSON_INVALID_CHAR);
            }

        }
        catch (JSONException je) {
            //ignore the description if not provided by user
        }

        try {
            validationUtil.validateLength("description", desc, 256);
        }
        catch (EMAnalyticsWSException e) {
            throw e;
        }

        try {
            JSONObject jsonObj = json.getJSONObject("category");
            searchObj.setCategoryId(Integer.parseInt(jsonObj.getString("id")));
        }
        catch (JSONException je) {
            throw new EMAnalyticsWSException("The category key for search is missing in the input JSON Object",
                    EMAnalyticsWSException.JSON_SEARCH_CATEGORY_ID_MISSING, je);
        }
        try {
            JSONObject jsonFold = json.getJSONObject("folder");
            searchObj.setFolderId(Integer.parseInt(jsonFold.getString("id")));
        }
        catch (JSONException je) {
            throw new EMAnalyticsWSException("The folder key for search is missing in the input JSON Object",
                    EMAnalyticsWSException.JSON_SEARCH_FOLDER_ID_MISSING, je);
        }

        // Nullable properties !
        searchObj.setMetadata(json.optString("metadata", searchObj.getMetadata()));
        searchObj.setQueryStr(json.optString("queryStr", searchObj.getQueryStr()));
        searchObj.setDescription(json.optString("description", searchObj.getDescription()));
        // non-nullable with db defaults !!
        searchObj.setLocked(Boolean.parseBoolean(json.optString("locked", Boolean.toString(searchObj.isLocked()))));
        searchObj.setUiHidden(Boolean.parseBoolean(json.optString("uiHidden", Boolean.toString(searchObj.isUiHidden()))));
        searchObj.setIsWidget(Boolean.parseBoolean(json.optString("isWidget", Boolean.toString(searchObj.getIsWidget()))));

        // Parameters
        if (json.has("parameters")) {
            JSONArray jsonArr = json.optJSONArray("parameters");
            List<SearchParameter> searchParamList = new ArrayList<SearchParameter>();
            // FIltering values with Map .. not exactly required .. duplicate
            // params if any will be discarded at persistence layer !!
            String type;
            for (int i = 0; i < jsonArr.length(); i++) {
                SearchParameter searchParam = new SearchParameter();
                JSONObject jsonParam = jsonArr.optJSONObject(i);
                if (jsonParam.has("attributes")) {
                    searchParam.setAttributes(jsonParam.optString("attributes"));
                }
                try {
                    String name = jsonParam.getString("name");
                    if (name != null && name.trim().equals("")) {
                        throw new EMAnalyticsWSException(
                                "The name key for search param can not be empty in the input JSON Object",
                                EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING);
                    }

                    searchParam.setName(name);
                }
                catch (JSONException je) {
                    throw new EMAnalyticsWSException("The name key for search parameter is missing in the input JSON Object",
                            EMAnalyticsWSException.JSON_SEARCH_PARAM_NAME_MISSING, je);
                }
                try {
                    type = jsonParam.getString("type");
                }
                catch (JSONException je) {
                    throw new EMAnalyticsWSException("The type key for search param is missing in the input JSON Object",
                            EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_MISSING, je);
                }
                if (type.equals("STRING") | type.equals("CLOB")) {
                    searchParam.setType(ParameterType.valueOf(type));
                }
                else {
                    throw new EMAnalyticsWSException("Invalid param type, please specify either STRING or CLOB",
                            EMAnalyticsWSException.JSON_SEARCH_PARAM_TYPE_INVALID);
                }

                searchParam.setValue(jsonParam.optString("value"));
                searchParamList.add(searchParam);

            }
            searchObj.setParameters(searchParamList);

        }
        else {
            searchObj.setParameters(null);
        }
        return searchObj;
    }

}