package com.nexiilabs.autolifecycle.jira;

import java.util.List;

public class IssuesAndResponseDTO {
	private List<BackLogsModel> backLogsModel;
	private String message;
	private int statusCode;
	private long fk_jira_sprint_id;
	private String jira_sprintName;
	private long fk_jira_boardid;
	private String JiraBoardName;
	private String fk_jira_projectid;
	private String JiraprojectName;
	private long Issueid;
	
	public long getIssueid() {
		return Issueid;
	}

	public void setIssueid(long issueid) {
		Issueid = issueid;
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

	public String getJiraBoardName() {
		return JiraBoardName;
	}

	public void setJiraBoardName(String jiraBoardName) {
		JiraBoardName = jiraBoardName;
	}

	public String getFk_jira_projectid() {
		return fk_jira_projectid;
	}

	public void setFk_jira_projectid(String fk_jira_projectid) {
		this.fk_jira_projectid = fk_jira_projectid;
	}

	public String getJiraprojectName() {
		return JiraprojectName;
	}

	public void setJiraprojectName(String jiraprojectName) {
		JiraprojectName = jiraprojectName;
	}

	public List<BackLogsModel> getBackLogsModel() {
		return backLogsModel;
	}

	public void setBackLogsModel(List<BackLogsModel> backLogsModel) {
		this.backLogsModel = backLogsModel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
