/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence;

/**
 * @author vinjoshi
 *
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.JSONUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author guobaochen
 */
public class SchemaUtil
{

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class SchemaDeployment
	{
		private String deploymentId;
		private String deploymentUUID;
		private String containerName;
		private String softwareName;
		private String softwareVersion;
		private String deploymentTs;
		private String deploymentType;
		private String deploymentStatus;
		private String schemaId;
		private String schemaUser;
		private String entityDomainName;
		private String entityDomainKey;
		private String entityDomainValue;

		/**
		 * @return the containerName
		 */
		public String getContainerName()
		{
			return containerName;
		}

		/**
		 * @return the deploymentId
		 */
		public String getDeploymentId()
		{
			return deploymentId;
		}

		/**
		 * @return the deploymentStatus
		 */
		public String getDeploymentStatus()
		{
			return deploymentStatus;
		}

		/**
		 * @return the deploymentTs
		 */
		public String getDeploymentTs()
		{
			return deploymentTs;
		}

		/**
		 * @return the deploymentType
		 */
		public String getDeploymentType()
		{
			return deploymentType;
		}

		/**
		 * @return the deploymentUUID
		 */
		public String getDeploymentUUID()
		{
			return deploymentUUID;
		}

		/**
		 * @return the entityDomainKey
		 */
		public String getEntityDomainKey()
		{
			return entityDomainKey;
		}

		/**
		 * @return the entityDomainName
		 */
		public String getEntityDomainName()
		{
			return entityDomainName;
		}

		/**
		 * @return the entityDomainValue
		 */
		public String getEntityDomainValue()
		{
			return entityDomainValue;
		}

		/**
		 * @return the schemaId
		 */
		public String getSchemaId()
		{
			return schemaId;
		}

		/**
		 * @return the schemaUser
		 */
		public String getSchemaUser()
		{
			return schemaUser;
		}

		/**
		 * @return the softwareName
		 */
		public String getSoftwareName()
		{
			return softwareName;
		}

		/**
		 * @return the softwareVersion
		 */
		public String getSoftwareVersion()
		{
			return softwareVersion;
		}

		/**
		 * @param containerName
		 *            the containerName to set
		 */
		public void setContainerName(String containerName)
		{
			this.containerName = containerName;
		}

		/**
		 * @param deploymentId
		 *            the deploymentId to set
		 */
		public void setDeploymentId(String deploymentId)
		{
			this.deploymentId = deploymentId;
		}

		/**
		 * @param deploymentStatus
		 *            the deploymentStatus to set
		 */
		public void setDeploymentStatus(String deploymentStatus)
		{
			this.deploymentStatus = deploymentStatus;
		}

		/**
		 * @param deploymentTs
		 *            the deploymentTs to set
		 */
		public void setDeploymentTs(String deploymentTs)
		{
			this.deploymentTs = deploymentTs;
		}

		/**
		 * @param deploymentType
		 *            the deploymentType to set
		 */
		public void setDeploymentType(String deploymentType)
		{
			this.deploymentType = deploymentType;
		}

		/**
		 * @param deploymentUUID
		 *            the deploymentUUID to set
		 */
		public void setDeploymentUUID(String deploymentUUID)
		{
			this.deploymentUUID = deploymentUUID;
		}

		/**
		 * @param entityDomainKey
		 *            the entityDomainKey to set
		 */
		public void setEntityDomainKey(String entityDomainKey)
		{
			this.entityDomainKey = entityDomainKey;
		}

		/**
		 * @param entityDomainName
		 *            the entityDomainName to set
		 */
		public void setEntityDomainName(String entityDomainName)
		{
			this.entityDomainName = entityDomainName;
		}

		/**
		 * @param entityDomainValue
		 *            the entityDomainValue to set
		 */
		public void setEntityDomainValue(String entityDomainValue)
		{
			this.entityDomainValue = entityDomainValue;
		}

		/**
		 * @param schemaId
		 *            the schemaId to set
		 */
		public void setSchemaId(String schemaId)
		{
			this.schemaId = schemaId;
		}

		/**
		 * @param schemaUser
		 *            the schemaUser to set
		 */
		public void setSchemaUser(String schemaUser)
		{
			this.schemaUser = schemaUser;
		}

		/**
		 * @param softwareName
		 *            the softwareName to set
		 */
		public void setSoftwareName(String softwareName)
		{
			this.softwareName = softwareName;
		}

		/**
		 * @param softwareVersion
		 *            the softwareVersion to set
		 */
		public void setSoftwareVersion(String softwareVersion)
		{
			this.softwareVersion = softwareVersion;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class SchemaDeploymentUrls
	{
		private List<String> virtualEndpoints;
		private List<String> canonicalEndpoints;

		/**
		 * @return the canonicalEndpoints
		 */
		public List<String> getCanonicalEndpoints()
		{
			return canonicalEndpoints;
		}

		/**
		 * @return the virtualEndpoints
		 */
		public List<String> getVirtualEndpoints()
		{
			return virtualEndpoints;
		}

		/**
		 * @param canonicalEndpoints
		 *            the canonicalEndpoints to set
		 */
		public void setCanonicalEndpoints(List<String> canonicalEndpoints)
		{
			this.canonicalEndpoints = canonicalEndpoints;
		}

		/**
		 * @param virtualEndpoints
		 *            the virtualEndpoints to set
		 */
		public void setVirtualEndpoints(List<String> virtualEndpoints)
		{
			this.virtualEndpoints = virtualEndpoints;
		}

	}

	private static final String ITEMS = "items";

	private static Logger logger = LogManager.getLogger(SchemaUtil.class);

	public static List<String> getSchemaUrls(String json)
	{
		if (json == null || "".equals(json)) {
			return null;
		}

		HashSet<String> urlSet = new HashSet<String>();

		try {

			List<SchemaDeploymentUrls> sdlist = JSONUtil.fromJsonToList(json, SchemaDeploymentUrls.class, ITEMS);
			if (sdlist == null | sdlist.isEmpty()) {
				return null;
			}
			for (SchemaDeploymentUrls sd : sdlist) {
				for (String temp : sd.getCanonicalEndpoints()) {
					if (temp.contains("https")) {
						continue;
					}
					urlSet.add(temp);
				}
				for (String temp : sd.getVirtualEndpoints()) {
					if (temp.contains("https")) {
						continue;
					}
					urlSet.add(temp);
				}

			}
		}
		catch (Exception e) {

			logger.error("an error occureed while getting schema name", e);
			return null;
		}
		List<String> urls = new ArrayList();
		urls.addAll(urlSet);
		return urls;
	}

	public static String getSchemaUserBySoftwareName(String json, String softwareName)
	{
		if (json == null || "".equals(json) || softwareName == null || "".equals(softwareName)) {
			return null;
		}

		try {
			List<SchemaDeployment> sdlist = JSONUtil.fromJsonToList(json, SchemaDeployment.class);
			if (sdlist == null | sdlist.isEmpty()) {
				return null;
			}
			for (SchemaDeployment sd : sdlist) {
				if (softwareName.equals(sd.getSoftwareName())) {
					return sd.getSchemaUser();
				}
			}
		}
		catch (IOException e) {

			logger.error("an error occureed while getting schema name", e);
		}
		return null;
	}
}
