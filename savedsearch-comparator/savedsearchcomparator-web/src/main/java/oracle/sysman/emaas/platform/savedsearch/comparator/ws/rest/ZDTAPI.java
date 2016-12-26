/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.CountsEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.SavedsearchCountsComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.SavedsearchRowsComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstanceData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstancesComparedData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.ZDTTableRowEntity;

@Path("/v1/comparator")
public class ZDTAPI
{
	public static class InstanceCounts
	{
		private String instanceName;
		private CountsEntity counts;

		public InstanceCounts(InstanceData<CountsEntity> data)
		{
			instanceName = data.getInstance().getKey();
			counts = data.getData();
		}

		/**
		 * @return the counts
		 */
		public CountsEntity getCounts()
		{
			return counts;
		}

		/**
		 * @return the instanceName
		 */
		public String getInstanceName()
		{
			return instanceName;
		}

		/**
		 * @param counts
		 *            the counts to set
		 */
		public void setCounts(CountsEntity counts)
		{
			this.counts = counts;
		}

		/**
		 * @param instanceName
		 *            the instanceName to set
		 */
		public void setInstanceName(String instanceName)
		{
			this.instanceName = instanceName;
		}
	}

	public static class InstancesComapredCounts
	{
		private InstanceCounts instance1;
		private InstanceCounts instance2;

		/**
		 * @param instance1
		 * @param instance2
		 */
		public InstancesComapredCounts(InstanceCounts instance1, InstanceCounts instance2)
		{
			super();
			this.instance1 = instance1;
			this.instance2 = instance2;
		}

		/**
		 * @return the instance1
		 */
		public InstanceCounts getInstance1()
		{
			return instance1;
		}

		/**
		 * @return the instance2
		 */
		public InstanceCounts getInstance2()
		{
			return instance2;
		}

		/**
		 * @param instance1
		 *            the instance1 to set
		 */
		public void setInstance1(InstanceCounts instance1)
		{
			this.instance1 = instance1;
		}

		/**
		 * @param instance2
		 *            the instance2 to set
		 */
		public void setInstance2(InstanceCounts instance2)
		{
			this.instance2 = instance2;
		}
	}

	private static final Logger logger = LogManager.getLogger(ZDTAPI.class);

	public ZDTAPI()
	{
		super();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response compareOnSSF()
	{
		logger.info("There is an incoming call from ZDT comparator API to compare");
		// this comparator invokes the 2 instances REST APIs and retrieves the counts for objects, and return the counts for each instance
		SavedsearchCountsComparator dcc = new SavedsearchCountsComparator();
		InstancesComparedData<CountsEntity> result = dcc.compare();
		InstancesComapredCounts ic = new InstancesComapredCounts(new InstanceCounts(result.getInstance1()),
				new InstanceCounts(result.getInstance2()));
		return Response.status(Status.OK).entity(JsonUtil.buildNormalMapper().toJson(ic)).build();
	}

	@PUT
	@Path("sync")
	@Produces(MediaType.APPLICATION_JSON)
	public Response syncOnSSF()
	{
		logger.info("There is an incoming call from ZDT comparator API to sync");
		// this comparator invokes the 2 instances REST APIs and retrieves the different table rows for the 2 instances, and update the 2 instances accordingly
		SavedsearchRowsComparator dcc = new SavedsearchRowsComparator();
		InstancesComparedData<ZDTTableRowEntity> result = dcc.compare();
		try {
			dcc.sync(result);
			return Response.status(Status.NO_CONTENT).build();
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Failed to sync data for the 2 instances").build();
		}
	}
}
