package com.nexiilabs.autolifecycle.features;

import java.util.List;

public class FeatureslistModel {
	
	private int feature_id;
	private String feature_name;
	private String feature_description;
	private int story_points;
	private String feature_type;
	private String status;
	private int is_active;
	private String releaseName;
	private int totalFeaturesCount;
	private int readyToShipCount;
	private String createdOn;
	private int createdBy;
	private int assignedTo;
	private int feature_type_id;
	private int feature_status_id;
	private int fkReleaseId;
	private int fkproductId;
	private String productName;
	private String productDesc;
	private int fkAssignmentId;
	private long jira_issue_type_id;
	private String jira_issue_type;
	private boolean isProductMapped;
	private boolean isReleaseMapped;
	
	public boolean isProductMapped() {
		return isProductMapped;
	}
	public void setProductMapped(boolean isProductMapped) {
		this.isProductMapped = isProductMapped;
	}
	public boolean isReleaseMapped() {
		return isReleaseMapped;
	}
	public void setReleaseMapped(boolean isReleaseMapped) {
		this.isReleaseMapped = isReleaseMapped;
	}
	public String getJira_issue_type() {
		return jira_issue_type;
	}
	public void setJira_issue_type(String jira_issue_type) {
		this.jira_issue_type = jira_issue_type;
	}
	public long getJira_issue_type_id() {
		return jira_issue_type_id;
	}
	public void setJira_issue_type_id(long jira_issue_type_id) {
		this.jira_issue_type_id = jira_issue_type_id;
	}
	public int getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}
	public int getFkAssignmentId() {
		return fkAssignmentId;
	}
	public void setFkAssignmentId(int fkAssignmentId) {
		this.fkAssignmentId = fkAssignmentId;
	}
	
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getFkproductId() {
		return fkproductId;
	}
	public void setFkproductId(int fkproductId) {
		this.fkproductId = fkproductId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public int getFkReleaseId() {
		return fkReleaseId;
	}
	public void setFkReleaseId(int fkReleaseId) {
		this.fkReleaseId = fkReleaseId;
	}
	public int getFeature_status_id() {
		return feature_status_id;
	}
	public void setFeature_status_id(int feature_status_id) {
		this.feature_status_id = feature_status_id;
	}
	public int getFeature_type_id() {
		return feature_type_id;
	}
	public void setFeature_type_id(int feature_type_id) {
		this.feature_type_id = feature_type_id;
	}
	
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
	
	public int getReadyToShipCount() {
		return readyToShipCount;
	}
	public void setReadyToShipCount(int readyToShipCount) {
		this.readyToShipCount = readyToShipCount;
	}
	public int getTotalFeaturesCount() {
		return totalFeaturesCount;
	}
	public void setTotalFeaturesCount(int totalFeaturesCount) {
		this.totalFeaturesCount = totalFeaturesCount;
	}
	private  List<FeaturesUploadModel> files;
	public FeatureslistModel() {
		super();
	}
	public int getFeature_id() {
		return feature_id;
	}
	public void setFeature_id(int feature_id) {
		this.feature_id = feature_id;
	}
	public String getFeature_name() {
		return feature_name;
	}
	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}
	public String getFeature_description() {
		return feature_description;
	}
	public void setFeature_description(String feature_description) {
		this.feature_description = feature_description;
	}
	public int getStory_points() {
		return story_points;
	}
	public void setStory_points(int story_points) {
		this.story_points = story_points;
	}
	public String getFeature_type() {
		return feature_type;
	}
	public void setFeature_type(String feature_type) {
		this.feature_type = feature_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public List<FeaturesUploadModel> getFiles() {
		return files;
	}
	public void setFiles(List<FeaturesUploadModel> files) {
		this.files = files;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeatureslistModel [feature_id=");
		builder.append(feature_id);
		builder.append(", feature_name=");
		builder.append(feature_name);
		builder.append(", feature_description=");
		builder.append(feature_description);
		builder.append(", story_points=");
		builder.append(story_points);
		builder.append(", feature_type=");
		builder.append(feature_type);
		builder.append(", status=");
		builder.append(status);
		builder.append(", is_active=");
		builder.append(is_active);
		builder.append(", releaseName=");
		builder.append(releaseName);
		builder.append(", totalFeaturesCount=");
		builder.append(totalFeaturesCount);
		builder.append(", readyToShipCount=");
		builder.append(readyToShipCount);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", assignedTo=");
		builder.append(assignedTo);
		builder.append(", feature_type_id=");
		builder.append(feature_type_id);
		builder.append(", feature_status_id=");
		builder.append(feature_status_id);
		builder.append(", fkReleaseId=");
		builder.append(fkReleaseId);
		builder.append(", fkproductId=");
		builder.append(fkproductId);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", productDesc=");
		builder.append(productDesc);
		builder.append(", fkAssignmentId=");
		builder.append(fkAssignmentId);
		builder.append(", jira_issue_type_id=");
		builder.append(jira_issue_type_id);
		builder.append(", jira_issue_type=");
		builder.append(jira_issue_type);
		builder.append(", isProductMapped=");
		builder.append(isProductMapped);
		builder.append(", isReleaseMapped=");
		builder.append(isReleaseMapped);
		builder.append(", files=");
		builder.append(files);
		builder.append("]");
		return builder.toString();
	}	

}
