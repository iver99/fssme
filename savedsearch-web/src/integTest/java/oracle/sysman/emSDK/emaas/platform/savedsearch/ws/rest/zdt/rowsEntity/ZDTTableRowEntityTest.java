package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class ZDTTableRowEntityTest {
	ZDTTableRowEntity row = new ZDTTableRowEntity();
	
	public void testEquals() {
		Assert.assertTrue(row.equals(row));
		Assert.assertFalse(row.equals(null));
		Assert.assertFalse(row.equals(new String()));
		
		ZDTTableRowEntity other = new ZDTTableRowEntity();
		other.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
		row.setSavedSearchCategory(null);
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchCategory(new ArrayList<SavedSearchCategoryRowEntity>());
		row.getSavedSearchCategory().add(new SavedSearchCategoryRowEntity());
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchCategory(other.getSavedSearchCategory());
		
		other.setSavedSearchCategoryParams(new ArrayList<SavedSearchCategoryParamRowEntity>());
		row.setSavedSearchCategoryParams(null);
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchCategoryParams(new ArrayList<SavedSearchCategoryParamRowEntity>());
		row.getSavedSearchCategoryParams().add(new SavedSearchCategoryParamRowEntity());
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchCategoryParams(other.getSavedSearchCategoryParams());
		
		other.setSavedSearchFoldersy(new ArrayList<SavedSearchFolderRowEntity>());
		row.setSavedSearchFoldersy(null);
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchFoldersy(new ArrayList<SavedSearchFolderRowEntity>());
		row.getSavedSearchFoldersy().add(new SavedSearchFolderRowEntity());
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchFoldersy(other.getSavedSearchFoldersy());
		
		other.setSavedSearchSearch(new ArrayList<SavedSearchSearchRowEntity>());
		row.setSavedSearchSearch(null);
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchSearch(new ArrayList<SavedSearchSearchRowEntity>());
		row.getSavedSearchSearch().add(new SavedSearchSearchRowEntity());
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchSearch(other.getSavedSearchSearch());
		
		other.setSavedSearchSearchParams(new ArrayList<SavedSearchSearchParamRowEntity>());
		row.setSavedSearchSearchParams(null);
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchSearchParams(new ArrayList<SavedSearchSearchParamRowEntity>());
		row.getSavedSearchSearchParams().add(new SavedSearchSearchParamRowEntity());
		Assert.assertFalse(row.equals(other));
		row.setSavedSearchSearchParams(other.getSavedSearchSearchParams());
		
		Assert.assertTrue(row.equals(other));
	}
	
	public void testHashCode() {
		Assert.assertNotNull(row.hashCode());
	}
	
	public void testToString() {
		Assert.assertNotNull(row.toString());
	}
}
