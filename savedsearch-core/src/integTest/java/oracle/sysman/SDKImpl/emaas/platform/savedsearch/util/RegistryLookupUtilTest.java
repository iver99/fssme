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