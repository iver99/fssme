package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups = {"s1"})
public class DomainsEntityTest {
    private DomainsEntity domainEntity = new DomainsEntity();
    @Test
    public void testDomainsENtity() throws Exception {
        domainEntity.setItems(new ArrayList<DomainEntity>());
        domainEntity.setCount(1);
        domainEntity.setTotal(1);

        Assert.assertNotNull(domainEntity.getCount());
        Assert.assertNotNull(domainEntity.getItems());
        Assert.assertNotNull(domainEntity.getTotal());

    }
}