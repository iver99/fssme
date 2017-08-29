package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.ws.test;

import oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities.ZDTComparatorStatusRowEntity;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ZDTComparatorStatusRowEntityTest {
	
	@Test
	public void testZdtComparatorRowsEntityGetterSetter() {
		ZDTComparatorStatusRowEntity entity = new ZDTComparatorStatusRowEntity( "comparisonDate", 
				"comparisonType",
				"nextComparisonDate", 
				"0.99", 
				"comparisonResult");
		
		Assert.assertEquals(entity.getComparisonDate(), "comparisonDate");
		Assert.assertEquals(entity.getComparisonType(), "comparisonType");
		Assert.assertEquals(entity.getNextComparisonDate(), "nextComparisonDate");
		//entity.getDivergencePercentage();
		Assert.assertEquals(entity.getDivergencePercentage(), "0.99");
		
		entity.setComparisonDate("");
		entity.setComparisonType("");
		entity.setDivergencePercentage("0.0");
		entity.setNextComparisonDate("");
	}

}
