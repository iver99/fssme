package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json;

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
    public void testGetCount(){
        appMappingCollection.setCount(1);
        Assert.assertNotNull(appMappingCollection.getCount());
    }

    @Test
    public void testGetItems(){
        appMappingCollection.setTotal(1);
        Assert.assertNotNull(appMappingCollection.getTotal());
    }

    @Test
    public void testGetTotal(){
        List<AppMappingEntity> list = new ArrayList<AppMappingEntity>();
        appMappingCollection.setItems(list);
        Assert.assertNotNull(appMappingCollection.getItems());

    }

    @Test
    public void testSetCount(){

    }

    @Test
    public void testSetItems(){

    }

    @Test
    public void testSetTotal(){

    }
}