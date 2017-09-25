package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.countEntity.ZDTCountEntity;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class ZDTCountEntityTest {
	
	@Test
	public void testCountEntity(){
		ZDTCountEntity zdtCountEntity = new ZDTCountEntity(1L, 2L, 3L);
		Assert.assertEquals((long)zdtCountEntity.getCountOfFolders(), 1L);
		Assert.assertEquals((long)zdtCountEntity.getCountOfSearch(), 2L);
		zdtCountEntity.setCountOfFolders(5L);
		zdtCountEntity.setCountOfSearch(6L);
		Assert.assertNotEquals((long)zdtCountEntity.getCountOfFolders(), 2L);
		Assert.assertNotEquals((long)zdtCountEntity.getCountOfSearch(), 3L);
		zdtCountEntity.setCountOfSearchParams(0L);
		zdtCountEntity.getCountOfSearchParams();
	}

}
