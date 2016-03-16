package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.loggingconfig;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/17/2016.
 */
@Test(groups={"s2"})
public class LoggingConfigAPITest {
    private LoggingConfigAPI loggingConfigAPI  = new LoggingConfigAPI();
    @Test
    public void testChangeRootLoggerLevel() throws Exception {
        JSONObject inputJson = new JSONObject();
        Assert.assertNotNull(loggingConfigAPI.changeRootLoggerLevel(inputJson));
    }

    @Test
    public void testChangeSpecificLoggerLevel() throws Exception {
        JSONObject inputJson = new JSONObject();
        final UpdatedLoggerLevel updatedLoggerLevel = new UpdatedLoggerLevel();
        updatedLoggerLevel.setLevel("Level1");
        inputJson.put("loggerName","name");
        inputJson.put("level","DEBUG");

        Assert.assertNotNull(loggingConfigAPI.changeSpecificLoggerLevel("loggerName",inputJson));
    }
    @Test
    public void testChangeSpecificLoggerLevel2nd() throws Exception {
        JSONObject inputJson = new JSONObject();
        final UpdatedLoggerLevel updatedLoggerLevel = new UpdatedLoggerLevel();
        updatedLoggerLevel.setLevel("Level1");
        inputJson.put("loggerName","name");
        inputJson.put("level","");
        Assert.assertNotNull(loggingConfigAPI.changeSpecificLoggerLevel("loggerName",inputJson));
    }

    @Test
    public void testGetAllLoggerLevels() throws Exception {
        TenantContext.setContext(new TenantInfo("user",11L));
        Assert.assertNotNull(loggingConfigAPI.getAllLoggerLevels());
    }

}