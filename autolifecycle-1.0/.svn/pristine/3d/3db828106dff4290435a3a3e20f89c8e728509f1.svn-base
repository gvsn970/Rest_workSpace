package com.nexiilabs.autolifecycle.jira;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueTypeModel {
	private String description;
	private String name;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IssueTypeModel [description=");
		builder.append(description);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	@JsonCreator
	public IssueTypeModel(@JsonProperty("description") String description, @JsonProperty("name") String name) {
		super();
		this.description = description;
		this.name = name;
	}

}
