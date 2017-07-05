package oracle.sysman.emaas.platform.savedsearch.entity;

import java.util.Date;

import org.testng.annotations.Test;

@Test(groups={"s1"})
public class EmBaseEntityTest {
    
    @Test
    public void testEmBaseEntity() {
        EmBaseEntity entity = new EmBaseEntity();
        entity.getCreationDate();
        entity.getLastModificationDate();
        entity.setCreationDate(new Date());
        entity.setLastModificationDate(new Date());
    }

}
