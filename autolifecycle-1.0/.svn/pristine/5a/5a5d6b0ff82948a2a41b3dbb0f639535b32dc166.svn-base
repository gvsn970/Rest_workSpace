package com.nexiilabs.autolifecycle.jira;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JiraUserModel {
	private String emailAddress;
	private String displayName;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JiraUserModel [emailAddress=");
		builder.append(emailAddress);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append("]");
		return builder.toString();
	}

	@JsonCreator
	public JiraUserModel(@JsonProperty("emailAddress") String emailAddress,
			@JsonProperty("displayName") String displayName) {
		super();
		this.emailAddress = emailAddress;
		this.displayName = displayName;
	}

}
