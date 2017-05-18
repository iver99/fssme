package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class SSFFolderRowEntityTest {

	@Test
	public void testEquals(){
		SavedSearchFolderRowEntity entity =  new SavedSearchFolderRowEntity("folderId", "description",
				"descriptionNlsid", "descriptionSubsystem",
				"emPluginId", "lastModifiedBy",
				"lastModificationDate", "name","nameNlsid",
				"nameSubsystem","owner", 1,
				1, "deleted", 123L,
				"parentId", "creationDate");
		entity.getCreationDate();
		entity.getDeleted();
		entity.getDescription();
		entity.getDescription();
		entity.getDescriptionNlsid();
		entity.getDescriptionSubsystem();
		entity.getEmPluginId();
		entity.getFolderId();
		entity.getLastModificationDate();
		entity.getLastModifiedBy();
		entity.getName();
		entity.getNameNlsid();
		entity.getNameSubsystem();
		entity.getOwner();
		entity.getParentId();
		entity.getSystemFolder();
		entity.getTenantId();
		entity.getUiHidden();
		SavedSearchFolderRowEntity  folderEntity = new SavedSearchFolderRowEntity();
		SavedSearchFolderRowEntity  folderEntity2 = new SavedSearchFolderRowEntity();
		Assert.assertEquals(folderEntity,folderEntity2);
		folderEntity.setDeleted("1");
		folderEntity2.setDeleted("1");
		Assert.assertEquals(folderEntity,folderEntity2);
				
		folderEntity.setDescription("description");
		folderEntity2.setDescription("description");
		Assert.assertEquals(folderEntity,folderEntity2);
		
		folderEntity.setDescriptionNlsid("descriptionNlsid");
		folderEntity2.setDescriptionNlsid("descriptionNlsid");
		Assert.assertEquals(folderEntity,folderEntity2);
		
		folderEntity.setDescriptionSubsystem("descriptionSubsystem");
		folderEntity2.setDescriptionSubsystem("descriptionSubsystem");
		Assert.assertEquals(folderEntity,folderEntity2);
		folderEntity.setEmPluginId("emPluginId");
		folderEntity2.setEmPluginId("emPluginId");
		Assert.assertEquals(folderEntity,folderEntity2);
		
		folderEntity.setFolderId("1");
		folderEntity2.setFolderId("1");
		Assert.assertEquals(folderEntity,folderEntity2);
		
		folderEntity.setLastModificationDate("lastModificationDate");
		folderEntity2.setLastModificationDate("lastModificationDate");
		
		Assert.assertEquals(folderEntity,folderEntity2);
		folderEntity.setLastModifiedBy("lastModifiedBy");
		folderEntity2.setLastModifiedBy("lastModifiedBy");
		Assert.assertEquals(folderEntity,folderEntity2);
		
		folderEntity.setName("name");
		folderEntity2.setName("name");
		Assert.assertEquals(folderEntity,folderEntity2);
		
		folderEntity.setNameNlsid("nameNlsid");
		folderEntity2.setNameNlsid("nameNlsid");
		Assert.assertEquals(folderEntity,folderEntity2);
		
		folderEntity.setNameSubsystem("nameSubsystem");
		folderEntity2.setNameSubsystem("nameSubsystem");
		Assert.assertEquals(folderEntity,folderEntity2);
		
		folderEntity.setOwner("owner");
		folderEntity2.setOwner("owner");
		Assert.assertEquals(folderEntity,folderEntity2);
		
		folderEntity.setParentId("1");
		folderEntity2.setParentId("1");
		
		Assert.assertEquals(folderEntity,folderEntity2);
		folderEntity.setTenantId(1l);
		folderEntity2.setTenantId(1l);
		Assert.assertEquals(folderEntity,folderEntity2);
		folderEntity.setUiHidden(1);
		folderEntity2.setUiHidden(2);
		Assert.assertNotEquals(folderEntity,folderEntity2);
	
	}
	
	@Test
	public void testHashcode(){
		SavedSearchFolderRowEntity  folderEntity = new SavedSearchFolderRowEntity();
		SavedSearchFolderRowEntity  folderEntity2 = new SavedSearchFolderRowEntity();
		Assert.assertEquals(folderEntity,folderEntity2);
		folderEntity.setDeleted("1");
		folderEntity2.setDeleted("1");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
				
		folderEntity.setDescription("description");
		folderEntity2.setDescription("description");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		
		folderEntity.setDescriptionNlsid("descriptionNlsid");
		folderEntity2.setDescriptionNlsid("descriptionNlsid");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		
		folderEntity.setDescriptionSubsystem("descriptionSubsystem");
		folderEntity2.setDescriptionSubsystem("descriptionSubsystem");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		folderEntity.setEmPluginId("emPluginId");
		folderEntity2.setEmPluginId("emPluginId");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		
		folderEntity.setFolderId("1");
		folderEntity2.setFolderId("1");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		
		folderEntity.setLastModificationDate("lastModificationDate");
		folderEntity2.setLastModificationDate("lastModificationDate");
		
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		folderEntity.setLastModifiedBy("lastModifiedBy");
		folderEntity2.setLastModifiedBy("lastModifiedBy");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		
		folderEntity.setName("name");
		folderEntity2.setName("name");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		
		folderEntity.setNameNlsid("nameNlsid");
		folderEntity2.setNameNlsid("nameNlsid");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		
		folderEntity.setNameSubsystem("nameSubsystem");
		folderEntity2.setNameSubsystem("nameSubsystem");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		
		folderEntity.setOwner("owner");
		folderEntity2.setOwner("owner");
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		
		folderEntity.setParentId("1");
		folderEntity2.setParentId("1");
		
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		folderEntity.setTenantId(1l);
		folderEntity2.setTenantId(1l);
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		folderEntity.setUiHidden(1);
		folderEntity2.setUiHidden(2);
		Assert.assertNotEquals(folderEntity.hashCode(),folderEntity2.hashCode());
	}
}
