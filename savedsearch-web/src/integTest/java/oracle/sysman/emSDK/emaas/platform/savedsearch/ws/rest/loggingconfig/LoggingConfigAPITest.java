package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.loggingconfig;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xidai on 2/17/2016.
 */
@Test(groups={"s2"})
public class LoggingConfigAPITest {
    private LoggingConfigAPI loggingConfigAPI  = new LoggingConfigAPI();
    @Test
    public void testChangeRootLoggerLevel()  {
        JSONObject inputJson = new JSONObject();
        Assert.assertNotNull(loggingConfigAPI.changeRootLoggerLevel(inputJson));
    }


    @Mocked
    org.apache.logging.log4j.LogManager logManager;
    @Mocked
    org.apache.logging.log4j.core.LoggerContext loggerContext;
    @Mocked
    org.apache.logging.log4j.core.config.Configuration cfg;
    @Mocked
    org.apache.logging.log4j.core.config.LoggerConfig loggerConfig;
    @Test
    public void testChangeSpecificLoggerLevel() throws JSONException {
        JSONObject inputJson = new JSONObject();
        final UpdatedLoggerLevel updatedLoggerLevel = new UpdatedLoggerLevel();
        updatedLoggerLevel.setLevel("Level1");
        inputJson.put("loggerName","name");
        inputJson.put("level","DEBUG");
        final Map<String, LoggerConfig> loggers = new HashMap<>();
        loggers.put("logger", loggerConfig);
        new Expectations(){
            {
                loggerContext.getConfiguration();
                result = cfg;
                cfg.getLoggers();
                result = loggers;
                loggerConfig.getName();
                result = "loggerName";
            }
        };
        Assert.assertNotNull(loggingConfigAPI.changeSpecificLoggerLevel("loggerName",inputJson));
    }
    @Test
    public void testChangeSpecificLoggerLevel2nd() throws JSONException {
        JSONObject inputJson = new JSONObject();
        final UpdatedLoggerLevel updatedLoggerLevel = new UpdatedLoggerLevel();
        updatedLoggerLevel.setLevel("Level1");
        inputJson.put("loggerName","name");
        inputJson.put("level","");
        Assert.assertNotNull(loggingConfigAPI.changeSpecificLoggerLevel("loggerName",inputJson));
    }

    @Test
    public void testGetAllLoggerLevels() throws JSONException {
        final Map<String, LoggerConfig> loggers = new HashMap<>();
        loggers.put("logger", loggerConfig);
        new Expectations(){
            {
                loggerContext.getConfiguration();
                result = cfg;
                cfg.getLoggers();
                result = loggers;
                loggerConfig.getName();
                result = "loggerName";
            }
        };
        TenantContext.setContext(new TenantInfo("user",11L));
        Assert.assertNotNull(loggingConfigAPI.getAllLoggerLevels());
    }

}