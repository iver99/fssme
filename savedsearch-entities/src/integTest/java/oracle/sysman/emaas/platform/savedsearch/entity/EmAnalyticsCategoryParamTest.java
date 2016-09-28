package oracle.sysman.emaas.platform.savedsearch.entity;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-17.
 */
@Test (groups = {"s1"})
public class EmAnalyticsCategoryParamTest {

    private EmAnalyticsCategoryParam emAnalyticsCategoryParam;

    @BeforeClass
    public void setUp() throws Exception {
        emAnalyticsCategoryParam = new EmAnalyticsCategoryParam();
        emAnalyticsCategoryParam.setCategoryId(BigInteger.ONE);
        emAnalyticsCategoryParam.setName("name1");
        emAnalyticsCategoryParam.setValue("value1");
    }

    public void testEquals() throws Exception {
        Assert.assertTrue(emAnalyticsCategoryParam.equals(emAnalyticsCategoryParam));

        Assert.assertFalse(emAnalyticsCategoryParam.equals(null));
        Assert.assertFalse(emAnalyticsCategoryParam == null);
        Assert.assertFalse(emAnalyticsCategoryParam.equals(new String("astring")));

        EmAnalyticsCategoryParam emAnalyticsCategoryParam2 =  new EmAnalyticsCategoryParam();
        emAnalyticsCategoryParam2.setCategoryId(BigInteger.ONE.add(BigInteger.ONE));
        emAnalyticsCategoryParam2.setName("name1");
        emAnalyticsCategoryParam2.setValue("value1");
        Assert.assertFalse(emAnalyticsCategoryParam2.equals(emAnalyticsCategoryParam));

        emAnalyticsCategoryParam2.setCategoryId(BigInteger.ONE);
        emAnalyticsCategoryParam2.setName(null);
        Assert.assertFalse(emAnalyticsCategoryParam2.equals(emAnalyticsCategoryParam));

        emAnalyticsCategoryParam2.setName("name2");
        Assert.assertFalse(emAnalyticsCategoryParam2.equals(emAnalyticsCategoryParam));

        emAnalyticsCategoryParam2.setName("name1");
        emAnalyticsCategoryParam2.setValue(null);
        Assert.assertFalse(emAnalyticsCategoryParam2.equals(emAnalyticsCategoryParam));

        emAnalyticsCategoryParam2.setValue("value2");
        Assert.assertFalse(emAnalyticsCategoryParam2.equals(emAnalyticsCategoryParam));

        emAnalyticsCategoryParam2.setCategoryId(BigInteger.ONE);
        emAnalyticsCategoryParam2.setName("name1");
        emAnalyticsCategoryParam2.setValue("value1");
        Assert.assertTrue(emAnalyticsCategoryParam2.equals(emAnalyticsCategoryParam));
    }

    @Test (groups = {"s1"})
    public void testGetCategoryId() throws Exception {
        Assert.assertEquals(emAnalyticsCategoryParam.getCategoryId(),BigInteger.ONE);
    }

    @Test (groups = {"s1"})
    public void getEmAnalyticsCategory() throws Exception {
        EmAnalyticsCategory emAnalyticsCategory = new EmAnalyticsCategory();
        emAnalyticsCategoryParam.setEmAnalyticsCategory(emAnalyticsCategory);
        Assert.assertEquals(emAnalyticsCategory,emAnalyticsCategoryParam.getEmAnalyticsCategory());
    }

    @Test (groups = {"s1"})
    public void testGetName() throws Exception {
        Assert.assertEquals(emAnalyticsCategoryParam.getName(),"name1");
    }

    @Test (groups = {"s1"})
    public void getValue() throws Exception {
        Assert.assertEquals(emAnalyticsCategoryParam.getValue(),"value1");
    }

    @Test (groups = {"s1"})
    public void testHashCode() throws Exception {
        emAnalyticsCategoryParam.setCategoryId(BigInteger.ONE);
        emAnalyticsCategoryParam.setName("name1");
        emAnalyticsCategoryParam.setValue("value1");
        Assert.assertEquals(emAnalyticsCategoryParam.hashCode(),-1876616455);
    }

    @Test (groups = {"s1"})
    public void getTenantId(){
        emAnalyticsCategoryParam.setTenantId(1L);
        Assert.assertEquals((Long) 1L, emAnalyticsCategoryParam.getTenantId());
    }

}