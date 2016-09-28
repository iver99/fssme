package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups={"s2"})
public class AppMappingEntityTest {
    private AppMappingEntity appMappingEntity = new AppMappingEntity();
    @Test
    public void testGetCanonicalUrl(){
        appMappingEntity.setCanonicalUrl("url");
        Assert.assertEquals("url",appMappingEntity.getCanonicalUrl());
    }

    @Test
    public void testGetDomainName(){
        appMappingEntity.setDomainName("domainname");
        Assert.assertNotNull(appMappingEntity.getDomainName());
    }

    @Test
    public void testGetDomainUuid(){
        appMappingEntity.setDomainUuid("id");
        Assert.assertNotNull(appMappingEntity.getDomainUuid());
    }

    @Test
    public void testGetHash(){
        appMappingEntity.setHash(10L);
        Assert.assertNotNull(appMappingEntity.getHash());
    }

    @Test
    public void testGetKeys(){
        appMappingEntity.setKeys(new ArrayList<AppMappingEntity.AppMappingKey>());
        Assert.assertNotNull(appMappingEntity.getKeys());
    }

    @Test
    public void testGetUuid(){
        appMappingEntity.setUuid("id");
        Assert.assertNotNull(appMappingEntity.getUuid());
    }

    @Test
    public void testGetValues(){
        appMappingEntity.setValues(new ArrayList<AppMappingEntity.AppMappingValue>());
        Assert.assertNotNull(appMappingEntity.getValues());
    }

    @Test
    public void testSetCanonicalUrl(){
        appMappingEntity.setCanonicalUrl("url");
        Assert.assertNotNull(appMappingEntity.getCanonicalUrl());
    }

    @Test
    public void testAppMappingValue(){
        AppMappingEntity.AppMappingValue appMappingValue = new AppMappingEntity.AppMappingValue();
        appMappingValue.setOpcTenantId("");
        appMappingValue.setApplicationNames("");
        Assert.assertNotNull(appMappingValue.getApplicationNames());
        Assert.assertNotNull(appMappingValue.getOpcTenantId());
    }

    @Test
    public void testAppMappingkey(){
        AppMappingEntity.AppMappingKey appMappingKey = new AppMappingEntity.AppMappingKey();
        appMappingKey.setName("");
        appMappingKey.setValue("");
        Assert.assertNotNull(appMappingKey.getName());
        Assert.assertNotNull(appMappingKey.getValue());

    }


}