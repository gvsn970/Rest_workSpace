package com.nexiilabs.autolifecycle.jira;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexiilabs.autolifecycle.features.FeaturesModel;
import com.nexiilabs.autolifecycle.features.FeaturesService;
import com.nexiilabs.autolifecycle.features.FeatureslistModel;
import com.nexiilabs.autolifecycle.product.Product;
import com.nexiilabs.autolifecycle.productline.Response;
import com.nexiilabs.autolifecycle.releases.ReleaseListModel;
import com.nexiilabs.autolifecycle.releases.ReleaseModel;
import com.nexiilabs.autolifecycle.releases.ReleaseService;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

import net.rcarz.jiraclient.JiraException;

@RestController
@RequestMapping("/jira")
public class JiraController {

	@Autowired
	JiraService service;

	@Autowired
	ReleaseService releaseService;

	@Autowired
	FeaturesService featuresService;

	@Autowired
	JiraAgileClientUtility agileClientUtility;

	/* 1.list of boards by userId */
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getboards", method = RequestMethod.GET, produces = "application/Json")
	public List<?> getBoards(@RequestParam("fk_user_id") int fk_user_id) throws Exception {

		// Getting credentials by userId.
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		List<BoardModel> boards = null;
		try {
			boards = agileClientUtility.getJiraBoardDetails(jcm);
		} catch (JiraException ex) {
			System.err.println(ex.getMessage());
			if (ex.getCause() != null) {
				System.err.println(ex.getCause().getMessage());
				if ((ex.getCause().getMessage()).contains(("401 Unauthorized")) || (ex.getCause().getMessage())
						.contains(("Target host must not be null, or set in parameters."))) {
					String s1 = "Invalid Jira Credentials.";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
					String s1 = "Your Atlassian Cloud subscription requires renewal";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				}
			}
		}
		return boards;
	}

	/* 1.1list of backlogs by BoardId */
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getbacklogsbyboardId", method = RequestMethod.GET, produces = "application/Json")
	public List<?> getBackLogsByBoardId(@RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("BoardId") long BoardId) throws Exception {

		// Getting credentials by userId.
		List<BackLogsModel> backlogs = null;
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		try {
			backlogs = agileClientUtility.getbacklogsByboardId(BoardId, jcm);
		} catch (JiraException ex) {
			System.err.println(ex.getMessage());

			if (ex.getCause() != null) {
				System.err.println(ex.getCause().getMessage());
				if ((ex.getCause().getMessage()).contains(("401 Unauthorized")) || (ex.getCause().getMessage())
						.contains(("Target host must not be null, or set in parameters."))) {
					String s1 = "Invalid Jira Credentials.";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
					String s1 = "Your Atlassian Cloud subscription requires renewal";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				}
			}
		}
		return backlogs;

	}

	/* 2.list of Boards by 1. */
	/* 2.1 list of sprints by boardId */
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofsprintsbyboardId", method = RequestMethod.GET, produces = "application/Json")
	public List<?> getListofSprintsbyBoardId(@RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("BoardId") long BoardId) throws Exception {
		// Getting credentials by userId.
		List<SprintModel> sprints = null;
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		try {
			sprints = agileClientUtility.listOfSprintsByBoardId(BoardId, jcm);
		} catch (JiraException ex) {
			System.err.println(ex.getMessage());

			if (ex.getCause() != null) {
				System.err.println(ex.getCause().getMessage());
				if ((ex.getCause().getMessage()).contains(("401 Unauthorized")) || (ex.getCause().getMessage())
						.contains(("Target host must not be null, or set in parameters."))) {
					String s1 = "Invalid Jira Credentials.";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
					String s1 = "Your Atlassian Cloud subscription requires renewal";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				}
			}
		}
		return sprints;
	}

	/* 2.2 list of issues by sprintId */
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofissuesbysprintId", method = RequestMethod.GET, produces = "application/Json")
	public List<?> getListofIssuesbySprintId(@RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("SprintId") long SprintId) throws Exception {

		// Getting credentials by userId.
		List<BackLogsModel> issues = null;
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		try {
			issues = agileClientUtility.listOfIssuesBySprintId(SprintId, jcm);
		} catch (JiraException ex) {
			System.err.println(ex.getMessage());

			if (ex.getCause() != null) {
				System.err.println(ex.getCause().getMessage());
				if ((ex.getCause().getMessage()).contains(("401 Unauthorized")) || (ex.getCause().getMessage())
						.contains(("Target host must not be null, or set in parameters."))) {
					String s1 = "Invalid Jira Credentials.";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
					String s1 = "Your Atlassian Cloud subscription requires renewal";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				}
			}
		}

		return issues;
	}

	/* 3.list of Boards by 1. */
	/* 3.1 list of epics by boardId */
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofepicsbyboardId", method = RequestMethod.GET, produces = "application/Json")
	public List<?> getListofEpicsbyBoardId(@RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("BoardId") long BoardId) throws Exception {

		// Getting credentials by userId.
		List<EpicModel> epics = null;
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		try {
			epics = agileClientUtility.listOfEpicsByBoardId(BoardId, jcm);
		} catch (JiraException ex) {
			System.err.println(ex.getMessage());

			if (ex.getCause() != null) {
				System.err.println(ex.getCause().getMessage());
				if ((ex.getCause().getMessage()).contains(("401 Unauthorized")) || (ex.getCause().getMessage())
						.contains(("Target host must not be null, or set in parameters."))) {
					String s1 = "Invalid Jira Credentials.";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
					String s1 = "Your Atlassian Cloud subscription requires renewal";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				}
			}
		}

		return epics;
	}

	/* 3.2 list of issues by epicId */
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofissuesbyepicId", method = RequestMethod.GET, produces = "application/Json")
	public List<?> getListofIssuesbyEpicId(@RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("epicId") long epicId) throws Exception {

		// Getting credentials by userId.
		List<BackLogsModel> listofissues = null;
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		try {
			listofissues = agileClientUtility.listOfissuesbyEpicId(epicId, jcm);
		} catch (JiraException ex) {
			System.err.println(ex.getMessage());

			if (ex.getCause() != null) {
				System.err.println(ex.getCause().getMessage());
				if ((ex.getCause().getMessage()).contains(("401 Unauthorized")) || (ex.getCause().getMessage())
						.contains(("Target host must not be null, or set in parameters."))) {
					String s1 = "Invalid Jira Credentials.";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
					String s1 = "Your Atlassian Cloud subscription requires renewal";
					List<String> s = new ArrayList<>();
					s.add(s1);
					return s;
				}
			}
		}
		return listofissues;
	}

	/* Jira credentials configuration */
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/addcredentials", method = RequestMethod.POST, produces = "application/Json")
	public Response addCredentials(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("url") String url, @RequestParam("fk_user_id") int fk_user_id) {
		Response response = null;
		boolean isUserNotExist = service.isUserNotExist(fk_user_id);
		if (isUserNotExist) {
			JiraConfigurationModel jcm = new JiraConfigurationModel();
			jcm.setApplication_url(url);
			jcm.setUser_name(username);
			jcm.setPassword(password);
			Response response1 = agileClientUtility.testJiraCredentials(jcm);
			if (response1.getStatusCode() == 1) {
				response = service.addJiraCredentials(username, password, url, fk_user_id);
			} else {
				return response1;
			}
		} else {
			response = new Response();
			response.setStatusCode(0);
			response.setMessage("Jira Credentials already exist with this User Id:" + fk_user_id);
		}
		return response;
	}

	/* Jira Credentials of User */
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/jiracredentialsofuser", method = RequestMethod.GET, produces = "application/Json")
	public ResponseEntity<?> getjiraDetails(@RequestParam("fk_user_id") int fk_user_id) {

		List<JiraConfigurationModel> jiramodel = service.getJiraDetails(fk_user_id);
		Response response = null;
		int list = jiramodel.size();
		if (list > 0) {
			return new ResponseEntity<List<JiraConfigurationModel>>(jiramodel, HttpStatus.OK);
		} else {
			response = new Response();
			response.setStatusCode(0);
			response.setMessage("No Jira Credentials Found to this User.Please add your Jira Credentials.");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updatecredentials", method = RequestMethod.POST, produces = "application/Json")
	public Response updateCredentials(@RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("url") String url,
			@RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("jira_configuration_id") int jira_configuration_id) {
		Response response = null;
		JiraConfigurationModel jcm = new JiraConfigurationModel();
		jcm.setApplication_url(url);
		jcm.setUser_name(username);
		jcm.setPassword(password);
		Response response1 = agileClientUtility.testJiraCredentials(jcm);
		if (response1.getStatusCode() == 1) {
			response = service.updateJiraCredentials(username, password, url, fk_user_id, jira_configuration_id);
		} else {
			return response1;
		}
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/importissuesofepic", method = RequestMethod.POST, produces = "application/Json")
	public List<Response> addissuesofEpic(
			// @RequestParam("fk_feature_id") int fk_feature_id,
			@RequestParam("fk_user_id") int fk_user_id, @RequestParam("product_id") int product_id,
			@RequestParam("EpicId") long EpicId, @RequestParam("EpicName") String EpicName,
			@RequestParam("key") String EpicKey, @RequestParam("listofIssueIds") String listofIssueIds)
			throws Exception {

		List<Product> productlist = service.getproductConfigurationwithJiraProject(product_id);
		String boardName = productlist.get(0).getJiraBoardName();
		long boardId = productlist.get(0).getFk_jira_boardid();
		String projectName = productlist.get(0).getJiraprojectName();
		String projectId = productlist.get(0).getFk_jira_projectid();

		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);

		List<BackLogsModel> backlogs = agileClientUtility.getbacklogsByboardId(boardId, jcm);

		List<ImportBacklogModel> backLogsModel = new ArrayList<>();
		ImportBacklogModel importModel;
		List<Integer> listofIds = new ArrayList<Integer>();

		for (String s : listofIssueIds.split(","))
			listofIds.add(Integer.parseInt(s));
		System.out.println(listofIds);

		for (BackLogsModel b : backlogs) {
			for (Integer id : listofIds) {
				if (b.getIssueid() == id) {
					importModel = new ImportBacklogModel();

					importModel.setIssueid(b.getIssueid());
					importModel.setIssueName(b.getIssueName());
					importModel.setIssueDescription(b.getIssueDescription());
					importModel.setIssueKey(b.getIssueKey());
					importModel.setIssueCreatedon(b.getIssueCreatedon());

					importModel.setIssueTypeId(b.getIssueTypeId());
					importModel.setIssueTypeName(b.getIssueTypeName());
					importModel.setIssueTypeDescription(b.getIssueTypeDescription());

					importModel.setIssueStatusId((int) (b.getIssueStatusId()));
					importModel.setIssueStatus(b.getIssueStatus());

					importModel.setIssueCreaterName(b.getIssueCreaterName());
					importModel.setIssueCreaterEmail(b.getIssueCreaterEmail());
					importModel.setIssueAssigneeName(b.getIssueAssigneeName());
					importModel.setIssueAssigneeEmail(b.getIssueAssigneeEmail());
					backLogsModel.add(importModel);

				}
			}

		}

		System.out.println("BackLogsModel:" + backLogsModel.toString());

		List<Response> response = service.addIssuesOfEpic(backLogsModel, EpicId, EpicName, EpicKey, boardId, boardName);
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofimportedbacklogsofboard", method = RequestMethod.GET, produces = "application/Json")
	public List<FeatureJiraMetaDataModel> listOfBacklogsofBoard(@RequestParam("fk_feature_id") int fk_feature_id,
			@RequestParam("BoardId") long boardId) {
		return service.listOfBacklogsofBoard(fk_feature_id, boardId);

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofimportedissuesofsprint", method = RequestMethod.GET, produces = "application/Json")
	public List<FeatureJiraMetaDataModel> listOfImportedIssuesofSprint(@RequestParam("fk_feature_id") int fk_feature_id,
			@RequestParam("BoardId") long boardId, @RequestParam("SprintId") long SprintId) {
		return service.listOfImportedIssuesofSprint(fk_feature_id, boardId, SprintId);

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofimportedissuesofepic", method = RequestMethod.GET, produces = "application/Json")
	public List<FeatureJiraMetaDataModel> listOfImportedIssuesofEpic(@RequestParam("fk_feature_id") int fk_feature_id,
			@RequestParam("BoardId") long boardId, @RequestParam("EpicId") long EpicId) {
		return service.listOfImportedIssuesofEpic(fk_feature_id, boardId, EpicId);

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createSprint", method = RequestMethod.POST, produces = "application/Json")
	public Response createSprint(@RequestParam("sprintName") String sprintName, @RequestParam("BoardId") long boardId,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("goal") String goal, @RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("productId") int productId, @RequestParam("releaseId") int releaseId)
			throws JiraException, URISyntaxException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			List<SprintModel> sprints = agileClientUtility.listOfSprintsByBoardId(boardId, jcm);
			List<SprintModel> foundObjs = sprints.stream()
					.filter(sprint -> sprint.getSprintName().equalsIgnoreCase(sprintName)).collect(Collectors.toList());
			if (foundObjs.size() != 0) {
				res.setMessage("Sprint already exists with the Same Name");
				res.setStatus(2);
			} else {
				System.out.println("startDate***********:" + startDate);
				System.out.println("endDate***********:" + endDate);
				Sprint response = agileClientUtility.createSprint(sprintName, boardId, startDate, endDate, jcm, goal);
				System.err.println("name::" + response.getProjectName());
				System.err.println("id::" + response.getId());
				if (response.getId() != 0) {
					res.setMessage("Sprint created successfully");
					res.setStatus(1);
					res = service.assignSprinttoRelease(productId, (int) response.getId(), sprintName, releaseId);
					System.err.println("res:::::>>>>>:::::::::>>>>>" + res.toString());
				} else {
					res.setMessage("Sprint creation failed");
					res.setStatus(0);
				}
			}
		} catch (Exception e) {
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else if (e.getCause().getMessage().contains("The start date of a sprint must be before the end date.")) {
				res.setMessage("Internal Release Date Should be greater than CurrentDate.");
			} else {
				res.setMessage("Jira Sprint Failed to Map with Release.");
			}
		}
		System.err.println("res in createSprint:::::::::::::" + res.toString());
		return res;
	}

	/*
	 * @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	 * 
	 * @RequestMapping(value = "/createBoard", method = RequestMethod.POST, produces
	 * = "application/Json") public Board createBoard(@RequestParam("boardName")
	 * String boardName, @RequestParam("type") String type,
	 * 
	 * @RequestParam("filterId") long filterId, @RequestParam("fk_user_id") int
	 * fk_user_id) throws JiraException, URISyntaxException { JiraConfigurationModel
	 * jcm = service.getJiraCredentialsByUserId(fk_user_id); Board response =
	 * agileClientUtility.createBoard(boardName, type, filterId, jcm); return
	 * response; }
	 */

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createProject", method = RequestMethod.POST, produces = "application/Json")
	public Response createProject(@RequestParam("assigneeType") String assigneeType, @RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("key") String projectKey,
			@RequestParam("lead") String lead, @RequestParam("projectTemplateKey") String projectTemplateKey,
			@RequestParam("projectTypeKey") String projectTypeKey, @RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("productId") int productId) {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			List<Project> projects = agileClientUtility.getProjectsList(jcm);
			List<Project> foundObjs = projects.stream()
					.filter(project -> (project.getKey().equalsIgnoreCase(projectKey))
							|| (project.getProjectName().equalsIgnoreCase(name)))
					.collect(Collectors.toList());
			// System.err.println("foundObjs"+foundObjs.toString());
			if (foundObjs.size() != 0) {
				res.setMessage("Project already exists with the Same key and name");
				res.setStatus(2);
			} else {
				Project response = agileClientUtility.createProject(assigneeType, description, projectKey, lead,
						projectTemplateKey, projectTypeKey, name, jcm);
				List<BoardModel> boards = agileClientUtility.getJiraBoardDetails(jcm);
				List<BoardModel> objs = boards.stream()
						.filter(board -> (Long.parseLong(board.getProjectId())) == (response.getId()))
						.collect(Collectors.toList());
				if (response.getId() != 0) {
					res.setMessage("Project created successfully");
					res.setStatus(1);
					res = service.boardassignedtoAutoProduct(productId, (int) objs.get(0).getBoardId(), name,
							objs.get(0).getProjectId(), objs.get(0).getBoardName());

				} else {
					res.setMessage("Project creation failed");
					res.setStatus(0);
				}
			}
		} catch (Exception e) {
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Jira Board Failed to Map with Product.");
			}
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createIssue", method = RequestMethod.POST, produces = "application/Json")
	public Response createIssue(@RequestParam("summary") String summary, @RequestParam("projectId") String projectId,
			@RequestParam("issueType") String issueType, @RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("sprintId") long sprintId, @RequestParam("featureId") int featureId,
			@RequestParam("releaseId") int releaseId, @RequestParam("story_points") int story_points)
			throws JiraException, URISyntaxException {
		Response res = new Response();
		List<BackLogsModel> issues = null;
		List<BackLogsModel> foundObjs = null;
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			issues = agileClientUtility.listOfIssuesBySprintId(sprintId, jcm);
			foundObjs = issues.stream().filter(issue -> issue.getIssueName().equalsIgnoreCase(summary))
					.collect(Collectors.toList());
			if (foundObjs.size() != 0) {
				res.setMessage("Issue already exists with the Same Description");
				res.setStatus(2);
			} else {
				Issue response = agileClientUtility.createIssue(summary, projectId, issueType, fk_user_id, jcm,
						sprintId);
				System.err.println("response::::" + response.toString());
				if (response.getName() == null) {
					res.setStatus(0);
					res.setMessage("Issue creation failed.");
				} else {
					res.setStatus(1);
					res.setMessage("Issue created successfully.");
					issues = agileClientUtility.listOfIssuesBySprintId(sprintId, jcm);
					foundObjs = issues.stream()
							.filter(issue -> issue.getIssueKey().equalsIgnoreCase(response.getName()))
							.collect(Collectors.toList());
					res = issueassignedtoAutoFeature(featureId, releaseId, foundObjs.get(0).getIssueid(), issueType,
							fk_user_id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Jira Issue Failed to Map with Feature.");
			}

		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createEpic", method = RequestMethod.POST, produces = "application/Json")
	public Response createEpic(@RequestParam("summary") String summary, @RequestParam("projectId") String projectId,
			@RequestParam("issueType") String issueType, @RequestParam("epicValue") String epicValue,
			@RequestParam("fk_user_id") int fk_user_id, @RequestParam("sprintId") long sprintId,
			@RequestParam("featureId") int featureId, @RequestParam("releaseId") int releaseId)
			throws JiraException, URISyntaxException {
		Response res = new Response();
		List<BackLogsModel> issues = null;
		List<BackLogsModel> foundObjs = null;
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			issues = agileClientUtility.listOfIssuesBySprintId(sprintId, jcm);
			foundObjs = issues.stream().filter(issue -> issue.getIssueName().equalsIgnoreCase(summary))
					.collect(Collectors.toList());
			if (foundObjs.size() != 0) {
				res.setMessage("Epic already exists with the Same Description");
				res.setStatus(2);
			} else {
				Issue response = agileClientUtility.createEpic(summary, projectId, issueType, fk_user_id, jcm, sprintId,
						epicValue);
				if (response.getName() == null) {
					res.setStatus(0);
					res.setMessage("Epic creation failed");
				} else {
					res.setStatus(1);
					res.setMessage("Epic created successfully");
					issues = agileClientUtility.listOfIssuesBySprintId(sprintId, jcm);
					foundObjs = issues.stream()
							.filter(issue -> issue.getIssueKey().equalsIgnoreCase(response.getName()))
							.collect(Collectors.toList());
					res = issueassignedtoAutoFeature(featureId, releaseId, foundObjs.get(0).getIssueid(), issueType,
							fk_user_id);
				}
			}
		} catch (Exception e) {
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Exception occured");
			}
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getpriorityList", method = RequestMethod.GET, produces = "application/Json")
	public List<?> priorityList(@RequestParam("fk_user_id") int fk_user_id) throws JiraException {
		List priorities = null;
		String message = null;
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			priorities = agileClientUtility.priorityList(jcm);
		} catch (Exception e) {
			// System.out.println("::::::::::::::::::"+e.getCause().getMessage());
			priorities = new ArrayList<>();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				message = "Invalid Jira credentials";
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				message = "Your Atlassian Cloud subscription requires renewal";
			} else {
				message = "Exception occured";
			}
			priorities.add(message);
		}
		return priorities;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getProjectsList", method = RequestMethod.GET, produces = "application/Json")
	public List<?> getProjectsList(@RequestParam("fk_user_id") int fk_user_id) throws JiraException {
		List projects = null;
		String message = null;
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			projects = agileClientUtility.getProjectsList(jcm);
		} catch (Exception e) {
			projects = new ArrayList<>();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				message = "Invalid Jira credentials";
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				message = "Your Atlassian Cloud subscription requires renewal";
			} else {
				message = "Exception occured";
			}
			projects.add(message);
		}
		return projects;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getIssueTypes", method = RequestMethod.GET, produces = "application/Json")
	public List<?> getIssueTypes(@RequestParam("fk_user_id") int fk_user_id) throws JiraException {
		List issueTypes = null;
		String message = null;
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			issueTypes = agileClientUtility.getIssueTypes(jcm);
		} catch (Exception e) {
			issueTypes = new ArrayList<>();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				message = "Invalid Jira credentials";
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				message = "Your Atlassian Cloud subscription requires renewal";
			} else {
				message = "Exception occured";
			}
			issueTypes.add(message);
		}
		return issueTypes;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAvatarIdsForProject", method = RequestMethod.GET, produces = "application/Json")
	public List<?> getAvatarIdsForProject(@RequestParam("fk_user_id") int fk_user_id) throws JiraException {
		List avatarIds = null;
		String message = null;
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			avatarIds = agileClientUtility.getAvatarIdsForProject(jcm);
		} catch (Exception e) {
			avatarIds = new ArrayList<>();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				message = "Invalid Jira credentials";
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				message = "Your Atlassian Cloud subscription requires renewal";
			} else {
				message = "Exception occured";
			}
			avatarIds.add(message);
		}
		return avatarIds;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createBacklog", method = RequestMethod.POST, produces = "application/Json")
	public Response createBacklog(@RequestParam("summary") String summary, @RequestParam("projectId") String projectId,
			@RequestParam("issueType") String issueType, @RequestParam("fk_user_id") int fk_user_id)
			throws JiraException, URISyntaxException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			Issue response = agileClientUtility.createBacklog(summary, projectId, issueType, fk_user_id, jcm);
			if (response.getName() == null) {
				res.setMessage("Backlog creation failed");
				res.setStatus(0);
			} else {
				res.setMessage("Backlog created successfully.");
				res.setStatus(1);
			}
		} catch (Exception e) {
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Exception occured");
			}
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updateIssue", method = RequestMethod.PUT, produces = "application/Json")
	public Response updateIssue(@RequestParam("issueId") String issueId, @RequestParam("summary") String summary,
			@RequestParam("issueType") String issueType, @RequestParam("assignee") String assignee,
			@RequestParam("fk_user_id") int fk_user_id) throws JiraException, URISyntaxException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			Issue response = agileClientUtility.updateIssue(issueId, summary, issueType, assignee, fk_user_id, jcm);
			// System.err.println("response::::"+response.getName());
			res.setStatus(1);
			res.setMessage("Issue updated successfully.");
			/*
			 * if (response.getName()!=null) { res.setStatus(1);
			 * res.setMessage("Issue updated successfully."); } else { res.setStatus(0);
			 * res.setMessage("Issue updation failed."); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("error:::::::::::::::::::::" + e.getMessage());
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Exception occured");
			}

		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/deleteIssue", method = RequestMethod.DELETE, produces = "application/Json")
	public Response deleteIssue(@RequestParam("issueId") String issueId, @RequestParam("fk_user_id") int fk_user_id)
			throws JiraException, URISyntaxException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			Issue response = agileClientUtility.deleteIssue(issueId, fk_user_id, jcm);
			// System.err.println("response::::"+response.getName());
			res.setStatus(1);
			res.setMessage("Issue deleted successfully.");
			/*
			 * if (response.getName() == null) { res.setStatus(0);
			 * res.setMessage("Issue deletion failed."); } else { res.setStatus(1);
			 * res.setMessage("Issue deleted successfully."); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("error:::::::::::::::::::::" + e.getMessage());
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Exception occured");
			}

		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/deleteProject", method = RequestMethod.DELETE, produces = "application/Json")
	public Response deleteProject(@RequestParam("projectId") String projectId,
			@RequestParam("fk_user_id") int fk_user_id) {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);

			Project response = agileClientUtility.deleteProject(projectId, jcm);
			res.setMessage("Project Deleted successfully");
			res.setStatus(1);
			/*
			 * if (response.getProjectName() != null) {
			 * res.setMessage("Project created successfully"); res.setStatus(1); } else {
			 * res.setMessage("Project creation failed"); res.setStatus(0); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Exception occured");
			}
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/deleteSprint", method = RequestMethod.DELETE, produces = "application/Json")
	public Response deleteSprint(@RequestParam("sprintId") long sprintId, @RequestParam("fk_user_id") int fk_user_id)
			throws JiraException, URISyntaxException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			Sprint response = agileClientUtility.deleteSprint(sprintId, jcm);
			res.setMessage("Sprint deleted successfully");
			res.setStatus(1);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Exception occured");
			}
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updateSprint", method = RequestMethod.POST, produces = "application/Json")
	public Response updateSprint(@RequestParam("sprintId") long sprintId, @RequestParam("sprintName") String sprintName,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("goal") String goal, @RequestParam("fk_user_id") int fk_user_id)
			throws JiraException, URISyntaxException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			Sprint response = agileClientUtility.updateSprint(sprintId, sprintName, startDate, endDate, goal, jcm);
			if (response.getId() != 0) {
				res.setMessage("Sprint updated successfully");
				res.setStatus(1);
			} else {
				res.setMessage("Sprint updation failed");
				res.setStatus(0);
			}
		} catch (Exception e) {
			System.out.println("e.getCause().getMessage()::" + e.getCause().getMessage());
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else if (e.getCause().getMessage().contains("Sprint does not exist")) {
				res.setMessage("Sprint does not exist");
			} else {
				res.setMessage("Exception occured");
			}
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updateProject", method = RequestMethod.PUT, produces = "application/Json")
	public Response updateProject(@RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("projectId") String projectId, @RequestParam("lead") String lead,
			@RequestParam("fk_user_id") int fk_user_id) {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			Project response = agileClientUtility.updateProject(description, projectId, lead, name, jcm);
			res.setMessage("Project updated successfully");
			res.setStatus(1);
			/*
			 * if (response.getProjectName() != null) {
			 * res.setMessage("Project updated successfully"); res.setStatus(1); } else {
			 * res.setMessage("Project updation failed"); res.setStatus(0); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Exception occured");
			}
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/moveIssuesToSprint", method = RequestMethod.POST, produces = "application/Json")
	public Response moveIssuesToSprint(@RequestParam("issueKeys") List issueKeys,
			@RequestParam("sprintId") int sprintId, @RequestParam("fk_user_id") int fk_user_id)
			throws JiraException, URISyntaxException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			Sprint response = agileClientUtility.moveIssuesToSprint(jcm, sprintId, issueKeys);
			res.setMessage("Issue added successfully");
			res.setStatus(1);
			/*
			 * if (response.getId() != 0) { res.setMessage("Issue added successfully");
			 * res.setStatus(1); } else { res.setMessage("Issue adition failed");
			 * res.setStatus(0); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Exception occured");
			}
		}
		return res;
	}

	/*
	 * @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	 * 
	 * @RequestMapping(value = "/syncProject", method = RequestMethod.POST, produces
	 * = "application/Json") public Response
	 * syncProject(@RequestParam("projectName") String
	 * projectName,@RequestParam("fk_user_id") int fk_user_id) throws JiraException{
	 * Response res = new Response(); JiraConfigurationModel jcm =
	 * service.getJiraCredentialsByUserId(fk_user_id); List<Project> projects =
	 * agileClientUtility.getProjectsList(jcm); List<Project> foundObjs =
	 * projects.stream().filter(project ->
	 * project.getName().equalsIgnoreCase(projectName)).collect(Collectors.
	 * toList()) ; if(foundObjs.size()>0){ res.setMessage("Project Exists in jira");
	 * res.setStatusCode(0); }else{ res=createProject("PROJECT_LEAD", 10001,
	 * projectName, "", projectName, "admin",
	 * "com.pyxis.greenhopper.jira:gh-simplified-scrum", "software", fk_user_id);
	 * 
	 * } return res; }
	 */
	/***********************************
	 * Board Mapping ***********************************
	 */

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/checkboxforjiracredentialstest", method = RequestMethod.GET, produces = "application/Json")
	public Response checkboxforJiraCredentialsTest(
			// @RequestParam("productName") String productName,
			@RequestParam("fk_user_id") int fk_user_id) {
		Response response;
		// List<Product> productlist =
		// service.productConfigurationwithJiraProject(productName);
		// if (productlist.size() == 0) {
		List<JiraConfigurationModel> jiramodel = getjiraDetailsbyuserIDForcheckbox(fk_user_id);
		int list = jiramodel.size();
		if (list > 0) {
			JiraConfigurationModel jcm = new JiraConfigurationModel();
			jcm.setApplication_url(jiramodel.get(0).getApplication_url());
			jcm.setUser_name(jiramodel.get(0).getUser_name());
			jcm.setPassword(jiramodel.get(0).getPassword());
			Response response1 = agileClientUtility.testJiraCredentials(jcm);
			if (response1.getStatusCode() == 1) {
				return response1;
			} else {
				return response1;
			}

		} else {
			response = new Response();
			response.setStatusCode(0);
			response.setMessage("No Jira Credentials Found to this User.Please add your Jira Credentials.");
			return response;
		}
		/*
		 * } else { response = new Response(); response.setStatusCode(0);
		 * response.setMessage("Product is already mapped with Jira Board."); return new
		 * ResponseEntity<Response>(response, HttpStatus.OK); }
		 */

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/checkboxforproductconfigandjiracredentialstest", method = RequestMethod.GET, produces = "application/Json")
	public Response checkboxofisproductConfigured(@RequestParam("product_name") String productName,
			@RequestParam("fk_user_id") int fk_user_id) {
		Response response;
		// List<Product> productlist =
		/*
		 * List<Product> listofProducts =
		 * service.productConfigurationwithJiraProject(productName); if
		 * (listofProducts.size() == 0) {
		 */
		List<JiraConfigurationModel> jiramodel = getjiraDetailsbyuserIDForcheckbox(fk_user_id);
		int list = jiramodel.size();
		if (list > 0) {
			JiraConfigurationModel jcm = new JiraConfigurationModel();
			jcm.setApplication_url(jiramodel.get(0).getApplication_url());
			jcm.setUser_name(jiramodel.get(0).getUser_name());
			jcm.setPassword(jiramodel.get(0).getPassword());
			Response response1 = agileClientUtility.testJiraCredentials(jcm);
			if (response1.getStatusCode() == 1) {
				return response1;
			} else {
				return response1;
			}

		} else {
			response = new Response();
			response.setStatusCode(0);
			response.setMessage("No Jira Credentials Found to this User.Please add your Jira Credentials.");
			return response;
		}

		/*
		 * } else { response = new Response(); response.setStatusCode(0);
		 * response.setMessage("Product is already mapped with Jira Board."); return
		 * response; }
		 */

	}

	// for checkboxproductConfigurationwithJiraProject method.
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getjiraDetailsbyuserIDForcheckbox", method = RequestMethod.GET, produces = "application/Json")
	public List<JiraConfigurationModel> getjiraDetailsbyuserIDForcheckbox(@RequestParam("fk_user_id") int fk_user_id) {

		List<JiraConfigurationModel> jiramodel = service.getJiraDetails(fk_user_id);
		return jiramodel;

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofunassignedboards", method = RequestMethod.GET, produces = "application/Json")
	public ResponseEntity<?> ListofUnAssinedBoardsForProductMapping(@RequestParam("fk_user_id") int fk_user_id)
			throws Exception {
		Response response;
		Response checkboxResponse = checkboxforJiraCredentialsTest(fk_user_id);
		if (checkboxResponse.getStatusCode() == 1) {
			List<BoardModel> boardModel = (List<BoardModel>) getBoards(fk_user_id);
			System.out.println("*********in controller boards:" + boardModel.toString());

			List<BoardModel> listofnotassignedBoards = service.mergeProjectsandBoards(boardModel);

			// List<Product> productlist =
			// service.productConfigurationwithJiraProject(product_id);
			if (listofnotassignedBoards.size() > 0) {
				return new ResponseEntity<List<BoardModel>>(listofnotassignedBoards, HttpStatus.OK);
			} else {
				response = new Response();
				response.setStatusCode(2);
				response.setMessage("All Boards are already mapped with other Products.");
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Response>(checkboxResponse, HttpStatus.OK);
		}

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/assignboardtoproduct", method = RequestMethod.POST, produces = "application/Json")
	public Response boardassignedtoAutoProduct(@RequestParam("product_id") int product_id,
			@RequestParam("boardId") int boardId, @RequestParam("projectName") String projectName,
			@RequestParam("projectId") String projectId, @RequestParam("boardName") String boardName) throws Exception {
		Response response = new Response();
		response = service.boardassignedtoAutoProduct(product_id, boardId, projectName, projectId, boardName);
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/assignjiraboardtoautolifecycleproduct", method = RequestMethod.POST, produces = "application/Json")
	public Response jiraboardassignedtoAutoLifecycleProduct(@RequestParam("product_id") int product_id,
			@RequestParam("boardId") int boardId, @RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response response = new Response();
		if (boardId > 0) {
			List<BoardModel> boardModel = (List<BoardModel>) getBoards(fk_user_id);
			for (BoardModel b : boardModel) {
				if (boardId == b.getBoardId()) {
					response = service.boardassignedtoAutoProduct(product_id, boardId, b.getProjectName(),
							b.getProjectId(), b.getBoardName());
					if (response.getStatusCode() == 1) {
						response.setMessage(response.getMessage());
						response.setStatusCode(1);
						// return response;
					} else if (response.getStatusCode() == 2) {
						response.setMessage(response.getMessage());
						response.setStatusCode(2);
					} else {
						response.setMessage(response.getMessage());
						response.setStatusCode(0);
					}
				}
			}
		} else {
			List<Product> productlist = service.getproductDetailsByProductId(product_id);

			String productNameUpperCase = productlist.get(0).getProduct_name().toUpperCase().trim();
			String productNameUpperCaseForKey = productlist.get(0).getProduct_name().toUpperCase().trim()
					.replaceAll(" ", "");
			String key = productNameUpperCaseForKey.substring(0,
					Math.min(productNameUpperCaseForKey.length(), (productNameUpperCaseForKey.length() / 2) + 1));
			if (key.length() >= 10) {
				key = key.substring(0, Math.min(key.length(), (key.length() / 2)));
			}
			System.err.println("key::::::::" + key);
			System.err.println("******productNameUpperCaseForKey" + productNameUpperCaseForKey);
			response = createProject("PROJECT_LEAD", productNameUpperCase, productlist.get(0).getProduct_desc(), key,
					"admin", "com.pyxis.greenhopper.jira:gh-scrum-template", "software", fk_user_id, product_id);
			if (response.getStatusCode() == 1) {
				response.setMessage(response.getMessage());
				response.setStatusCode(1);
				// return response;
			} else if (response.getStatusCode() == 2) {
				response.setMessage(response.getMessage());
				response.setStatusCode(2);
			} else {
				response.setMessage(response.getMessage());
				response.setStatusCode(0);
			}

		}
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getjiraboarddetailsbyproductid", method = RequestMethod.GET, produces = "application/Json")
	public ResponseEntity<?> productConfigurationwithJiraProject(@RequestParam("product_id") int product_id) {
		Response response;
		List<Product> productlist = service.getproductConfigurationwithJiraProject(product_id);
		if (productlist.size() > 0) {
			return new ResponseEntity<List<Product>>(productlist, HttpStatus.OK);
		} else {
			response = new Response();
			response.setStatusCode(2);
			response.setMessage("Product is not mapped with Jira Boards.");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/jiraboarddetailsbyautoproductid", method = RequestMethod.GET, produces = "application/Json")
	public List<Product> getJiraBoardDetailsByAutoProductId(@RequestParam("product_id") int product_id) {

		List<Product> productlist = service.getproductConfigurationwithJiraProject(product_id);
		return productlist;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getbacklogsofboardbyproductid", method = RequestMethod.GET, produces = "application/Json")
	public ResponseEntity<?> getBacklogsofBoardByProductId(@RequestParam("product_id") int product_id,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response response;
		long issueId = 0;
		List<BackLogsModel> filteredBacklogs = new ArrayList<>();
		List<Product> productlist = service.getproductConfigurationwithJiraProject(product_id);
		if (productlist.size() > 0) {
			long BoardId = productlist.get(0).getFk_jira_boardid();
			List<BackLogsModel> backlogs = (List<BackLogsModel>) getBackLogsByBoardId(fk_user_id, BoardId);
			if (backlogs.size() > 0) {
				for (BackLogsModel model : backlogs) {
					issueId = model.getIssueid();
					boolean exists = service.getIssueExistencyinDatabase(issueId);
					if (!exists) {
						filteredBacklogs.add(model);
					}
				}
			}
			return new ResponseEntity<List<BackLogsModel>>(filteredBacklogs, HttpStatus.OK);
		} else {
			response = new Response();
			response.setStatusCode(2);
			response.setMessage("Product is not mapped with Jira Boards.");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}

	}

	/*
	 * @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	 * 
	 * @RequestMapping(value = "/importbacklogsofboardtoauto", method =
	 * RequestMethod.POST, produces = "application/Json") public List<Response>
	 * addBacklogofBoardtoAuto(@RequestParam("fk_user_id") int fk_user_id,
	 * 
	 * @RequestParam("product_id") int product_id, @RequestBody
	 * List<ImportBacklogModel> backLogsModel)
	 * 
	 * , @RequestParam("boardName") String boardName,
	 * 
	 * @RequestParam("projectName") String projectName, @RequestParam("boardId") int
	 * boardId,
	 * 
	 * @RequestParam("projectId") String projectId,
	 * 
	 * throws JiraException { List<Product> productlist =
	 * service.getproductConfigurationwithJiraProject(product_id); String boardName
	 * = productlist.get(0).getJiraBoardName(); long boardId =
	 * productlist.get(0).getFk_jira_boardid(); String projectName =
	 * productlist.get(0).getJiraprojectName(); String projectId =
	 * productlist.get(0).getFk_jira_projectid();
	 * 
	 * List<Response> response = service.addBackLogs(backLogsModel, product_id,
	 * fk_user_id, boardName, projectName, boardId, projectId); return response; }
	 */

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/importbacklogsofboardtoauto", method = RequestMethod.POST, produces = "application/Json")
	public List<Response> addBacklogofBoardtoAuto(@RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("product_id") int product_id, @RequestParam("listofIssueIds") String listofIssueIds)
			throws Exception {
		List<Product> productlist = service.getproductConfigurationwithJiraProject(product_id);
		String boardName = productlist.get(0).getJiraBoardName();
		long boardId = productlist.get(0).getFk_jira_boardid();
		String projectName = productlist.get(0).getJiraprojectName();
		String projectId = productlist.get(0).getFk_jira_projectid();

		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);

		List<BackLogsModel> backlogs = agileClientUtility.getbacklogsByboardId(boardId, jcm);

		List<ImportBacklogModel> backLogsModel = new ArrayList<>();
		ImportBacklogModel importModel;
		List<Integer> listofIds = new ArrayList<Integer>();

		for (String s : listofIssueIds.split(","))
			listofIds.add(Integer.parseInt(s));
		System.out.println(listofIds);

		for (BackLogsModel b : backlogs) {
			for (Integer id : listofIds) {
				if (b.getIssueid() == id) {
					importModel = new ImportBacklogModel();

					importModel.setIssueid(b.getIssueid());
					importModel.setIssueName(b.getIssueName());
					importModel.setIssueDescription(b.getIssueDescription());
					importModel.setIssueKey(b.getIssueKey());
					importModel.setIssueCreatedon(b.getIssueCreatedon());

					importModel.setIssueTypeId(b.getIssueTypeId());
					importModel.setIssueTypeName(b.getIssueTypeName());
					importModel.setIssueTypeDescription(b.getIssueTypeDescription());

					importModel.setIssueStatusId((int) (b.getIssueStatusId()));
					importModel.setIssueStatus(b.getIssueStatus());

					importModel.setIssueCreaterName(b.getIssueCreaterName());
					importModel.setIssueCreaterEmail(b.getIssueCreaterEmail());
					importModel.setIssueAssigneeName(b.getIssueAssigneeName());
					importModel.setIssueAssigneeEmail(b.getIssueAssigneeEmail());
					backLogsModel.add(importModel);

				}
			}

		}

		System.out.println("BackLogsModel:" + backLogsModel.toString());
		List<Response> response = service.addBackLogs(backLogsModel, product_id, fk_user_id, boardName, projectName,
				boardId, projectId);
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/productnamealreadyconfigure", method = RequestMethod.POST, produces = "application/Json")
	public Response isProductNameConfigurationwithJiraProject(String productName, int fk_product_line) {
		List<Product> list = service.productConfigurationwithJiraProject(productName, fk_product_line);
		Response response;
		if (list.size() > 0) {
			response = new Response();
			response.setStatusCode(0);
			response.setMessage("Product Name is already configured with Jira Board.");
			return response;
		} else {
			response = new Response();
			response.setStatusCode(1);
			response.setMessage("Product Name is Unique.Please click on mapping or create");
			return response;
		}

	}

	/**************************************
	 * Sprint Mapping***************
	 ***********************/

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/checkboxforreleaseconfigandjiracredentialstest", method = RequestMethod.GET, produces = "application/Json")
	public Response checkboxofisreleaseConfiguredAndTestCredentials(@RequestParam("releaseName") String releaseName,
			@RequestParam("fk_user_id") int fk_user_id) {
		Response response;
		// List<Product> productlist =
		List<ReleaseModel> listofProducts = service.isreleaseConfigurationwithJiraSprint(releaseName);
		if (listofProducts.size() == 0) {
			List<JiraConfigurationModel> jiramodel = getjiraDetailsbyuserIDForcheckbox(fk_user_id);
			int list = jiramodel.size();
			if (list > 0) {
				JiraConfigurationModel jcm = new JiraConfigurationModel();
				jcm.setApplication_url(jiramodel.get(0).getApplication_url());
				jcm.setUser_name(jiramodel.get(0).getUser_name());
				jcm.setPassword(jiramodel.get(0).getPassword());
				Response response1 = agileClientUtility.testJiraCredentials(jcm);
				if (response1.getStatusCode() == 1) {
					return response1;
				} else {
					return response1;
				}

			} else {
				response = new Response();
				response.setStatusCode(0);
				response.setMessage("No Jira Credentials Found to this User.Please add your Jira Credentials.");
				return response;
			}

		} else {
			response = new Response();
			response.setStatusCode(0);
			response.setMessage("Release is already mapped with Jira Sprint.");
			return response;
		}

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofunassignedsprintsofboard", method = RequestMethod.GET, produces = "application/Json")
	public SprintAndResponseDTO ListofUnAssinedsprintsForReleaseMapping(@RequestParam("product_id") int product_id,
			// @RequestParam("BoardId") long BoardId,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		SprintAndResponseDTO response;
		SprintModel model = null;
		Response checkboxResponse = checkboxforJiraCredentialsTest(fk_user_id);
		if (checkboxResponse.getStatusCode() == 1) {
			// getProductReleaseofJiraByReleaseId(@RequestParam("relrelease_Id)
			List<Product> productlist = service.getproductConfigurationwithJiraProject(product_id);
			if ((!productlist.isEmpty()) && productlist.get(0).getFk_jira_boardid() > 0) {
				long BoardId = productlist.get(0).getFk_jira_boardid();
				List<SprintModel> sprintModel = (List<SprintModel>) getListofSprintsbyBoardId(fk_user_id, BoardId);
				System.out.println("*********in controller sprints:" + sprintModel.toString());

				List<SprintModel> listofnotassignedSprints = service.getListOfUnassignedSprints(sprintModel,
						product_id);
				if (listofnotassignedSprints.size() > 0) {
					response = new SprintAndResponseDTO();
					response.setStatusCode(1);
					response.setSprintModel(listofnotassignedSprints);
					response.setMessage("");
					return response;

				} else {
					response = new SprintAndResponseDTO();
					sprintModel = new ArrayList<>();

					model = new SprintModel();
					model.setSprintId(0);
					model.setSprintName("Create New");
					model.setBoardName(productlist.get(0).getJiraBoardName());
					sprintModel.add(model);

					response.setStatusCode(1);
					response.setSprintModel(sprintModel);
					response.setMessage("No sprints to Map. Create New");
					return response;
				}
			} else {
				response = new SprintAndResponseDTO();
				response.setStatusCode(2);
				response.setMessage("Product is not mapped with Jira Boards.");
				return response;

			}

		} else {
			response = new SprintAndResponseDTO();
			response.setStatusCode(checkboxResponse.getStatusCode());
			response.setMessage(checkboxResponse.getMessage());
			return response;

		}
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/assignsprinttorelease", method = RequestMethod.POST, produces = "application/Json")
	public Response assignSprinttoRelease(@RequestParam("product_id") int product_id,
			@RequestParam("release_Id") int release_Id, @RequestParam("sprintId") int sprintId,
			@RequestParam("sprintName") String sprintName) throws Exception {
		Response response = new Response();
		response = service.assignSprinttoRelease(product_id, sprintId, sprintName, release_Id);
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/assignjirasprinttoautolifecyclerelease", method = RequestMethod.POST, produces = "application/Json")
	public Response assignJiraSprinttoAutoRelease(@RequestParam("product_id") int product_id,
			@RequestParam("release_Id") int release_Id, @RequestParam("sprintId") int sprintId,
			// @RequestParam("sprintName") String sprintName,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response response = new Response();

		List<SprintModel> sprints = null;
		try {

			List<Product> productlist = service.getproductConfigurationwithJiraProject(product_id);
			long boardId = productlist.get(0).getFk_jira_boardid();

			if (sprintId > 0) {
				JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
				sprints = agileClientUtility.listOfSprintsByBoardId(boardId, jcm);
				for (SprintModel sm : sprints) {
					if (sprintId == sm.getSprintId()) {
						response = service.assignSprinttoRelease(product_id, sprintId, sm.getSprintName(), release_Id);
						if (response.getStatusCode() == 1) {
							response.setStatusCode(response.getStatusCode());
							response.setMessage(response.getMessage());
						} else if (response.getStatusCode() == 2) {
							response.setStatusCode(2);
							response.setMessage(response.getMessage());
						} else {
							response.setStatusCode(0);
							response.setMessage(response.getMessage());
						}
					}
				}
			} else {
				/*
				 * long millis = System.currentTimeMillis(); java.sql.Date date = new
				 * java.sql.Date(millis);
				 */
				// List<Product> productlist =getJiraBoardDetailsByAutoProductId(product_id);
				System.out.println(" *******product list:" + productlist.toString());
				boardId = productlist.get(0).getFk_jira_boardid();

				List<JiraReleaseSprintDTO> releaselist = service.getReleaseDetailsByReleaseId(release_Id);
				String releaseName = releaselist.get(0).getReleaseName();
				String releaseDesc = releaselist.get(0).getReleaseDescription();
				String internalReleaseDate = releaselist.get(0).getReleaseDateInternal();
				String externalReleaseDate = releaselist.get(0).getReleaseDateExternal();
				response = createSprint(releaseName, boardId, internalReleaseDate, externalReleaseDate, releaseDesc,
						fk_user_id, product_id, release_Id);
				if (response.getStatusCode() == 1) {
					response.setStatusCode(1);
					response.setMessage(response.getMessage());
				} else if (response.getStatusCode() == 2) {
					response.setStatusCode(2);
					response.setMessage(response.getMessage());
				}

				else {
					response.setStatusCode(0);
					response.setMessage(response.getMessage());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getproductreleasejiradetailsbyrelease_Id", method = RequestMethod.GET, produces = "application/Json")
	public ResponseEntity<?> getProductReleaseofJiraByReleaseId(@RequestParam("release_Id") int release_Id) {
		Response response;
		List<JiraReleaseSprintDTO> releaselist = service.getProductReleaseofJiraByReleaseId(release_Id);
		if (releaselist.size() > 0) {
			return new ResponseEntity<List<?>>(releaselist, HttpStatus.OK);
		} else {
			response = new Response();
			response.setStatusCode(2);
			response.setMessage("Product_Release is not mapped with Jira Sprint.");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getissuesofsprintbyproductreleaseId", method = RequestMethod.GET, produces = "application/Json")
	public ResponseEntity<?> getIssuesOfSprintByProductReleaseId(@RequestParam("release_Id") int release_Id,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response responseDTO = null;
		// List<Response> listResponse = new ArrayList<>();
		IssuesAndResponseDTO response = new IssuesAndResponseDTO();
		List<BackLogsModel> filteredIssues = new ArrayList<>();
		long issueId = 0;
		try {
			List<JiraReleaseSprintDTO> releaselist = service.getProductReleaseofJiraByReleaseId(release_Id);

			List<Product> productlist = service
					.getproductConfigurationwithJiraProject(releaselist.get(0).getFkProductId());
			if ((!productlist.isEmpty()) && productlist.get(0).getFk_jira_boardid() > 0) {
				long SprintId = releaselist.get(0).getFk_jira_sprint_id();
				if (releaselist.size() > 0 && SprintId > 0) {

					List<BackLogsModel> issues = (List<BackLogsModel>) getListofIssuesbySprintId(fk_user_id, SprintId);
					if (issues.size() > 0) {
						for (BackLogsModel model : issues) {
							issueId = model.getIssueid();
							boolean exists = service.getIssueExistencyinDatabase(issueId);
							if (!exists) {
								filteredIssues.add(model);

							}
						}
					}
					// return response;
					return new ResponseEntity<List<BackLogsModel>>(filteredIssues, HttpStatus.OK);
				} else {
					responseDTO = new Response();
					responseDTO.setStatusCode(2);
					responseDTO.setMessage("Product_Release is not mapped with Jira Sprint.");
					// return response;
					// return new ResponseEntity<Response>(responseDTO, HttpStatus.OK);
				}
			} else {
				responseDTO = new Response();
				responseDTO.setStatusCode(2);
				responseDTO.setMessage("Product is not mapped with Jira Boards.");
				// return new ResponseEntity<Response>(responseDTO, HttpStatus.OK);
				// return response;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Response>(responseDTO, HttpStatus.OK);

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/importissuesofsprinttoauto", method = RequestMethod.POST, produces = "application/Json")
	public List<Response> addissuesofSprint(@RequestParam("fk_user_id") int fk_user_id,
			@RequestParam("release_Id") int release_Id, @RequestParam("listofIssueIds") String listofIssueIds
	// @RequestBody List<ImportBacklogModel> backLogsModel
	// @RequestParam("product_id") int product_id,
	) throws Exception {

		List<JiraReleaseSprintDTO> releaselist = service.getProductReleaseofJiraByReleaseId(release_Id);
		String releaseName = releaselist.get(0).getReleaseName();
		int product_id = releaselist.get(0).getFkProductId();
		int sprintId = (int) releaselist.get(0).getFk_jira_sprint_id();
		String jira_sprintName = releaselist.get(0).getJira_sprintName();
		long fk_jira_boardid = releaselist.get(0).getFk_jira_boardid();
		String fk_jira_projectid = releaselist.get(0).getFk_jira_projectid();
		String jiraprojectName = releaselist.get(0).getJiraprojectName();
		String jiraBoardName = releaselist.get(0).getJiraBoardName();

		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);

		// List<BackLogsModel> backlogs =
		// agileClientUtility.getbacklogsByboardId(boardId, jcm);
		List<BackLogsModel> issues = agileClientUtility.listOfIssuesBySprintId(sprintId, jcm);
		List<ImportBacklogModel> backLogsModel = new ArrayList<>();
		ImportBacklogModel importModel;
		List<Integer> listofIds = new ArrayList<Integer>();

		for (String s : listofIssueIds.split(","))
			listofIds.add(Integer.parseInt(s));
		System.out.println(listofIds);

		for (BackLogsModel b : issues) {
			for (Integer id : listofIds) {
				if (b.getIssueid() == id) {
					importModel = new ImportBacklogModel();

					importModel.setIssueid(b.getIssueid());
					importModel.setIssueName(b.getIssueName());
					importModel.setIssueDescription(b.getIssueDescription());
					importModel.setIssueKey(b.getIssueKey());
					importModel.setIssueCreatedon(b.getIssueCreatedon());

					importModel.setIssueTypeId(b.getIssueTypeId());
					importModel.setIssueTypeName(b.getIssueTypeName());
					importModel.setIssueTypeDescription(b.getIssueTypeDescription());

					importModel.setIssueStatusId((int) (b.getIssueStatusId()));
					importModel.setIssueStatus(b.getIssueStatus());

					importModel.setIssueCreaterName(b.getIssueCreaterName());
					importModel.setIssueCreaterEmail(b.getIssueCreaterEmail());
					importModel.setIssueAssigneeName(b.getIssueAssigneeName());
					importModel.setIssueAssigneeEmail(b.getIssueAssigneeEmail());
					backLogsModel.add(importModel);

				}
			}

		}

		System.out.println("BackLogsModel:" + backLogsModel.toString());

		List<Response> response = service.addIssues(backLogsModel, product_id, release_Id, sprintId, releaseName,
				jira_sprintName, fk_jira_boardid, fk_jira_projectid, jiraprojectName, jiraBoardName);
		return response;
	}

	/* ************************* Feature Mapping ******************** */

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/checkboxforfeatureconfigandjiracredentialstest", method = RequestMethod.GET, produces = "application/Json")
	public Response checkboxofisFeatureConfiguredAndTestCredentials(@RequestParam("release_Id") int release_Id,
			@RequestParam("feature_name") String feature_name, @RequestParam("fk_user_id") int fk_user_id) {
		Response response;
		// List<Product> productlist =
		List<FeaturesModel> listofProducts = service.isFeatureConfigurationwithJiraSprintIssue(release_Id,
				feature_name);
		if (listofProducts.size() == 0) {
			List<JiraConfigurationModel> jiramodel = getjiraDetailsbyuserIDForcheckbox(fk_user_id);
			int list = jiramodel.size();
			if (list > 0) {
				JiraConfigurationModel jcm = new JiraConfigurationModel();
				jcm.setApplication_url(jiramodel.get(0).getApplication_url());
				jcm.setUser_name(jiramodel.get(0).getUser_name());
				jcm.setPassword(jiramodel.get(0).getPassword());
				Response response1 = agileClientUtility.testJiraCredentials(jcm);
				if (response1.getStatusCode() == 1) {
					return response1;
				} else {
					return response1;
				}

			} else {
				response = new Response();
				response.setStatusCode(0);
				response.setMessage("No Jira Credentials Found to this User.Please add your Jira Credentials.");
				return response;
			}

		} else {
			response = new Response();
			response.setStatusCode(0);
			response.setMessage("Feature is already mapped with Jira Issue.");
			return response;
		}

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/listofunassignedissues", method = RequestMethod.GET, produces = "application/Json")
	public IssuesAndResponseDTO ListofUnAssinedIssuesForFeatureMapping(@RequestParam("release_Id") int release_Id,
			@RequestParam("fk_feature_type_id") int fk_feature_type_id, @RequestParam("fk_user_id") int fk_user_id)
			throws Exception {
		IssuesAndResponseDTO response;
		BackLogsModel backLogsModel = null;
		List<SprintModel> sprints = null;
		// try {
		Response checkboxResponse = checkboxforJiraCredentialsTest(fk_user_id);
		if (checkboxResponse.getStatusCode() == 1) {
			List<JiraReleaseSprintDTO> releaselist = service.getProductReleaseofJiraByReleaseId(release_Id);

			if ((!releaselist.isEmpty()) && releaselist.get(0).getFk_jira_sprint_id() > 0) {

				int sprintId = (int) releaselist.get(0).getFk_jira_sprint_id();
				JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
				sprints = agileClientUtility.listOfSprintsByBoardId(releaselist.get(0).getFk_jira_boardid(), jcm);
				for (SprintModel sm : sprints) {

					Set<Long> ids = sprints.stream().map(SprintModel::getSprintId).collect(Collectors.toSet());
					List<JiraReleaseSprintDTO> parentBooks = releaselist.stream()
							.filter(book -> !ids.contains(book.getFk_jira_sprint_id())).collect(Collectors.toList());
					System.err.println("deleted sprint***************:" + parentBooks.toString());
					for (JiraReleaseSprintDTO jrst : parentBooks) {
						if (sprintId == jrst.getFk_jira_sprint_id()) {
							response = new IssuesAndResponseDTO();
							response.setStatusCode(0);
							response.setMessage(
									"Sprint deleted in Jira:" + releaselist.get(0).getJira_sprintName() + ".");
							return response;
						}

					}
					if (sm.getSprintId() == sprintId && (sm.getSprintStatus().contains("closed"))) {

						response = new IssuesAndResponseDTO();
						response.setStatusCode(3);
						response.setMessage("Jira Sprint closed in Jira.");
						return response;

					}

				}
				List<BackLogsModel> issues = (List<BackLogsModel>) getListofIssuesbySprintId(fk_user_id, sprintId);

				List<BackLogsModel> listofnotassignedIssuesofSprintforReleaseMapping = service
						.listofnotassignedIssuesofSprintforReleaseMapping(issues, release_Id, fk_feature_type_id);

				if (listofnotassignedIssuesofSprintforReleaseMapping.size() > 0) {
					response = new IssuesAndResponseDTO();
					response.setStatusCode(1);
					response.setMessage("");
					response.setBackLogsModel(listofnotassignedIssuesofSprintforReleaseMapping);
					response.setFk_jira_boardid(releaselist.get(0).getFk_jira_boardid());
					response.setFk_jira_projectid(releaselist.get(0).getFk_jira_projectid());
					response.setJiraBoardName(releaselist.get(0).getJiraBoardName());
					response.setFk_jira_sprint_id(releaselist.get(0).getFk_jira_sprint_id());
					response.setJira_sprintName(releaselist.get(0).getJira_sprintName());
					return response;

				} else {
					String JiraIssueTypeName = service.getJiraIssueTypeName(fk_feature_type_id);
					response = new IssuesAndResponseDTO();
					backLogsModel = new BackLogsModel();
					listofnotassignedIssuesofSprintforReleaseMapping = new ArrayList<>();

					response.setStatusCode(1);
					response.setMessage("No Issue to Map. Create New");
					backLogsModel.setIssueid(0);
					backLogsModel.setIssueName("Create New");
					listofnotassignedIssuesofSprintforReleaseMapping.add(backLogsModel);
					response.setBackLogsModel(listofnotassignedIssuesofSprintforReleaseMapping);
					response.setFk_jira_boardid(releaselist.get(0).getFk_jira_boardid());
					response.setFk_jira_projectid(releaselist.get(0).getFk_jira_projectid());
					response.setJiraBoardName(releaselist.get(0).getJiraBoardName());
					response.setFk_jira_sprint_id(releaselist.get(0).getFk_jira_sprint_id());
					response.setJira_sprintName(releaselist.get(0).getJira_sprintName());
					return response;
				}
				/*
				 * } else { response = new IssuesAndResponseDTO(); response.setStatusCode(2);
				 * response.setMessage("Jira Sprint already Shipped."); return response;
				 * 
				 * 
				 * }
				 */
				// }
			} else {
				response = new IssuesAndResponseDTO();
				response.setStatusCode(2);
				response.setMessage("Product_Release is not mapped with Jira Sprint.");
				return response;
			}
		} else {
			response = new IssuesAndResponseDTO();
			response.setStatusCode(checkboxResponse.getStatusCode());
			response.setMessage(checkboxResponse.getMessage());
			return response;
		}

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createandassignissuetoautofeature", method = RequestMethod.POST, produces = "application/Json")
	public Response issueassignedtoAutoFeature(@RequestParam("feature_Id") int feature_Id,
			@RequestParam("release_Id") int release_Id, @RequestParam("issueId") long issueId,
			@RequestParam("jiraIssueType") String jiraIssueType, @RequestParam("fk_user_id") int fk_user_id)
			throws Exception {

		Response response = new Response();
		List<FeatureslistModel> featureModel = null;

		List<JiraReleaseSprintDTO> releaselist = service.getProductReleaseofJiraByReleaseId(release_Id);
		int product_id = releaselist.get(0).getFkProductId();
		if (issueId > 0) {
			response = service.issueassignedtoAutoFeature(product_id, release_Id, feature_Id, issueId, jiraIssueType);
			if (response.getStatusCode() == 1) {
				response.setStatusCode(response.getStatusCode());
				response.setMessage(response.getMessage());
			} else if (response.getStatusCode() == 0) {
				response.setStatusCode(0);
				response.setMessage(response.getMessage());
			} else {
				response.setStatusCode(2);
				response.setMessage(response.getMessage());
			}

		} else {

			long SprintId = releaselist.get(0).getFk_jira_sprint_id();
			List<Product> productlist = getJiraBoardDetailsByAutoProductId(product_id);
			String projectId = productlist.get(0).getFk_jira_projectid();
			String boardName = productlist.get(0).getJiraBoardName().replace(" board", "");
			featureModel = featuresService.getFeatureDetailsByFeatureId(feature_Id);
			String featuresName = featureModel.get(0).getFeature_name();
			String featuresNameUpperCase = featuresName.toUpperCase();
			int story_points = featureModel.get(0).getStory_points();
			response = createIssue(featuresNameUpperCase, boardName, jiraIssueType, fk_user_id, SprintId, feature_Id,
					release_Id, story_points);
			// res1 = jiraController.createIssue(featuresName, projectId, issueType,
			// fk_user_id, sprintId, featureId, releaseId)
			if (response.getStatusCode() == 1) {
				response.setStatusCode(response.getStatusCode());
				response.setMessage(response.getMessage());
			} else if (response.getStatusCode() == 0) {
				response.setStatusCode(0);
				response.setMessage(response.getMessage());
			} else {
				response.setStatusCode(2);
				response.setMessage(response.getMessage());
			}
		}
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/assignissuetoautofeature", method = RequestMethod.POST, produces = "application/Json")
	public Response issueassignedtoAutoFeatureForUnMappedFeatures(@RequestParam("feature_Id") int feature_Id,
			// @RequestParam("release_Id") int release_Id,
			// @RequestParam("jiraIssueType") String jiraIssueType,
			@RequestParam("issueId") long issueId,
			// @RequestParam("feature_type_id") int feature_type_id,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {

		Response response = new Response();
		List<FeatureslistModel> featureModel = null;

		List<FeatureslistModel> DetailsoffeatureModel = featuresService.getFeatureDetailsByFeatureId(feature_Id);
		System.out.println("DetailsoffeatureModel:" + DetailsoffeatureModel.toString());
		int release_Id = DetailsoffeatureModel.get(0).getFkReleaseId();
		int feature_type_id = DetailsoffeatureModel.get(0).getFeature_type_id();
		List<FeatureslistModel> featuresTypeList = featuresService.getFeaturesTypeByFeatureTypeId(feature_type_id);
		String jiraIssueType = featuresTypeList.get(0).getJira_issue_type();

		List<JiraReleaseSprintDTO> releaselist = service.getProductReleaseofJiraByReleaseId(release_Id);
		int product_id = releaselist.get(0).getFkProductId();
		if (issueId > 0) {
			response = service.issueassignedtoAutoFeature(product_id, release_Id, feature_Id, issueId, jiraIssueType);
			if (response.getStatusCode() == 1) {
				response.setStatusCode(response.getStatusCode());
				response.setMessage(response.getMessage());
			} else if (response.getStatusCode() == 0) {
				response.setStatusCode(0);
				response.setMessage(response.getMessage());
			} else {
				response.setStatusCode(2);
				response.setMessage(response.getMessage());
			}

		} else {

			long SprintId = releaselist.get(0).getFk_jira_sprint_id();
			List<Product> productlist = getJiraBoardDetailsByAutoProductId(product_id);
			String projectId = productlist.get(0).getFk_jira_projectid();
			String boardName = productlist.get(0).getJiraBoardName().replace(" board", "");
			featureModel = featuresService.getFeatureDetailsByFeatureId(feature_Id);
			String featuresName = featureModel.get(0).getFeature_name();
			String featuresNameUpperCase = featuresName.toUpperCase();
			int story_points = featureModel.get(0).getStory_points();
			response = createIssue(featuresNameUpperCase, boardName, jiraIssueType, fk_user_id, SprintId, feature_Id,
					release_Id, story_points);
			// res1 = jiraController.createIssue(featuresName, projectId, issueType,
			// fk_user_id, sprintId, featureId, releaseId)
			if (response.getStatusCode() == 1) {
				response.setStatusCode(response.getStatusCode());
				response.setMessage(response.getMessage());
			} else if (response.getStatusCode() == 0) {
				response.setStatusCode(0);
				response.setMessage(response.getMessage());
			} else {
				response.setStatusCode(2);
				response.setMessage(response.getMessage());
			}
		}
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/featurenamealreadyconfigure", method = RequestMethod.POST, produces = "application/Json")
	public Response isFeatureNameAlreadyConfigurewithJiraIssue(String featureName, int release_Id) {
		List<FeaturesModel> list = service.isFeatureNameAlreadyConfigurewithJiraIssue(featureName, release_Id);
		Response response;
		if (list.size() > 0) {
			response = new Response();
			response.setStatusCode(0);
			response.setMessage("Feature Name is already configured with Jira Board.");
			return response;
		} else {
			response = new Response();
			response.setStatusCode(1);
			response.setMessage("Feature Name is Unique.Please click on mapping or create");
			return response;
		}

	}

	/* **************************************************************** */

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/unMappedReleasesList", method = RequestMethod.GET, produces = "application/Json")
	public List<ReleaseListModel> unMappedReleasesList() {
		List<ReleaseListModel> releases = null;
		try {
			releases = service.unMappedReleasesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releases;
	}

	// ***********************SYNC AUTOPRODUCT TO JIRABOARD************
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/singlesyncProductAutoToJira", method = RequestMethod.POST, produces = "application/Json")
	public Response syncProductAutoToJira(@RequestParam("productId") int productId,
			@RequestParam("fk_user_id") int fk_user_id) throws JiraException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			Product productDetails = service.getProductDetailsInAuto(productId);
			res = updateProject(productDetails.getProduct_name(), productDetails.getProduct_desc(),
					productDetails.getFk_jira_projectid(), "admin", fk_user_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/syncProductAutoToJira", method = RequestMethod.POST, produces = "application/Json")
	public Response syncTotalAutoProductToJira(@RequestParam("productId") int productId,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response res = null;
		List<Response> listresponse = new ArrayList<>();
		ResponseDTO responseDTO = new ResponseDTO();
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		List<BoardModel> boards = null;
		List<BackLogsModel> issues = null;
		String boardName = null;
		String projectName = null;
		List<SprintModel> sprints = null;
		ReleaseModel releaseModel = null;
		FeaturesModel featuresModel = null;

		try {

			Product productDetails = service.getProductDetailsInAuto(productId);
			System.err.println("productDetails*****" + productDetails.toString());
			res = updateProject(productDetails.getProduct_name(), productDetails.getProduct_desc(),
					productDetails.getFk_jira_projectid(), "admin", fk_user_id);

			if (res.getStatus() > 0) {
				System.out.println("********syncAutoProductToJiraBoard" + res.toString());

				// List<Product> productlist =
				// service.getproductConfigurationwithJiraProject(productId);
				List<ReleaseModel> releaseDetails = service.getReleaseDetailsInAutoByProductId(productId);
				if (releaseDetails.size() > 0) {
					System.out.println("releaseDetails***" + releaseDetails.toString());

					for (ReleaseModel rm : releaseDetails) {
						sprints = agileClientUtility.listOfSprintsByBoardId(productDetails.getFk_jira_boardid(), jcm);
						System.out.println("sprints list*******:" + sprints.toString());
						if (rm.getFk_jira_sprint_id() == 0) {
							res = createSprint(rm.getReleaseName(), productDetails.getFk_jira_boardid(),
									rm.getReleaseDateInternal(), rm.getReleaseDateExternal(),
									rm.getReleaseDescription(), fk_user_id, productId, rm.getReleaseId());
							if (res.getStatusCode() == 1) {
								int releaseId = rm.getReleaseId();
								res = new Response();
								res.setStatusCode(responseDTO.getStatusCode());
								res.setMessage(responseDTO.getMessage());
								// listresponse.add(res);

								List<FeaturesModel> featureDetails = service.getFeatureDetailsByReleaseId(releaseId);

								for (FeaturesModel fm : featureDetails) {

									if (fm.getIssue_id() == 0 && fm.getIs_backlog() == 0) {
										// Feature Creation by Issue

										String featuresNameUpperCase = fm.getFeature_name().toUpperCase();
										ReleaseModel singlereleaseDetails = service.getReleaseDetailsInAuto(releaseId);

										String Jira_issue_type = service
												.getJira_issue_typebyFeatureTypeId(fm.getFk_feature_type_id());
										String BoardKey = productDetails.getJiraBoardName().replaceAll(" board", "");
										System.out.println("BoardKey:" + BoardKey);
										res = createIssue(featuresNameUpperCase, BoardKey, Jira_issue_type, fk_user_id,
												singlereleaseDetails.getFk_jira_sprint_id(), fm.getFeature_id(),
												releaseId, fm.getStory_points());
										// responseDTO = featuresService.addFeatures(featuresModel);
										if (res.getStatusCode() == 1) {
											res = new Response();
											res.setStatusCode(1);
											res.setMessage("Feature Created Successfully.");
											// listresponse.add(res);

										} else if (responseDTO.getStatusCode() == 2) {
											res = new Response();
											res.setStatusCode(2);
											res.setMessage("Feature Already exists");
											// listresponse.add(res);
										} else {
											res = new Response();
											res.setStatusCode(0);
											res.setMessage("Feature creation failed");
											// listresponse.add(res);
										}

									} else {
										// update the feature by IssueExistanceByFeatureId
										String AssigneeByAssigneeId = service.assigneeByAssigneeId(fm.getAssigned_To());
										String issueId = String.valueOf(fm.getIssue_id());
										String Jira_issue_type = service
												.getJira_issue_typebyFeatureTypeId(fm.getFk_feature_type_id());
										System.out.println("Jira_issue_type:" + Jira_issue_type);
										res = updateIssue(issueId, fm.getFeature_name(), Jira_issue_type, "admin",
												fk_user_id);
										if (res.getStatusCode() > 1) {
											res = new Response();
											res.setMessage("JiraRelease To AutoIssue updated successfully");
											res.setStatusCode(1);
											// listresponse.add(res);
										} else {
											res = new Response();
											res.setMessage("JiraRelease To AutoIssue Not updated ");
											res.setStatusCode(0);
											// listresponse.add(res);
										}

									}

								}

							} else if (responseDTO.getStatusCode() == 2) {
								res = new Response();
								res.setStatusCode(2);
								res.setMessage("Release Already exists");
								// listresponse.add(res);
							} else {
								res = new Response();
								res.setStatusCode(0);
								res.setMessage("Release creation failed");
								// listresponse.add(res);
							}
						} else {

							// update the Release by SprintExistanceByReleaseId

							// for (SprintModel sml : sprints) {
							// if (sml.getSprintStatus() == "active") {

							/*
							 * Set<Long> ids =
							 * sprints.stream().map(SprintModel::getSprintId).collect(Collectors.toSet());
							 * List<ReleaseModel> ListofDeletedSprints = releaseDetails.stream()
							 * .filter(book -> !ids.contains(book.getFk_jira_sprint_id()))
							 * .collect(Collectors.toList()); for (ReleaseModel rm1 : ListofDeletedSprints)
							 * {
							 * 
							 * if (rm.getFk_jira_sprint_id() != rm1.getFk_jira_sprint_id()) {
							 */

							for (SprintModel sml : sprints) {

								if (rm.getFk_jira_sprint_id() == sml.getSprintId()) {
									if (!(sml.getSprintStatus().contains("closed"))) {

										res = updateSprint(rm.getFk_jira_sprint_id(), rm.getReleaseName(),
												rm.getReleaseDateInternal(), rm.getReleaseDateExternal(),
												rm.getReleaseDescription(), fk_user_id);
										if (res.getStatusCode() > 0) {
											int releaseId = rm.getReleaseId();
											res = new Response();
											res.setStatusCode(responseDTO.getStatusCode());
											res.setMessage(responseDTO.getMessage());
											// listresponse.add(res);

											List<FeaturesModel> featureDetails = service
													.getFeatureDetailsByReleaseId(releaseId);

											for (FeaturesModel fm : featureDetails) {

												if (fm.getIssue_id() == 0) {
													// Feature Creation by Issue

													String featuresNameUpperCase = fm.getFeature_name().toUpperCase();
													ReleaseModel singlereleaseDetails = service
															.getReleaseDetailsInAuto(releaseId);
													res = createIssue(featuresNameUpperCase,
															productDetails.getJiraBoardName(), fm.getJira_issue_type(),
															fk_user_id, singlereleaseDetails.getFk_jira_sprint_id(),
															fm.getFeature_id(), releaseId, fm.getStory_points());
													// responseDTO = featuresService.addFeatures(featuresModel);
													if (res.getStatusCode() == 1) {
														res = new Response();
														res.setStatusCode(1);
														res.setMessage("Feature Created Successfully.");
														// listresponse.add(res);

													} else if (responseDTO.getStatusCode() == 2) {
														res = new Response();
														res.setStatusCode(2);
														res.setMessage("Feature Already exists");
														// listresponse.add(res);
													} else {
														res = new Response();
														res.setStatusCode(0);
														res.setMessage("Feature creation failed");
														// listresponse.add(res);
													}

												} else {
													// update the feature by IssueExistanceByFeatureId
													String AssigneeByAssigneeId = service
															.assigneeByAssigneeId(fm.getAssigned_To());
													String issueId = String.valueOf(fm.getIssue_id());
													// res = updateIssue(issueId, fm.getFeature_name(),
													// fm.getJira_issue_type(),
													// AssigneeByAssigneeId, fk_user_id);
													res = updateIssue(String.valueOf(fm.getIssue_id()),
															fm.getFeature_description(), fm.getJira_issue_type(),
															"admin", fk_user_id);
													if (res.getStatus() > 1) {
														res = new Response();
														res.setMessage("JiraRelease To AutoIssue updated successfully");
														res.setStatusCode(1);
														// listresponse.add(res);
													} else {
														res = new Response();
														res.setMessage("JiraRelease To AutoIssue Not updated ");
														res.setStatusCode(0);
														// listresponse.add(res);
													}

												}

											}

										} else {
											res = new Response();
											res.setStatusCode(0);
											res.setMessage(responseDTO.getMessage());
											// listresponse.add(res);

										}
									} else {
										res = new Response();
										res.setStatusCode(0);
										res.setMessage("Sprint Closed in Jira:" + rm.getJira_sprintName());
									}
								}
							}
							/*
							 * } else { res = new Response(); res.setStatusCode(0);
							 * res.setMessage("Sprint deleted in Jira." + rm.getJira_sprintName()); //
							 * listresponse.add(res); // System.err.println("rm***************:" +
							 * rm.getJira_sprintName()); } }
							 */
						}

					}

				}
				res = new Response();
				res.setStatus(1);
				res.setMessage("AutoProduct Successfully Synced to JiraProject.");
				// listresponse.add(res);

			} /*
				 * else { res = new Response(); res.setStatus(0);
				 * res.setMessage("AutoProduct Failed to Sync."); // listresponse.add(res);
				 * 
				 * }
				 */
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex.getCause().getMessage().contains("401 Unauthorized")
					|| ex.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setStatus(0);
				res.setMessage("AutoProduct Failed to Sync.");
			}
		}
		// listresponse.add(res);
		return res;
	}

	// *********************************************************************

	// ***********************SYNC AUTORELEASE TO JIRASPRINT************
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/syncReleaseAutoToJira", method = RequestMethod.POST, produces = "application/Json")
	public Response syncReleaseAutoToJira(@RequestParam("releaseId") int releaseId,
			@RequestParam("fk_user_id") int fk_user_id) throws JiraException, URISyntaxException {
		Response res = new Response();
		List<SprintModel> sprints = null;
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);

			ReleaseModel releaseDetails = service.getReleaseDetailsInAuto(releaseId);
			System.out.println("product Id:" + releaseDetails.getFkProductId());
			Product productDetails = service.getProductDetailsInAuto(releaseDetails.getFkProductId());

			// update the Release by SprintExistanceByReleaseId

			// for (SprintModel sml : sprints) {
			// if (sml.getSprintStatus() == "active") {
			sprints = agileClientUtility.listOfSprintsByBoardId(productDetails.getFk_jira_boardid(), jcm);
			for (SprintModel sml : sprints) {
				if (releaseDetails.getFk_jira_sprint_id() == sml.getSprintId()) {
					if (!(sml.getSprintStatus().contains("closed"))) {

						res = updateSprint(releaseDetails.getFk_jira_sprint_id(), releaseDetails.getReleaseName(),
								releaseDetails.getReleaseDateInternal(), releaseDetails.getReleaseDateExternal(),
								releaseDetails.getReleaseDescription(), fk_user_id);
						if (res.getStatus() > 0) {
							// int releaseId = rm.getReleaseId();
							// res = new Response();
							// res.setStatusCode(res.getStatusCode());
							// res.setMessage(res.getMessage());
							// listresponse.add(res);

							List<FeaturesModel> featureDetails = service.getFeatureDetailsByReleaseId(releaseId);

							for (FeaturesModel fm : featureDetails) {

								if (fm.getIssue_id() == 0) {
									// Feature Creation by Issue

									String featuresNameUpperCase = fm.getFeature_name().toUpperCase();
									ReleaseModel singlereleaseDetails = service.getReleaseDetailsInAuto(releaseId);
									res = createIssue(featuresNameUpperCase, productDetails.getJiraBoardName(),
											fm.getJira_issue_type(), fk_user_id,
											singlereleaseDetails.getFk_jira_sprint_id(), fm.getFeature_id(), releaseId,
											fm.getStory_points());
									// responseDTO = featuresService.addFeatures(featuresModel);
									if (res.getStatusCode() == 1) {
										// res = new Response();
										res.setStatusCode(1);
										res.setMessage("Issue Created Successfully.");
										// listresponse.add(res);

									} else if (res.getStatusCode() == 2) {
										// res = new Response();
										res.setStatusCode(2);
										res.setMessage("Issue Already exists");
										// listresponse.add(res);
									} else {
										// res = new Response();
										res.setStatusCode(0);
										res.setMessage("Issue creation failed");
										// listresponse.add(res);
									}

								} else {
									// update the feature by IssueExistanceByFeatureId
									String AssigneeByAssigneeId = service.assigneeByAssigneeId(fm.getAssigned_To());
									String issueId = String.valueOf(fm.getIssue_id());
									// res = updateIssue(issueId, fm.getFeature_name(), fm.getJira_issue_type(),
									// AssigneeByAssigneeId,
									// fk_user_id);
									res = updateIssue(String.valueOf(fm.getIssue_id()), fm.getFeature_description(),
											fm.getJira_issue_type(), "admin", fk_user_id);
									if (res.getStatusCode() > 1) {
										// res = new Response();
										res.setMessage("AutoFeature To JiraIssue updated successfully");
										res.setStatusCode(1);
										// listresponse.add(res);
									} else {
										// res = new Response();
										res.setMessage("AutoRelease To JiraSprint Not updated ");
										res.setStatusCode(0);
										// listresponse.add(res);
									}

								}

							}
							// res = new Response();
							res.setMessage("AutoRelease To JiraSprint updated successfully. ");
							res.setStatusCode(1);

						} else {
							// res = new Response();
							res.setStatusCode(0);
							res.setMessage(res.getMessage());
							// listresponse.add(res);

						}
					}else {

						res = new Response();
						res.setStatusCode(0);
						res.setMessage("Sprint Closed in Jira:" + releaseDetails.getJira_sprintName());
					
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			}
		}
		return res;
	}

	// *********************************************************************

	// ***********************SYNC AUTOFEATURE TO JIRAISSUE************
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/syncFeatureAutoToJira", method = RequestMethod.POST, produces = "application/Json")
	public Response syncFeatureAutoToJira(@RequestParam("featureId") int featureId,
			@RequestParam("fk_user_id") int fk_user_id) throws JiraException, URISyntaxException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			FeaturesModel fm = service.getFeatureDetails(featureId);
			res = updateIssue(String.valueOf(fm.getIssue_id()), fm.getFeature_description(), fm.getJira_issue_type(),
					"admin", fk_user_id);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			}
		}
		return res;
	}

	// *********************************************************************

	// ***********************SYNC JIRABOARD TO AUTOPRODUCT ************

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/onlysyncJiraBoardToAutoProduct", method = RequestMethod.POST, produces = "application/Json")
	public Response syncJiraBoardToAutoProduct(@RequestParam("product_id") int productId,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response res = new Response();
		List<Product> productlist = service.getproductConfigurationwithJiraProject(productId);

		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		List<BoardModel> boards = null;
		String boardName = null;
		String projectName = null;
		try {

			if (productlist.size() > 0) {
				long boardId = productlist.get(0).getFk_jira_boardid();
				boards = agileClientUtility.getJiraBoardDetails(jcm);
				for (BoardModel bm : boards) {
					if (bm.getBoardId() == boardId) {
						boardName = bm.getBoardName();
						projectName = bm.getProjectName();
						res = service.syncJiraBoardToAutoProduct(productId, boardId, boardName, projectName);
						return res;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			}
		}
		return res;
	}

	// ***********************auto product,release,feature sync from Jira

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/syncJiraBoardToAutoProduct", method = RequestMethod.POST, produces = "application/Json")
	public List<Response> syncJiraSprintsByAutoProduct(@RequestParam("product_id") int productId,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response res = null;
		List<Response> listresponse = new ArrayList<>();
		List<ResponseDTO> listresponseDTO = new ArrayList<>();
		ResponseDTO responseDTO = null;
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		List<BackLogsModel> issues = null;
		List<SprintModel> sprints = null;
		ReleaseModel releaseModel = null;
		FeaturesModel featuresModel = null;
		try {
			res = syncJiraBoardToAutoProduct(productId, fk_user_id);
			if (res.getStatus() > 0) {
				System.out.println("********syncJiraBoardToAutoProduct" + res.toString());

				List<Product> productlist = service.getproductConfigurationwithJiraProject(productId);
				if (productlist.size() > 0) {
					System.out.println("productlist" + productlist.toString());
					long boardId = productlist.get(0).getFk_jira_boardid();

					sprints = agileClientUtility.listOfSprintsByBoardId(boardId, jcm);
					for (SprintModel sm : sprints) {
						int SprintExistanceByReleaseId = service.isSprintAlreadyExist(productId, sm.getSprintId(),
								sm.getBoardIdofSprint(), fk_user_id, sm.getSprintName());

						// *******sprint creation as release*********//

						if (SprintExistanceByReleaseId == 0) {
							if (!(sm.getSprintStatus().contains("closed"))) {
								System.out.println("********sprint name:" + sm.getSprintName());

								try {
									DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

									Date endDate = sm.getEndDate();
									String sprintendDate = df.format(endDate);
									System.err.println("DDDDDDDDDDDDDDDDDDDDD:    " + sprintendDate.toString());
									if (sprintendDate.equals(null)) {

									} else {

										releaseModel.setReleaseDateInternal(sprintendDate.toString());
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								// Release Creation by Sprint
								releaseModel = new ReleaseModel();
								releaseModel.setJira_sprintName(sm.getSprintName());
								releaseModel.setFk_jira_sprint_id(sm.getSprintId());
								releaseModel.setReleaseName(sm.getSprintName());
								releaseModel.setReleaseDescription(sm.getSprintName());
								// releaseModel.setReleaseDateExternal(sprintendDate);

								releaseModel.setFkProductId(productId);
								releaseModel.setFkReleaseOwner(fk_user_id);
								int releasestatusid = service.releasestatusidBySprintStatus(sm.getSprintStatus());
								releaseModel.setFkStatusId(releasestatusid);
								releaseModel.setCreatedBy(fk_user_id);
								responseDTO = releaseService.addRelease(releaseModel);
								if (responseDTO.getStatusCode() == 1) {
									int releaseId = responseDTO.getReleaseId();
									res = new Response();
									res.setStatusCode(responseDTO.getStatusCode());
									res.setMessage(responseDTO.getMessage());
									// listresponse.add(res);
									List<JiraReleaseSprintDTO> releaselist = service
											.getProductReleaseofJiraByReleaseId(releaseId);
									long SprintId = releaselist.get(0).getFk_jira_sprint_id();
									issues = agileClientUtility.listOfIssuesBySprintId(SprintId, jcm);
									for (BackLogsModel b : issues) {

										int IssueExistanceByFeatureId = service.isIssueExistanceByFeatureId(productId,
												fk_user_id, releaseId, b.getIssueid(), b);

										// *******Issue Creation as Feature*********//

										if (IssueExistanceByFeatureId == 0) {
											// Feature Creation by Issue
											featuresModel = new FeaturesModel();
											featuresModel.setFeature_name(b.getIssueName());

											if (b.getIssueDescription() == null) {
												featuresModel.setFeature_description(b.getIssueName());
											} else {
												featuresModel.setFeature_description(b.getIssueDescription());
											}
											featuresModel.setStory_points(1);
											featuresModel.setJira_issue_type(b.getIssueTypeName());
											featuresModel.setIssue_id(b.getIssueid());

											int FeatureTypeId = service.getFeatureTypeIdbyIssueType(b.getIssueTypeId());
											featuresModel.setFk_feature_type_id(FeatureTypeId);

											int FeatureStatusId = service
													.getFeatureStatusIdbyIssueStatus((int) b.getIssueStatusId());
											featuresModel.setFk_feature_status_id(FeatureStatusId);

											int UserIdbyIssueAssigneeEmail = service
													.getUserIdbyJiraEmail(b.getIssueAssigneeEmail());

											// featuresModel.setFk_assignment_id(UserIdbyIssueAssigneeEmail);
											featuresModel.setAssigned_To(UserIdbyIssueAssigneeEmail);

											featuresModel.setIs_backlog(0);

											featuresModel.setFk_release_id(SprintExistanceByReleaseId);
											featuresModel.setFk_product_id(productId);

											int UserIdbyIssueCreaterEmail = service
													.getUserIdbyJiraEmail(b.getIssueCreaterEmail());
											featuresModel.setCreated_by(UserIdbyIssueCreaterEmail);

											responseDTO = featuresService.addFeatures(featuresModel);

											if (responseDTO.getStatusCode() == 1) {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setStatusCode(1);
												responseDTO.setMessage("Feature Created Successfully.");
												// res.setResponseDTO(responseDTO);

											} else if (responseDTO.getStatusCode() == 2) {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setStatusCode(2);
												responseDTO.setMessage("Feature Already exists");

												// res.setResponseDTO(responseDTO);
												// listresponse.add(res);
											} else {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setStatusCode(0);
												responseDTO.setMessage("Feature creation failed");
												// listresponse.add(res);
											}
											//listresponseDTO.add(responseDTO);

										}

										// *******Issue Updation for Mapped Feature*********//

										else {
											// update the feature by IssueExistanceByFeatureId
											responseDTO = service.assignFullDetailsofIssuetoExistFeature(b, productId,
													SprintExistanceByReleaseId, fk_user_id, IssueExistanceByFeatureId);
											if (responseDTO.getStatusCode() == 1) {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setMessage("JiraIssue To AutoFeature updated successfully");
												responseDTO.setStatusCode(1);
												// res.setResponseDTO(responseDTO);
												// listresponse.add(res);
											} else {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setMessage("JiraIssue To AutoFeature Not updated ");
												responseDTO.setStatusCode(0);
												// res.setResponseDTO(responseDTO);
												// listresponse.add(res);
											}
											//listresponseDTO.add(responseDTO);

										}

									}

								} else if (responseDTO.getStatusCode() == 2) {
									res = new Response();
									res.setStatusCode(2);
									res.setMessage("Release Already exists");
									res.setResponseDTO(null);
									listresponse.add(res);
								} else {
									res = new Response();
									res.setStatusCode(0);
									res.setMessage("Release creation failed");
									res.setResponseDTO(null);
									listresponse.add(res);
								}
							} else {

								res = new Response();
								res.setStatusCode(0);
								res.setMessage("Sprint Closed in Jira:" + sm.getSprintName());
								res.setResponseDTO(null);
								listresponse.add(res);
							}

						}

						// *******sprint updation for Mapped Release*********//

						else {
							// update the Release by SprintExistanceByReleaseId
							if (!(sm.getSprintStatus().contains("closed"))) {
								responseDTO = service.assignFullDetailsofSprinttoRelease(productId, sm.getSprintId(),
										sm.getSprintName(), SprintExistanceByReleaseId, sm.getStartDate(),
										sm.getEndDate(), fk_user_id, sm);
								if (responseDTO.getStatusCode() > 0) {
									int releaseId = responseDTO.getReleaseId();
									res = new Response();
									res.setStatusCode(responseDTO.getStatusCode());
									res.setMessage(responseDTO.getMessage());
									List<JiraReleaseSprintDTO> releaselist = service
											.getProductReleaseofJiraByReleaseId(SprintExistanceByReleaseId);
									long SprintId = releaselist.get(0).getFk_jira_sprint_id();

									issues = agileClientUtility.listOfIssuesBySprintId(SprintId, jcm);
									for (BackLogsModel b : issues) {

										int IssueExistanceByFeatureId = service.isIssueExistanceByFeatureId(productId,
												fk_user_id, releaseId, b.getIssueid(), b);
										if (IssueExistanceByFeatureId == 0) {
											// Feature Creation by Issue
											featuresModel = new FeaturesModel();
											featuresModel.setFeature_name(b.getIssueName());

											if (b.getIssueDescription() == null) {
												featuresModel.setFeature_description(b.getIssueName());
											} else {
												featuresModel.setFeature_description(b.getIssueDescription());
											}
											featuresModel.setStory_points(1);
											featuresModel.setJira_issue_type(b.getIssueTypeName());
											featuresModel.setIssue_id(b.getIssueid());

											int FeatureTypeId = service.getFeatureTypeIdbyIssueType(b.getIssueTypeId());
											featuresModel.setFk_feature_type_id(FeatureTypeId);

											int FeatureStatusId = service
													.getFeatureStatusIdbyIssueStatus((int) b.getIssueStatusId());
											featuresModel.setFk_feature_status_id(FeatureStatusId);

											int UserIdbyIssueAssigneeEmail = service
													.getUserIdbyJiraEmail(b.getIssueAssigneeEmail());

											// featuresModel.setFk_assignment_id(UserIdbyIssueAssigneeEmail);
											featuresModel.setAssigned_To(UserIdbyIssueAssigneeEmail);

											featuresModel.setIs_backlog(0);

											featuresModel.setFk_release_id(SprintExistanceByReleaseId);
											featuresModel.setFk_product_id(productId);

											int UserIdbyIssueCreaterEmail = service
													.getUserIdbyJiraEmail(b.getIssueCreaterEmail());
											featuresModel.setCreated_by(UserIdbyIssueCreaterEmail);

											responseDTO = featuresService.addFeatures(featuresModel);

											if (responseDTO.getStatusCode() == 1) {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setStatusCode(1);
												responseDTO.setMessage("Feature Created Successfully.");
												// res.setResponseDTO(responseDTO);
											} else if (responseDTO.getStatusCode() == 2) {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setStatusCode(2);
												responseDTO.setMessage("Feature Already exists");

												// res.setResponseDTO(responseDTO);
												// listresponse.add(res);
											} else {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setStatusCode(0);
												responseDTO.setMessage("Feature creation failed");
												// listresponse.add(res);
											}
											//listresponseDTO.add(responseDTO);

										} else {
											// update the feature by IssueExistanceByFeatureId
											responseDTO = service.assignFullDetailsofIssuetoExistFeature(b, productId,
													SprintExistanceByReleaseId, fk_user_id, IssueExistanceByFeatureId);
											if (responseDTO.getStatusCode() == 1) {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setMessage("JiraIssue To AutoFeature updated successfully");
												responseDTO.setStatusCode(1);

												// res.setResponseDTO(responseDTO);
												// listresponse.add(res);
											} else {
												// res = new Response();
												responseDTO = new ResponseDTO();
												responseDTO.setMessage("JiraIssue To AutoFeature Not updated ");
												responseDTO.setStatusCode(0);
												// res.setResponseDTO(responseDTO);
												// listresponse.add(res);
											}

										//	listresponseDTO.add(responseDTO);

										}

									}

									res = new Response();
									res.setStatusCode(1);
									res.setMessage("JiraSprint Updated in Auto:" + sm.getSprintName());
									res.setListofResponseDTO(listresponseDTO);
									listresponse.add(res);

								} else {
									res = new Response();
									res.setStatusCode(0);
									res.setMessage(responseDTO.getMessage());
									res.setResponseDTO(null);
									listresponse.add(res);

								}

							} else {

								res = new Response();
								res.setStatusCode(0);
								res.setMessage("Sprint Closed in Jira:" + sm.getSprintName());
								res.setResponseDTO(null);
								listresponse.add(res);

							}
						}

					}

					// response for deleted sprint ,comparing with release
					List<ReleaseModel> releaseDetails = service.getReleaseDetailsInAutoByProductId(productId);

					Set<Long> ids = sprints.stream().map(SprintModel::getSprintId).collect(Collectors.toSet());
					List<ReleaseModel> parentBooks = releaseDetails.stream()
							.filter(book -> !ids.contains(book.getFk_jira_sprint_id())).collect(Collectors.toList());
					for (ReleaseModel rm : parentBooks) {
						res = new Response();
						res.setStatusCode(0);
						res.setMessage("Sprint deleted in Jira." + rm.getJira_sprintName());
						listresponse.add(res);
						System.err.println("rm***************:" + rm.getJira_sprintName());
					}

					// for features updation as backlog if issue moved to backlog
					List<BackLogsModel> backlogs = null;
					long boardId1 = productlist.get(0).getFk_jira_boardid();
					backlogs = agileClientUtility.getbacklogsByboardId(boardId1, jcm);
					for (BackLogsModel bl : backlogs) {
						int updatefeaturesasbacklogs = service.updatefeaturesasbacklogs(bl, productId);
						if (updatefeaturesasbacklogs > 0) {
							System.out.println("Features updated as backlog." + bl.getIssueid());
						}
					}

				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex.getCause().getMessage().contains("401 Unauthorized")
					|| ex.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setStatus(0);
				res.setMessage("JiraProject Failed to Sync.");
			}
		}
		// listresponse.add(res);
		return listresponse;
	}

	// updating the issues as status shipped for Releases mapped with closed sprint
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updateReleaseandFeatureasshippedstatus", method = RequestMethod.POST, produces = "application/Json")
	public List<Response> updateReleaseandFeatureasshippedstatus(@RequestParam("product_id") int productId,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response res = null;
		List<Response> listresponse = new ArrayList<>();
		ResponseDTO responseDTO = new ResponseDTO();
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		List<BoardModel> boards = null;
		List<BackLogsModel> issues = null;
		String boardName = null;
		String projectName = null;
		List<SprintModel> sprints = null;
		ReleaseModel releaseModel = null;
		FeaturesModel featuresModel = null;
		try {
			res = syncJiraBoardToAutoProduct(productId, fk_user_id);
			if (res.getStatus() > 0) {
				System.out.println("********syncJiraBoardToAutoProduct" + res.toString());

				List<Product> productlist = service.getproductConfigurationwithJiraProject(productId);
				if (productlist.size() > 0) {
					System.out.println("productlist" + productlist.toString());
					long boardId = productlist.get(0).getFk_jira_boardid();

					sprints = agileClientUtility.listOfSprintsByBoardId(boardId, jcm);
					for (SprintModel sm : sprints) {
						if ((sm.getSprintStatus().contains("closed"))) {
							int SprintExistanceByReleaseId = service.isSprintAlreadyExist(productId, sm.getSprintId(),
									sm.getBoardIdofSprint(), fk_user_id, sm.getSprintName());
							if (SprintExistanceByReleaseId > 0) {
								// updating status of mapped release with closed sprint as status shipped.
								responseDTO = service.assignFullDetailsofSprinttoReleaseAsClosedSprint(productId,
										sm.getSprintId(), sm.getSprintName(), SprintExistanceByReleaseId,
										sm.getStartDate(), sm.getEndDate(), fk_user_id, sm);
								if (responseDTO.getStatusCode() > 0) {
									res = new Response();
									res.setStatusCode(1);
									res.setMessage("Release and Feature updated as status shipped:"
											+ SprintExistanceByReleaseId);

									/*
									 * int releaseIdBySprintId = service
									 * .releaseIdBySprintIdAsClosedStatus(sm.getSprintId(), sm, productId);
									 */
									List<FeaturesModel> featureDetails = service
											.getFeatureDetailsByReleaseId(SprintExistanceByReleaseId);

									for (FeaturesModel fm : featureDetails) {
										int updateFeatureStatusAsDone = service.updateFeaturesStatusAsDone(fm,
												SprintExistanceByReleaseId, productId);
										if (updateFeatureStatusAsDone > 0) {
											System.out.println("***updateFeatureStatusAsDone:" + fm.getFeature_id());
										}

									}

								}
							}
						}

					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex.getCause().getMessage().contains("401 Unauthorized")
					|| ex.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setStatus(0);
				res.setMessage("closed JiraSprints failed to update.");
			}
		}
		return listresponse;
	}

	//
	// ********************jira sprint,Issue sync to auto

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/syncJiraSprintToAutoRelease", method = RequestMethod.POST, produces = "application/Json")
	public Response totalsyncJiraSprintAndIssueToAutoRelease(@RequestParam("release_id") int releaseId,
			@RequestParam("product_id") int productId, @RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response res = null;
		List<Response> listresponse = new ArrayList<>();
		ResponseDTO responseDTO = new ResponseDTO();
		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		List<BoardModel> boards = null;
		List<BackLogsModel> issues = null;
		String boardName = null;
		String projectName = null;
		List<SprintModel> sprints = null;
		ReleaseModel releaseModel = null;
		FeaturesModel featuresModel = null;
		try {

			List<Product> productlist = service.getproductConfigurationwithJiraProject(productId);
			if (productlist.size() > 0) {
				System.out.println("productlist" + productlist.toString());
				long boardId = productlist.get(0).getFk_jira_boardid();

				List<JiraReleaseSprintDTO> releaselist = service.getProductReleaseofJiraByReleaseId(releaseId);
				long SprintId = releaselist.get(0).getFk_jira_sprint_id();
				sprints = agileClientUtility.listOfSprintsByBoardId(boardId, jcm);

				// checking the mapped sprint is deleted or not.
				List<ReleaseModel> releaseDetails = service.getReleaseDetailsInAutoByProductId(productId);
				Set<Long> ids = sprints.stream().map(SprintModel::getSprintId).collect(Collectors.toSet());
				List<ReleaseModel> Modellist = releaseDetails.stream()
						.filter(book -> !ids.contains(book.getFk_jira_sprint_id())).collect(Collectors.toList());
				for (ReleaseModel rm : Modellist) {
					if (releaseId == rm.getReleaseId()) {
						res = new Response();
						res.setStatusCode(0);
						res.setMessage("Sprint deleted in Jira." + rm.getJira_sprintName());
						return res;
						// System.err.println("rm***************:" + rm.getJira_sprintName());
					}
				}

				for (SprintModel sm : sprints) {

					if (SprintId == sm.getSprintId()) {
						// update the Release by SprintExistanceByReleaseId
						if (!(sm.getSprintStatus().contains("closed"))) {
							responseDTO = service.assignFullDetailsofSprinttoRelease(productId, sm.getSprintId(),
									sm.getSprintName(), releaseId, sm.getStartDate(), sm.getEndDate(), fk_user_id, sm);
							if (responseDTO.getStatusCode() > 0) {
								// int releaseId = responseDTO.getReleaseId();
								res = new Response();
								res.setStatusCode(responseDTO.getStatusCode());
								res.setMessage(responseDTO.getMessage());
								// List<JiraReleaseSprintDTO> releaselist = service
								// .getProductReleaseofJiraByReleaseId(SprintExistanceByReleaseId);
								// long SprintId = releaselist.get(0).getFk_jira_sprint_id();

								issues = agileClientUtility.listOfIssuesBySprintId(SprintId, jcm);
								for (BackLogsModel b : issues) {

									int IssueExistanceByFeatureId = service.isIssueExistanceByFeatureId(productId,
											fk_user_id, releaseId, b.getIssueid(), b);
									if (IssueExistanceByFeatureId == 0) {
										// Feature Creation by Issue
										featuresModel = new FeaturesModel();
										featuresModel.setFeature_name(b.getIssueName());

										if (b.getIssueDescription() == null) {
											featuresModel.setFeature_description(b.getIssueName());
										} else {
											featuresModel.setFeature_description(b.getIssueDescription());
										}
										featuresModel.setStory_points(1);
										featuresModel.setJira_issue_type(b.getIssueTypeName());
										featuresModel.setIssue_id(b.getIssueid());

										int FeatureTypeId = service.getFeatureTypeIdbyIssueType(b.getIssueTypeId());
										featuresModel.setFk_feature_type_id(FeatureTypeId);

										int FeatureStatusId = service
												.getFeatureStatusIdbyIssueStatus((int) b.getIssueStatusId());
										featuresModel.setFk_feature_status_id(FeatureStatusId);

										int UserIdbyIssueAssigneeEmail = service
												.getUserIdbyJiraEmail(b.getIssueAssigneeEmail());

										// featuresModel.setFk_assignment_id(UserIdbyIssueAssigneeEmail);
										featuresModel.setAssigned_To(UserIdbyIssueAssigneeEmail);

										featuresModel.setIs_backlog(0);

										featuresModel.setFk_release_id(releaseId);
										featuresModel.setFk_product_id(productId);

										int UserIdbyIssueCreaterEmail = service
												.getUserIdbyJiraEmail(b.getIssueCreaterEmail());
										featuresModel.setCreated_by(UserIdbyIssueCreaterEmail);

										responseDTO = featuresService.addFeatures(featuresModel);
										if (responseDTO.getStatusCode() == 1) {
											res = new Response();
											res.setStatusCode(1);
											res.setMessage("Feature Created Successfully.");
											// listresponse.add(res);

										} else if (responseDTO.getStatusCode() == 2) {
											res = new Response();
											res.setStatusCode(2);
											res.setMessage("Feature Already exists");
											// listresponse.add(res);
										} else {
											res = new Response();
											res.setStatusCode(0);
											res.setMessage("Feature creation failed");
											// listresponse.add(res);
										}
									} else {
										// update the feature by IssueExistanceByFeatureId
										responseDTO = service.assignFullDetailsofIssuetoExistFeature(b, productId,
												releaseId, fk_user_id, IssueExistanceByFeatureId);
										if (responseDTO.getStatusCode() > 1) {
											res = new Response();
											res.setMessage("JiraRelease To AutoIssue updated successfully");
											res.setStatusCode(1);
											// listresponse.add(res);
										} else {
											res = new Response();
											res.setMessage("JiraRelease To AutoIssue Not updated ");
											res.setStatusCode(0);
											// listresponse.add(res);
										}

									}

								}

								res = new Response();
								res.setStatus(1);
								res.setMessage("JiraSprint Successfully Synced to AutoRelease .");

							} else {
								res = new Response();
								res.setStatus(0);
								res.setMessage("JiraSprint Failed to sync.");
							}
						} else {
							res = new Response();
							res.setStatus(0);
							res.setMessage("JiraSprint Closed in Jira.");

							/*
							 * responseDTO =
							 * service.assignFullDetailsofSprinttoReleaseAsClosedSprint(productId,
							 * sm.getSprintId(), sm.getSprintName(), releaseId, sm.getStartDate(),
							 * sm.getEndDate(), fk_user_id, sm);
							 * 
							 * res = new Response(); res.setStatus(1);
							 * res.setMessage("JiraSprint Successfully Synced to AutoRelease .");
							 * 
							 * // for (SprintModel sm : sprints) { if
							 * ((sm.getSprintStatus().contains("closed"))) { // int releaseIdBySprintId = //
							 * service.releaseIdBySprintIdAsClosedStatus(sm.getSprintId(), sm, //
							 * productId); List<FeaturesModel> featureDetails =
							 * service.getFeatureDetailsByReleaseId(releaseId);
							 * 
							 * for (FeaturesModel fm : featureDetails) { int updateFeatureStatusAsDone =
							 * service.updateFeaturesStatusAsDone(fm, releaseId, productId); if
							 * (updateFeatureStatusAsDone > 0) {
							 * System.out.println("***updateFeatureStatusAsDone:" + fm.getFeature_id()); } }
							 * 
							 * }
							 * 
							 * // }
							 * 
							 */}

						List<BackLogsModel> backlogs = null;
						long boardId1 = productlist.get(0).getFk_jira_boardid();
						backlogs = agileClientUtility.getbacklogsByboardId(boardId1, jcm);
						List<FeaturesModel> featureDetails = service.getFeatureDetailsByReleaseId(releaseId);
						for (FeaturesModel fm : featureDetails) {
							for (BackLogsModel bl : backlogs) {
								if (fm.getIssue_id() == bl.getIssueid()) {
									int updatefeaturesasbacklogs = service.updatefeaturesasbacklogs(bl, productId);
									if (updatefeaturesasbacklogs > 0) {
										System.out.println("Features updated as backlog." + bl.getIssueid());
									}
								}
							}
						}

					}

				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex.getCause().getMessage().contains("401 Unauthorized")
					|| ex.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setStatus(0);
				res.setMessage("JiraSprint Failed to sync.");
			}
		}
		return res;

	}

	// **********************************************************

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/onlysyncJiraSprintsToAutoByProductId", method = RequestMethod.POST, produces = "application/Json")
	public Response onlysyncJiraSprintsToAutoByProductId(@RequestParam("product_id") int productId,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response res = new Response();
		List<Product> productlist = service.getproductConfigurationwithJiraProject(productId);

		JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
		List<BoardModel> boards = null;
		String boardName = null;
		String projectName = null;
		try {

			if (productlist.size() > 0) {
				long boardId = productlist.get(0).getFk_jira_boardid();
				boards = agileClientUtility.getJiraBoardDetails(jcm);
				for (BoardModel bm : boards) {
					if (bm.getBoardId() == boardId) {
						boardName = bm.getBoardName();
						projectName = bm.getProjectName();
						res = service.syncJiraBoardToAutoProduct(productId, boardId, boardName, projectName);
						return res;
					}
				}
			}
		} catch (JiraException ex) {
			ex.printStackTrace();
			if (ex.getCause().getMessage().contains("401 Unauthorized")
					|| ex.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			}
		}
		return res;
	}

	// *********************SYNC JIRASPRINT TO AUTORELEASE *****************

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/onlysyncJiraSprintToAutoRelease", method = RequestMethod.POST, produces = "application/Json")
	public Response syncJiraSprintToAutoRelease(@RequestParam("release_id") int releaseId,
			@RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response res = new Response();
		String sprintName = null;
		try {
			List<JiraReleaseSprintDTO> releaselist = service.getProductReleaseofJiraByReleaseId(releaseId);
			long sprintId = releaselist.get(0).getFk_jira_sprint_id();
			long boardId = releaselist.get(0).getFk_jira_boardid();
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			List<SprintModel> sprints = null;
			sprints = agileClientUtility.listOfSprintsByBoardId(boardId, jcm);
			for (SprintModel sm : sprints) {
				if (sm.getSprintId() == sprintId) {
					sprintName = sm.getSprintName();
					res = service.syncJiraSprintToAutoRelease(releaseId, sprintId, boardId, sprintName);
					return res;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex.getCause().getMessage().contains("401 Unauthorized")
					|| ex.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			}
		}
		return res;
	}

	// *********************SYNC JIRAISSUE TO AUTOFEATURE *****************

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/syncJiraIssueToAutoFeature", method = RequestMethod.POST, produces = "application/Json")
	public Response syncJiraIssuesToAutoRelease(
			/*
			 * @RequestParam("product_id") int productId,
			 * 
			 * @RequestParam("release_id") int releaseId,
			 */
			@RequestParam("feature_id") int feature_id, @RequestParam("fk_user_id") int fk_user_id) throws Exception {
		Response res = new Response();

		try {
			FeaturesModel featuremodellist = service.getJiraIssueDetailsByFeatureId(feature_id);
			int releaseId = featuremodellist.getFk_release_id();
			System.out.println("*********releaseId:" + releaseId);
			List<JiraReleaseSprintDTO> releaselist = service.getProductReleaseofJiraByReleaseId(releaseId);
			long sprintId = releaselist.get(0).getFk_jira_sprint_id();
			System.out.println("*********sprintId:" + sprintId);
			// long boardId = releaselist.get(1).getFk_jira_boardid();
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);

			List<BackLogsModel> issues = null;
			issues = agileClientUtility.listOfIssuesBySprintId(sprintId, jcm);
			for (BackLogsModel bm : issues) {
				if (featuremodellist.getIssue_id() == bm.getIssueid()) {

					res = service.syncJiraIssuesToAutoFeature(feature_id, bm);
					return res;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex.getCause().getMessage().contains("401 Unauthorized")
					|| ex.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (ex.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			}
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/moveIssuesToBacklog", method = RequestMethod.POST, produces = "application/Json")
	public Response moveIssuesToBacklog(@RequestParam("issueKeys") List issueKeys,
			@RequestParam("fk_user_id") int fk_user_id) throws JiraException, URISyntaxException {
		Response res = new Response();
		try {
			JiraConfigurationModel jcm = service.getJiraCredentialsByUserId(fk_user_id);
			Sprint response = agileClientUtility.moveIssuesToBacklog(jcm, issueKeys);
			res.setMessage("Backlog added successfully");
			res.setStatus(1);
			/*
			 * if (response.getId() != 0) { res.setMessage("Issue added successfully");
			 * res.setStatus(1); } else { res.setMessage("Issue adition failed");
			 * res.setStatus(0); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause().getMessage().contains("401 Unauthorized")
					|| e.getCause().getMessage().contains("Name or service not known")) {
				res.setMessage("Invalid Jira credentials");
			} else if (e.getCause().getMessage().contains("503 Service Temporarily Unavailable")) {
				res.setMessage("Your Atlassian Cloud subscription requires renewal");
			} else {
				res.setMessage("Exception occured");
			}
		}
		return res;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/mappedReleasesList", method = RequestMethod.GET, produces = "application/Json")
	public List<ReleaseListModel> mappedReleasesList() {
		List<ReleaseListModel> releases = null;
		try {
			releases = service.mappedReleasesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releases;
	}

}
