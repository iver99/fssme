package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups={"s1"})
public class DomainEntityTest {
    private DomainEntity domainEntity = new DomainEntity();
    @Test
    public void testDomainEntity()  {
        domainEntity.setCanonicalUrl("url");
        domainEntity.setUuid("id");
        domainEntity.setDomainName("name");
        domainEntity.setKeys(new ArrayList<DomainEntity.DomainKeyEntity>());
        Assert.assertNotNull(domainEntity.getDomainName());
        Assert.assertNotNull(domainEntity.getUuid());
        Assert.assertNotNull(domainEntity.getKeys());
        Assert.assertNotNull(domainEntity.getCanonicalUrl());
    }

    @Test
    public void testGetDomainName()  {
        DomainEntity.DomainKeyEntity domainKeyEntity = new DomainEntity.DomainKeyEntity();
        domainKeyEntity.setName("name");
        Assert.assertNotNull(domainKeyEntity.getName());

    }


}