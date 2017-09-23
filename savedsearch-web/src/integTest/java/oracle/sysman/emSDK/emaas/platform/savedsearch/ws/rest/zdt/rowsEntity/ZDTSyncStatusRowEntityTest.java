package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import org.testng.annotations.Test;

/**
 * Created by chehao on 9/23/2017 16:19.
 */
@Test(groups = { "s1" })
public class ZDTSyncStatusRowEntityTest {
    public void testZDTSyncStatusRowEntity(){
        ZDTSyncStatusRowEntity z = new ZDTSyncStatusRowEntity(null, null,null,0);
        z.setDivergencePercentage(1);
        z.setLastSyncDateTime(null);
        z.setNextScheduledSyncDateTime(null);
        z.setSyncType(null);
    }
}
