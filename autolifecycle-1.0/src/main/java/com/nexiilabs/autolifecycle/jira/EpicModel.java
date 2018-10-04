package com.nexiilabs.autolifecycle.jira;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EpicModel {
	private Issue issue;
	private String key;
	private String summary;
	private boolean done;
	private long EpicId;
	private String EpicName;
	private List<Issue> issues;

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public long getEpicId() {
		return EpicId;
	}

	public void setEpicId(long epicId) {
		EpicId = epicId;
	}

	public String getEpicName() {
		return EpicName;
	}

	public void setEpicName(String epicName) {
		EpicName = epicName;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EpicModel [issue=");
		builder.append(issue);
		builder.append(", key=");
		builder.append(key);
		builder.append(", summary=");
		builder.append(summary);
		builder.append(", done=");
		builder.append(done);
		builder.append(", EpicId=");
		builder.append(EpicId);
		builder.append(", EpicName=");
		builder.append(EpicName);
		builder.append(", issues=");
		builder.append(issues);
		builder.append("]");
		return builder.toString();
	}

	@JsonCreator
	public EpicModel(@JsonProperty("issue") Issue issue,@JsonProperty("key") String key,
			@JsonProperty("summary") String summary,@JsonProperty("done") boolean done,
			@JsonProperty("epicId") long epicId,@JsonProperty("epicName") String epicName,
			@JsonProperty("issues") List<Issue> issues) {
		super();
		this.issue = issue;
		this.key = key;
		this.summary = summary;
		this.done = done;
		EpicId = epicId;
		EpicName = epicName;
		this.issues = issues;
	}

	public EpicModel() {
		super();
	}
	
	

}
