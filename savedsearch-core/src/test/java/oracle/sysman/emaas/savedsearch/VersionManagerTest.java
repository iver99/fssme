package oracle.sysman.emaas.savedsearch;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SchemaVersion;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.VersionManager;

import org.testng.Assert;
import org.testng.annotations.Test;

public class VersionManagerTest extends BaseTest
{
	@Test
	public void testGetSchemaVesrion()
	{
		VersionManager vm = VersionManager.getInstance();
		SchemaVersion schVer = vm.getSchemaVersion();
		Assert.assertNotNull(schVer);
		Assert.assertEquals(schVer.getName(), SchemaVersion.VERSION_NAME);
		Assert.assertEquals(schVer.getMajorVersion(), 0);
		Assert.assertEquals(schVer.getMinorVersion(), 5);
	}
}
