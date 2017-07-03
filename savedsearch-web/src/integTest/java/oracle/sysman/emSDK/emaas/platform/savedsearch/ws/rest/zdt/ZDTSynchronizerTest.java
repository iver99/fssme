package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchCategoryParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchCategoryRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchFolderRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.ZDTTableRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.DataManager;

import org.testng.annotations.Test;

public class ZDTSynchronizerTest {
	@Mocked DataManager dm;
	@Test (groups = {"s1"})
	public void testSyncNull(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em){
		ZDTSynchronizer syn = new ZDTSynchronizer();
		syn.sync(null);
		ZDTTableRowEntity row = new ZDTTableRowEntity(null, null, null, null, null);
		syn.sync(row);
	}

	@Test (groups = {"s1"})
	public void testSync(@Mocked final PersistenceManager persistenceManager, 
			@Mocked final EntityManager em) throws Exception {
		List<SavedSearchCategoryRowEntity> savedSearchCategory = new ArrayList<SavedSearchCategoryRowEntity>();
		savedSearchCategory.add(new SavedSearchCategoryRowEntity(null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null));
		List<SavedSearchCategoryParamRowEntity> savedSearchCategoryParams = new ArrayList<SavedSearchCategoryParamRowEntity>();
		savedSearchCategoryParams.add(new SavedSearchCategoryParamRowEntity(null, null, null, null, null, null));
		List<SavedSearchFolderRowEntity> savedSearchFoldersy = new ArrayList<SavedSearchFolderRowEntity>();
		savedSearchFoldersy.add(new SavedSearchFolderRowEntity(null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null));
		List<SavedSearchSearchParamRowEntity> savedSearchSearchParams = new ArrayList<SavedSearchSearchParamRowEntity>();
		savedSearchSearchParams.add(new SavedSearchSearchParamRowEntity(null, null, null, null, null, null, null, null, null,0));
		List<SavedSearchSearchRowEntity> savedSearchSearch = new ArrayList<SavedSearchSearchRowEntity>();
		savedSearchSearch.add(new SavedSearchSearchRowEntity(null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null));
		ZDTTableRowEntity row = new ZDTTableRowEntity(savedSearchCategory, savedSearchCategoryParams, savedSearchFoldersy, savedSearchSearchParams, savedSearchSearch);
		
		new Expectations() {
			{
				DataManager.getInstance();
				result = dm;
				dm.syncCategoryTable(em,(BigInteger) any, anyString, anyString, anyString, anyString, anyString, anyString, anyString,
						anyString, anyString, (BigInteger) any, (BigInteger) any, anyString, anyString, anyString, anyString, anyLong, anyString, anyString);
				dm.syncCategoryParamTable(em,(BigInteger) any, anyString, anyString, anyLong, anyString, anyString, (Integer)any);
				dm.syncFolderTable(em,(BigInteger) any, anyString, (BigInteger) any, anyString, anyString, anyString, anyString, anyString, anyString,
						anyString, anyString, anyString, anyInt, anyString, anyInt, (BigInteger) any, anyLong);
				dm.syncSearchTable(em,(BigInteger) any, anyString, anyString, anyString, anyString, anyString, anyString, (BigInteger) any, 
						(BigInteger) any, anyInt, anyInt, anyString, anyString, 
						anyInt, (BigInteger) any, anyInt, anyLong, anyString, anyString, anyString, anyString, anyString, anyString, anyString, 
						anyString, anyLong, anyLong, anyLong, anyString, anyString, anyString, anyString);
				dm.syncSearchParamsTable(em,(BigInteger) any, anyString, anyString, anyLong, anyString, anyString, anyLong, anyString, anyString,(Integer)any);
			}
		};
		
		ZDTSynchronizer syn = new ZDTSynchronizer();
		syn.sync(row);
	}
}
