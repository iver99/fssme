package oracle.sysman.emaas.platform.savedsearch.entity;

import java.math.BigInteger;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("2")
@NamedQueries({
	@NamedQuery(name = "LastAccess.updateLastAccessByIds", query = "UPDATE EmAnalyticsLastAccess t SET t.accessDate = :accessDate WHERE t.objectId IN :ids AND t.accessedBy = :accessedBy AND t.objectType = 2 ")
})
public class EmAnalyticsSearchLastAccess extends EmAnalyticsLastAccess
{
	private static final long serialVersionUID = 7890402955076628981L;
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "OBJECT_ID", referencedColumnName = "SEARCH_ID", insertable = false, updatable = false),
			//below join means one search can only be accessed by its owner, now seed data's owner is ORACLE, so seed data may not be accessed by user
			//let's consider more about below
			@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID", insertable = false, updatable = false) })
	private EmAnalyticsSearch emAnalyticsSearch;

	public EmAnalyticsSearchLastAccess()
	{

	}

	public EmAnalyticsSearchLastAccess(BigInteger objectId, String accessedBy)
	{
		super(objectId, accessedBy, EmAnalyticsLastAccess.LAST_ACCESS_TYPE_SEARCH);
	}

	/**
	 * @return the emAnalyticsSearch
	 */
	public EmAnalyticsSearch getEmAnalyticsSearch()
	{
		return emAnalyticsSearch;
	}

	/**
	 * @param emAnalyticsSearch
	 *            the emAnalyticsSearch to set
	 */
	public void setEmAnalyticsSearch(EmAnalyticsSearch emAnalyticsSearch)
	{
		this.emAnalyticsSearch = emAnalyticsSearch;
	}
}
