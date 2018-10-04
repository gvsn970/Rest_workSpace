package com.nexiilabs.autolifecycle.releasephases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "release_phase_attachments")
public class ReleasePhasesUploadModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "release_phase_attachment_id")
	private int attachmentId;
	@Column(name = "release_phase_attachment_file_type")
	private String fileType;
	@Column(name = "release_phase_attachment_file_name")
	private String fileName;
	@Column(name = "release_phase_attachment_file_location")
	private String fileLocation;
	@Column(name = "release_phase_attachment_file_size")
	private String fileSize;
	@Column(name = "fk_release_id")
	private int fkreleaseId;
	@Column(name = "fk_release_phase_id")
	private int fkreleasephaseId;
	@Column(name = "fk_release_phase_map_id")
	private int fkreleasephasemapId;
	
	@Column(name = "created_by")
	private int createdBy;
	@Column(name = "updated_by")
	private int updatedBy;
	@Column(name = "deleted_by")
	private int deletedBy;
	
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
	public int getFkreleaseId() {
		return fkreleaseId;
	}
	public void setFkreleaseId(int fkreleaseId) {
		this.fkreleaseId = fkreleaseId;
	}
	public int getFkreleasephaseId() {
		return fkreleasephaseId;
	}
	public void setFkreleasephaseId(int fkreleasephaseId) {
		this.fkreleasephaseId = fkreleasephaseId;
	}
	public int getFkreleasephasemapId() {
		return fkreleasephasemapId;
	}
	public void setFkreleasephasemapId(int fkreleasephasemapId) {
		this.fkreleasephasemapId = fkreleasephasemapId;
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
		builder.append("ReleasePhasesUploadModel [attachmentId=");
		builder.append(attachmentId);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", fileLocation=");
		builder.append(fileLocation);
		builder.append(", fileSize=");
		builder.append(fileSize);
		builder.append(", fkreleaseId=");
		builder.append(fkreleaseId);
		builder.append(", fkreleasephaseId=");
		builder.append(fkreleasephaseId);
		builder.append(", fkreleasephasemapId=");
		builder.append(fkreleasephasemapId);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", deletedBy=");
		builder.append(deletedBy);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append(", deletedOn=");
		builder.append(deletedOn);
		builder.append("]");
		return builder.toString();
	}
	

}
