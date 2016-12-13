package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SessionInfoUtil {
	public static final String PRODUCT_NAME = "EMCPSSF";  

    /**
     * Set Module and Action so that EM users can know who to access db by JDBC
     */
    public static void setModuleAndAction(EntityManager em, String moduleName, String actionName) throws SQLException
    {
    	em.getTransaction().begin();
		Query query = em.createNativeQuery("{call DBMS_APPLICATION_INFO.SET_MODULE(?,?)}");
		query.setParameter(1, PRODUCT_NAME + ":" + moduleName);
		query.setParameter(2, actionName);
		query.executeUpdate();
		em.getTransaction().commit();    	
    }

}
