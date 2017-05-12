package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.DataManager;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import static org.testng.Assert.*;

/**
 * Created by pingwu 
 */

@Test(groups = { "s2" })
public class ZDTAPITest {
    private ZDTAPI zdtapi = new ZDTAPI();
    @Mocked
    DataManager dataManager;
    @Mocked
    Throwable throwable;
    @Test
    public void testGetAllTableData(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws Exception {
        final List<Map<String, Object>> list = new ArrayList<>();
        new Expectations(){
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.getSearchTableData(em);
                result = list;
            }
        };
        zdtapi.getAllTableData();
    }

    @Test
    public void testGetAllTableDataException(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws Exception {
        final List<Map<String, Object>> list = new ArrayList<>();
        new Expectations(){
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.getSearchTableData(em);
                result = new JSONException(throwable);
            }
        };
        zdtapi.getAllTableData();
    }

    @Test
    public void testGetEntitiesCoung(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws Exception {
        new Expectations(){
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.getAllCategoryCount(em);
                result = 1;
                dataManager.getAllFolderCount(em);
                result = 1;
                dataManager.getAllSearchCount(em);
                result = 1;
            }
        };
        zdtapi.getEntitiesCount();
    }

    @Test
    public void testSync(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws Exception {
        zdtapi.sync(new JSONObject());
    }

}