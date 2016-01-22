package oracle.sysman.emSDK.emaas.platform.savedsearch.test.exception;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EMAnalyticsWSExceptionTest {
static final Throwable th = new Throwable();
static final Object[] ob = new Object[3]; 
static final EMAnalyticsWSException ex1= new EMAnalyticsWSException(70010,th);
static final EMAnalyticsWSException ex2= new EMAnalyticsWSException("test message", 70011);
static final EMAnalyticsWSException ex3= new EMAnalyticsWSException("test message", 70012, th);
static final EMAnalyticsWSException ex4= new EMAnalyticsWSException("test message", 70022, ob, th);
static final EMAnalyticsWSException ex5= new EMAnalyticsWSException("test message", 70041, ob);
static final EMAnalyticsWSException ex6= new EMAnalyticsWSException(th);
static final EMAnalyticsWSException ex7= new EMAnalyticsWSException("test message", 70040, ob);
  @Test (groups = {"s1"})
  public void getErrorCode() {
	  
	 System.out.println(ex1.getErrorCode());
     Assert.assertEquals(ex1.getErrorCode(), 70010);
  }

  @Test (groups = {"s1"})
  public void getErrorParam() {
    System.out.println("THE OBJECT IS"+ex4.getErrorParam());
    Assert.assertNotNull(ex4.getErrorParam());
  }

  @Test (groups = {"s1"})
  public void getLocalizedMessage() {
   System.out.println(ex2.getLocalizedMessage());
   Assert.assertEquals(ex2.getLocalizedMessage(), "test message");
  }

  @Test (groups = {"s1"})
  public void getStatusCode() {
    Assert.assertEquals(ex1.getStatusCode(), 400);
    Assert.assertEquals(ex2.getStatusCode(), 400);
    Assert.assertEquals(ex3.getStatusCode(), 400);
    Assert.assertEquals(ex4.getStatusCode(), 400);
    Assert.assertEquals(ex5.getStatusCode(), 500);
    Assert.assertEquals(ex7.getStatusCode(), 500);
  }
}
