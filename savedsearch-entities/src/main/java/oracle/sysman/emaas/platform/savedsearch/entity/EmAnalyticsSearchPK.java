package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class EmAnalyticsSearchPK implements Serializable {
	private static final long serialVersionUID = -8052165914140211820L;
	
	private Long tenantId;
	private BigInteger id;
	
	public EmAnalyticsSearchPK(Long tenantId, BigInteger searchId){
		this.tenantId = tenantId;
		this.id =searchId;
	}
	
	@Override
	public boolean equals(Object object){
		if(object instanceof EmAnalyticsSearchPK){
			final EmAnalyticsSearchPK otherEmAnalyticsSearchPK = (EmAnalyticsSearchPK) object;
			final boolean isEqual = otherEmAnalyticsSearchPK.id.equals(id) 
					&& otherEmAnalyticsSearchPK.tenantId.equals(tenantId);
			return isEqual;
		}
		return false;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public BigInteger getSearchId() {
		return id;
	}
	public void setSearchId(BigInteger searchId) {
		this.id = searchId;
	}
	
	

}
