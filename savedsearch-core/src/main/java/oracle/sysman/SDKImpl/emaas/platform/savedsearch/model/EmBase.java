package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.Date;

public class EmBase {
	protected Date creationDate;
	protected Date lastModificationDate;
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModificationDate() {
		return lastModificationDate;
	}
	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
	
}
