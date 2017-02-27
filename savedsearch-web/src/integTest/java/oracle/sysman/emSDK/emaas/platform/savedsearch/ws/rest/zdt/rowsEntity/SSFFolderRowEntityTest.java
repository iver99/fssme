package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "s1" })
public class SSFFolderRowEntityTest {

	@Test
	public void testEquals(){
		SavedSearchFolderRowEntity  folderEntity = new SavedSearchFolderRowEntity(new BigInteger("1"), "description",
				"descriptionNlsid", "descriptionSubsystem",
				"emPluginId", "lastModifiedBy",
				"lastModificationDate", "name", "nameNlsid",
				"nameSubsystem", "owner", new Integer("1"),
				new Integer("1"), new BigInteger("1"), 1L,
				new BigInteger("1"), "creationDate");
		SavedSearchFolderRowEntity  folderEntity2 = new SavedSearchFolderRowEntity(new BigInteger("1"), "description",
				"descriptionNlsid", "descriptionSubsystem",
				"emPluginId", "lastModifiedBy",
				"lastModificationDate", "name", "nameNlsid",
				"nameSubsystem", "owner", new Integer("1"),
				new Integer("1"), new BigInteger("1"), 1L,
				new BigInteger("1"), "creationDate");
		Assert.assertEquals(folderEntity,folderEntity2);
		folderEntity.setDeleted(new BigInteger("1"));
		folderEntity2.setDeleted(new BigInteger("1"));
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
		
		folderEntity.setFolderId(new BigInteger("1"));
		folderEntity2.setFolderId(new BigInteger("1"));
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
		
		folderEntity.setParentId(new BigInteger("1"));
		folderEntity2.setParentId(new BigInteger("1"));
		
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
		SavedSearchFolderRowEntity  folderEntity = new SavedSearchFolderRowEntity(new BigInteger("1"), "description",
				"descriptionNlsid", "descriptionSubsystem",
				"emPluginId", "lastModifiedBy",
				"lastModificationDate", "name", "nameNlsid",
				"nameSubsystem", "owner", new Integer("1"),
				new Integer("1"), new BigInteger("1"), 1L,
				new BigInteger("1"), "creationDate");
		SavedSearchFolderRowEntity  folderEntity2 = new SavedSearchFolderRowEntity(new BigInteger("1"), "description",
				"descriptionNlsid", "descriptionSubsystem",
				"emPluginId", "lastModifiedBy",
				"lastModificationDate", "name", "nameNlsid",
				"nameSubsystem", "owner", new Integer("1"),
				new Integer("1"), new BigInteger("1"), 1L,
				new BigInteger("1"), "creationDate");
		Assert.assertEquals(folderEntity,folderEntity2);
		folderEntity.setDeleted(new BigInteger("1"));
		folderEntity2.setDeleted(new BigInteger("1"));
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
		
		folderEntity.setFolderId(new BigInteger("1"));
		folderEntity2.setFolderId(new BigInteger("1"));
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
		
		folderEntity.setParentId(new BigInteger("1"));
		folderEntity2.setParentId(new BigInteger("1"));
		
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		folderEntity.setTenantId(1l);
		folderEntity2.setTenantId(1l);
		Assert.assertEquals(folderEntity.hashCode(),folderEntity2.hashCode());
		folderEntity.setUiHidden(1);
		folderEntity2.setUiHidden(2);
		Assert.assertNotEquals(folderEntity.hashCode(),folderEntity2.hashCode());
	}
}
