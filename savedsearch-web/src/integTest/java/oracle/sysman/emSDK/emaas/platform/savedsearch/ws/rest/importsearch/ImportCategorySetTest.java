package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;

import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategorySet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups={"s2"})
public class ImportCategorySetTest {
    private ImportCategorySet importCategorySet = new ImportCategorySet();
    private String xml = "[{\"id\":121,\"name\":\"Category123\",\"providerName\":\"Log Analytics\",\"prividerVersion\":\"1.0\",\"providerDiscovery\":\"discovery\",\"providerAssetRoot\":\"asset\"}]";

    @Test
    public void testImportsCategories(@Mocked final JAXBUtil anyJaxbutil) throws Exception {
        final List<ImportCategoryImpl> list = new ArrayList<ImportCategoryImpl>();
        list.add(new ImportCategoryImpl());
        new Expectations(){
            {
                JAXBUtil.unmarshal(withAny(new StringReader(anyString)),withAny(new FileInputStream("")),withAny(JAXBContextImpl.newInstance()));
                result= new CategorySet();
            }
        };
        importCategorySet.importsCategories(xml);
    }
}