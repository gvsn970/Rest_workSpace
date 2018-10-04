package com.nexiilabs.autolifecycle.jira;

import javax.persistence.Column;

public class JiraReleaseSprintDTO {
	private int releaseId;
	private String releaseName;
	private int fkProductId;
	private long fk_jira_sprint_id;
	private String jira_sprintName;
	private long fk_jira_boardid;
	private String fk_jira_projectid;
	private String JiraBoardName;
	private String JiraprojectName;
	private String releaseDescription;	
	private String releaseDateInternal;	
	private String releaseDateExternal;
	
	public String getReleaseDateInternal() {
		return releaseDateInternal;
	}
	public void setReleaseDateInternal(String releaseDateInternal) {
		this.releaseDateInternal = releaseDateInternal;
	}
	public String getReleaseDateExternal() {
		return releaseDateExternal;
	}
	public void setReleaseDateExternal(String releaseDateExternal) {
		this.releaseDateExternal = releaseDateExternal;
	}
	public String getReleaseDescription() {
		return releaseDescription;
	}
	public void setReleaseDescription(String releaseDescription) {
		this.releaseDescription = releaseDescription;
	}
	public int getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(int releaseId) {
		this.releaseId = releaseId;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public int getFkProductId() {
		return fkProductId;
	}
	public void setFkProductId(int fkProductId) {
		this.fkProductId = fkProductId;
	}
	public long getFk_jira_sprint_id() {
		return fk_jira_sprint_id;
	}
	public void setFk_jira_sprint_id(long fk_jira_sprint_id) {
		this.fk_jira_sprint_id = fk_jira_sprint_id;
	}
	public String getJira_sprintName() {
		return jira_sprintName;
	}
	public void setJira_sprintName(String jira_sprintName) {
		this.jira_sprintName = jira_sprintName;
	}
	public long getFk_jira_boardid() {
		return fk_jira_boardid;
	}
	public void setFk_jira_boardid(long fk_jira_boardid) {
		this.fk_jira_boardid = fk_jira_boardid;
	}
	public String getFk_jira_projectid() {
		return fk_jira_projectid;
	}
	public void setFk_jira_projectid(String fk_jira_projectid) {
		this.fk_jira_projectid = fk_jira_projectid;
	}
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JiraReleaseSprintDTO [releaseId=");
		builder.append(releaseId);
		builder.append(", releaseName=");
		builder.append(releaseName);
		builder.append(", fkProductId=");
		builder.append(fkProductId);
		builder.append(", fk_jira_sprint_id=");
		builder.append(fk_jira_sprint_id);
		builder.append(", jira_sprintName=");
		builder.append(jira_sprintName);
		builder.append(", fk_jira_boardid=");
		builder.append(fk_jira_boardid);
		builder.append(", fk_jira_projectid=");
		builder.append(fk_jira_projectid);
		builder.append(", JiraBoardName=");
		builder.append(JiraBoardName);
		builder.append(", JiraprojectName=");
		builder.append(JiraprojectName);
		builder.append("]");
		return builder.toString();
	}
	
}
