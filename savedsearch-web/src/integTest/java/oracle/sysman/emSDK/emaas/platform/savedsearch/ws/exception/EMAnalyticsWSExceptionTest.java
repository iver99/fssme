package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by xidai on 2/18/2016.
 */

public class EMAnalyticsWSExceptionTest {
    private EMAnalyticsWSException emAnalyticsWSException = new EMAnalyticsWSException(1000,new Throwable());
    public static final int JSON_CATEGORY_DEFAULT_FOLDER_ID_MISSING = 70010;
    public static final int JSON_CATEGORY_PARAM_NAME_MISSING = 70011;
    public static final int JSON_CATEGORY_NAME_MISSING = 70012;

    //related to search input JSON object
    public static final int JSON_SEARCH_NAME_MISSING = 70020;
    public static final int JSON_SEARCH_PARAM_NAME_MISSING = 70021;
    public static final int JSON_SEARCH_DISPLAY_NAME_MISSING = 70022;
    public static final int JSON_SEARCH_FOLDER_ID_MISSING = 70023;
    public static final int JSON_SEARCH_CATEGORY_ID_MISSING = 70024;
    public static final int JSON_SEARCH_PARAM_TYPE_MISSING = 70025;
    public static final int JSON_SEARCH_PARAM_TYPE_INVALID = 70026;

    //related to folder input JSON object
    public static final int JSON_FOLDER_NAME_MISSING = 70030;

    //related to conversion of JSON to Object and Object to JSON
    public static final int JSON_OBJECT_TO_JSON_EXCEPTION = 70040;
    public static final int JSON_JSON_TO_OBJECT = 70041;

    public static final int JSON_INVALID_CHAR = 70042;
    public static final int JSON_INVALID_LENGTH = 70045;

    @Test (groups = {"s1"})
    public void EMAnalyticsWSExceptionTest1st()throws Exception{
        EMAnalyticsWSException emAnalyticsWSException = new EMAnalyticsWSException("error code",100);

    }

    @Test (groups = {"s1"})
    public void EMAnalyticsWSExceptionTest2nd()throws Exception{
        Object[] errorparam = null;
        EMAnalyticsWSException emAnalyticsWSException = new EMAnalyticsWSException("error code",100,errorparam);

    }

    @Test (groups = {"s1"})
    public void EMAnalyticsWSExceptionTest3th()throws Exception{
        Object[] errorparam = null;
        EMAnalyticsWSException emAnalyticsWSException = new EMAnalyticsWSException("error code",100,errorparam,new Throwable());

    }

    @Test (groups = {"s1"})
    public void EMAnalyticsWSExceptionTest4th()throws Exception{
        EMAnalyticsWSException emAnalyticsWSException = new EMAnalyticsWSException("error code",100,new Throwable());

    }

    @Test (groups = {"s1"})
    public void EMAnalyticsWSExceptionTest5th()throws Exception{
        EMAnalyticsWSException emAnalyticsWSException = new EMAnalyticsWSException(new Throwable());

    }

    @Test (groups = {"s1"})
    public void testGetErrorCode() throws Exception {
        Assert.assertEquals(1000,emAnalyticsWSException.getErrorCode());
    }

    @Test (groups = {"s1"})
    public void testGetErrorParam() throws Exception {
        Object[] errorparam = null;
        EMAnalyticsWSException emAnalyticsWSException = new EMAnalyticsWSException("error code",100,errorparam,new Throwable());
        Assert.assertEquals(errorparam,emAnalyticsWSException.getErrorParam());
    }

    @Test (groups = {"s1"})
    public void testGetLocalizedMessage() throws Exception {
        Assert.assertNotNull(emAnalyticsWSException.getLocalizedMessage());

    }

    @Test (groups = {"s1"})
    public void testGetStatusCode() throws Exception {
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_CATEGORY_DEFAULT_FOLDER_ID_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_CATEGORY_PARAM_NAME_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_CATEGORY_NAME_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_CATEGORY_NAME_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_SEARCH_NAME_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_SEARCH_PARAM_NAME_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_SEARCH_DISPLAY_NAME_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_CATEGORY_PARAM_NAME_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_SEARCH_FOLDER_ID_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_SEARCH_CATEGORY_ID_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_FOLDER_NAME_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_SEARCH_PARAM_TYPE_INVALID).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_SEARCH_FOLDER_ID_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_FOLDER_NAME_MISSING).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_SEARCH_PARAM_TYPE_INVALID).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_SEARCH_PARAM_TYPE_MISSING).getStatusCode());
        Assert.assertEquals(500,new EMAnalyticsWSException("error code",JSON_OBJECT_TO_JSON_EXCEPTION).getStatusCode());
        Assert.assertEquals(500,new EMAnalyticsWSException("error code",JSON_JSON_TO_OBJECT).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_INVALID_CHAR).getStatusCode());
        Assert.assertEquals(400,new EMAnalyticsWSException("error code",JSON_INVALID_LENGTH).getStatusCode());

    }
}