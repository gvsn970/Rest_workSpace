package com.nexiilabs.autolifecycle.jira;

import java.util.Date;

public class SprintModel {
	private long SprintId;
	private String SprintName;
	private long BoardIdofSprint;
	private String BoardName;
	private Date StartDate;
	private Date EndDate;
	private Date CompleteDate;
	private String SprintStatus;
	
	
	
	public String getSprintStatus() {
		return SprintStatus;
	}

	public void setSprintStatus(String sprintStatus) {
		SprintStatus = sprintStatus;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public Date getCompleteDate() {
		return CompleteDate;
	}

	public void setCompleteDate(Date completeDate) {
		CompleteDate = completeDate;
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

	public long getBoardIdofSprint() {
		return BoardIdofSprint;
	}

	public void setBoardIdofSprint(long boardIdofSprint) {
		BoardIdofSprint = boardIdofSprint;
	}

}
