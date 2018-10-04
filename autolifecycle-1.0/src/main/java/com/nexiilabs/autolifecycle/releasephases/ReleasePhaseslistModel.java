package com.nexiilabs.autolifecycle.releasephases;

import java.util.List;

public class ReleasePhaseslistModel {
	
	private int release_phase_id;
	private String release_phase_name;
	private String release_phase_description;
	private String fk_release_phase_type;
	private int is_default;
	private int phase_type_id;
	private String phase_type;
	private String startdate;
	private String endDate;
	private String releaseName;
	
	
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getPhase_type_id() {
		return phase_type_id;
	}
	public void setPhase_type_id(int phase_type_id) {
		this.phase_type_id = phase_type_id;
	}
	public String getPhase_type() {
		return phase_type;
	}
	public void setPhase_type(String phase_type) {
		this.phase_type = phase_type;
	}
	private  List<ReleasePhasesUploadModel> files;
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
	public String getFk_release_phase_type() {
		return fk_release_phase_type;
	}
	public void setFk_release_phase_type(String fk_release_phase_type) {
		this.fk_release_phase_type = fk_release_phase_type;
	}
	public int getIs_default() {
		return is_default;
	}
	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}
	public List<ReleasePhasesUploadModel> getFiles() {
		return files;
	}
	public void setFiles(List<ReleasePhasesUploadModel> files) {
		this.files = files;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReleasePhaseslistModel [release_phase_id=");
		builder.append(release_phase_id);
		builder.append(", release_phase_name=");
		builder.append(release_phase_name);
		builder.append(", release_phase_description=");
		builder.append(release_phase_description);
		builder.append(", fk_release_phase_type=");
		builder.append(fk_release_phase_type);
		builder.append(", is_default=");
		builder.append(is_default);
		builder.append(", phase_type_id=");
		builder.append(phase_type_id);
		builder.append(", phase_type=");
		builder.append(phase_type);
		builder.append(", startdate=");
		builder.append(startdate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", releaseName=");
		builder.append(releaseName);
		builder.append(", files=");
		builder.append(files);
		builder.append("]");
		return builder.toString();
	}
	public ReleasePhaseslistModel() {
		super();
	}
	
}
