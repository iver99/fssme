package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


public class LocaleUtil {
    private static final Logger LOGGER = LogManager.getLogger(LocaleUtil.class);
    private static final String defaultLocale = "en-US";
    private static final String[] supportedLanguages = new String[]{"en", "fr", "ko", "zh-Hans", "zh-Hant", "zh"};
    public static String getLangAttr(HttpServletRequest httpRequest) {
        LOGGER.debug("Calling LocaleUtil.getLangAttr");
        if (httpRequest == null) {
            return null;
        }
        Locale alh = httpRequest.getLocale();
        String locale = getSupportedLocale(alh.toLanguageTag());
        return "lang=\"" + locale + "\"";
    }


    private static String getSupportedLocale(String alh)
    {
        LOGGER.debug("Calling LocaleUtil.getSupportedLocale");
        if (alh != null && !alh.isEmpty()) {
            String locale = alh.split(",")[0].split(";")[0];
            for (String lang : supportedLanguages) {
                if (locale.matches("^" + lang + "(-[A-Za-z]{2})?$")) {
                    return locale;
                }
            }
        }
        return defaultLocale;
    }
}
