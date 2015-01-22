package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.navigation.SavedSearchAPI;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.category.CategoryAPI;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.folder.FolderAPI;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.folder.TenantOnBoard;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch.ImportCategorySet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch.ImportFolderSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch.ImportSearchSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search.FilterSearchAPI;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search.SearchAPI;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget.WidgetAPI;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget.WidgetGroupAPI;

@ApplicationPath("v1")
public class SavedSearchApplication extends Application
{
	@Override
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(SavedSearchAPI.class);
		s.add(CategoryAPI.class);
		s.add(FolderAPI.class);
		s.add(TenantOnBoard.class);
		s.add(SearchAPI.class);
		s.add(FilterSearchAPI.class);
		s.add(ImportFolderSet.class);
		s.add(ImportCategorySet.class);
		s.add(ImportSearchSet.class);
		s.add(WidgetAPI.class);
		s.add(WidgetGroupAPI.class);

		return s;
	}

}
