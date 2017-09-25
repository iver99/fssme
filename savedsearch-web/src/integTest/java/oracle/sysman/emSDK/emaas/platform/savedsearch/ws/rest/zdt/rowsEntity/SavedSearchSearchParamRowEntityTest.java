package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import org.testng.annotations.Test;

/**
 * Created by chehao on 9/23/2017 16:32.
 */
@Test(groups = { "s1" })
public class SavedSearchSearchParamRowEntityTest {
    public void testSavedSearchSearchParamRowEntity(){
        SavedSearchSearchParamRowEntity s = new SavedSearchSearchParamRowEntity();
        s.equals(s);
        s.equals(null);
        s.equals("");

        SavedSearchSearchParamRowEntity s2 = new SavedSearchSearchParamRowEntity();
        s.equals(s2);
        s2.equals(s);

        s2.setSearchId("");
        s2.setCreationDate("");
        s2.setDeleted(0);
        s2.setLastModificationDate("");
        s2.setName("");
        s2.setParamAttributes("");
        s2.setParamType(0L);
        s2.setParamValueClob("");
        s2.setParamValueStr("");
        s2.setTenantId(0L);
        s.equals(s2);
        s2.equals(s);

        s.setSearchId("");
        s.setCreationDate("");
        s.setDeleted(0);
        s.setLastModificationDate("");
        s.setName("");
        s.setParamAttributes("");
        s.setParamType(0L);
        s.setParamValueClob("");
        s.setParamValueStr("");
        s.setTenantId(0L);
        s.equals(s2);
        s2.equals(s);

        s2.setSearchId("");
        s2.setCreationDate("");
        s2.setDeleted(0);
        s2.setLastModificationDate("");
        s2.setName("");
        s2.setParamAttributes("");
        s2.setParamType(0L);
        s2.setParamValueClob("");
        s2.setParamValueStr("");
        s2.setTenantId(1L);
        s.equals(s2);
        s2.equals(s);

    }
}
