package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class ZDTComparatorStatusRowEntity {
	

	@JsonProperty("lastComparisonDateTime")
	private String comparisonDate;
	
	@JsonProperty("comparisonType")
	private String comparisonType;
	
	@JsonProperty("nextScheduledComparisonDateTime")
	private String nextComparisonDate;
	
	@JsonProperty("divergencePercentage")
	private String divergencePercentage;
	
	@JsonProperty("comparisonResult")
	private String comparisonResult;
	
	
	public String getComparisonDate() {
		return comparisonDate;
	}

	public void setComparisonDate(String comparisonDate) {
		this.comparisonDate = comparisonDate;
	}

	public String getComparisonType() {
		return comparisonType;
	}

	public void setComparisonType(String comparisonType) {
		this.comparisonType = comparisonType;
	}

	public String getNextComparisonDate() {
		return nextComparisonDate;
	}

	public void setNextComparisonDate(String nextComparisonDate) {
		this.nextComparisonDate = nextComparisonDate;
	}

	public String getDivergencePercentage() {
		return divergencePercentage;
	}

	public void setDivergencePercentage(String divergencePercentage) {
		this.divergencePercentage = divergencePercentage;
	}

	public ZDTComparatorStatusRowEntity(String comparisonDate, String comparisonType,
			String nextComparisonDate, String divergencePercentage, String comparisonResult) {
		super();
		this.comparisonDate = comparisonDate;
		this.comparisonType = comparisonType;
		this.nextComparisonDate = nextComparisonDate;
		this.divergencePercentage = divergencePercentage;
		this.comparisonResult = comparisonResult;
	}

	public ZDTComparatorStatusRowEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
