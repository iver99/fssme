package oracle.sysman.emaas.platform.savedsearch.model;

import org.testng.annotations.Test;

@Test(groups = {"s2"})
public class ImportMsgModelTest {

    @Test
    public void testImportMsgModel(){
        ImportMsgModel m = new ImportMsgModel(true, null);
        m.setSuccess(true);
        m.setMsg("msg");
        m.getMsg();
        m.isSuccess();
    }
}
