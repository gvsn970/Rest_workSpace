package com.nexiilabs.autolifecycle.jira;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nexiilabs.autolifecycle.productline.Response;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class JiraAgileClientUtility {

	RestClient restclient;

	JSONObject json;

	Board boards;

	Sprint sprint;

	Epic epic;

	AgileClient agileClient;

	JiraClient jira;

	Issue issue;

	public List<BoardModel> getJiraBoardDetails(JiraConfigurationModel jcm) throws Exception {

		List<BoardModel> boardModel1 = new ArrayList<>();
		// Board board1 = null;

		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		// AgileClient agileClient = new AgileClient(jira);
		AgileClient agileClient = new AgileClient(jira);

		/* Retrieve all Boards */
		System.out.println("*************all Boards*************");
		List<Board> allBoards = agileClient.getBoards();
		System.out.println(allBoards);
		BoardModel boardModel = null;

		String name = null;
		String projectId = null;
		for (Board boards : allBoards) {
			boardModel = new BoardModel();
			System.out.println("Board id:" + boards.getAttribute("id"));
			System.out.println("Board Name:" + boards.getName());
			System.out.println("Board Name:" + boards.getAttribute("name"));

			boardModel.setBoardId(((Integer) boards.getAttribute("id")).longValue());
			boardModel.setBoardName((String) boards.getAttribute("name"));

			JSONObject obj = (JSONObject) boards.getAttribute("location");
			if (!obj.equals(null)) {
				System.out.println("********project list:" + obj.toString());

				System.out.println("***************projectid :" + obj.getString("projectId"));
				boardModel.setProjectId(obj.getString("projectId"));
				System.out.println("***************project name :" + obj.getString("name"));
				boardModel.setProjectName(obj.getString("name"));
			}
			boardModel1.add(boardModel);
		}
		return boardModel1;
	}

	public List<BackLogsModel> getbacklogsByboardId(long boardId, JiraConfigurationModel jcm) throws Exception {

		List<BackLogsModel> backLogsModel = new ArrayList<>();
		BackLogsModel backLogsModel1 = null;
		// try {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		System.out.println(jcm.toString());
		System.out.println("backlog list*******************");
		boards = new Board(restclient, json);

		// Issue issue=new Issue(restclient, json);
		// List<Issue> backlog1=new ArrayList<Issue>();
		// List<Issue> backlog = boards.getBacklogByBoardId(boardId);

		List<Issue> backlog = agileClient.getListOfIssuesByBoardId(boardId);
		System.out.println("***List of Backlogs by BoardId***");
		System.out.println(backlog.toString());
		for (Issue i : backlog) {
			backLogsModel1 = new BackLogsModel();
			System.out.println("Issue id: " + i.getAttribute("id"));
			// System.out.println("id:"+i.getId());
			System.out.println("Issue Key: " + i.getKey());
			System.out.println("Issue Name:" + i.getName());
			System.out.println("Issue Description:" + i.getDescription());
			System.out.println("Issue Assignee:" + i.getAssignee());
			System.out.println("Issue Type: " + i.getIssueType());
			System.out.println("Issue Created on:" + i.getCreated());
			System.out.println("Issue Created by: " + i.getCreator());

			System.out.println("Issue Type: " + i.getIssueType().getName());
			// backLogsModel1.setIssueid(((Integer) i.getAttribute("id")).longValue());
			backLogsModel1.setIssueid(Long.parseLong((String) i.getAttribute("id")));
			backLogsModel1.setIssueKey(i.getKey());
			backLogsModel1.setIssueName(i.getName());
			backLogsModel1.setIssueDescription(i.getDescription());
			backLogsModel1.setIssueCreatedon(i.getCreated());
			backLogsModel1.setIssueStatusId(Long.parseLong((String) i.getStatus().getAttribute("id")));
			backLogsModel1.setIssueStatus((String) i.getStatus().getAttribute("name"));
			backLogsModel1.setIssueTypeId(Long.parseLong((String) i.getIssueType().getAttribute("id")));
			backLogsModel1.setIssueTypeName((String) i.getIssueType().getAttribute("name"));

			// backLogsModel1.setIssueAssignee(i.getAssignee());
			if (i.getAssignee() == null) {
				System.out.println("anaaaaaaa::::::::::::::::::::::::::::::");
				backLogsModel1.setIssueAssigneeName("null");
				backLogsModel1.setIssueAssigneeEmail("null");
			} else {
				backLogsModel1.setIssueAssigneeName((String) i.getAssignee().getDisplayName());
				backLogsModel1.setIssueAssigneeEmail((String) i.getAssignee().getEmailAddress());
			}
			/*
			 * backLogsModel1.setIssueAssigneeName(i.getAssignee().getDisplayName());
			 * backLogsModel1.setIssueAssigneeEmail(i.getAssignee().getEmailAddress());
			 */

			// backLogsModel1.setIssueType(i.getIssueType());
			// backLogsModel1.setIssueTypeName(i.getIssueType().getName());
			backLogsModel1.setIssueTypeDescription(i.getIssueType().getDescription());

			// backLogsModel1.setIssueCreatedby(i.getCreator());
			backLogsModel1.setIssueCreaterName(i.getCreator().getDisplayName());
			backLogsModel1.setIssueCreaterEmail(i.getCreator().getEmailAddress());

			backLogsModel.add(backLogsModel1);

		}
		/*
		 * } catch (JiraException j) { j.printStackTrace(); }
		 */

		return backLogsModel;
	}

	public List<SprintModel> listOfSprintsByBoardId(long boardId, JiraConfigurationModel jcm) throws Exception {
		List<SprintModel> listOfSprints = new ArrayList<>();
		SprintModel sprintModel = null;

		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);

		// List<Sprint> sprints = boards.getSprintsByBoardId(boardId);
		List<Sprint> sprints = agileClient.getSprintsByBoardId(boardId);
		System.out.println("***List of Sprints by BoardId***");
		System.out.println(sprints.toString());

		for (Sprint s1 : sprints) {
			sprintModel = new SprintModel();
			System.out.println("---------------");
			System.out.println("SprintId:" + s1.getAttribute("id"));
			System.out.println("Sprint Name:" + s1.getName());
			System.out.println("SprintId:" + s1.getAttribute("name"));
			System.out.println("Board Id of Sprint:" + s1.getOriginBoardId());
			System.out.println("Sprint goal:"+s1.getState());
			System.out.println("date:"+s1.getStartDate());
			System.out.println("date:"+s1.getEndDate());
			System.out.println("date:"+s1.getCompleteDate());
			
			

			sprintModel.setSprintId(((Integer) s1.getAttribute("id")).longValue());
			// sprintModel.setSprintName(s1.getName());
			sprintModel.setSprintName((String) s1.getAttribute("name"));
			sprintModel.setBoardIdofSprint(s1.getOriginBoardId());
			sprintModel.setStartDate(s1.getStartDate());
			sprintModel.setEndDate(s1.getEndDate());
			sprintModel.setCompleteDate(s1.getCompleteDate());
			sprintModel.setSprintStatus(s1.getState());
			

			listOfSprints.add(sprintModel);

		}
		return listOfSprints;
	}

	public List<BackLogsModel> listOfIssuesBySprintId(long sprintId, JiraConfigurationModel jcm) throws Exception {

		List<BackLogsModel> backLogsModel = new ArrayList<>();
		BackLogsModel backLogsModel1 = null;

		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);

		List<Issue> issues = agileClient.getIssuesBySprintAttributeId(sprintId);
		System.out.println("***List of Issues by SprintId***");
		System.out.println(issues.toString());
		for (Issue i : issues) {
			backLogsModel1 = new BackLogsModel();
			System.out.println("Issue id: " + i.getAttribute("id"));
			System.out.println("Issue Key: " + i.getKey());
			System.out.println("Issue Name:" + i.getName());
			System.out.println("Issue Description:" + i.getDescription());
			System.out.println("Issue Assignee:" + i.getAssignee());
			System.out.println("Issue Type: " + i.getIssueType());
			System.out.println("Issue Created on:" + i.getCreated());
			System.out.println("Issue Created by: " + i.getCreator());

			System.out.println("Issue Status: " + i.getAttribute("status"));
			System.out.println("Issue Status name: " + i.getStatus().getAttribute("name"));
			System.out.println("Issue Status id: " + i.getStatus().getAttribute("id"));

			System.out.println("Issue Type: " + i.getIssueType().getAttribute("id"));
			System.out.println("Issue Type: " + i.getIssueType().getAttribute("name"));
			System.out.println("Issue Sprint Id: " +i.getSprint().getAttribute("id"));

			// System.out.println("Issue Assigne Id: " + i.getAssignee().);
			// System.out.println("Issue Assigne Id: " +
			// i.getAssignee().getAttribute("id"));
			// System.out.println("Issue Assigne name: " +
			// i.getAssignee().getAttribute("name"));

			/*
			 * System.out.println("Issue Creater Id: " +
			 * i.getReporter().getAttribute("accountid"));
			 * System.out.println("Issue Creater Name: " +
			 * i.getReporter().getAttribute("name"));
			 */

			/* issue creater issue */
			// System.out.println("Issue Creater Id: " + (Integer.parseInt((String)
			// i.getCreator().getAttribute("id"))));

			// backLogsModel1.setIssueid(((Integer) i.getAttribute("id")).longValue());
			backLogsModel1.setIssueid(Long.parseLong((String) i.getAttribute("id")));
			backLogsModel1.setIssueKey(i.getKey());
			backLogsModel1.setIssueName(i.getName());
			backLogsModel1.setIssueDescription(i.getDescription());
			backLogsModel1.setIssueCreatedon(i.getCreated());
			backLogsModel1.setIssueStatusId(Long.parseLong((String) i.getStatus().getAttribute("id")));
			backLogsModel1.setIssueStatus((String) i.getStatus().getAttribute("name"));
			backLogsModel1.setIssueTypeId(Long.parseLong((String) i.getIssueType().getAttribute("id")));
			backLogsModel1.setIssueTypeName((String) i.getIssueType().getAttribute("name"));
			backLogsModel1.setSprintId( i.getSprint().getId());
			// backLogsModel1.setSprint(i.getSprint());
			System.err.println("asineeeeeeeeeeee" + i.getAssignee());
			if (i.getAssignee() == null) {
				System.out.println("anaaaaaaa::::::::::::::::::::::::::::::");
				backLogsModel1.setIssueAssigneeName("null");
				backLogsModel1.setIssueAssigneeEmail("null");
			} else {
				backLogsModel1.setIssueAssigneeName((String) i.getAssignee().getDisplayName());
				backLogsModel1.setIssueAssigneeEmail((String) i.getAssignee().getEmailAddress());
			}

			// backLogsModel1.setIssueTypeName(i.getIssueType().getName());
			backLogsModel1.setIssueTypeDescription(i.getIssueType().getDescription());

			backLogsModel1.setIssueCreaterName(i.getCreator().getDisplayName());
			backLogsModel1.setIssueCreaterEmail(i.getCreator().getEmailAddress());

			backLogsModel.add(backLogsModel1);
		}
		return backLogsModel;

	}

	public List<EpicModel> listOfEpicsByBoardId(long boardId, JiraConfigurationModel jcm) throws Exception {
		List<EpicModel> listofEpics = new ArrayList<>();
		EpicModel epicModel = null;
		// List<Epic> epicsofBoard = null;

		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);

		// epicsofBoard = boards.getEpicsByBoardId(boardId);
		List<Epic> epicsofBoard = agileClient.getEpicsByBoardId(boardId);
		System.out.println("**epics of Board**");
		System.out.println(epicsofBoard.toString());
		for (Epic e : epicsofBoard) {
			epicModel = new EpicModel();

			System.out.println("Epic Id:" + ((Integer) e.getAttribute("id")).longValue());
			epicModel.setEpicId(((Integer) e.getAttribute("id")).longValue());

			System.out.println("Epic Name:" + e.getName());
			epicModel.setEpicName(e.getName());

			System.out.println("Epic key:" + e.getKey());
			epicModel.setKey(e.getKey());

			System.out.println("Epic summary:" + e.getSummary());
			epicModel.setSummary(e.getSummary());

			/*
			 * System.out.println("Issues Of Epic:"+e.getIssues());
			 * epicModel.setIssues(e.getIssues());
			 */

			listofEpics.add(epicModel);

		}

		return listofEpics;

	}

	public List<BackLogsModel> listOfissuesbyEpicId(long epicId, JiraConfigurationModel jcm) throws Exception {
		// listofissuesofEpic = null;
		List<BackLogsModel> backLogsModel = new ArrayList<>();
		BackLogsModel backLogsModel1 = null;

		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);

		// listofissuesofEpic = epic.getIssuesByEpicId(epicId);
		List<Issue> listofissuesofEpic = agileClient.getIssuesByEpicId(epicId);
		System.out.println("list of issues of epic :" + listofissuesofEpic.toString());
		for (Issue i : listofissuesofEpic) {
			backLogsModel1 = new BackLogsModel();
			System.out.println("Issue id: " + i.getAttribute("id"));
			// System.out.println("id:"+i.getId());
			System.out.println("Issue Key: " + i.getKey());
			System.out.println("Issue Name:" + i.getName());
			System.out.println("Issue Description:" + i.getDescription());
			System.out.println("Issue Assignee:" + i.getAssignee());
			System.out.println("Issue Type: " + i.getIssueType());
			System.out.println("Issue Created on:" + i.getCreated());
			System.out.println("Issue Created by: " + i.getCreator());
			// System.out.println("Issues of
			// epic**************************:"+i.getAttribute("sprint").toString());
			// try {

			/*
			 * System.out.println("obj1**********:"+obj1.toString());
			 * System.out.println("obj1 sprint Id**********:"+obj1.getId());
			 */

			try {
				if ((Object) i.getSprint().getAttribute("id") != null) {
					JSONObject obj = (JSONObject) i.getAttribute("sprint");
					// if (!obj.equals(null)) {
					System.out.println("********Issue of Sprint:" + obj.toString());
					System.out.println("***************sprintid :" + obj.getString("id"));
					backLogsModel1.setSprintId(Long.parseLong((String) obj.getString("id")));
					System.out.println("***************sprint name :" + obj.getString("name"));
					backLogsModel1.setSprintname(obj.getString("name"));

				}

				else {
					backLogsModel1.setSprintId(0);
					// System.out.println("***************sprint name :" + obj.getString("name"));
					backLogsModel1.setSprintname(null);
				}
			} catch (NullPointerException np) {
				np.getMessage();
			}

			// backLogsModel1.setIssueid(((Integer) i.getAttribute("id")).longValue());
			backLogsModel1.setIssueid(Long.parseLong((String) i.getAttribute("id")));
			backLogsModel1.setIssueKey(i.getKey());
			backLogsModel1.setIssueName(i.getName());
			backLogsModel1.setIssueDescription(i.getDescription());
			backLogsModel1.setIssueCreatedon(i.getCreated());
			backLogsModel1.setIssueStatusId(Long.parseLong((String) i.getStatus().getAttribute("id")));
			backLogsModel1.setIssueStatus((String) i.getStatus().getAttribute("name"));
			backLogsModel1.setIssueTypeId(Long.parseLong((String) i.getIssueType().getAttribute("id")));
			backLogsModel1.setIssueTypeName((String) i.getIssueType().getAttribute("name"));
			// backLogsModel1.setSprint(i.getSprint());

			if (i.getAssignee() == null) {
				System.out.println("anaaaaaaa::::::::::::::::::::::::::::::");
				backLogsModel1.setIssueAssigneeName("null");
				backLogsModel1.setIssueAssigneeEmail("null");
			} else {
				backLogsModel1.setIssueAssigneeName((String) i.getAssignee().getDisplayName());
				backLogsModel1.setIssueAssigneeEmail((String) i.getAssignee().getEmailAddress());
			}

			// backLogsModel1.setIssueType(i.getIssueType());
			// backLogsModel1.setIssueTypeName(i.getIssueType().getName());
			backLogsModel1.setIssueTypeDescription(i.getIssueType().getDescription());

			// backLogsModel1.setIssueCreatedby(i.getCreator());
			backLogsModel1.setIssueCreaterName(i.getCreator().getDisplayName());
			backLogsModel1.setIssueCreaterEmail(i.getCreator().getEmailAddress());
			backLogsModel.add(backLogsModel1);

		}
		System.out.println("***List of Issues by EpicId***");
		System.out.println(backLogsModel.toString());

		return backLogsModel;
	}

	public Sprint createSprint(String sprintName, long boardId, String startDate, String endDate,
			JiraConfigurationModel jcm, String goal) throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Sprint response = agileClient.createSprint(sprintName, boardId, startDate, endDate, goal);
		return response;
	}

	public Board createBoard(String boardName, String type, long filterId, JiraConfigurationModel jcm)
			throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Board response = agileClient.createBoard(boardName, type, filterId, jcm);
		return response;
	}

	public Project createProject(String assigneeType, String description, String projectKey, String lead,
			String projectTemplateKey, String projectTypeKey, String name, JiraConfigurationModel jcm)
			throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Project response = agileClient.createProject(assigneeType, description, projectKey, lead, projectTemplateKey,
				projectTypeKey, name);
		return response;
	}

	public Issue createIssue(String summary, String projectId, String issueType, int fk_user_id,
			JiraConfigurationModel jcm, long sprintId) throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Issue response = agileClient.createIssue(summary, projectId, issueType, sprintId);
		return response;
	}

	public Issue createEpic(String summary, String projectId, String issueType, int fk_user_id,
			JiraConfigurationModel jcm, long sprintId, String epicValue) throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Issue response = agileClient.createEpic(summary, projectId, issueType, sprintId, epicValue);
		return response;
	}

	public List<Priority> priorityList(JiraConfigurationModel jcm) throws JiraException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		List<Priority> priorities = agileClient.priorityList();
		System.out.println(priorities.toString());
		return priorities;
	}

	public List<Project> getProjectsList(JiraConfigurationModel jcm) throws JiraException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		List<Project> projects = agileClient.getProjectsList();
		System.out.println(projects.toString());
		return projects;
	}

	public List<IssueType> getIssueTypes(JiraConfigurationModel jcm) throws JiraException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		List<IssueType> issueTypes = agileClient.getIssueTypes();
		return issueTypes;
	}

	public List<AvatarModel> getAvatarIdsForProject(JiraConfigurationModel jcm) throws JiraException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		List<AvatarModel> avatarIds = agileClient.getAvatarIdsForProject();
		return avatarIds;
	}

	public Issue createBacklog(String summary, String projectId, String issueType, int fk_user_id,
			JiraConfigurationModel jcm) throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Issue response = agileClient.createBacklog(summary, projectId, issueType);
		return response;
	}

	public Response testJiraCredentials(JiraConfigurationModel jcm) {
		Response response = new Response();
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		try {
			getJiraBoardDetails(jcm);
			response.setStatusCode(1);
			response.setMessage("Valid Credentials");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode(0);
			response.setMessage("Invalid Jira credentials.Please Enter Valid Jira Credentials.");
		}
		return response;

	}

	public Issue updateIssue(String issueId, String summary, String issueType, String assignee, int fk_user_id,
			JiraConfigurationModel jcm) throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Issue response = agileClient.updateIssue(issueId, summary, issueType, assignee);
		return response;

	}

	public Issue deleteIssue(String issueId, int fk_user_id, JiraConfigurationModel jcm) throws JiraException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Issue response = agileClient.deleteIssue(issueId);
		return response;
	}

	public Project deleteProject(String projectId, JiraConfigurationModel jcm) throws JiraException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Project response = agileClient.deleteProject(projectId);
		return response;
	}

	public Sprint deleteSprint(long sprintId, JiraConfigurationModel jcm) throws JiraException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Sprint response = agileClient.deleteSprint(sprintId);
		return response;
	}

	public Sprint updateSprint(long sprintId, String sprintName, String startDate, String endDate, String goal,
			JiraConfigurationModel jcm) throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Sprint response = agileClient.updateSprint(sprintId, sprintName, startDate, endDate, goal);
		return response;
	}

	public Project updateProject(String description, String projectId, String lead, String name,
			JiraConfigurationModel jcm) throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Project response = agileClient.updateProject(description, projectId, lead, name);
		return response;
	}

	public Sprint moveIssuesToSprint(JiraConfigurationModel jcm, int sprintId, List issueKeys)
			throws JiraException, URISyntaxException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Sprint response = agileClient.moveIssuesToSprint(sprintId, issueKeys);
		return response;
	}

	public Sprint moveIssuesToBacklog(JiraConfigurationModel jcm, List issueKeys) throws JiraException {
		BasicCredentials creds = new BasicCredentials(jcm.getUser_name(), jcm.getPassword());
		JiraClient jira = new JiraClient(jcm.getApplication_url(), creds);
		AgileClient agileClient = new AgileClient(jira);
		Sprint response = agileClient.moveIssuesToBacklog(issueKeys);
		return response;
	}
}
