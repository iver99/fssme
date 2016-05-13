package oracle.sysman.emSDK.emaas.platform.savedsearch.exception;

import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-26.
 */
@Test(groups = {"s1"})
public class EmAnalyticsProcessingExceptionTest {
    EmAnalyticsProcessingException emAnalyticsProcessingException;

    @Test
    public void testProcessCategoryPersistantException() throws Exception {
        EmAnalyticsProcessingException.processCategoryPersistantException(new Exception(""),1111,"namexx");
    }

    @Test
    public void testProcessCategoryPersistantException_SEARCH_FK1() throws Exception {
        try {
            Exception ex = new Exception("", new Throwable("ANALYTICS_SEARCH_FK1"));
            EmAnalyticsProcessingException.processCategoryPersistantException(ex, 1111, "namexx");
        }catch (Exception e) {
        }
    }

    @Test
    public void testProcessCategoryPersistantException_CATEGORY_U01() throws Exception {
        try {
            Exception ex = new Exception("", new Throwable("ANALYICS_CATEGORY_U01"));
            EmAnalyticsProcessingException.processCategoryPersistantException(ex, 1111, "namexx");
        }catch (Exception e) {
        }
    }

    @Test
    public void testProcessCategoryPersistantException_CATEGORY_FK1() throws Exception {
        try {
            Exception ex = new Exception("", new Throwable("ANALYTICS_CATEGORY_FK1"));
            EmAnalyticsProcessingException.processCategoryPersistantException(ex, 1111, "namexx");
        }catch (Exception e) {
        }
    }

    @Test
    public void testProcessFolderPersistantException() throws Exception {
        EmAnalyticsProcessingException.processFolderPersistantException(new Exception(""),1111,2222,"namexx");
    }

    @Test
    public void testProcessFolderPersistantException_SEARCH_FK2() throws Exception {
        try {
            Exception ex = new Exception("", new Throwable("ANALYTICS_SEARCH_FK2"));
            EmAnalyticsProcessingException.processFolderPersistantException(ex, 1111, 2222, "namexx");
        }catch (Exception e) {
        }
    }

    @Test
    public void testProcessFolderPersistantException_SEARCH_FK1() throws Exception {
        try {
            Exception ex = new Exception("", new Throwable("ANALYTICS_SEARCH_FK1"));
            EmAnalyticsProcessingException.processFolderPersistantException(ex, 1111, 2222, "namexx");
        }catch (Exception e) {
        }
    }

    @Test
    public void testProcessFolderPersistantException_FOLDERS_FK1() throws Exception {
        try {
            Exception ex = new Exception("", new Throwable("ANALYTICS_FOLDERS_FK1"));
            EmAnalyticsProcessingException.processFolderPersistantException(ex, 1111, 2222, "namexx");
        }catch (Exception e) {
        }
    }

    @Test
    public void testProcessSearchPersistantException_cannotAcquireData() throws Exception {
        try {
            Exception ex = new Exception("", new Throwable("Cannot acquire data source"));
            EmAnalyticsProcessingException.processSearchPersistantException(ex, "namexx");
        }catch (Exception e){

        }
    }

    @Test
    public void testProcessSearchPersistantException() throws Exception {
            Exception ex = new Exception("", new Throwable("no"));
            EmAnalyticsProcessingException.processSearchPersistantException(ex, "namexx");
    }
}