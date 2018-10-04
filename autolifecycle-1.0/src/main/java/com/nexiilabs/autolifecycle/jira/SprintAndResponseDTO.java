package com.nexiilabs.autolifecycle.jira;

import java.util.List;

public class SprintAndResponseDTO {
	private List<SprintModel> sprintModel;
	private String message;
	private int statusCode;
	private long SprintId;
	private long BoardId;
	private String BoardName;
	
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
	public List<SprintModel> getSprintModel() {
		return sprintModel;
	}
	public void setSprintModel(List<SprintModel> sprintModel) {
		this.sprintModel = sprintModel;
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
