package com.nexiilabs.autolifecycle.releases;

import java.util.List;

public class ReleaseListModel {
	private int releaseId;
	private int fkProductId;
	private String productName;
	private String releaseName;
	private String releaseDateInternal;
	private String releaseDateExternal;
	private String releaseDescription;
	private String Owner;
	private int fkReleaseOwner;
	private int fkStatusId;
	private String productLineName;
	private String status;
	private boolean isProductMapped;
	private String daysCount;
	private int totalFeaturesCount;
	private int readyToShipCount;
	private int releasePhaseID;
	private int phaseTypeId;
	private String phaseType;
	
	public boolean isProductMapped() {
		return isProductMapped;
	}
	public void setProductMapped(boolean isProductMapped) {
		this.isProductMapped = isProductMapped;
	}
	public String getPhaseType() {
		return phaseType;
	}
	public void setPhaseType(String phaseType) {
		this.phaseType = phaseType;
	}
	public int getReleasePhaseID() {
		return releasePhaseID;
	}
	public void setReleasePhaseID(int releasePhaseID) {
		this.releasePhaseID = releasePhaseID;
	}
	public int getPhaseTypeId() {
		return phaseTypeId;
	}
	public void setPhaseTypeId(int phaseTypeId) {
		this.phaseTypeId = phaseTypeId;
	}
	public int getTotalFeaturesCount() {
		return totalFeaturesCount;
	}
	public void setTotalFeaturesCount(int totalFeaturesCount) {
		this.totalFeaturesCount = totalFeaturesCount;
	}
	public int getReadyToShipCount() {
		return readyToShipCount;
	}
	public void setReadyToShipCount(int readyToShipCount) {
		this.readyToShipCount = readyToShipCount;
	}
	public String getDaysCount() {
		return daysCount;
	}
	public void setDaysCount(String daysCount) {
		this.daysCount = daysCount;
	}
	private List<ProductReleaseUploadModel> files;
	public int getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(int releaseId) {
		this.releaseId = releaseId;
	}
	public int getFkProductId() {
		return fkProductId;
	}
	public void setFkProductId(int fkProductId) {
		this.fkProductId = fkProductId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getReleaseDateInternal() {
		return releaseDateInternal;
	}
	public void setReleaseDateInternal(String releaseDateInternal) {
		this.releaseDateInternal = releaseDateInternal;
	}
	public String getReleaseDateExternal() {
		return releaseDateExternal;
	}
	public void setReleaseDateExternal(String releaseDateExternal) {
		this.releaseDateExternal = releaseDateExternal;
	}
	public String getReleaseDescription() {
		return releaseDescription;
	}
	public void setReleaseDescription(String releaseDescription) {
		this.releaseDescription = releaseDescription;
	}
	public String getOwner() {
		return Owner;
	}
	public void setOwner(String owner) {
		Owner = owner;
	}
	public int getFkReleaseOwner() {
		return fkReleaseOwner;
	}
	public void setFkReleaseOwner(int fkReleaseOwner) {
		this.fkReleaseOwner = fkReleaseOwner;
	}
	public int getFkStatusId() {
		return fkStatusId;
	}
	public void setFkStatusId(int fkStatusId) {
		this.fkStatusId = fkStatusId;
	}
	
	public List<ProductReleaseUploadModel> getFiles() {
		return files;
	}
	public void setFiles(List<ProductReleaseUploadModel> files) {
		this.files = files;
	}
	
	public String getProductLineName() {
		return productLineName;
	}
	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReleaseListModel [releaseId=");
		builder.append(releaseId);
		builder.append(", fkProductId=");
		builder.append(fkProductId);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", releaseName=");
		builder.append(releaseName);
		builder.append(", releaseDateInternal=");
		builder.append(releaseDateInternal);
		builder.append(", releaseDateExternal=");
		builder.append(releaseDateExternal);
		builder.append(", releaseDescription=");
		builder.append(releaseDescription);
		builder.append(", Owner=");
		builder.append(Owner);
		builder.append(", fkReleaseOwner=");
		builder.append(fkReleaseOwner);
		builder.append(", fkStatusId=");
		builder.append(fkStatusId);
		builder.append(", productLineName=");
		builder.append(productLineName);
		builder.append(", status=");
		builder.append(status);
		builder.append(", isProductMapped=");
		builder.append(isProductMapped);
		builder.append(", daysCount=");
		builder.append(daysCount);
		builder.append(", totalFeaturesCount=");
		builder.append(totalFeaturesCount);
		builder.append(", readyToShipCount=");
		builder.append(readyToShipCount);
		builder.append(", releasePhaseID=");
		builder.append(releasePhaseID);
		builder.append(", phaseTypeId=");
		builder.append(phaseTypeId);
		builder.append(", phaseType=");
		builder.append(phaseType);
		builder.append(", files=");
		builder.append(files);
		builder.append("]");
		return builder.toString();
	}	
	
}
