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
import java.util.Date;
import java.util.List;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

/**
 * Represents an Agile Sprint.
 *
 * @author pldupont
 */

public class Sprint extends AgileResource {

    private String state;
    private long originBoardId;
    private Date startDate;
    private Date endDate;
    private Date completeDate;

    /**
     * Creates a rapid view from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    protected Sprint(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
    }

    /**
     * Retrieve all sprints related to the specified board.
     *
     * @param restclient REST client instance
     * @param sprintId   The Internal JIRA sprint ID.
     * @return The sprint for the specified ID.
     * @throws JiraException when the retrieval fails
     */
    public static Sprint get(RestClient restclient, long sprintId) throws JiraException {
        return AgileResource.get(restclient, Sprint.class, RESOURCE_URI + "sprint/" + sprintId);
    }

    /**
     * Retrieve all sprints related to the specified board.
     *
     * @param restclient REST client instance
     * @param boardId    The Internal JIRA board ID.
     * @return The list of sprints associated to the board.
     * @throws JiraException when the retrieval fails
     */
    public static List<Sprint> getAll(RestClient restclient, long boardId) throws JiraException {
        return AgileResource.list(restclient, Sprint.class, RESOURCE_URI + "board/" + boardId + "/sprint");
    }

    /**
     * @return All issues in the Sprint.
     * @throws JiraException when the retrieval fails
     */
/*    public List<Issue> getIssues() throws JiraException {
        return AgileResource.list(getRestclient(), Issue.class, RESOURCE_URI + "sprint/" + getId() + "/issue", "issues");
    }*/
    
    /**
     * @param restclient 
     * @return All issues in the Sprint.
     * @throws JiraException when the retrieval fails
     */
    public static List<Issue> getIssuesBySprintAttributeId(RestClient restclient, Long sprintAttributeId) throws JiraException {
        return AgileResource.list(restclient, Issue.class, RESOURCE_URI + "sprint/" + sprintAttributeId + "/issue", "issues");
    }

    @Override
    protected void deserialize(JSONObject json) throws JiraException {
        super.deserialize(json);
        state = Field.getString(json.get("state"));
        //originBoardId = getLong(json.get("originBoardId"));
        originBoardId = Field.getInteger(json.get("originBoardId"));
        startDate = Field.getDate(json.get("startDate"));
        endDate = Field.getDate(json.get("endDate"));
        completeDate = Field.getDate(json.get("completeDate"));
    }

    public String getState() {
        return state;
    }

    public long getOriginBoardId() {
        return originBoardId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

	public List<Sprint> getAllByBoardId(RestClient restclient, long longValue) throws JiraException{
	
		return AgileResource.list(restclient, Sprint.class, RESOURCE_URI + "board/" + longValue + "/sprint");

	}

	public static Sprint createSprint(RestClient restclient, String sprintName, long boardId, String startDate,
			String endDate, String goal) throws JiraException, URISyntaxException {
		JSONObject jsonData = new JSONObject();
		jsonData.put("name", sprintName);
		jsonData.put("originBoardId", boardId);
		jsonData.put("startDate", startDate);
		jsonData.put("endDate", endDate);
		jsonData.put("goal", goal);
		 return AgileResource.invokePostMethod(restclient,Sprint.class, RESOURCE_URI + "sprint",jsonData);
	}

	public static Sprint deleteSprint(RestClient restclient, long sprintId) throws JiraException {
		return AgileResource.delete(restclient, Sprint.class, RESOURCE_URI + "sprint/"+ sprintId);
	}

	public static Sprint updateSprint(RestClient restclient, long sprintId, String sprintName, String startDate,
			String endDate, String goal) throws JiraException, URISyntaxException {
		JSONObject jsonData = new JSONObject();
		jsonData.put("name", sprintName);
		//jsonData.put("originBoardId", boardId);
		jsonData.put("startDate", startDate);
		jsonData.put("endDate", endDate);
		jsonData.put("goal", goal);
		return AgileResource.invokePostMethod(restclient,Sprint.class, RESOURCE_URI + "sprint/"+sprintId,jsonData);
	}

	public static Sprint moveIssuesToSprint(RestClient restclient, int sprintId, List issueKeys) throws JiraException, URISyntaxException {
		JSONObject jsonData = new JSONObject();
		jsonData.put("issues", issueKeys);
		//System.err.println("jsonData::::::::::::"+jsonData.toString());
		 return AgileResource.post(restclient,Sprint.class, RESOURCE_URI + "sprint/"+sprintId+"/issue",jsonData);
	}

	public static Sprint moveIssuesToBacklog(RestClient restclient, List issueKeys) throws JiraException {
		JSONObject jsonData = new JSONObject();
		jsonData.put("issues", issueKeys);
		return AgileResource.post(restclient,Sprint.class, RESOURCE_URI + "/backlog/issue",jsonData);
	}
}

