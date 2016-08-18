package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CachedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Keys;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.SanitizedInstanceInfo;
import org.testng.annotations.Test;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups = { "s2" })
public class RegistryLookupUtilTest
{
	private RegistryLookupUtil registryLookupUtil;
	@Mocked
	LookupManager lookupManager;
	@Mocked
	LookupClient lookupClient;
	@Mocked
	InstanceInfo instanceInfo;
	@Mocked
	InstanceQuery instanceQuery;
	@Mocked
	CacheManager cacheManager;
	@Mocked
	CachedLink cachedLink;
	@Mocked
	Keys keys;
	@Mocked
	Tenant tenant;
	@Mocked
	Link link;
	@Mocked
	SanitizedInstanceInfo sanitizedInstanceInfo;
	@Mocked
	URI uri;
	@Test
	public void testGetServiceInternalLink() throws Exception
	{
		new Expectations() {
			{
				LookupManager.getInstance();
				result = lookupManager;
				lookupManager.getLookupClient();
				result = lookupClient;
				lookupClient.lookup(withAny(instanceQuery));
				new InstanceQuery(withAny(instanceInfo));
				result = instanceQuery;
			}
		};
		RegistryLookupUtil.getServiceInternalLink("LoganService", "1.0+", "assetRoot", "testtennant1");
	}

	// merge following case from Qian Qi from webutils project
	@Test
	public void testGetServiceInternalLinkMocked(@Mocked final LookupManager lookupManager,
												 @Mocked final LookupClient lookupClient) throws Exception
	{
		new Expectations() {
			{
				LookupManager.getInstance();
				result = lookupManager;
				lookupManager.getLookupClient();
				result = lookupClient;
				lookupClient.lookup((InstanceQuery) any);
				result = null;
			}
		};

		oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil.getServiceInternalLink("a", "b", "c", "d");
	}

	@Test
	public void testGetServiceInternalLink2nd() throws Exception
	{
		final List<InstanceInfo> list = new ArrayList<>();
		list.add(instanceInfo);
		new Expectations() {
			{
				LookupManager.getInstance();
				result = lookupManager;
				lookupManager.getLookupClient();
				result = lookupClient;
				lookupClient.lookup(withAny(instanceQuery));
				result = list;
				new InstanceQuery(withAny(instanceInfo));
				result = instanceQuery;
			}
		};
		RegistryLookupUtil.getServiceInternalLink("", "", "", "");
	}
	@Test
	public void testGetServiceInternalLinkCLNOTNULL() throws Exception{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
		links.add(link);
		new Expectations(){
			{
				CacheManager.getInstance();
				result= cacheManager;
				cacheManager.getCacheable(withAny(tenant),anyString, withAny(keys));
				result = cachedLink;
				cachedLink.getHref();
				result = "https://";
			}
		};
		RegistryLookupUtil.getServiceInternalLink("", "", "", "");
	}
	@Test
	public void testGetServiceInternalLinkCLNULL() throws Exception{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
		links.add(link);
		new Expectations(){
			{
				CacheManager.getInstance();
				result= cacheManager;
				cacheManager.getCacheable(withAny(tenant),anyString, withAny(keys));
				result = null;
				LookupManager.getInstance();
				result = lookupManager;
				lookupManager.getLookupClient();
				result = lookupClient;
				lookupClient.lookup(withAny(instanceQuery));
				result = results;
			}
		};
		RegistryLookupUtil.getServiceInternalLink("", "", "", "");
	}
	@Test
	public void testGetServiceInternalLinkLinkNULL() throws Exception{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
		links.add(link);
		new Expectations(){
			{
				CacheManager.getInstance();
				result= cacheManager;
				cacheManager.getCacheable(withAny(tenant),anyString, withAny(keys));
				result = null;
				LookupManager.getInstance();
				result = lookupManager;
				lookupManager.getLookupClient();
				result = lookupClient;
				lookupClient.lookup(withAny(instanceQuery));
				result = results;

			}
		};
		RegistryLookupUtil.getServiceInternalLink("", "", "", "");
	}

	@Test
	public void testGetServiceExternalLinkCLNULL()throws Exception{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
		links.add(link);
		new Expectations(){
			{
				CacheManager.getInstance();
				result= cacheManager;
				cacheManager.getCacheable(withAny(tenant),anyString, withAny(keys));
				result = null;
				LookupManager.getInstance();
				result = lookupManager;
				lookupManager.getLookupClient();
				result = lookupClient;
				lookupClient.getInstanceForTenant(withAny(instanceInfo), anyString);
				result = results;
		 	}
		};
		RegistryLookupUtil.getServiceExternalLink("Logan Service", "1.0+", "assetroot", "testtenant");
	}
	@Test
	public void testGetServiceExternalLinkCLNOTNULL()throws Exception{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
		links.add(link);
		new Expectations(){
			{
				CacheManager.getInstance();
				result= cacheManager;
				cacheManager.getCacheable(withAny(tenant),anyString, withAny(keys));
				result = cachedLink;
			}
		};
		RegistryLookupUtil.getServiceExternalLink("Logan Service", "1.0+", "assetroot", "testtenant");
	}
	@Test
	public void testGetServiceExternalLinkINSNULL()throws Exception{
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
		links.add(link);
		new Expectations(){
			{
				CacheManager.getInstance();
				result= cacheManager;
				cacheManager.getCacheable(withAny(tenant),anyString, withAny(keys));
				result = null;
				LookupManager.getInstance();
				result = lookupManager;
				lookupManager.getLookupClient();
				result = lookupClient;
				lookupClient.getInstanceForTenant(withAny(instanceInfo), anyString);
				result = null;
			}
		};
		RegistryLookupUtil.getServiceExternalLink("Logan Service", "1.0+", "assetroot", "testtenant");
	}
	@Test
	public void testGetServiceExternalLinkLKNOTNULL()throws Exception{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
		links.add(link);
		new Expectations(){
			{
				CacheManager.getInstance();
				result= cacheManager;
				cacheManager.getCacheable(withAny(tenant),anyString, withAny(keys));
				result = null;
				LookupManager.getInstance();
				result = lookupManager;
				lookupManager.getLookupClient();
				result = lookupClient;
				lookupClient.getInstanceForTenant(withAny(instanceInfo), anyString);
				result = results;
			}
		};
		RegistryLookupUtil.getServiceExternalLink("Logan Service", "1.0+", "assetroot", "testtenant");
	}

	@Test
	public void testGetServiceExternalLinkLKNULL()throws Exception{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
		links.add(link);

		RegistryLookupUtil.getServiceExternalLink("Logan Service", "1.0+", "assetroot", "testtenant");
	}
}