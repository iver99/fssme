package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xiadai on 2016/8/23.
 */
@Test(groups = {"s1"})
public class CategoryImplTest {
    private CategoryImpl category = new CategoryImpl();
    @Test
    public void testGetCreatedOn(){
        Date date  = new Date();
        category.setCreatedOn(date);
        Assert.assertEquals(date, category.getCreatedOn());
    }

    @Test
    public void testGetDefaultFolderId(){
        category.setDefaultFolderId(BigInteger.ONE);
        category.getDefaultFolderId();
    }

    @Test
    public void testGetDescription(){
        category.setDescription("des");
        category.getDescription();
    }

    @Test
    public void testGetId(){
        category.setId(BigInteger.ONE);
        category.getId();
    }

    @Test
    public void testGetName(){
        category.setName("name");
        category.getName();
    }

    @Test
    public void testGetOwner(){
        category.setOwner("Oracle");
        category.getOwner();
    }

    @Test
    public void testGetParameters(){
        List<Parameter> parameters = new ArrayList<>();
        category.setParameters(parameters);
        category.getParameters();
    }

    @Test
    public void testGetProviderAssetRoot(){
        category.setProviderAssetRoot("pro_assetroot");
        category.getProviderAssetRoot();
    }

    @Test
    public void testGetProviderDiscovery(){
        category.setProviderDiscovery("pro_discovery");
        category.getProviderDiscovery();
    }

    @Test
    public void testGetProviderName(){
        category.setProviderName("pro_name");
        category.getProviderName();
    }

    @Test
    public void testGetProviderVersion(){
        category.setProviderVersion("pro_version");
        category.getProviderVersion();
    }
}