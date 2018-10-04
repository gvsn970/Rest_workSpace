package com.nexiilabs.autolifecycle.resources;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_logo")
public class ProductLogoUploadModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_logo_id")
	private int logoId;
	@Column(name = "product_logo_type")
	private String logoType;
	@Column(name = "product_logo_name")
	private String logoName;
	@Column(name = "product_logo_location")
	private String logoLocation;
	@Column(name = "created_by")
	private int createdBy;
	@Column(name = "updated_by")
	private int updatedBy;
	@Column(name = "deletedBy")
	private int deletedBy;
	@Column(name = "fk_product_id")
	private int fkProductId;
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
	public int getFkProductId() {
		return fkProductId;
	}
	public void setFkProductId(int fkProductId) {
		this.fkProductId = fkProductId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductLogoUploadModel [logoId=");
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
		builder.append(", fkProductId=");
		builder.append(fkProductId);
		builder.append("]");
		return builder.toString();
	}
	public ProductLogoUploadModel() {
		super();
	}

}
