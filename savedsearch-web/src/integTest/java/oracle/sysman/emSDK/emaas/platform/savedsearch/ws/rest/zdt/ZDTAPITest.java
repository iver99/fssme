package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.DataManager;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void testGetAllTableData() throws Exception {
        final List<Map<String, Object>> list = new ArrayList<>();
        new Expectations(){
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.getSearchTableData();
                result = list;
            }
        };
        zdtapi.getAllTableData();
    }

    @Test
    public void testGetAllTableDataException() throws Exception {
        final List<Map<String, Object>> list = new ArrayList<>();
        new Expectations(){
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.getSearchTableData();
                result = new JSONException(throwable);
            }
        };
        zdtapi.getAllTableData();
    }

    @Test
    public void testGetEntitiesCoung() throws Exception {
        new Expectations(){
            {
                DataManager.getInstance();
                result = dataManager;
                dataManager.getAllCategoryCount();
                result = 1;
                dataManager.getAllFolderCount();
                result = 1;
                dataManager.getAllSearchCount();
                result = 1;
            }
        };
        zdtapi.getEntitiesCount();
    }

    @Test
    public void testSync() throws Exception {
        zdtapi.sync(new JSONObject());
    }

}