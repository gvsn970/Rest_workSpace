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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;

/**
 * An Agile extension to the JIRA client.
 *
 * @author pldupont
 * @see "https://docs.atlassian.com/jira-software/REST/cloud/"
 */
@Service
public class AgileClient {

	RestClient restclient;

	@Autowired
	JiraClient jira;

	/**
	 * Creates an Agile client.
	 *
	 * @param jira
	 *            JIRA client
	 */

	public AgileClient(JiraClient jira) {
		restclient = jira.getRestClient();
	}

	/**
	 * Retrieves the board with the given ID.
	 *
	 * @param id
	 *            Board ID
	 * @return a Board instance
	 * @throws JiraException
	 *             when something goes wrong
	 */
	public Board getBoard(long id) throws JiraException {
		return Board.get(restclient, id);
	}

	/**
	 * Retrieves all boards visible to the session user.
	 *
	 * @return a list of boards
	 * @throws JiraException
	 *             when something goes wrong
	 */
	public List<Board> getBoards() throws JiraException {
		return Board.getAll(restclient);
	}

	/**
	 * Retrieves the sprint with the given ID.
	 *
	 * @param id
	 *            Sprint ID
	 * @return a Sprint instance
	 * @throws JiraException
	 *             when something goes wrong
	 */
	public Sprint getSprint(long id) throws JiraException {
		return Sprint.get(restclient, id);
	}

	/**
	 * Retrieves the issue with the given ID.
	 *
	 * @param id
	 *            Issue ID
	 * @return an Issue instance
	 * @throws JiraException
	 *             when something goes wrong
	 */
	public Issue getIssue(long id) throws JiraException {
		return Issue.get(restclient, id);
	}

	/**
	 * Retrieves the issue with the given Key.
	 *
	 * @param key
	 *            Issue Key
	 * @return an Issue instance
	 * @throws JiraException
	 *             when something goes wrong
	 */
	public Issue getIssue(String key) throws JiraException {
		return Issue.get(restclient, key);
	}

	/**
	 * Retrieves the epic with the given ID.
	 *
	 * @param id
	 *            Epic ID
	 * @return an Epic instance
	 * @throws JiraException
	 *             when something goes wrong
	 */
	public Epic getEpic(long id) throws JiraException {
		return Epic.get(restclient, id);
	}

	public RestClient getRestclient() {
		return restclient;
	}

	public List<Issue> getListOfIssuesByBoardId(long longValue) throws JiraException {
		return Board.getBacklogByBoardId(restclient, longValue);
	}

	public List<Sprint> getSprintsByBoardId(long boardId) throws JiraException {

		return Board.getSprintsByBoardId(restclient, boardId);
	}

	public List<Issue> getIssuesBySprintAttributeId(long sprintId) throws JiraException {

		return Sprint.getIssuesBySprintAttributeId(restclient, sprintId);
	}

	public List<Epic> getEpicsByBoardId(long boardId) throws JiraException {

		return Board.getEpicsByBoardId(restclient, boardId);
		//return Board.getEpics(restclient,boardId);
	}

	public List<Issue> getIssuesByEpicId(long epicId) throws JiraException {

		return Epic.getIssuesByEpicId(restclient, epicId);
	}

	public Sprint createSprint(String sprintName, long boardId, String startDate, String endDate, String goal) throws JiraException, URISyntaxException {
		return Sprint.createSprint(restclient,sprintName,boardId,startDate,endDate,goal);
	}

	public Board createBoard(String boardName, String type, long filterId, JiraConfigurationModel jcm) throws JiraException, URISyntaxException {
		return Board.createBoard(restclient,boardName,type,filterId);
	}

	public Project createProject(String assigneeType, String description,
			String projectKey, String lead, String projectTemplateKey,
			String projectTypeKey, String name) throws JiraException, URISyntaxException {
		return Project.createProject(restclient,assigneeType,description,projectKey,lead,projectTemplateKey,projectTypeKey,name);
	}

	public Issue createIssue(String summary, String projectId, String issueType, 
			long sprintId) throws JiraException, URISyntaxException {
		return Issue.createIssue(restclient,summary,projectId,issueType,sprintId);
	}

	public Issue createEpic(String summary, String projectId, String issueType, long sprintId, String epicValue) throws JiraException, URISyntaxException {
		return Issue.createEpic(restclient,summary,projectId,issueType,sprintId,epicValue);
	}

	public List<Priority> priorityList() throws JiraException {
		return Priority.getPriorityList(restclient);
	}

	public List<Project> getProjectsList() throws JiraException {
		return Project.getProjectsList(restclient);
	}

	public List<IssueType> getIssueTypes() throws JiraException {
		return IssueType.getIssueTypes(restclient);
	}

	public List<AvatarModel> getAvatarIdsForProject() throws JiraException {
		return AvatarModel.getAvatarIdsForProject(restclient);
	}

	public Issue createBacklog(String summary, String projectId, String issueType) throws JiraException, URISyntaxException {
		return Issue.createBacklog(restclient,summary,projectId,issueType);
	}

	public Issue updateIssue(String issueId, String summary, String issueType, String assignee) throws JiraException, URISyntaxException {
		return Issue.updateIssue(restclient,issueId,summary,issueType,assignee);
	}

	public Issue deleteIssue(String issueId) throws JiraException {
		return Issue.deleteIssue(restclient,issueId);
	}

	public Project deleteProject(String projectId) throws JiraException {
		return Project.deleteProject(restclient,projectId);
	}

	public Sprint deleteSprint(long sprintId) throws JiraException {
		return Sprint.deleteSprint(restclient,sprintId);
	}

	public Sprint updateSprint(long sprintId, String sprintName, String startDate, String endDate, String goal) throws JiraException, URISyntaxException {
		return Sprint.updateSprint(restclient,sprintId,sprintName,startDate,endDate,goal);
	}

	public Project updateProject( String description, String projectId, String lead,String name) throws JiraException, URISyntaxException {
		return Project.updateProject(restclient,description,projectId,lead,name);
	}

	public Sprint moveIssuesToSprint(int sprintId, List issueKeys) throws JiraException, URISyntaxException {
		return Sprint.moveIssuesToSprint(restclient,sprintId,issueKeys);
	}

	public Sprint moveIssuesToBacklog(List issueKeys) throws JiraException {
		return Sprint.moveIssuesToBacklog(restclient,issueKeys);
	}
}
