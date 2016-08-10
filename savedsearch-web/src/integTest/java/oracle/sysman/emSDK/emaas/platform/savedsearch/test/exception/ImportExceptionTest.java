package oracle.sysman.emSDK.emaas.platform.savedsearch.test.exception;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ImportExceptionTest {
	static final Throwable TH = new Exception("this is a throwable object");
	static final ImportException IE = new ImportException();
	static final ImportException IE_1 = new ImportException("test message");
	static final ImportException IE_2 = new ImportException("test message", TH);
	static final ImportException IE_3 = new ImportException(TH);

	@Test (groups = {"s1"})
	public void importException() {
		Assert.assertNull(IE.getMessage());
	}

	@Test (groups = {"s1"})
	public void importExceptionString() {
		Assert.assertEquals(IE_1.getMessage(), "test message");
	}

	@Test (groups = {"s1"})
	public void importExceptionStringThrowable() {
		Assert.assertEquals(IE_2.getCause(), TH);
	}

	@Test (groups = {"s1"})
	public void importExceptionThrowable() {
		Assert.assertEquals(IE_3.getCause(), TH);
	}
}
