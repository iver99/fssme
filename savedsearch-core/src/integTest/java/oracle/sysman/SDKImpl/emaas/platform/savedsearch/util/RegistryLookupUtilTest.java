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
		final List<InstanceInfo> list = new ArrayList<>();
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
	public void testGetServiceInternalLink_Mocked(@Mocked final LookupManager lookupManager,
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

		registryLookupUtil = new RegistryLookupUtil();
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
	public void testGetServiceInternalLink_CL_NOTNULL() throws Exception{
		final ArrayList<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final ArrayList<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
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
	public void testGetServiceInternalLink_CL_NULL() throws Exception{
		final ArrayList<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final ArrayList<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
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
				instanceInfo.getLinksWithProtocol(anyString, anyString);
				result = links;
			}
		};
		RegistryLookupUtil.getServiceInternalLink("", "", "", "");
	}
	@Test
	public void testGetServiceInternalLink_Link_NULL() throws Exception{
		final ArrayList<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final ArrayList<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
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
				instanceInfo.getLinksWithProtocol(anyString, "https");
				result = null;
				instanceInfo.getLinksWithProtocol(anyString, "http");
				result = links;

			}
		};
		RegistryLookupUtil.getServiceInternalLink("", "", "", "");
	}

	@Test
	public void testGetServiceExternalLink_CL_NULL()throws Exception{
		final ArrayList<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final ArrayList<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
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
	public void testGetServiceExternalLink_CL_NOTNULL()throws Exception{
		final ArrayList<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final ArrayList<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
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
	public void testGetServiceExternalLink_INS_NULL()throws Exception{
		final ArrayList<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
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
	public void testGetServiceExternalLink_LK_NOTNULL()throws Exception{
		final ArrayList<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final ArrayList<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
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
				sanitizedInstanceInfo.getLinks(anyString);
				result = links;
				URI.create(anyString);
				result = uri;
				uri.getScheme();
				result = "https";
			}
		};
		RegistryLookupUtil.getServiceExternalLink("Logan Service", "1.0+", "assetroot", "testtenant");
	}

	@Test
	public void testGetServiceExternalLink_LK_NULL()throws Exception{
		final ArrayList<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final ArrayList<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links  = new ArrayList<>();
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
				sanitizedInstanceInfo.getLinks(anyString);
				result = null;
			}
		};
		RegistryLookupUtil.getServiceExternalLink("Logan Service", "1.0+", "assetroot", "testtenant");
	}
}