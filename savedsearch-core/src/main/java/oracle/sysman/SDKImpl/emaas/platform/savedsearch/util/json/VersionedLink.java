package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json;

import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;

public class VersionedLink extends Link {
	private String authToken;

	public VersionedLink() {
		// TODO Auto-generated constructor stub
	}

	public VersionedLink(Link link, String authToken) {
		withHref(link.getHref());
		withOverrideTypes(link.getOverrideTypes());
		withRel(link.getRel());
		withTypesStr(link.getTypesStr());
		this.authToken = authToken;
	}
	
	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken
	 *            the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
