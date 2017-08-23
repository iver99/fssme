package oracle.sysman.emaas.platform.savedsearch.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups={"s1"})
public class EmsResourceBundlePKTest {
    
    @Test
    public void testEmsResourceBundlePK() {
        EmsResourceBundlePK pk = new EmsResourceBundlePK();
        pk.setCountryCode("countryCode");
        Assert.assertEquals(pk.getCountryCode(), "countryCode");
        pk.setLanguageCode("languageCode");
        Assert.assertEquals(pk.getLanguageCode(), "languageCode");
        pk.setServiceName("serviceName");
        Assert.assertEquals(pk.getServiceName(), "serviceName");
    }
    
    @Test
    public void testEquals() {
        EmsResourceBundlePK pk = new EmsResourceBundlePK();
        pk.setCountryCode("countryCode");
        pk.setLanguageCode("languageCode");
        pk.setServiceName("serviceName");
        
        EmsResourceBundlePK same = new EmsResourceBundlePK();
        same.setCountryCode("countryCode");
        same.setLanguageCode("languageCode");
        same.setServiceName("serviceName");
        
        EmsResourceBundlePK dif = new EmsResourceBundlePK();
        dif.setCountryCode("1");
        dif.setLanguageCode("2");
        dif.setServiceName("3");
        
        Assert.assertTrue(pk.equals(same));
        Assert.assertFalse(pk.equals(dif));
    }
}
