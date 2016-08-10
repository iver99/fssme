package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author guobaochen
 */
public class DBConnectionManager
{
	private final Logger LOGGER = LogManager.getLogger(DBConnectionManager.class);
	private static DBConnectionManager instance;

	static {
		instance = new DBConnectionManager();
	}

	/**
	 * Returns the singleton INSTANCE for database connection manager
	 *
	 * @return
	 */
	public static DBConnectionManager getInstance()
	{
		return instance;
	}

	private DBConnectionManager()
	{
	}

	/**
	 * Check if the database connection is available or not
	 *
	 * @return true if database connection is available
	 */
	public boolean isDatabaseConnectionAvailable()
	{
		EntityManager em = null;
		try {
			final EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			BigDecimal result = (BigDecimal) em.createNativeQuery("select 1 from dual").getSingleResult();
			return BigDecimal.valueOf(1).equals(result);
		}
		catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			return false;
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
	}

}
