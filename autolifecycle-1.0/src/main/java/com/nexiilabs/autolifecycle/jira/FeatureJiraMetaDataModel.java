package com.nexiilabs.autolifecycle.jira;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "feature_jira_metadata")
public class FeatureJiraMetaDataModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "feature_jirametadata_id")
	private int feature_jirametadata_id;

	@Column(name = "fk_feature_id")
	private int fk_feature_id;

	@Column(name = "Issueid")
	private long Issueid;

	@Column(name = "IssueKey")
	private String IssueKey;

	@Column(name = "IssueName")
	private String IssueName;

	@Column(name = "IssueDescription")
	private String IssueDescription;

	@Column(name = "IssueCreatedon")
	private Date IssueCreatedon;

	@Column(name = "IssueCreaterName")
	private String IssueCreaterName;

	@Column(name = "IssueCreaterEmail")
	private String IssueCreaterEmail;

	@Column(name = "IssueAssigneeEmail")
	private String IssueAssigneeEmail;

	@Column(name = "IssueAssigneeName")
	private String IssueAssigneeName;

	@Column(name = "IssueTypeName")
	private String IssueTypeName;

	@Column(name = "IssueTypeDescription")
	private String IssueTypeDescription;

	/*
	 * @Column(name="sprint") private Sprint sprint;
	 */

	@Column(name = "BoardId")
	private long BoardId;

	@Column(name = "BoardName")
	private String BoardName;

	@Column(name = "SprintId")
	private long SprintId;

	@Column(name = "SprintName")
	private String SprintName;

	@Column(name = "EpicId")
	private long EpicId;

	@Column(name = "EpicKey")
	private String EpicKey;

	@Column(name = "EpicName")
	private String EpicName;
	
	@Column(name="delete_status")
	private int delete_status;

	/*
	 * @Column(name="IssueCreatedby") private User IssueCreatedby;
	 */

	/*
	 * @Column(name="IssueAssignee") private User IssueAssignee;
	 */

	/*
	 * @Column(name="EpicName") private String EpicName;
	 */

	@Column(name="projectName")
	private String projectName;
	
	
	@Column(name="projectId")
	private String projectId;
	
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getFeature_jirametadata_id() {
		return feature_jirametadata_id;
	}

	public int getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(int delete_status) {
		this.delete_status = delete_status;
	}

	public String getEpicKey() {
		return EpicKey;
	}

	public void setEpicKey(String epicKey) {
		EpicKey = epicKey;
	}

	public String getEpicName() {
		return EpicName;
	}

	public void setEpicName(String epicName) {
		EpicName = epicName;
	}

	public String getIssueAssigneeEmail() {
		return IssueAssigneeEmail;
	}

	public void setIssueAssigneeEmail(String issueAssigneeEmail) {
		IssueAssigneeEmail = issueAssigneeEmail;
	}

	public String getIssueAssigneeName() {
		return IssueAssigneeName;
	}

	public void setIssueAssigneeName(String issueAssigneeName) {
		IssueAssigneeName = issueAssigneeName;
	}

	public int getFk_feature_id() {
		return fk_feature_id;
	}

	public void setFk_feature_id(int fk_feature_id) {
		this.fk_feature_id = fk_feature_id;
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

	public void setFeature_jirametadata_id(int feature_jirametadata_id) {
		this.feature_jirametadata_id = feature_jirametadata_id;
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

	/*
	 * public IssueType getIssueType() { return IssueType; }
	 * 
	 * public void setIssueType(IssueType issueType) { IssueType = issueType; }
	 */

	public Date getIssueCreatedon() {
		return IssueCreatedon;
	}

	public void setIssueCreatedon(Date issueCreatedon) {
		IssueCreatedon = issueCreatedon;
	}

	/*
	 * public Sprint getSprint() { return sprint; }
	 * 
	 * public void setSprint(Sprint sprint) { this.sprint = sprint; }
	 */

	public long getBoardId() {
		return BoardId;
	}

	public void setBoardId(long boardId) {
		BoardId = boardId;
	}

	public String getBoardName() {
		return BoardName;
	}

	public void setBoardName(String boardName) {
		BoardName = boardName;
	}

	public long getSprintId() {
		return SprintId;
	}

	public void setSprintId(long sprintId) {
		SprintId = sprintId;
	}

	public String getSprintName() {
		return SprintName;
	}

	public void setSprintName(String sprintName) {
		SprintName = sprintName;
	}

	public long getEpicId() {
		return EpicId;
	}

	public void setEpicId(long epicId) {
		EpicId = epicId;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeatureJiraMetaDataModel [feature_jirametadata_id=");
		builder.append(feature_jirametadata_id);
		builder.append(", fk_feature_id=");
		builder.append(fk_feature_id);
		builder.append(", Issueid=");
		builder.append(Issueid);
		builder.append(", IssueKey=");
		builder.append(IssueKey);
		builder.append(", IssueName=");
		builder.append(IssueName);
		builder.append(", IssueDescription=");
		builder.append(IssueDescription);
		builder.append(", IssueCreatedon=");
		builder.append(IssueCreatedon);
		builder.append(", IssueCreaterName=");
		builder.append(IssueCreaterName);
		builder.append(", IssueCreaterEmail=");
		builder.append(IssueCreaterEmail);
		builder.append(", IssueAssigneeEmail=");
		builder.append(IssueAssigneeEmail);
		builder.append(", IssueAssigneeName=");
		builder.append(IssueAssigneeName);
		builder.append(", IssueTypeName=");
		builder.append(IssueTypeName);
		builder.append(", IssueTypeDescription=");
		builder.append(IssueTypeDescription);
		builder.append(", BoardId=");
		builder.append(BoardId);
		builder.append(", BoardName=");
		builder.append(BoardName);
		builder.append(", SprintId=");
		builder.append(SprintId);
		builder.append(", SprintName=");
		builder.append(SprintName);
		builder.append(", EpicId=");
		builder.append(EpicId);
		builder.append(", EpicKey=");
		builder.append(EpicKey);
		builder.append(", EpicName=");
		builder.append(EpicName);
		builder.append(", delete_status=");
		builder.append(delete_status);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append(", projectId=");
		builder.append(projectId);
		builder.append("]");
		return builder.toString();
	}




	/*
	 * public String getEpicName() { return EpicName; }
	 * 
	 * public void setEpicName(String epicName) { EpicName = epicName; }
	 */

	/*
	 * public User getIssueAssignee() { return IssueAssignee; }
	 * 
	 * public void setIssueAssignee(User issueAssignee) { IssueAssignee =
	 * issueAssignee; }
	 */

	/*
	 * public User getIssueCreatedby() { return IssueCreatedby; }
	 * 
	 * public void setIssueCreatedby(User issueCreatedby) { IssueCreatedby =
	 * issueCreatedby; }
	 */

}
