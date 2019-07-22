package com.maoz.utils.bean;

public class CvsBaaniaCmp {


	private Long baaniaCmp;
	private Long jobId;
	private Long compNo;
	private Long propertyId;

	private String projectCode;
	private String projectName;

	private String currentStatus;

	private String propertyType;
	private Double latitude;
	private Double longitude;

	

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getCompNo() {
		return compNo;
	}

	public void setCompNo(Long compNo) {
		this.compNo = compNo;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}



	public Long getBaaniaCmp() {
		return baaniaCmp;
	}

	public void setBaaniaCmp(Long baaniaCmp) {
		this.baaniaCmp = baaniaCmp;
	}

	@Override
	public String toString() {
		return String.format(
				"CvsBaaniaCmp [baaniaCmp=%s, jobId=%s, compNo=%s, propertyId=%s, projectCode=%s, projectName=%s, currentStatus=%s, propertyType=%s, latitude=%s, longitude=%s]",
				baaniaCmp, jobId, compNo, propertyId, projectCode, projectName, currentStatus, propertyType, latitude,
				longitude);
	}
}
