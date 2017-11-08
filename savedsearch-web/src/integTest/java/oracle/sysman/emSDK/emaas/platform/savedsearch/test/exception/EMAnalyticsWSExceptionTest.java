package oracle.sysman.emSDK.emaas.platform.savedsearch.test.exception;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EMAnalyticsWSExceptionTest {
static final Throwable THROWABLE = new Throwable();
static final Object[] OBJECTS = new Object[3];
static final EMAnalyticsWSException EX_1 = new EMAnalyticsWSException(70010, THROWABLE);
static final EMAnalyticsWSException EX_2 = new EMAnalyticsWSException("test message", 70011);
static final EMAnalyticsWSException EX_3 = new EMAnalyticsWSException("test message", 70012, THROWABLE);
static final EMAnalyticsWSException EX_4 = new EMAnalyticsWSException("test message", 70022, OBJECTS, THROWABLE);
static final EMAnalyticsWSException EX_5 = new EMAnalyticsWSException("test message", 70041, OBJECTS);
static final EMAnalyticsWSException EX_6 = new EMAnalyticsWSException(THROWABLE);
static final EMAnalyticsWSException EX_7 = new EMAnalyticsWSException("test message", 70040, OBJECTS);
static final EMAnalyticsWSException EX_8 = new EMAnalyticsWSException("test message", 70027, OBJECTS);
  @Test (groups = {"s1"})
  public void getErrorCode() {
	  
     Assert.assertEquals(EX_1.getErrorCode(), 70010);
  }

  @Test (groups = {"s1"})
  public void getErrorParam() {
    Assert.assertNotNull(EX_4.getErrorParam());
  }

  @Test (groups = {"s1"})
  public void getLocalizedMessage() {
   Assert.assertEquals(EX_2.getLocalizedMessage(), "test message");
  }

  @Test (groups = {"s1"})
  public void getStatusCode() {
    Assert.assertEquals(EX_1.getStatusCode(), 400);
    Assert.assertEquals(EX_2.getStatusCode(), 400);
    Assert.assertEquals(EX_3.getStatusCode(), 400);
    Assert.assertEquals(EX_4.getStatusCode(), 400);
    Assert.assertEquals(EX_5.getStatusCode(), 500);
    Assert.assertEquals(EX_7.getStatusCode(), 500);
    Assert.assertEquals(EX_8.getStatusCode(), 400);
  }
}
