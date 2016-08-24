package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * Created by xiadai on 2016/8/23.
 */
@Test(groups = {"s1"})
public class SearchSummaryImplTest {
    private SearchSummaryImpl searchSummaryImpl = new SearchSummaryImpl();
    private Date date = new Date();
    @Test
    public void testGetCategoryId(){
        searchSummaryImpl.setCategoryId(1);
        searchSummaryImpl.getCategoryId();
    }

    @Test
    public void testGetCreatedOn(){
        searchSummaryImpl.setCreatedOn(date);
        searchSummaryImpl.getCreatedOn();
    }

    @Test
    public void testGetDescription(){
        searchSummaryImpl.setDescription("des");
        searchSummaryImpl.getDescription();
    }

    @Test
    public void testGetFolderId(){
        searchSummaryImpl.setFolderId(1);
        searchSummaryImpl.getFolderId();
    }

    @Test
    public void testGetGuid(){
        searchSummaryImpl.setGuid("guid");
        searchSummaryImpl.getGuid();
    }

    @Test
    public void testGetId(){
        searchSummaryImpl.setId(1);
        searchSummaryImpl.getId();
    }

    @Test
    public void testGetLastAccessDate(){
        searchSummaryImpl.setLastAccessDate(date);
        searchSummaryImpl.getLastAccessDate();
    }

    @Test
    public void testGetLastModifiedBy(){
        searchSummaryImpl.setLastModifiedBy("lastModifiedby");
        searchSummaryImpl.getLastModifiedBy();
    }

    @Test
    public void testGetLastModifiedOn(){
        searchSummaryImpl.setLastModifiedOn(date);
        searchSummaryImpl.getLastModifiedOn();
    }

    @Test
    public void testGetName(){
        searchSummaryImpl.setName("name");
        searchSummaryImpl.getName();
    }

    @Test
    public void testGetOwner(){
        searchSummaryImpl.setOwner("Oracle");
        searchSummaryImpl.getOwner();
    }

    @Test
    public void testGetTags(){
        searchSummaryImpl.setTags(new String[10]);
        searchSummaryImpl.getTags();
    }

    @Test
    public void testIsSystemSearch(){
        searchSummaryImpl.setSystemSearch(false);
        searchSummaryImpl.isSystemSearch();
    }

}