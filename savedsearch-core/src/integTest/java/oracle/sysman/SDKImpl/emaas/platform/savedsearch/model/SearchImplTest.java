package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2016/8/23.
 */
@Test(groups = "s1")
public class SearchImplTest {
    private SearchImpl search = new SearchImpl();
    @Test
    public void testGetIsWidget(){
        search.setIsWidget(false);
        search.getIsWidget();
    }

    @Test
    public void testGetMetadata(){
        search.setMetadata("eata_data");
        search.getMetadata();
    }

    @Test
    public void testGetParameters(){
        List<SearchParameter> searchParameterList = new ArrayList<>();
        search.setParameters(searchParameterList);
        search.getParameters();
    }

    @Test
    public void testGetQueryStr(){
        search.setQueryStr("query_str");
        search.getQueryStr();
    }

    @Test
    public void testIsLocked(){
        search.setLocked(false);
        search.isLocked();
    }

    @Test
    public void testIsUiHidden(){
        search.setUiHidden(false);
        search.isUiHidden();
    }

}