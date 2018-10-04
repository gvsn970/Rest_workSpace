package com.nexiilabs.autolifecycle.jira;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BoardModel {
	private long BoardId;
	private String BoardName;
	private String projectName;
	private String projectId;
	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

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

	@JsonCreator
	public BoardModel(@JsonProperty("boardId") long boardId,@JsonProperty("boardName") String boardName,
			@JsonProperty("projectName") String projectName,@JsonProperty("projectId") String projectId) {
		super();
		BoardId = boardId;
		BoardName = boardName;
		this.projectName = projectName;
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardModel [BoardId=");
		builder.append(BoardId);
		builder.append(", BoardName=");
		builder.append(BoardName);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append(", projectId=");
		builder.append(projectId);
		builder.append("]");
		return builder.toString();
	}

	public BoardModel() {
		super();
	}

	
}
