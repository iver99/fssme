package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception;

import org.testng.annotations.Test;

/**
 * Created by xidai on 2/19/2016.
 */
@Test(groups = {"s1"})
public class ImportExceptionTest {
    private ImportException importException = new ImportException();
    @Test
    public void ImportExceptionTest1st(){
        importException = new ImportException("message");
        importException = new ImportException("message",new Throwable());
        importException = new ImportException(new Throwable());

    }

}