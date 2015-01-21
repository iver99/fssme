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

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.UpgradeManager;

/**
 * @author vinjoshi
 */

@Path("admin/tenantonboard")
public class TenantOnBoard
{

	@Context
	private UriInfo uri;

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response upgradeData()
	{
		int statusCode = 201;
		String msg = "";

		try {
			UpgradeManager objupgrade = UpgradeManager.getInstance();
			objupgrade.upgradeData();

		}
		catch (EMAnalyticsFwkException e) {
			msg = e.getMessage();
			statusCode = e.getStatusCode();
		}
		return Response.status(statusCode).entity(msg).build();
	}

}
