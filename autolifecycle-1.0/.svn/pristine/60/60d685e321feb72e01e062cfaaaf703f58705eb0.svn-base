/**
 * jira-client - a simple JIRA REST client
 * Copyright (c) 2013 Bob Carroll (bob.carroll@alum.rit.edu)
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.nexiilabs.autolifecycle.jira;

import java.net.URISyntaxException;
import java.util.List;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

/**
 * Represents an Agile Project.
 *
 * @author pldupont
 */

public class Project extends AgileResource {

	private String key;

	/**
	 * Creates a new Agile resource.
	 *
	 * @param restclient
	 *            REST client instance
	 * @param json
	 *            JSON payload
	 */
	public Project(RestClient restclient, JSONObject json) throws JiraException {
		super(restclient, json);
	}

	/**
	 * Deserialize the json to extract standard attributes and keep a reference
	 * of other attributes.
	 *
	 * @param json
	 *            The JSON object to read.
	 */
	@Override
	void deserialize(JSONObject json) throws JiraException {
		super.deserialize(json);
		this.key = Field.getString(json.get("key"));
	}

	public String getKey() {
		return key;
	}

	public static Project createProject(RestClient restclient, String assigneeType, String description,
			String projectKey, String lead, String projectTemplateKey, String projectTypeKey, String name)
			throws JiraException, URISyntaxException {
		JSONObject jsonData = new JSONObject();
		jsonData.put("key", projectKey);
		jsonData.put("projectTypeKey", projectTypeKey);
		jsonData.put("projectTemplateKey", projectTemplateKey);
		jsonData.put("description", description);
		jsonData.put("lead", lead);
		jsonData.put("assigneeType", assigneeType);
		//jsonData.put("avatarId", avatarId);
		jsonData.put("name", name);
		return AgileResource.invokePostMethod(restclient, Project.class, "/rest/api/2/project", jsonData);
	}
	public static List<Project> getProjectsList(RestClient restclient) throws JiraException {
		return AgileResource.listOfArrays(restclient, Project.class, "/rest/api/2/project", "projects");
	}

	public static Project deleteProject(RestClient restclient, String projectId) throws JiraException {
		return AgileResource.delete(restclient, Project.class, "/rest/api/2/project/"+projectId);
	}

	public static Project updateProject(RestClient restclient, String description,
			String projectId, String lead,String name) throws JiraException, URISyntaxException {
		JSONObject jsonData = new JSONObject();
		jsonData.put("description", description);
		jsonData.put("lead", lead);
		jsonData.put("name", name);
		return AgileResource.invokePutMethod(restclient, Project.class, "/rest/api/2/project/"+projectId, jsonData);
	}

}
