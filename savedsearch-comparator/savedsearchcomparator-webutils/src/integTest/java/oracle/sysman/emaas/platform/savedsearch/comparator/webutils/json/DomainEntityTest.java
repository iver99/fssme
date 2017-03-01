package oracle.sysman.emaas.platform.savedsearch.comparator.webutils.json;

import org.testng.annotations.Test;

import java.util.ArrayList;


@Test(groups = {"s2"})
public class DomainEntityTest {
    @Test
    public void testDomainEntity(){
        DomainEntity de=new DomainEntity();
        DomainEntity.DomainKeyEntity obj =new DomainEntity.DomainKeyEntity();

        de.getCanonicalUrl();
        de.getUuid();
        de.getKeys();
        de.getDomainName();

        de.setDomainName("name");
        de.setCanonicalUrl("url");
        de.setKeys(new ArrayList<DomainEntity.DomainKeyEntity>());
        de.setUuid("uuid");

        obj.getName();
        obj.setName("name");
    }
}
