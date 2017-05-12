/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.JsonUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.webutils.util.TenantSubscriptionUtil;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.AbstractComparator;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstanceData;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.InstancesComparedData;

/**
 * @author pingwu
 */
public class SavedsearchCountsComparator extends AbstractComparator
{
	private static final Logger logger = LogManager.getLogger(SavedsearchCountsComparator.class);

	public InstancesComparedData<CountsEntity> compare(String tenantId, String userTenant)
	{
		try {
			logger.info("Starts to compare the two SSF OMC instances");
			HashMap<String, LookupClient> instances = getOMCInstances();
			if (instances == null) {
				logger.error("Failed to retrieve ZDT OMC instances");
				return null;
			}
			
			String key1 = null;
 			String key2 = null;
 			LookupClient client1 = null;
 			LookupClient client2 = null;
 			for (String key : instances.keySet()) {
 				if (client1 == null) {
 					client1 = instances.get(key);
 					key1 = key;
 				} else {
 					if (client2 == null)
 					client2 = instances.get(key);
 					key2 = key;
 				}
 			}
 			logger.info("client1 = ",client1);
 			logger.info("client2 = ",client2);
 			CountsEntity ze1 = retrieveCountsForSingleInstance(tenantId, userTenant,client1);
			if (ze1 == null) {
				logger.error("Failed to retrieve ZDT count entity for instance {}", key1);
				logger.info("Completed to compare the two SSF OMC instances");
				return null;
			}

			CountsEntity ze2 = retrieveCountsForSingleInstance(tenantId, userTenant,client2);
			if (ze2 == null) {
				logger.error("Failed to retrieve ZDT count entity for instance {}",key2);
				logger.info("Completed to compare the two SSF OMC instances");
				return null;
			}

			// now compare
			InstancesComparedData<CountsEntity> cd = compareInstancesCounts(key1, client1, ze1, key2, client2, ze2);
			logger.info("Completed to compare the two SSF OMC instances");
			return cd;
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return null;
		}
	}

	/**
	 * Compare the data/counts from the 2 instances<br>
	 * The returned <code>ComparedData</code> will contain only the different counts, and leave the same counts null
	 *
	 * @param ins1
	 * @param ze1
	 * @param ins2
	 * @param ze2
	 */
	private InstancesComparedData<CountsEntity> compareInstancesCounts(String key1, LookupClient client1, CountsEntity ze1,
 			String key2, LookupClient client2, CountsEntity ze2)
	{
		CountsEntity differentCountsForInstance1 = new CountsEntity();
		CountsEntity differentCountsForInstance2 = new CountsEntity();
		boolean allMatch = true;
		if (ze1.getCountOfCategory() != ze2.getCountOfCategory()) {
			logger.error("Category count does not match. In instance \"{}\", count is {}. In instance \"{}\", count is {}",
					key1, ze1.getCountOfCategory(), key2, ze2.getCountOfCategory());
			differentCountsForInstance1.setcountOfCategory(ze1.getCountOfCategory());
			differentCountsForInstance2.setcountOfCategory(ze2.getCountOfCategory());
			allMatch = false;
		}
		if (ze1.getCountOfFolder() != ze2.getCountOfFolder()) {
			logger.error("Folders count does not match. In instance \"{}\", count is {}. In instance \"{}\", count is {}",
					key1, ze1.getCountOfFolder(), key2, ze2.getCountOfFolder());
			differentCountsForInstance1.setcountOfFolder(ze1.getCountOfFolder());
			differentCountsForInstance2.setcountOfFolder(ze2.getCountOfFolder());
			allMatch = false;
		}
		if (ze1.getCountOfSearch() != ze2.getCountOfSearch()) {
			logger.error("Searchs count does not match. In instance \"{}\", count is {}. In instance \"{}\", count is {}",
					key1, ze1.getCountOfSearch(), key2, ze2.getCountOfSearch());
			differentCountsForInstance1.setcountOfSearch(ze1.getCountOfSearch());
			differentCountsForInstance2.setcountOfSearch(ze2.getCountOfSearch());
			allMatch = false;
		}
		if (allMatch) {
			logger.info("All counts from both instances (instance \"{}\" and instance \"{}\") match!", key1,
					key2);
		}
		InstanceData<CountsEntity> instance1 = new InstanceData<CountsEntity>(key1, client1, differentCountsForInstance1, 0);
		InstanceData<CountsEntity> instance2 = new InstanceData<CountsEntity>(key2, client2, differentCountsForInstance2, 0);
		InstancesComparedData<CountsEntity> cd = new InstancesComparedData<CountsEntity>(instance1, instance2);
		return cd;
	}

	/**
	 * @throws Exception
	 * @throws IOException
	 */
	private CountsEntity retrieveCountsForSingleInstance(String tenantId, String userTenant,LookupClient lc) throws Exception, IOException
	{
		Link lk = getSingleInstanceUrl(lc, "zdt/counts", "http");
		if (lk == null) {
			logger.warn("Get a null or empty link for one single instance!");
			return null;
		}
		logger.info("*****lookup link is {}", lk.getHref());
		String response = new TenantSubscriptionUtil.RestClient().get(lk.getHref(), tenantId, userTenant);
		logger.info("Checking savedsearch OMC instance counts. Response is " + response);
		JsonUtil ju = JsonUtil.buildNormalMapper();
		CountsEntity ze = ju.fromJson(response, CountsEntity.class);
		if (ze == null) {
			logger.warn("Checking savedsearch OMC instance counts: null/empty entity retrieved.");
			return null;
		}
		// TODO: for the 1st step implementation, let's log in log files then
		logger.info(
				"Retrieved counts for Category OMC instance: savedsearch count - {}, Folders count - {}, Search count - {}",
				ze.getCountOfCategory(), ze.getCountOfFolder(), ze.getCountOfSearch());
		return ze;
	}
}
