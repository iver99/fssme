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
    public void testGetCanonicalUrl() throws Exception {
        appMappingEntity.setCanonicalUrl("url");
        Assert.assertEquals("url",appMappingEntity.getCanonicalUrl());
    }

    @Test
    public void testGetDomainName() throws Exception {
        appMappingEntity.setDomainName("domainname");
        Assert.assertNotNull(appMappingEntity.getDomainName());
    }

    @Test
    public void testGetDomainUuid() throws Exception {
        appMappingEntity.setDomainUuid("id");
        Assert.assertNotNull(appMappingEntity.getDomainUuid());
    }

    @Test
    public void testGetHash() throws Exception {
        appMappingEntity.setHash(10L);
        Assert.assertNotNull(appMappingEntity.getHash());
    }

    @Test
    public void testGetKeys() throws Exception {
        appMappingEntity.setKeys(new ArrayList<AppMappingEntity.AppMappingKey>());
        Assert.assertNotNull(appMappingEntity.getKeys());
    }

    @Test
    public void testGetUuid() throws Exception {
        appMappingEntity.setUuid("id");
        Assert.assertNotNull(appMappingEntity.getUuid());
    }

    @Test
    public void testGetValues() throws Exception {
        appMappingEntity.setValues(new ArrayList<AppMappingEntity.AppMappingValue>());
        Assert.assertNotNull(appMappingEntity.getValues());
    }

    @Test
    public void testSetCanonicalUrl() throws Exception {
        appMappingEntity.setCanonicalUrl("url");
        Assert.assertNotNull(appMappingEntity.getCanonicalUrl());
    }

    @Test
    public void testAppMappingValue() throws Exception {
        AppMappingEntity.AppMappingValue appMappingValue = new AppMappingEntity.AppMappingValue();
        appMappingValue.setOpcTenantId("");
        appMappingValue.setApplicationNames("");
        Assert.assertNotNull(appMappingValue.getApplicationNames());
        Assert.assertNotNull(appMappingValue.getOpcTenantId());
    }

    @Test
    public void testAppMappingkey() throws Exception {
        AppMappingEntity.AppMappingKey appMappingKey = new AppMappingEntity.AppMappingKey();
        appMappingKey.setName("");
        appMappingKey.setValue("");
        Assert.assertNotNull(appMappingKey.getName());
        Assert.assertNotNull(appMappingKey.getValue());

    }


}