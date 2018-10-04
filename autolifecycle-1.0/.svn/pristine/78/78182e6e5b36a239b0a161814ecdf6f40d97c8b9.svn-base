package com.nexiilabs.autolifecycle.productline;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productline_attachments")
public class ProductLineUploadModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "productline_attachment_id")
	private int attachmentId;
	@Column(name = "productline_attachment_file_type")
	private String fileType;
	@Column(name = "productline_attachment_file_name")
	private String fileName;
	@Column(name = "productline_attachment_file_location")
	private String fileLocation;
	@Column(name = "created_by")
	private int createdBy;
	@Column(name = "updated_by")
	private int updatedBy;
	@Column(name = "deletedBy")
	private int deletedBy;
	@Column(name = "fk_product_line_id")
	private int fk_product_line_id;
	@Column(name = "delete_status")
	private int delete_status;
	@Column(name = "deleted_on")
	private String deletedOn;
	@Column(name = "productline_attachment_file_size")
	private String fileSize;

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(String deletedOn) {
		this.deletedOn = deletedOn;
	}

	public int getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(int delete_status) {
		this.delete_status = delete_status;
	}

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

	public int getFk_product_line_id() {
		return fk_product_line_id;
	}

	public void setFk_product_line_id(int fk_product_line_id) {
		this.fk_product_line_id = fk_product_line_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductLineUploadModel [attachmentId=");
		builder.append(attachmentId);
		builder.append(", fileType=");
		builder.append(fileType);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", fileLocation=");
		builder.append(fileLocation);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", deletedBy=");
		builder.append(deletedBy);
		builder.append(", fk_product_line_id=");
		builder.append(fk_product_line_id);
		builder.append(", delete_status=");
		builder.append(delete_status);
		builder.append(", deletedOn=");
		builder.append(deletedOn);
		builder.append(", fileSize=");
		builder.append(fileSize);
		builder.append("]");
		return builder.toString();
	}

}
