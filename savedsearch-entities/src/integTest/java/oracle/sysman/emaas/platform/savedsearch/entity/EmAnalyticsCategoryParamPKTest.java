package oracle.sysman.emaas.platform.savedsearch.entity;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-17.
 */
@Test(groups = { "s1" })
public class EmAnalyticsCategoryParamPKTest
{

	private EmAnalyticsCategoryParamPK emAnalyticsCategoryParamPK;

	@BeforeClass
	public void setUp()
	{
		emAnalyticsCategoryParamPK = new EmAnalyticsCategoryParamPK();
		emAnalyticsCategoryParamPK.setCategoryId(BigInteger.ONE);
		emAnalyticsCategoryParamPK.setName("name1");
		emAnalyticsCategoryParamPK.setTenantId(1L);
	}

	@Test(groups = { "s1" })
	public void testEquals()
	{
		Assert.assertTrue(emAnalyticsCategoryParamPK.equals(emAnalyticsCategoryParamPK));

		Assert.assertFalse(emAnalyticsCategoryParamPK.equals(new String("astring")));

		EmAnalyticsCategoryParamPK emAnalyticsCategoryParamPK2 = new EmAnalyticsCategoryParamPK();
		emAnalyticsCategoryParamPK2.setCategoryId(BigInteger.ONE);
		emAnalyticsCategoryParamPK2.setName("name1");
		emAnalyticsCategoryParamPK2.setTenantId(1L);
		Assert.assertTrue(emAnalyticsCategoryParamPK.equals(emAnalyticsCategoryParamPK2));
	}

	@Test(groups = { "s1" })
	public void testGetCategoryId()
	{
		Assert.assertEquals(emAnalyticsCategoryParamPK.getCategoryId(), BigInteger.ONE);
	}

	@Test(groups = { "s1" })
	public void testGetName()
	{
		Assert.assertEquals(emAnalyticsCategoryParamPK.getName(), "name1");
	}

	@Test(groups = { "s1" })
	public void testHashCode()
	{
		Assert.assertEquals(emAnalyticsCategoryParamPK.hashCode(), emAnalyticsCategoryParamPK.hashCode());

		EmAnalyticsCategoryParamPK emAnalyticsCategoryParamPK2 = new EmAnalyticsCategoryParamPK();
		emAnalyticsCategoryParamPK2.setCategoryId(BigInteger.ONE);
		emAnalyticsCategoryParamPK2.setName("name1");
		emAnalyticsCategoryParamPK2.setTenantId(1L);
		Assert.assertEquals(emAnalyticsCategoryParamPK.hashCode(), emAnalyticsCategoryParamPK2.hashCode());
	}

	@Test(groups = { "s1" })
	public void testGetTenantId(){
		emAnalyticsCategoryParamPK.setTenantId(1L);
		Assert.assertEquals((Long)1L, emAnalyticsCategoryParamPK.getTenantId());
	}
}