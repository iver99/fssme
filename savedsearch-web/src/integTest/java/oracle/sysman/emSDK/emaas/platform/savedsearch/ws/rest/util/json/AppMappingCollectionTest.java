package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.json;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/26/2016.
 */
@Test(groups={"s1"})
public class AppMappingCollectionTest {
    private AppMappingCollection appMappingCollection = new AppMappingCollection();
    @Test
    public void testGetCount() throws Exception {
        appMappingCollection.setCount(1);
        Assert.assertNotNull(appMappingCollection.getCount());
    }

    @Test
    public void testGetItems() throws Exception {
        appMappingCollection.setTotal(1);
        Assert.assertNotNull(appMappingCollection.getTotal());
    }

    @Test
    public void testGetTotal() throws Exception {
        List<AppMappingEntity> list = new ArrayList<AppMappingEntity>();
        appMappingCollection.setItems(list);
        Assert.assertNotNull(appMappingCollection.getItems());

    }

    @Test
    public void testSetCount() throws Exception {

    }

    @Test
    public void testSetItems() throws Exception {

    }

    @Test
    public void testSetTotal() throws Exception {

    }
}