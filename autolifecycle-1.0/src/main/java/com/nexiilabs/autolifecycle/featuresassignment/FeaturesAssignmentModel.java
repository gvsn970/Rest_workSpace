package com.nexiilabs.autolifecycle.featuresassignment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "features_assignment")
public class FeaturesAssignmentModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "feature_assignment_id", unique = true, nullable = false)
	private int feature_assignment_id;
	
	@Column(name = "fk_feature_id")
	private int fk_feature_id;
	
	@Column(name = "assigned_by")
	private int assigned_by;

	@Column(name = "is_active")
	private int is_active;
	
	@Column(name = "assigned_to")
	private int assigned_to;

	public int getFeature_assignment_id() {
		return feature_assignment_id;
	}

	public void setFeature_assignment_id(int feature_assignment_id) {
		this.feature_assignment_id = feature_assignment_id;
	}

	public int getFk_feature_id() {
		return fk_feature_id;
	}

	public void setFk_feature_id(int fk_feature_id) {
		this.fk_feature_id = fk_feature_id;
	}

	public int getAssigned_by() {
		return assigned_by;
	}

	public void setAssigned_by(int assigned_by) {
		this.assigned_by = assigned_by;
	}

	public int getIs_active() {
		return is_active;
	}

	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}

	public int getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(int assigned_to) {
		this.assigned_to = assigned_to;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeaturesAssignmentModel [feature_assignment_id=");
		builder.append(feature_assignment_id);
		builder.append(", fk_feature_id=");
		builder.append(fk_feature_id);
		builder.append(", assigned_by=");
		builder.append(assigned_by);
		builder.append(", is_active=");
		builder.append(is_active);
		builder.append(", assigned_to=");
		builder.append(assigned_to);
		builder.append("]");
		return builder.toString();
	}

	public FeaturesAssignmentModel() {
		super();
	}
	
	
}
