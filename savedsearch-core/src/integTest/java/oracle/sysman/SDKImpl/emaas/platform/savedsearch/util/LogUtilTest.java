package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;

import java.net.URISyntaxException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qianqi
 * @since 16-2-29.
 */
@Test(groups = {"s1"})
public class LogUtilTest {

    LogUtil logUtil= new LogUtil();

	@BeforeMethod
	public void beforeMethod() throws URISyntaxException
	{
		URL url = LogUtilTest.class.getResource("/log4j2_ssf.xml");
		Configurator.initialize("root", LogUtilTest.class.getClassLoader(), url.toURI());
	}

    @Test
    public void testClearInteractionLogContext(){
        LogUtil.clearInteractionLogContext();
    }

    @Test
    public void testGetInteractionLogger(){
        Assert.assertTrue(LogUtil.getInteractionLogger() instanceof Logger);
    }

    @Test
    public void testGetLoggerUpdateTime(){
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        Configuration cfg = loggerContext.getConfiguration();
        LogUtil.setLoggerUpdateTime(cfg,new LoggerConfig(),null);
        LogUtil.getLoggerUpdateTime(cfg,new LoggerConfig());
    }

    @Test(groups = {"s2"})
    public void testInitializeLoggersUpdateTime(@Injectable final LoggerConfig loggerConfig, @Mocked final LoggerContext loggerContext, @Mocked LogManager logManager, @Mocked final Configuration configuration){
        final Map<String, LoggerConfig> loggers = new HashMap<>();
        loggers.put("a",loggerConfig);
        loggers.put("b",loggerConfig);
        loggers.put("c",loggerConfig);
        new Expectations(){
            {
                LogManager.getContext(false);
                result = loggerContext;
                loggerContext.getConfiguration();
                result = configuration;
                configuration.getLoggers();
                result = loggers;
                configuration.getProperties();
                result = new HashMap<>();
                loggerConfig.getName();
                result = "namexx";
            }
        };
        LogUtil.initializeLoggersUpdateTime();
    }

    @Test
    public void testSetInteractionLogThreadContext(){
        LogUtil.setInteractionLogThreadContext(null,"serviceInvoked",null);

        LogUtil.setInteractionLogThreadContext("tenantId",null,null);

        LogUtil.setInteractionLogThreadContext("tenantId","serviceInvoked",null);
    }

    @Test
    public void testInteractionLogContext(){
        LogUtil.InteractionLogContext interactionLogContext = new LogUtil.InteractionLogContext("tenantId","serviceInvoked","direction");
        interactionLogContext.setDirection("direction");
        Assert.assertEquals(interactionLogContext.getDirection(),"direction");
        interactionLogContext.setServiceInvoked("serviceInvoked");
        Assert.assertEquals(interactionLogContext.getServiceInvoked(),"serviceInvoked");
        interactionLogContext.setTenantId("tenantId");
        Assert.assertEquals(interactionLogContext.getTenantId(),"tenantId");
    }

    @Test
    public void testInteractionLogDirection(){
        LogUtil.InteractionLogDirection.fromValue("aa");
        Assert.assertEquals(LogUtil.InteractionLogDirection.fromValue("IN"), LogUtil.InteractionLogDirection.IN);
    }
}
