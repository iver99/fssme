package oracle.sysman.emaas.platform.savedsearch.wls.management;

import mockit.Expectations;
import mockit.Mocked;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @author qianqi
 * @since 16-2-24.
 */
@Test (groups = {"s2"})
public class AppLoggingManageMXBeanTest {
    AppLoggingManageMXBean appLoggingManageMXBean;

    @Test
    public void testGetLogLevels_null() throws Exception {
        appLoggingManageMXBean = new AppLoggingManageMXBean();
        appLoggingManageMXBean.getLogLevels();
    }

    @Test
    public void testGetLogLevels(@Mocked LogManager logManager,@Mocked final LoggerContext loggerContext, @Mocked final Configuration cfg) throws Exception {



        new Expectations() {

            {
                final HashMap<String, LoggerConfig> myLoggers = new HashMap<>();
                myLoggers.put("xx", new LoggerConfig());
                myLoggers.put("yy", new LoggerConfig());
                LogManager.getContext(false);
                result = loggerContext;
                loggerContext.getConfiguration();
                result = cfg;
                cfg.getLoggers();
                result = myLoggers;

            }
        };

        appLoggingManageMXBean = new AppLoggingManageMXBean();
        appLoggingManageMXBean.getLogLevels();
    }

    @Test
    public void testSetLogLevel() throws Exception {
        appLoggingManageMXBean = new AppLoggingManageMXBean();
        appLoggingManageMXBean.setLogLevel("logger","level");
    }

    @Test
    public void testSetLogLevel_lcEqualsNull(@Mocked LogManager logManager,@Mocked final LoggerContext loggerContext, @Mocked final Configuration cfg) throws Exception {
        new Expectations(){
            {
                LogManager.getContext(false);
                result = loggerContext;
                loggerContext.getConfiguration();
                result = cfg;
                cfg.getLoggerConfig(anyString);
                result = null;
            }
        };
        appLoggingManageMXBean = new AppLoggingManageMXBean();
        appLoggingManageMXBean.setLogLevel("logger","level");
    }

    @Test
    public void testSetLogLevel_Debug() throws Exception {
        appLoggingManageMXBean = new AppLoggingManageMXBean();
        appLoggingManageMXBean.setLogLevel("logger","DEBUG");
        appLoggingManageMXBean.setLogLevel("logger","INFO");
        appLoggingManageMXBean.setLogLevel("logger","WARN");
        appLoggingManageMXBean.setLogLevel("logger","ERROR");
        appLoggingManageMXBean.setLogLevel("logger","FATAL");
    }
}