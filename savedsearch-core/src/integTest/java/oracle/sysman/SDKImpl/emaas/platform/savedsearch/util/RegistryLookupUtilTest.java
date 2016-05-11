package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.util.ArrayList;
import java.util.List;

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
		RegistryLookupUtil.getServiceInternalLink("", "", "", "");
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

	// merge following case from Qian Qi from webutils project
	@Test
	public void testGetServiceInternalLink3() throws Exception
	{
		registryLookupUtil = new RegistryLookupUtil();
		oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil.getServiceInternalLink("a", "b", "c", "d");
	}
}