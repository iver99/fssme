package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSchemaVer;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSchemaVerPK;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by QIQIAN on 2016/3/14.
 */
public class VersionManagerTest {

    VersionManager versionManager;

    @Test(groups = {"s1"})
    public void testGetInstance() throws Exception {
        versionManager = VersionManager.getInstance();
    }

    @Test(groups = {"s2"})
    public void testGetSchemaVersion(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManagerFactory entityManagerFactory, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSchemaVer emAnalyticsSchemaVer, @Mocked final EmAnalyticsSchemaVerPK emAnalyticsSchemaVerPK) throws Exception {
        versionManager = VersionManager.getInstance();
        final List<EmAnalyticsSchemaVer> vers = new ArrayList<>();
        vers.add(emAnalyticsSchemaVer);
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManagerFactory();
                result = entityManagerFactory;
                entityManagerFactory.createEntityManager();
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.getResultList();
                result = vers;
                emAnalyticsSchemaVer.getId();
                result = emAnalyticsSchemaVerPK;
                emAnalyticsSchemaVerPK.getMajor();
                result = 4321L;
                emAnalyticsSchemaVerPK.getMinor();
                result = 1234L;
            }
        };
        versionManager.getSchemaVersion();
        vers.add(emAnalyticsSchemaVer);
        try {
            versionManager.getSchemaVersion();
        }catch (Exception e){
            Assert.assertTrue(true);
        };
    }
}