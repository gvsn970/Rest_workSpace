package com.nexiilabs.autolifecycle.jira;

import java.util.Date;

public class ImportBacklogModel {
	private long Issueid;
	private String IssueKey;
	private String IssueName;
	private String IssueDescription;	
	private String IssueAssigneeName;
	private String IssueAssigneeEmail;
	private String IssueCreaterName;
	private String IssueCreaterEmail;
	private String IssueTypeName;
	private String IssueTypeDescription;
	private Date IssueCreatedon;
	private long IssueTypeId;
	private int IssueStatusId;
	private String IssueStatus;
	
	

	public int getIssueStatusId() {
		return IssueStatusId;
	}

	public void setIssueStatusId(int issueStatusId) {
		IssueStatusId = issueStatusId;
	}

	public String getIssueStatus() {
		return IssueStatus;
	}

	public void setIssueStatus(String issueStatus) {
		IssueStatus = issueStatus;
	}

	public long getIssueTypeId() {
		return IssueTypeId;
	}

	public void setIssueTypeId(long issueTypeId) {
		IssueTypeId = issueTypeId;
	}

	public long getIssueid() {
		return Issueid;
	}

	public void setIssueid(long issueid) {
		Issueid = issueid;
	}

	public String getIssueKey() {
		return IssueKey;
	}

	public void setIssueKey(String issueKey) {
		IssueKey = issueKey;
	}

	public String getIssueName() {
		return IssueName;
	}

	public void setIssueName(String issueName) {
		IssueName = issueName;
	}

	public String getIssueDescription() {
		return IssueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		IssueDescription = issueDescription;
	}

	public String getIssueAssigneeName() {
		return IssueAssigneeName;
	}

	public void setIssueAssigneeName(String issueAssigneeName) {
		IssueAssigneeName = issueAssigneeName;
	}

	public String getIssueAssigneeEmail() {
		return IssueAssigneeEmail;
	}

	public void setIssueAssigneeEmail(String issueAssigneeEmail) {
		IssueAssigneeEmail = issueAssigneeEmail;
	}

	public String getIssueCreaterName() {
		return IssueCreaterName;
	}

	public void setIssueCreaterName(String issueCreaterName) {
		IssueCreaterName = issueCreaterName;
	}

	public String getIssueCreaterEmail() {
		return IssueCreaterEmail;
	}

	public void setIssueCreaterEmail(String issueCreaterEmail) {
		IssueCreaterEmail = issueCreaterEmail;
	}

	public String getIssueTypeName() {
		return IssueTypeName;
	}

	public void setIssueTypeName(String issueTypeName) {
		IssueTypeName = issueTypeName;
	}

	public String getIssueTypeDescription() {
		return IssueTypeDescription;
	}

	public void setIssueTypeDescription(String issueTypeDescription) {
		IssueTypeDescription = issueTypeDescription;
	}

	public Date getIssueCreatedon() {
		return IssueCreatedon;
	}

	public void setIssueCreatedon(Date issueCreatedon) {
		IssueCreatedon = issueCreatedon;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImportBacklogModel [Issueid=");
		builder.append(Issueid);
		builder.append(", IssueKey=");
		builder.append(IssueKey);
		builder.append(", IssueName=");
		builder.append(IssueName);
		builder.append(", IssueDescription=");
		builder.append(IssueDescription);
		builder.append(", IssueAssigneeName=");
		builder.append(IssueAssigneeName);
		builder.append(", IssueAssigneeEmail=");
		builder.append(IssueAssigneeEmail);
		builder.append(", IssueCreaterName=");
		builder.append(IssueCreaterName);
		builder.append(", IssueCreaterEmail=");
		builder.append(IssueCreaterEmail);
		builder.append(", IssueTypeName=");
		builder.append(IssueTypeName);
		builder.append(", IssueTypeDescription=");
		builder.append(IssueTypeDescription);
		builder.append(", IssueCreatedon=");
		builder.append(IssueCreatedon);
		builder.append(", IssueTypeId=");
		builder.append(IssueTypeId);
		builder.append(", IssueStatusId=");
		builder.append(IssueStatusId);
		builder.append(", IssueStatus=");
		builder.append(IssueStatus);
		builder.append("]");
		return builder.toString();
	}



	public ImportBacklogModel(long issueid, String issueKey, String issueName, String issueDescription,
			String issueAssigneeName, String issueAssigneeEmail, String issueCreaterName, String issueCreaterEmail,
			String issueTypeName, String issueTypeDescription, Date issueCreatedon, long issueTypeId, int issueStatusId,
			String issueStatus) {
		super();
		Issueid = issueid;
		IssueKey = issueKey;
		IssueName = issueName;
		IssueDescription = issueDescription;
		IssueAssigneeName = issueAssigneeName;
		IssueAssigneeEmail = issueAssigneeEmail;
		IssueCreaterName = issueCreaterName;
		IssueCreaterEmail = issueCreaterEmail;
		IssueTypeName = issueTypeName;
		IssueTypeDescription = issueTypeDescription;
		IssueCreatedon = issueCreatedon;
		IssueTypeId = issueTypeId;
		IssueStatusId = issueStatusId;
		IssueStatus = issueStatus;
	}

	public ImportBacklogModel() {
		super();
	}
	
	
	
/*
	private JiraUserModel IssueAssigneeModel;
	private JiraUserModel IssueCreatedbyModel;
	private IssueTypeModel IssueTypeModel;*/
	
	
}
