package com.nexiilabs.autolifecycle.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	public Product() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id", unique = true, nullable = false)
	private int product_id;

	public Product(int product_id, String product_name, String product_desc, int status, int delete_status,
			int fk_product_line, int fk_product_owner, String fk_jira_projectid, long fk_jira_boardid) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_desc = product_desc;
		this.status = status;
		this.delete_status = delete_status;
		this.fk_product_line = fk_product_line;
		this.fk_product_owner = fk_product_owner;
		this.fk_jira_projectid = fk_jira_projectid;
		this.fk_jira_boardid = fk_jira_boardid;
	}

	@Column(name = "product_name")
	private String product_name;
	@Column(name = "product_desc")
	private String product_desc;
	@Column(name = "status")
	private int status;
	@Column(name = "delete_status")
	private int delete_status;
	@Column(name = "fk_product_line")
	private int fk_product_line;
	@Column(name = "fk_product_owner")
	private int fk_product_owner;

	@Column(name = "fk_jira_projectid")
	private String fk_jira_projectid;

	@Column(name = "fk_jira_boardid")
	private long fk_jira_boardid;

	@Column(name = "jira_boardName")
	private String JiraBoardName;

	@Column(name = "jira_projectName")
	private String JiraprojectName;



	public String getJiraBoardName() {
		return JiraBoardName;
	}

	public void setJiraBoardName(String jiraBoardName) {
		JiraBoardName = jiraBoardName;
	}

	public String getJiraprojectName() {
		return JiraprojectName;
	}

	public void setJiraprojectName(String jiraprojectName) {
		JiraprojectName = jiraprojectName;
	}

	public String getFk_jira_projectid() {
		return fk_jira_projectid;
	}

	public void setFk_jira_projectid(String fk_jira_projectid) {
		this.fk_jira_projectid = fk_jira_projectid;
	}

	public long getFk_jira_boardid() {
		return fk_jira_boardid;
	}

	public void setFk_jira_boardid(long fk_jira_boardid) {
		this.fk_jira_boardid = fk_jira_boardid;
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

	public String getProduct_desc() {
		return product_desc;
	}

	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(int delete_status) {
		this.delete_status = delete_status;
	}

	public int getFk_product_line() {
		return fk_product_line;
	}

	public void setFk_product_line(int fk_product_line) {
		this.fk_product_line = fk_product_line;
	}

	public int getFk_product_owner() {
		return fk_product_owner;
	}

	public void setFk_product_owner(int fk_product_owner) {
		this.fk_product_owner = fk_product_owner;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [product_id=");
		builder.append(product_id);
		builder.append(", product_name=");
		builder.append(product_name);
		builder.append(", product_desc=");
		builder.append(product_desc);
		builder.append(", status=");
		builder.append(status);
		builder.append(", delete_status=");
		builder.append(delete_status);
		builder.append(", fk_product_line=");
		builder.append(fk_product_line);
		builder.append(", fk_product_owner=");
		builder.append(fk_product_owner);
		builder.append(", fk_jira_projectid=");
		builder.append(fk_jira_projectid);
		builder.append(", fk_jira_boardid=");
		builder.append(fk_jira_boardid);
		builder.append(", JiraBoardName=");
		builder.append(JiraBoardName);
		builder.append(", JiraprojectName=");
		builder.append(JiraprojectName);
		builder.append("]");
		return builder.toString();
	}

}
