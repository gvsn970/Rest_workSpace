package com.nexiilabs.autolifecycle.releasephasesmaping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "release_phases_mapping")
public class ReleasePhasesmapingModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "release_phase_map_id", unique = true, nullable = false)
	private int release_phase_map_id;
	
	@Column(name = "fk_release_phase_id")
	private int fk_release_phase_id;
	
	@Column(name = "fk_release_id")
	private int fk_release_id;
	
	@Column(name = "fk_release_phase_type_id")
	private int fk_release_phase_type_id;
	
	@Column(name = "start_date")
	private String start_date;
	
	@Column(name = "end_date")
	private String end_date;
	
	@Column(name = "description")
	private String description;

	@Column(name = "fk_status_id")
	private int fk_status_id;
	
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

	public int getRelease_phase_map_id() {
		return release_phase_map_id;
	}

	public void setRelease_phase_map_id(int release_phase_map_id) {
		this.release_phase_map_id = release_phase_map_id;
	}

	public int getFk_release_phase_id() {
		return fk_release_phase_id;
	}

	public void setFk_release_phase_id(int fk_release_phase_id) {
		this.fk_release_phase_id = fk_release_phase_id;
	}

	public int getFk_release_id() {
		return fk_release_id;
	}

	public void setFk_release_id(int fk_release_id) {
		this.fk_release_id = fk_release_id;
	}

	public int getFk_release_phase_type_id() {
		return fk_release_phase_type_id;
	}

	public void setFk_release_phase_type_id(int fk_release_phase_type_id) {
		this.fk_release_phase_type_id = fk_release_phase_type_id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFk_status_id() {
		return fk_status_id;
	}

	public void setFk_status_id(int fk_status_id) {
		this.fk_status_id = fk_status_id;
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
		builder.append("ReleasePhasesmapingModel [release_phase_map_id=");
		builder.append(release_phase_map_id);
		builder.append(", fk_release_phase_id=");
		builder.append(fk_release_phase_id);
		builder.append(", fk_release_id=");
		builder.append(fk_release_id);
		builder.append(", fk_release_phase_type_id=");
		builder.append(fk_release_phase_type_id);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", description=");
		builder.append(description);
		builder.append(", fk_status_id=");
		builder.append(fk_status_id);
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

	public ReleasePhasesmapingModel() {
		super();
	}


}
