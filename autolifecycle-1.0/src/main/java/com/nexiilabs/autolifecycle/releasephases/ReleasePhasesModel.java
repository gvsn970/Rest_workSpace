package com.nexiilabs.autolifecycle.releasephases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "release_phases")
public class ReleasePhasesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "release_phase_id", unique = true, nullable = false)
	private int release_phase_id;
	
	@Column(name = "release_phase_name")
	private String release_phase_name;
	
	@Column(name = "release_phase_description")
	private String release_phase_description;
	
	@Column(name = "fk_release_phase_type")
	private int fk_release_phase_type;
	
	@Column(name = "is_default")
	private int is_default;
	
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

	public int getRelease_phase_id() {
		return release_phase_id;
	}

	public void setRelease_phase_id(int release_phase_id) {
		this.release_phase_id = release_phase_id;
	}

	public String getRelease_phase_name() {
		return release_phase_name;
	}

	public void setRelease_phase_name(String release_phase_name) {
		this.release_phase_name = release_phase_name;
	}

	public String getRelease_phase_description() {
		return release_phase_description;
	}

	public void setRelease_phase_description(String release_phase_description) {
		this.release_phase_description = release_phase_description;
	}

	public int getFk_release_phase_type() {
		return fk_release_phase_type;
	}

	public void setFk_release_phase_type(int fk_release_phase_type) {
		this.fk_release_phase_type = fk_release_phase_type;
	}

	public int getIs_default() {
		return is_default;
	}

	public void setIs_default(int is_default) {
		this.is_default = is_default;
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
		builder.append("ReleasePhasesModel [release_phase_id=");
		builder.append(release_phase_id);
		builder.append(", release_phase_name=");
		builder.append(release_phase_name);
		builder.append(", release_phase_description=");
		builder.append(release_phase_description);
		builder.append(", fk_release_phase_type=");
		builder.append(fk_release_phase_type);
		builder.append(", is_default=");
		builder.append(is_default);
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
		builder.append("]");
		return builder.toString();
	}

	public ReleasePhasesModel() {
		super();
	}

	
}
