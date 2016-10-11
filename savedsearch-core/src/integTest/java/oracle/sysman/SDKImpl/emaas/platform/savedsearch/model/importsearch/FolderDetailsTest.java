package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-29.
 */
@Test(groups = {"s1"})
public class FolderDetailsTest {

    FolderDetails folderDetails;
    @Test
    public void testGetId() {
        folderDetails = new FolderDetails();
        folderDetails.setId(BigInteger.ONE);
        Assert.assertEquals(folderDetails.getId(),BigInteger.ONE);
    }

    @Test
    public void testGetName() {
        folderDetails = new FolderDetails();
        String name = "namexx";
        folderDetails.setName(name);
        Assert.assertEquals(folderDetails.getName(),name);
    }

    @Test
    public void testGetDescription() {
        folderDetails = new FolderDetails();
        String description = "descriptionxx";
        folderDetails.setDescription(description);
        Assert.assertEquals(folderDetails.getDescription(),description);
    }

    @Test
    public void testGetParentId() {
        folderDetails = new FolderDetails();
        folderDetails.setParentId(BigInteger.ONE);
        Assert.assertEquals(folderDetails.getParentId(),BigInteger.ONE);
    }

    @Test
    public void testIsUiHidden() {
        folderDetails = new FolderDetails();
        folderDetails.setUiHidden(true);
        Assert.assertTrue(folderDetails.isUiHidden());
    }

    @Test
    public void testGetFolder() {
        folderDetails = new FolderDetails();
        folderDetails.getFolder();
    }
}