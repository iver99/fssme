package oracle.sysman.emaas.platform.savedsearch.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * @author qianqi
 * @since 16-2-18.
 */
@Test (groups = {"s1"})
public class EmAnalyticsSearchParamTest {
    EmAnalyticsSearchParam emAnalyticsSearchParam;

    @BeforeMethod
    public void setUp() throws Exception {
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("name1");
        emAnalyticsSearchParam.setSearchId(111L);
        emAnalyticsSearchParam.setParamAttributes("paramAttributes1");
        emAnalyticsSearchParam.setParamType(BigDecimal.valueOf(11L));
        emAnalyticsSearchParam.setParamValueClob("paramValueClob1");
        emAnalyticsSearchParam.setParamValueStr("paramValueStr1");

    }

    @Test (groups = {"s1"})
    public void testEquals() throws Exception {
        Assert.assertTrue(emAnalyticsSearchParam.equals(emAnalyticsSearchParam));

        Assert.assertFalse(emAnalyticsSearchParam == null);
        Assert.assertFalse(emAnalyticsSearchParam.equals(new String("astring")));

        EmAnalyticsSearchParam emAnalyticsSearchParam2 = new EmAnalyticsSearchParam();
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));

        emAnalyticsSearchParam2.setName("name2");
        emAnalyticsSearchParam2.setSearchId(111L);
        emAnalyticsSearchParam2.setParamAttributes("paramAttributes1");
        emAnalyticsSearchParam2.setParamType(BigDecimal.valueOf(11L));
        emAnalyticsSearchParam2.setParamValueClob("paramValueClob1");
        emAnalyticsSearchParam2.setParamValueStr("paramValueStr1");
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));

        emAnalyticsSearchParam2.setSearchId(222L);
        emAnalyticsSearchParam2.setName("name1");
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));

        emAnalyticsSearchParam2.setParamAttributes("paramAttributes2");
        emAnalyticsSearchParam2.setSearchId(111L);
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));
        emAnalyticsSearchParam2.setParamAttributes(null);
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));

        emAnalyticsSearchParam2.setParamType(BigDecimal.valueOf(22L));
        emAnalyticsSearchParam2.setParamAttributes("paramAttributes1");
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));
        emAnalyticsSearchParam2.setParamType(null);
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));

        emAnalyticsSearchParam2.setParamValueClob("paramValueClob2");
        emAnalyticsSearchParam2.setParamType(BigDecimal.valueOf(11L));
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));
        emAnalyticsSearchParam2.setParamValueClob(null);
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));

        emAnalyticsSearchParam2.setParamValueStr("paramValueStr2");
        emAnalyticsSearchParam2.setParamValueClob("paramValueClob1");
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));
        emAnalyticsSearchParam2.setParamValueStr(null);
        Assert.assertFalse(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));

        emAnalyticsSearchParam2.setName("name1");
        emAnalyticsSearchParam2.setSearchId(111L);
        emAnalyticsSearchParam2.setParamAttributes("paramAttributes1");
        emAnalyticsSearchParam2.setParamType(BigDecimal.valueOf(11L));
        emAnalyticsSearchParam2.setParamValueClob("paramValueClob1");
        emAnalyticsSearchParam2.setParamValueStr("paramValueStr1");
        Assert.assertTrue(emAnalyticsSearchParam2.equals(emAnalyticsSearchParam));
    }

    @Test (groups = {"s1"})
    public void testGetEmAnalyticsSearch() throws Exception {
        EmAnalyticsSearch emAnalyticsSearch = new EmAnalyticsSearch();
        emAnalyticsSearchParam.setEmAnalyticsSearch(emAnalyticsSearch);
        Assert.assertEquals(emAnalyticsSearch,emAnalyticsSearchParam.getEmAnalyticsSearch());
    }

    @Test (groups = {"s1"})
    public void testGetName() throws Exception {
        String name = "namexx";
        emAnalyticsSearchParam.setName(name);
        Assert.assertEquals(name,emAnalyticsSearchParam.getName());
    }

    @Test (groups = {"s1"})
    public void testGetParamAttributes() throws Exception {
        String paramAttributes = "paramAtributesxx";
        emAnalyticsSearchParam.setParamAttributes(paramAttributes);
        Assert.assertEquals(paramAttributes, emAnalyticsSearchParam.getParamAttributes());
    }

    @Test (groups = {"s1"})
    public void testGetParamType() throws Exception {
        BigDecimal paramType = BigDecimal.valueOf(333L);
        emAnalyticsSearchParam.setParamType(paramType);
        Assert.assertEquals(paramType,emAnalyticsSearchParam.getParamType());
    }

    @Test (groups = {"s1"})
    public void testGetParamValueClob() throws Exception {
        String paramValueClob = "paramValueClobxx";
        emAnalyticsSearchParam.setParamValueClob(paramValueClob);
        Assert.assertEquals(paramValueClob,emAnalyticsSearchParam.getParamValueClob());
    }

    @Test (groups = {"s1"})
    public void testGetParamValueStr() throws Exception {
        String paramValueStr = "paramValueStrxx";
        emAnalyticsSearchParam.setParamValueStr(paramValueStr);
        Assert.assertEquals(paramValueStr,emAnalyticsSearchParam.getParamValueStr());
    }

    @Test (groups = {"s1"})
    public void testGetSearchId() throws Exception {
        long searchId = 333L;
        emAnalyticsSearchParam.setSearchId(searchId);
        Assert.assertEquals(searchId,emAnalyticsSearchParam.getSearchId());
    }

    @Test (groups = {"s1"})
    public void testHashCode() throws Exception {
        Assert.assertEquals(-454604205,emAnalyticsSearchParam.hashCode());
    }
}