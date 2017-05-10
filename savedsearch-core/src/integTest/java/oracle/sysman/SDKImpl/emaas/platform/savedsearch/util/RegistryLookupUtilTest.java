package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.InstanceQuery;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.SanitizedInstanceInfo;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupClient;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.lookup.LookupManager;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups = { "s2" })
public class RegistryLookupUtilTest
{
	@Mocked
	LookupManager lookupManager;
	@Mocked
	LookupClient lookupClient;
	@Mocked
	InstanceInfo instanceInfo;
	@Mocked
	InstanceQuery instanceQuery;
	@Mocked
	VersionedLink link;
	@Mocked
	SanitizedInstanceInfo sanitizedInstanceInfo;
	@Mocked
	URI uri;

	@Test
	public void testGetAllServiceInternalLinksByRel(@Mocked final LookupManager anyLookupManager,
			@Mocked final LookupClient anyLookupClient, @Mocked final InstanceInfo anyInstanceInfo,
			@Mocked final List<InstanceInfo> anyInstanceInfoList) throws IOException
	{
		final List<Link> links = new ArrayList<Link>();
		links.add(link);
		final List<InstanceInfo> iiList = new ArrayList<InstanceInfo>();
		iiList.add(anyInstanceInfo);
		final String serviceName = "Test Service";
		new Expectations() {
			{
				LookupManager.getInstance().getLookupClient();
				result = anyLookupClient;
				anyLookupClient.getInstancesWithLinkRelPrefix(anyString, anyString);
				result = iiList;
				anyInstanceInfo.getLinksWithRelPrefix(anyString);
				result = links;
				anyInstanceInfo.getServiceName();
				result = serviceName;
				link.getHref();
				result = "http://test.link.com";
			}
		};
		List<VersionedLink> linkList = RegistryLookupUtil.getAllServicesInternalLinksByRel("ssf.widget.changed");
		Assert.assertNotNull(linkList);
		Assert.assertEquals(linkList.size(), 1);
		Assert.assertEquals(linkList.get(0).getHref(), link.getHref());
	}

	@Test
	public void testGetAllServiceInternalLinksByRelException(@Mocked final LookupManager anyLookupManager,
			@Mocked final LookupClient anyLookupClient, @Mocked final InstanceInfo anyInstanceInfo,
			@Mocked final List<InstanceInfo> anyInstanceInfoList, @Mocked final Throwable throwable) throws IOException
	{
		final List<InstanceInfo> iiList = new ArrayList<InstanceInfo>();
		iiList.add(anyInstanceInfo);
		new Expectations() {
			{
				LookupManager.getInstance().getLookupClient();
				result = anyLookupClient;
				anyLookupClient.getInstancesWithLinkRelPrefix(anyString, anyString);
				result = iiList;
				anyInstanceInfo.getLinksWithRelPrefix(anyString);
				result = new Exception(throwable);
			}
		};
		List<VersionedLink> linkList = RegistryLookupUtil.getAllServicesInternalLinksByRel("ssf.widget.changed");
		Assert.assertNotNull(linkList);
	}

	@Test
	public void testGetAllServiceInternalLinksByRelLinksNull(@Mocked final LookupManager anyLookupManager,
			@Mocked final LookupClient anyLookupClient, @Mocked final InstanceInfo anyInstanceInfo,
			@Mocked final List<InstanceInfo> anyInstanceInfoList) throws IOException
	{
		final List<InstanceInfo> iiList = new ArrayList<InstanceInfo>();
		iiList.add(anyInstanceInfo);
		new Expectations() {
			{
				LookupManager.getInstance().getLookupClient();
				result = anyLookupClient;
				anyLookupClient.getInstancesWithLinkRelPrefix(anyString, anyString);
				result = iiList;
				anyInstanceInfo.getLinksWithRelPrefix(anyString);
				result = null;
			}
		};
		List<VersionedLink> linkList = RegistryLookupUtil.getAllServicesInternalLinksByRel("ssf.widget.changed");
		Assert.assertNotNull(linkList);
	}

	@Test
	public void testGetAllServiceInternalLinksByRelListNull(@Mocked final LookupManager anyLookupManager,
			@Mocked final LookupClient anyLookupClient) throws IOException
	{
		new Expectations() {
			{
				LookupManager.getInstance().getLookupClient();
				result = anyLookupClient;
				anyLookupClient.getInstancesWithLinkRelPrefix(anyString, anyString);
				result = null;
			}
		};
		List<VersionedLink> linkList = RegistryLookupUtil.getAllServicesInternalLinksByRel("ssf.widget.changed");
		Assert.assertNotNull(linkList);
	}

	@Test
	public void testGetServiceExternalLinkCLNOTNULL() throws Exception
	{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links = new ArrayList<>();
		links.add(link);
		new Expectations() {
			{
//				CacheManager.getInstance();
//				result = cacheManager;
//				cacheManager.getCacheable(withAny(tenant), anyString, withAny(keys));
//				result = cachedLink;
			}
		};
		RegistryLookupUtil.getServiceExternalLink("Logan Service", "1.0+", "assetroot", "testtenant");
	}

	@Test
	public void testGetServiceExternalLinkCLNULL() throws Exception
	{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links = new ArrayList<>();
		links.add(link);
		new Expectations() {
			{
//				CacheManager.getInstance();
//				result = cacheManager;
//				cacheManager.getCacheable(withAny(tenant), anyString, withAny(keys));
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
	public void testGetServiceExternalLinkINSNULL() throws Exception
	{
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links = new ArrayList<>();
		links.add(link);
		new Expectations() {
			{
//				CacheManager.getInstance();
//				result = cacheManager;
//				cacheManager.getCacheable(withAny(tenant), anyString, withAny(keys));
//				result = null;
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
	public void testGetServiceExternalLinkLKNOTNULL() throws Exception
	{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links = new ArrayList<>();
		links.add(link);
		new Expectations() {
			{
//				CacheManager.getInstance();
//				result = cacheManager;
//				cacheManager.getCacheable(withAny(tenant), anyString, withAny(keys));
//				result = null;
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
	public void testGetServiceExternalLinkLKNULL() throws Exception
	{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links = new ArrayList<>();
		links.add(link);

		RegistryLookupUtil.getServiceExternalLink("Logan Service", "1.0+", "assetroot", "testtenant");
	}

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
	public void testGetServiceInternalLinkCLNOTNULL() throws Exception
	{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links = new ArrayList<>();
		links.add(link);
		new Expectations() {
			{
//				CacheManager.getInstance();
//				result = cacheManager;
//				cacheManager.getCacheable(withAny(tenant), anyString, withAny(keys));
////				result = cachedLink;
////				cachedLink.getHref();
//				result = "https://";
			}
		};
		RegistryLookupUtil.getServiceInternalLink("", "", "", "");
	}

	@Test
	public void testGetServiceInternalLinkCLNULL() throws Exception
	{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links = new ArrayList<>();
		links.add(link);
		new Expectations() {
			{
//				CacheManager.getInstance();
//				result = cacheManager;
//				cacheManager.getCacheable(withAny(tenant), anyString, withAny(keys));
//				result = null;
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
	public void testGetServiceInternalLinkLinkNULL() throws Exception
	{
		final List<InstanceInfo> results = new ArrayList<>();
		results.add(instanceInfo);
		final List<oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link> links = new ArrayList<>();
		links.add(link);
		new Expectations() {
			{
//				CacheManager.getInstance();
//				result = cacheManager;
//				cacheManager.getCacheable(withAny(tenant), anyString, withAny(keys));
//				result = null;
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
}