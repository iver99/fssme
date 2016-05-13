package oracle.sysman.emaas.platform.savedsearch.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-18.
 */
@Test(groups = { "s1" })
public class EmAnalyticsSearchParamPKTest
{
	private EmAnalyticsSearchParamPK emAnalyticsSearchParamPK;

	@BeforeMethod
	public void setUp() throws Exception
	{
		emAnalyticsSearchParamPK = new EmAnalyticsSearchParamPK();
		emAnalyticsSearchParamPK.setSearchId(111L);
		emAnalyticsSearchParamPK.setName("name1");
		emAnalyticsSearchParamPK.setTenantId(1234L);
	}

	@Test(groups = { "s1" })
	public void testEquals() throws Exception
	{
		Assert.assertTrue(emAnalyticsSearchParamPK.equals(emAnalyticsSearchParamPK));

		Assert.assertFalse(emAnalyticsSearchParamPK.equals(new String("astring")));

		EmAnalyticsSearchParamPK emAnalyticsSearchParamPK2 = new EmAnalyticsSearchParamPK();
		emAnalyticsSearchParamPK2.setSearchId(111L);
		emAnalyticsSearchParamPK2.setName("name1");
		emAnalyticsSearchParamPK2.setTenantId(1234L);
		Assert.assertTrue(emAnalyticsSearchParamPK.equals(emAnalyticsSearchParamPK2));
	}

	@Test(groups = { "s1" })
	public void testGetName() throws Exception
	{
		String name = "namexx";
		emAnalyticsSearchParamPK.setName(name);
		Assert.assertEquals(name, emAnalyticsSearchParamPK.getName());
	}

	@Test(groups = { "s1" })
	public void testGetSearchId() throws Exception
	{
		long searchid = 333L;
		emAnalyticsSearchParamPK.setSearchId(searchid);
		Assert.assertEquals(searchid, emAnalyticsSearchParamPK.getSearchId());
	}

	@Test(groups = { "s1" })
	public void testHashCode() throws Exception
	{
		Assert.assertEquals(emAnalyticsSearchParamPK.hashCode(), emAnalyticsSearchParamPK.hashCode());

		EmAnalyticsSearchParamPK emAnalyticsSearchParamPK2 = new EmAnalyticsSearchParamPK();
		emAnalyticsSearchParamPK2.setSearchId(111L);
		emAnalyticsSearchParamPK2.setName("name1");
		emAnalyticsSearchParamPK2.setTenantId(1234L);
		Assert.assertEquals(emAnalyticsSearchParamPK2.hashCode(), emAnalyticsSearchParamPK.hashCode());
	}
}