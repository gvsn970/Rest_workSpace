package com.nexiilabs.autolifecycle.features;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "features")
public class FeaturesModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "feature_id", unique = true, nullable = false)
	private int feature_id;
	
	@Column(name = "feature_name")
	private String feature_name;
	
	@Column(name = "feature_description")
	private String feature_description;
	
	@Column(name = "story_points")
	private int story_points;
	
	@Column(name = "fk_feature_type_id")
	private int fk_feature_type_id;
	
	@Column(name = "fk_feature_status_id")
	private int fk_feature_status_id;
	
	@Column(name = "fk_assignment_id")
	private int fk_assignment_id;
	
	@Column(name = "fk_release_id")
	private int fk_release_id;
	
	@Column(name = "created_by")
	private int created_by;
	
	@Column(name = "updated_on")
	private String updated_on;
	
	@Column(name = "updated_by")
	private int updated_by;
	
	@Column(name = "deleted_on")
	private String deleted_on;
	
	@Column(name = "deleted_by")
	private int deleted_by;
	
	@Column(name = "delete_status")
	private int delete_status;
	@Column(name = "assigned_to")
	private int assigned_To;
	
	@Column(name = "is_backlog")
	private int is_backlog;
	
	@Column(name = "fk_product_id")
	private int fk_product_id;
	
	@Column(name = "issue_id")
	private long issue_id;
	
	@Column(name = "jira_issue_type")
	private String jira_issue_type;

	
	public String getJira_issue_type() {
		return jira_issue_type;
	}


	public void setJira_issue_type(String jira_issue_type) {
		this.jira_issue_type = jira_issue_type;
	}


	public long getIssue_id() {
		return issue_id;
	}


	public void setIssue_id(long issue_id) {
		this.issue_id = issue_id;
	}


	public int getFk_product_id() {
		return fk_product_id;
	}


	public void setFk_product_id(int fk_product_id) {
		this.fk_product_id = fk_product_id;
	}


	public int getIs_backlog() {
		return is_backlog;
	}


	public void setIs_backlog(int is_backlog) {
		this.is_backlog = is_backlog;
	}


	public int getAssigned_To() {
		return assigned_To;
	}


	public void setAssigned_To(int assigned_To) {
		this.assigned_To = assigned_To;
	}


	public FeaturesModel() {
		super();
	}


	public int getFeature_id() {
		return feature_id;
	}


	public void setFeature_id(int feature_id) {
		this.feature_id = feature_id;
	}


	public String getFeature_name() {
		return feature_name;
	}


	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}


	public String getFeature_description() {
		return feature_description;
	}


	public void setFeature_description(String feature_description) {
		this.feature_description = feature_description;
	}


	public int getStory_points() {
		return story_points;
	}


	public void setStory_points(int story_points) {
		this.story_points = story_points;
	}


	public int getFk_feature_type_id() {
		return fk_feature_type_id;
	}


	public void setFk_feature_type_id(int fk_feature_type_id) {
		this.fk_feature_type_id = fk_feature_type_id;
	}


	public int getFk_feature_status_id() {
		return fk_feature_status_id;
	}


	public void setFk_feature_status_id(int fk_feature_status_id) {
		this.fk_feature_status_id = fk_feature_status_id;
	}


	public int getFk_assignment_id() {
		return fk_assignment_id;
	}


	public void setFk_assignment_id(int fk_assignment_id) {
		this.fk_assignment_id = fk_assignment_id;
	}


	public int getFk_release_id() {
		return fk_release_id;
	}


	public void setFk_release_id(int fk_release_id) {
		this.fk_release_id = fk_release_id;
	}


	public int getCreated_by() {
		return created_by;
	}


	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}


	public String getUpdated_on() {
		return updated_on;
	}


	public void setUpdated_on(String updated_on) {
		this.updated_on = updated_on;
	}


	public int getUpdated_by() {
		return updated_by;
	}


	public void setUpdated_by(int updated_by) {
		this.updated_by = updated_by;
	}


	public String getDeleted_on() {
		return deleted_on;
	}


	public void setDeleted_on(String deleted_on) {
		this.deleted_on = deleted_on;
	}


	public int getDeleted_by() {
		return deleted_by;
	}


	public void setDeleted_by(int deleted_by) {
		this.deleted_by = deleted_by;
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
		builder.append("FeaturesModel [feature_id=");
		builder.append(feature_id);
		builder.append(", feature_name=");
		builder.append(feature_name);
		builder.append(", feature_description=");
		builder.append(feature_description);
		builder.append(", story_points=");
		builder.append(story_points);
		builder.append(", fk_feature_type_id=");
		builder.append(fk_feature_type_id);
		builder.append(", fk_feature_status_id=");
		builder.append(fk_feature_status_id);
		builder.append(", fk_assignment_id=");
		builder.append(fk_assignment_id);
		builder.append(", fk_release_id=");
		builder.append(fk_release_id);
		builder.append(", created_by=");
		builder.append(created_by);
		builder.append(", updated_on=");
		builder.append(updated_on);
		builder.append(", updated_by=");
		builder.append(updated_by);
		builder.append(", deleted_on=");
		builder.append(deleted_on);
		builder.append(", deleted_by=");
		builder.append(deleted_by);
		builder.append(", delete_status=");
		builder.append(delete_status);
		builder.append(", assigned_To=");
		builder.append(assigned_To);
		builder.append(", is_backlog=");
		builder.append(is_backlog);
		builder.append(", fk_product_id=");
		builder.append(fk_product_id);
		builder.append(", issue_id=");
		builder.append(issue_id);
		builder.append(", jira_issue_type=");
		builder.append(jira_issue_type);
		builder.append("]");
		return builder.toString();
	}	
	
}
