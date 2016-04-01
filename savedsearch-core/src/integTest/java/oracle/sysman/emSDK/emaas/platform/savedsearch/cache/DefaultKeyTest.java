package oracle.sysman.emSDK.emaas.platform.savedsearch.cache;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * Created by QIQIAN on 2016/3/25.
 */
@Test(groups = { "s1" })
public class DefaultKeyTest
{

	DefaultKey defaultKey;

	@BeforeMethod
	public void setUp() throws Exception
	{
		defaultKey = new DefaultKey(new Tenant("tenantxx"), new Keys());
	}

	@Test
	@ExpectedExceptions(IllegalArgumentException.class)
	public void testConstructor() throws Exception
	{
		new DefaultKey(new Tenant("tenantxx"), null);
	}

	@Test
	public void testEquals() throws Exception
	{
		Assert.assertTrue(defaultKey.equals(defaultKey));
	}

	@Test
	public void testGetParams() throws Exception
	{
		defaultKey.getParams();
	}

	@Test
	public void testGetTenant() throws Exception
	{
		defaultKey.getTenant();
	}

	@Test
	public void testHashCode() throws Exception
	{
		defaultKey.hashCode();
	}

	@Test
	public void testToString() throws Exception
	{
		Assert.assertTrue(defaultKey.toString() instanceof String);
	}
}