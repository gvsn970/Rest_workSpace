package com.nexiilabs.autolifecycle.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_attachments")
public class ProductUploadModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_attachment_id")
	private int attachmentId;
	@Column(name = "product_attachment_file_type")
	private String fileType;
	@Column(name = "product_attachment_file_name")
	private String fileName;
	@Column(name = "product_attachment_file_location")
	private String fileLocation;
	@Column(name = "created_by")
	private int createdBy;
	@Column(name = "updated_by")
	private int updatedBy;
	@Column(name = "deletedBy")
	private int deletedBy;
	@Column(name = "fk_product_id")
	private int fkProductId;
	@Column(name = "delete_status")
	private int delete_status;
	@Column(name = "product_file_size")
	private String product_file_size;
	

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
	public int getFkProductId() {
		return fkProductId;
	}
	public void setFkProductId(int fkProductId) {
		this.fkProductId = fkProductId;
	}
	public String getProduct_file_size() {
		return product_file_size;
	}
	public void setProduct_file_size(String product_file_size) {
		this.product_file_size = product_file_size;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductUploadModel [attachmentId=");
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
		builder.append(", fkProductId=");
		builder.append(fkProductId);
		builder.append(", delete_status=");
		builder.append(delete_status);
		builder.append(", product_file_size=");
		builder.append(product_file_size);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
