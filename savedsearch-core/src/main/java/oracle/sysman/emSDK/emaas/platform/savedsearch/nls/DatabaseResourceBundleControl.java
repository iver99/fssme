package oracle.sysman.emSDK.emaas.platform.savedsearch.nls;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Charsets;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by xiadai on 2017/6/15.
 */
public class DatabaseResourceBundleControl extends ResourceBundle.Control {
    public static final List<String> FORMAT_DATABASE = Collections.unmodifiableList(Arrays.asList("database.bundle"));

    private static final Logger LOGGER = LogManager.getLogger(DatabaseResourceBundleControl.class);

    @Override
    public List<String> getFormats(String baseName) {
        if (baseName == null) {
            throw new NullPointerException();
        }
        return FORMAT_DATABASE;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {
        if (baseName == null || locale == null || format == null || loader == null) {
            throw new NullPointerException();
        }
        ResourceBundle bundle = null;
        if (format.equals("database.bundle")) {
            InputStream stream = null;
            EmsResourceBundle rb = findResourceBundle(baseName, locale);
            if(rb != null) {
                stream = new ByteArrayInputStream(rb.getPropertiesFile().getBytes(Charsets.UTF_8));
                if(stream != null) {
                    bundle = new DatabaseResourceBundle(stream);
                    stream.close();
                }
            }
        }
        return bundle;
    }

    @Override
    public long getTimeToLive(String baseName, Locale locale) {
        if (baseName == null || locale == null) {
            throw new NullPointerException();
        }
        // 10 minutes
        return 10*60*1000;
    }

    @Override
    public boolean needsReload(String baseName, Locale locale, String format, ClassLoader loader, ResourceBundle bundle, long loadTime) {
        if (!FORMAT_DATABASE.contains(format)) {
            return super.needsReload(baseName, locale, format, loader, bundle, loadTime);
        }

        Date loadDate = getUTCTime(loadTime);
        Date lastModifyTime = findLastMofifyTime(baseName, locale);
        if(lastModifyTime != null && lastModifyTime.after(loadDate)) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private EmsResourceBundle findResourceBundle(String baseName, Locale locale) {
        EntityManager em = null;
        try {
            em = PersistenceManager.getInstance().getEntityManager(new TenantInfo(SearchManagerImpl.DEFAULT_CURRENT_USER, SearchManagerImpl.DEFAULT_TENANT_ID));
            List<EmsResourceBundle> rb = em.createNamedQuery("EmsResourceBundle.loadByLangContry")
                    .setParameter("languageCode", locale.getLanguage().toLowerCase())
                    .setParameter("countryCode", locale.getCountry().toUpperCase())
                    .setParameter("serviceName", baseName)
                    .getResultList();
            if(rb != null && !rb.isEmpty()) {
                return rb.get(0);
            }
        } catch(Exception e) {
            LOGGER.error("Fail to DatabaseResourceBundleControl.findResourceBundle: {}", e.getLocalizedMessage());
        } finally {
            if(em != null) {
                em.close();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Date findLastMofifyTime(String baseName, Locale locale) {
        EntityManager em = null;
        try {
            em = PersistenceManager.getInstance().getEntityManager(new TenantInfo(SearchManagerImpl.DEFAULT_CURRENT_USER, SearchManagerImpl.DEFAULT_TENANT_ID));
            List<Date> lastModifyTime = em.createNamedQuery("EmsResourceBundle.loadModifyTimeByLangContry")
                    .setParameter("languageCode", locale.getLanguage())
                    .setParameter("countryCode", locale.getCountry())
                    .setParameter("serviceName", baseName).getResultList();
            if(lastModifyTime != null && !lastModifyTime.isEmpty()) {
                return lastModifyTime.get(0);
            }
        } catch(Exception e) {
            LOGGER.error("Fail to DatabaseResourceBundleControl.findLastMofifyTime: {}", e.getLocalizedMessage());
        } finally {
            if(em != null) {
                em.close();
            }
        }
        return null;
    }

    private Date getUTCTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        long offset = cal.getTimeZone().getOffset(time);
        Date utcDate = new Date(time - offset);
        return utcDate;
    }
}
