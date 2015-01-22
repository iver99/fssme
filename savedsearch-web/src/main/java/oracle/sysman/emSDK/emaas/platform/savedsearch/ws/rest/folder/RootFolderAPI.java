/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.folder;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkJsonException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author vinjoshi
 */

@Path("rootfolder")
public class RootFolderAPI
{

	@Context
	private UriInfo uri;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createFolder(JSONObject folderObj)
	{
		Folder objFld = null;
		String msg = "";
		JSONObject jsonObj;
		int statusCode = 201;
		try {
			objFld = getFolderFromJsonForCreate(folderObj);
			FolderManager mgrFolder = FolderManager.getInstance();
			Folder objFld1 = FolderManagerImpl.getInstance().getRootFolder();
			if (objFld1 == null) {
				objFld = mgrFolder.saveFolder(objFld);
			}
			else {
				objFld = objFld1;
			}
			jsonObj = EntityJsonUtil.getFullFolderJsonObj(uri.getBaseUri(), objFld);
			msg = jsonObj.toString();
		}
		catch (EMAnalyticsFwkException e) {
			msg = e.getMessage();
			statusCode = e.getStatusCode();
		}
		catch (EMAnalyticsWSException e) {
			msg = e.getMessage();
			statusCode = e.getStatusCode();
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			statusCode = 404;
			msg = e.getMessage();
		}
		catch (EMAnalyticsFwkJsonException e) {
			msg = e.getMessage();
			statusCode = e.getStatusCode();
		}
		return Response.status(statusCode).entity(msg).build();
	}

	private Folder getFolderFromJsonForCreate(JSONObject folderObj) throws EMAnalyticsWSException
	{
		Folder objFld = FolderManager.getInstance().createNewFolder();
		try {
			String name = folderObj.getString("name");
			if (name != null && name.trim().equals("")) {
				throw new EMAnalyticsWSException("The name key for rootfolder can not be empty in the input JSON Object",
						EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING);
			}

			objFld.setName(name);
		}
		catch (JSONException e) {
			throw new EMAnalyticsWSException("The name key for rootfolder is missing in the input JSON Object",
					EMAnalyticsWSException.JSON_FOLDER_NAME_MISSING, e);
		}
		// nullables !!
		objFld.setDescription(folderObj.optString("description", objFld.getDescription()));
		objFld.setUiHidden(folderObj.optBoolean("uiHidden", objFld.isUiHidden()));
		objFld.setParentId(-99);
		return objFld;
	}

}
