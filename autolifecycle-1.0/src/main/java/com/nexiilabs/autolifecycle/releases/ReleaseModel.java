package com.nexiilabs.autolifecycle.releases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_releases")
public class ReleaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "release_id")
	private int releaseId;
	@Column(name = "release_name")
	private String releaseName;
	@Column(name = "fk_product_id")
	private int fkProductId;
	@Column(name = "release_date_internal")
	private String releaseDateInternal;
	@Column(name = "release_date_external")
	private String releaseDateExternal;
	@Column(name = "release_description")
	private String releaseDescription;
	@Column(name = "fk_release_owner")
	private int fkReleaseOwner;
	@Column(name = "created_by")
	private int createdBy;
	@Column(name = "updated_by")
	private int updatedBy;
	@Column(name = "deleted_by")
	private int deletedBy;
	@Column(name = "fk_status_id")
	private int fkStatusId;
	@Column(name = "delete_status")
	private int deleteStatus;
	@Column(name = "updated_on")
	private String updatedOn;
	@Column(name = "deleted_on")
	private String deletedOn;

	@Column(name = "fk_jira_sprint_id")
	private long fk_jira_sprint_id;

	@Column(name = "jira_sprintName")
	private String jira_sprintName;

	public int getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(int releaseId) {
		this.releaseId = releaseId;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public int getFkProductId() {
		return fkProductId;
	}

	public void setFkProductId(int fkProductId) {
		this.fkProductId = fkProductId;
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

	public int getFkReleaseOwner() {
		return fkReleaseOwner;
	}

	public void setFkReleaseOwner(int fkReleaseOwner) {
		this.fkReleaseOwner = fkReleaseOwner;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(int deletedBy) {
		this.deletedBy = deletedBy;
	}

	public int getFkStatusId() {
		return fkStatusId;
	}

	public void setFkStatusId(int fkStatusId) {
		this.fkStatusId = fkStatusId;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(String deletedOn) {
		this.deletedOn = deletedOn;
	}

	public long getFk_jira_sprint_id() {
		return fk_jira_sprint_id;
	}

	public void setFk_jira_sprint_id(long fk_jira_sprint_id) {
		this.fk_jira_sprint_id = fk_jira_sprint_id;
	}

	public String getJira_sprintName() {
		return jira_sprintName;
	}

	public void setJira_sprintName(String jira_sprintName) {
		this.jira_sprintName = jira_sprintName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReleaseModel [releaseId=");
		builder.append(releaseId);
		builder.append(", releaseName=");
		builder.append(releaseName);
		builder.append(", fkProductId=");
		builder.append(fkProductId);
		builder.append(", releaseDateInternal=");
		builder.append(releaseDateInternal);
		builder.append(", releaseDateExternal=");
		builder.append(releaseDateExternal);
		builder.append(", releaseDescription=");
		builder.append(releaseDescription);
		builder.append(", fkReleaseOwner=");
		builder.append(fkReleaseOwner);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", deletedBy=");
		builder.append(deletedBy);
		builder.append(", fkStatusId=");
		builder.append(fkStatusId);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append(", updatedOn=");
		builder.append(updatedOn);
		builder.append(", deletedOn=");
		builder.append(deletedOn);
		builder.append(", fk_jira_sprint_id=");
		builder.append(fk_jira_sprint_id);
		builder.append(", jira_sprintName=");
		builder.append(jira_sprintName);
		builder.append("]");
		return builder.toString();
	}

}
