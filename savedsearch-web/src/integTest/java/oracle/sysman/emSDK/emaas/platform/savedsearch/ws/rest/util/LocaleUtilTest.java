package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import mockit.Expectations;
import mockit.Mock;
import mockit.Mocked;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;

import static org.testng.Assert.*;

@Test(groups = {"s2"})
public class LocaleUtilTest {
    @Mocked
    HttpServletRequest httpServletRequest;
    @Mocked
    Locale locale;
    @Mocked
    LogManager logManager;
    @Mocked
    Logger logger;
    @Test
    public void testGetLangAttr() throws Exception {
        new Expectations(){
            {
                LogManager.getLogger(LocaleUtil.class);
                result = logger;
            }
        };
        LocaleUtil.getLangAttr(null);
        new Expectations(){
            {
                httpServletRequest.getLocale();
                result = locale;
                locale.toLanguageTag();
                result = "En";
            }
        };
        LocaleUtil.getLangAttr(httpServletRequest);
    }

}