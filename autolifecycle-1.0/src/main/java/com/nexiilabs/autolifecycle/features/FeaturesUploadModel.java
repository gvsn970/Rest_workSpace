package com.nexiilabs.autolifecycle.features;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "features_attachments")
public class FeaturesUploadModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "feature_attachment_id")
	private int attachmentId;
	@Column(name = "feature_attachment_file_type")
	private String fileType;
	@Column(name = "feature_attachment_file_name")
	private String fileName;
	@Column(name = "feature_attachment_file_location")
	private String fileLocation;
	@Column(name = "feature_attachment_file_size")
	private String fileSize;
	@Column(name = "created_by")
	private int createdBy;
	@Column(name = "updated_by")
	private int updatedBy;
	@Column(name = "deletedBy")
	private int deletedBy;
	@Column(name = "fk_feature_id")
	private int fkfeatureId;
	@Column(name = "delete_status")
	private int deleteStatus;
	@Column(name = "deleted_on")
	private String deletedOn;
	public int getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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
	public int getFkfeatureId() {
		return fkfeatureId;
	}
	public void setFkfeatureId(int fkfeatureId) {
		this.fkfeatureId = fkfeatureId;
	}
	public int getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public String getDeletedOn() {
		return deletedOn;
	}
	public void setDeletedOn(String deletedOn) {
		this.deletedOn = deletedOn;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeaturesUploadModel [attachmentId=");
		builder.append(attachmentId);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", fileLocation=");
		builder.append(fileLocation);
		builder.append(", fileSize=");
		builder.append(fileSize);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", deletedBy=");
		builder.append(deletedBy);
		builder.append(", fkfeatureId=");
		builder.append(fkfeatureId);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append(", deletedOn=");
		builder.append(deletedOn);
		builder.append("]");
		return builder.toString();
	}
	public FeaturesUploadModel() {
		super();
	}
	

	
	
}
