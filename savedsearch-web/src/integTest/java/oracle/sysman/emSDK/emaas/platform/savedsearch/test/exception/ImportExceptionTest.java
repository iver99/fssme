package oracle.sysman.emSDK.emaas.platform.savedsearch.test.exception;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ImportExceptionTest {
	static final Throwable th = new Exception("this is a throwable object");
	static final ImportException ie = new ImportException();
	static final ImportException ie1 = new ImportException("test message");
	static final ImportException ie2 = new ImportException("test message", th);
	static final ImportException ie3 = new ImportException(th);

	@Test (groups = {"s1"})
	public void ImportException() {
		Assert.assertNull(ie.getMessage());
	}

	@Test (groups = {"s1"})
	public void ImportExceptionString() {
		Assert.assertEquals(ie1.getMessage(), "test message");
	}

	@Test (groups = {"s1"})
	public void ImportExceptionStringThrowable() {
		Assert.assertEquals(ie2.getCause(), th);
	}

	@Test (groups = {"s1"})
	public void ImportExceptionThrowable() {
		Assert.assertEquals(ie3.getCause(),th);
	}
}
