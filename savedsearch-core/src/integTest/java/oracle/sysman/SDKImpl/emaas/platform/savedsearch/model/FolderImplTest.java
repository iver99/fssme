package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigInteger;
import java.util.Date;

import org.testng.annotations.Test;

/**
 * Created by xiadai on 2016/8/23.
 */
@Test(groups = {"s1"})
public class FolderImplTest {
    private FolderImpl folder = new FolderImpl();
    private Date date  = new Date();
    @Test
    public void testGetCreatedOn() throws Exception {

        folder.setCreationDate(date);
        folder.getCreatedOn();
    }

    @Test
    public void testGetDescription() throws Exception {
        folder.setDescription("des");
        folder.getDescription();
    }

    @Test
    public void testGetId() throws Exception {
        folder.setId(BigInteger.ONE);
        folder.getId();
    }

    @Test
    public void testGetLastModifiedBy() throws Exception {
        folder.setLastModifiedBy("Oracle");
        folder.getLastModifiedBy();
    }

    @Test
    public void testGetLastModifiedOn() throws Exception {
        folder.setLastModificationDate(date);
        folder.getLastModifiedOn();
    }

    @Test
    public void testGetOwner() throws Exception {
        folder.setOwner("Oracle");
        folder.getOwner();
    }

    @Test
    public void testGetParentId() throws Exception {
        folder.setParentId(BigInteger.ONE);
        folder.getParentId();
    }

    @Test
    public void testIsSystemFolder() throws Exception {
        folder.setSystemFolder(false);
        folder.isSystemFolder();
    }

    @Test
    public void testIsUiHidden() throws Exception {
        folder.setUiHidden(false);
        folder.isUiHidden();
    }

}