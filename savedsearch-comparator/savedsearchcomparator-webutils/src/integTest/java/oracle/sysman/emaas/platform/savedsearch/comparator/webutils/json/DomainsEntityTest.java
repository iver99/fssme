package oracle.sysman.emaas.platform.savedsearch.comparator.webutils.json;

import org.testng.annotations.Test;

import java.util.ArrayList;


@Test(groups = {"s2"})
public class DomainsEntityTest {

    @Test
    public void testDomainsEntity(){
        DomainsEntity dse=new DomainsEntity();
        dse.getTotal();
        dse.getItems();
        dse.getCount();
        dse.setCount(1);
        dse.setItems(new ArrayList<DomainEntity>());
        dse.setTotal(1);
    }
}
