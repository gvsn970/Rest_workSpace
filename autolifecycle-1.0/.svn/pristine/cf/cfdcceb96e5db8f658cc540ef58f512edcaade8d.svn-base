package com.nexiilabs.autolifecycle.resources;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productline_logo")
public class ProductLineLogoUploadModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "productline_logo_id")
	private int logoId;
	@Column(name = "productline_logo_type")
	private String logoType;
	@Column(name = "productline_logo_name")
	private String logoName;
	@Column(name = "productline_logo_location")
	private String logoLocation;
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

	public int getLogoId() {
		return logoId;
	}

	public void setLogoId(int logoId) {
		this.logoId = logoId;
	}

	public String getLogoType() {
		return logoType;
	}

	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getLogoLocation() {
		return logoLocation;
	}

	public void setLogoLocation(String logoLocation) {
		this.logoLocation = logoLocation;
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

	public int getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(int delete_status) {
		this.delete_status = delete_status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductLineLogoUploadModel [logoId=");
		builder.append(logoId);
		builder.append(", logoType=");
		builder.append(logoType);
		builder.append(", logoName=");
		builder.append(logoName);
		builder.append(", logoLocation=");
		builder.append(logoLocation);
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
		builder.append("]");
		return builder.toString();
	}

	public ProductLineLogoUploadModel() {
		super();
	}

}
