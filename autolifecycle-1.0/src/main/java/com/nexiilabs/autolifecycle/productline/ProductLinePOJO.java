package com.nexiilabs.autolifecycle.productline;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="product_line")
public class ProductLinePOJO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_line_id")
	private int product_line_id;
	
	@Column(name="product_line_name")
	private String product_line_name;
	
	@Column(name="product_line_desc")
	private String product_line_desc;
	
	@Column(name="delete_status")
	private int delete_status;
	
	@Transient
	private int count;
	
	@Transient
	private String product_name;
	
	@Transient
	private int product_id;
	
	@Transient
	private List<ProductLineUploadModel> productLineUploadModel;
	
		

	public List<ProductLineUploadModel> getProductLineUploadModel() {
		return productLineUploadModel;
	}

	public void setProductLineUploadModel(List<ProductLineUploadModel> productLineUploadModel) {
		this.productLineUploadModel = productLineUploadModel;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getProduct_line_id() {
		return product_line_id;
	}

	public void setProduct_line_id(int product_line_id) {
		this.product_line_id = product_line_id;
	}

	public String getProduct_line_name() {
		return product_line_name;
	}

	public void setProduct_line_name(String product_line_name) {
		this.product_line_name = product_line_name;
	}

	public String getProduct_line_desc() {
		return product_line_desc;
	}

	public void setProduct_line_desc(String product_line_desc) {
		this.product_line_desc = product_line_desc;
	}

	public int getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(int delete_status) {
		this.delete_status = delete_status;
	}

	public ProductLinePOJO(int product_line_id, String product_line_name, String product_line_desc, int delete_status) {
		super();
		this.product_line_id = product_line_id;
		this.product_line_name = product_line_name;
		this.product_line_desc = product_line_desc;
		this.delete_status = delete_status;
	}

	public ProductLinePOJO() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductLinePOJO [product_line_id=");
		builder.append(product_line_id);
		builder.append(", product_line_name=");
		builder.append(product_line_name);
		builder.append(", product_line_desc=");
		builder.append(product_line_desc);
		builder.append(", delete_status=");
		builder.append(delete_status);
		builder.append(", count=");
		builder.append(count);
		builder.append(", product_name=");
		builder.append(product_name);
		builder.append(", product_id=");
		builder.append(product_id);
		builder.append(", productLineUploadModel=");
		builder.append(productLineUploadModel);
		builder.append("]");
		return builder.toString();
	}

	

}
