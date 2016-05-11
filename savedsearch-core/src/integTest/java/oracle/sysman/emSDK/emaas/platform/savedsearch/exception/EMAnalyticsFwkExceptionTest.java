package oracle.sysman.emSDK.emaas.platform.savedsearch.exception;

import org.omg.CORBA.Object;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-2-26.
 */
@Test(groups = {"s1"})
public class EMAnalyticsFwkExceptionTest {
    EMAnalyticsFwkException emAnalyticsFwkException2 = new EMAnalyticsFwkException("errormessage111",111,new Object[]{},new Throwable());
    EMAnalyticsFwkException emAnalyticsFwkException3 = new EMAnalyticsFwkException(new Throwable());

    EMAnalyticsFwkException emAnalyticsFwkException;
    @Test
    public void testGetErrorCode() throws Exception {
        emAnalyticsFwkException = new EMAnalyticsFwkException(111,new Throwable());
        Assert.assertEquals(emAnalyticsFwkException.getErrorCode(),111);
    }

    @Test
    public void testGetErrorParams() throws Exception {
        Object[] errorParams = new Object[]{};
        emAnalyticsFwkException = new EMAnalyticsFwkException("errormessage111",111,errorParams);
        Assert.assertEquals(emAnalyticsFwkException.getErrorParams(),errorParams);
    }

    @Test
    public void testGetLocalizedMessage() throws Exception {
        emAnalyticsFwkException = new EMAnalyticsFwkException(111,new Throwable());
        Assert.assertTrue(emAnalyticsFwkException.getLocalizedMessage() instanceof String);
    }

    @Test
    public void testGetStatusCode() throws Exception {
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_FOLDER_INVALID_PARENT,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_FOLDER_DUP_NAME,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_CREATE_FOLDER,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_UPDATE_FOLDER,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_DELETE_FOLDER,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GET_FOLDER,new Throwable());
        emAnalyticsFwkException.getStatusCode();

        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_CREATE_SEARCH,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_DELETE_SEARCH,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_UPDATE_SEARCH,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GET_SEARCH,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_SEARCH_INVALID_CATEGORY,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_SEARCH_INVALID_FOLDER,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID,new Throwable());
        emAnalyticsFwkException.getStatusCode();

        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_DELETE_CATEGORY,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_CREATE_CATEGORY,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_UPDATE_CATEGORY,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_ID_NOT_EXIST,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_DUPLICATE_CATEGORY_NAME,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_CATEGORY_INVALID_FOLDER,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GET_CATEGORY_BY_NAME,new Throwable());
        emAnalyticsFwkException.getStatusCode();

        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_GENERIC,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_NO_CONTEXT,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_INCOMPLETE_PARAMS,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_DATA_SOURCE_DETAILS,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.ERR_UPGRADE_DATA,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.JSON_OBJECT_TO_JSON_EXCEPTION,new Throwable());
        emAnalyticsFwkException.getStatusCode();
        emAnalyticsFwkException = new EMAnalyticsFwkException(EMAnalyticsFwkException.JSON_JSON_TO_OBJECT,new Throwable());
        emAnalyticsFwkException.getStatusCode();

        emAnalyticsFwkException = new EMAnalyticsFwkException(321321,new Throwable());
        emAnalyticsFwkException.getStatusCode();
    }
}