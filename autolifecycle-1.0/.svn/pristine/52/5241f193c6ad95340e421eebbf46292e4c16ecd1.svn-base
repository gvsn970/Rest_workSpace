package com.nexiilabs.autolifecycle.jira;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jira_configurations")
public class JiraConfigurationModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="jira_configuration_id")
	private int jira_configuration_id;
	
	@Column(name="application_url")
	private String application_url;
	
	@Column(name="user_name")
	private String user_name;
	
	@Column(name="password")
	private String password;
	
	@Column(name="fk_user_id")
	private int fk_user_id;
	
	@Column(name = "created_by")
	private int createdBy;
	
/*	@Column(name="created_on")
	private int createdOn;*/
	
	@Column(name = "updated_by")
	private int updatedBy;
	
	@Column(name = "updated_on")
	private String updatedOn;
	
	@Column(name = "deleted_by")
	private int deletedBy;
	
	@Column(name = "deleted_on")
	private String deletedOn;

	@Column(name = "delete_status")
	private int deleteStatus;

	public int getJira_configuration_id() {
		return jira_configuration_id;
	}

	public void setJira_configuration_id(int jira_configuration_id) {
		this.jira_configuration_id = jira_configuration_id;
	}

	public String getApplication_url() {
		return application_url;
	}

	public void setApplication_url(String application_url) {
		this.application_url = application_url;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getFk_user_id() {
		return fk_user_id;
	}

	public void setFk_user_id(int fk_user_id) {
		this.fk_user_id = fk_user_id;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

/*	public int getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(int createdOn) {
		this.createdOn = createdOn;
	}*/

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(int deletedBy) {
		this.deletedBy = deletedBy;
	}

	public String getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(String deletedOn) {
		this.deletedOn = deletedOn;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JiraConfigurationModel [jira_configuration_id=");
		builder.append(jira_configuration_id);
		builder.append(", application_url=");
		builder.append(application_url);
		builder.append(", user_name=");
		builder.append(user_name);
		builder.append(", password=");
		builder.append(password);
		builder.append(", fk_user_id=");
		builder.append(fk_user_id);
		builder.append(", createdBy=");
		builder.append(createdBy);
		//builder.append(", createdOn=");
		//builder.append(createdOn);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", updatedOn=");
		builder.append(updatedOn);
		builder.append(", deletedBy=");
		builder.append(deletedBy);
		builder.append(", deletedOn=");
		builder.append(deletedOn);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append("]");
		return builder.toString();
	}
	
}
