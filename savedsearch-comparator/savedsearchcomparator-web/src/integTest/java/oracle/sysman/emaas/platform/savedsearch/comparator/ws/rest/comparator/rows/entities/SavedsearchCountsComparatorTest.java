package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.CountsEntity;
import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts.SavedsearchCountsComparator;

import org.testng.annotations.Test;

public class SavedsearchCountsComparatorTest {
	
	@Test
	public void testcompareInstancesCounts() {
		CountsEntity entity1 = new CountsEntity();
		entity1.setCountOfCategory(11L);
		entity1.setCountOfFolders(12L);
		entity1.setCountOfSearch(13L);
		
		CountsEntity entity2 = new CountsEntity();
		entity2.setCountOfCategory(15L);
		entity2.setCountOfFolders(16L);
		entity2.setCountOfSearch(17L);
		
		SavedsearchCountsComparator comparator = new SavedsearchCountsComparator();
		comparator.compareInstancesCounts("key1", null, entity1,
				"key2", null, entity2);
	}

}
