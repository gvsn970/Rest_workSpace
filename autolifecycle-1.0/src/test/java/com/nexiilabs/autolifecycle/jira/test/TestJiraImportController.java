package com.nexiilabs.autolifecycle.jira.test;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexiilabs.autolifecycle.jira.BackLogsModel;
import com.nexiilabs.autolifecycle.jira.BoardModel;
import com.nexiilabs.autolifecycle.jira.EpicModel;
import com.nexiilabs.autolifecycle.jira.JiraAgileClientUtility;
import com.nexiilabs.autolifecycle.jira.JiraConfigurationModel;
import com.nexiilabs.autolifecycle.jira.JiraController;
import com.nexiilabs.autolifecycle.jira.JiraService;
import com.nexiilabs.autolifecycle.jira.SprintModel;
import com.nexiilabs.autolifecycle.productline.Response;

@RunWith(MockitoJUnitRunner.class)
// @RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class TestJiraImportController {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@InjectMocks
	private JiraController jiraController;

	@Mock
	private JiraAgileClientUtility agileservice;

	@Mock
	private JiraService jiraservice;

	@Mock
	ObjectMapper objectMapper;

	JacksonTester<BoardModel> jsonTester;

	List<BoardModel> boards = null;
	List<SprintModel> spints = null;
	List<EpicModel> epics = null;
	List<BackLogsModel> issues = null;

	BoardModel b1;
	SprintModel s1;
	EpicModel e1;
	BackLogsModel i1;
	JiraConfigurationModel jcm1;
	Response response;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(jiraController).build();
		BoardModel b1 = new BoardModel();
		b1.setBoardId(1);
		b1.setBoardName("AUT board");
		BoardModel b2 = new BoardModel();
		b2.setBoardId(2);
		b2.setBoardName("Stp");
		boards = Arrays.asList(b1, b2);

		SprintModel s1 = new SprintModel();
		s1.setBoardIdofSprint(1);
		s1.setSprintId(1);
		s1.setSprintName("Sprint 1");
		SprintModel s2 = new SprintModel();
		s2.setBoardIdofSprint(1);
		s2.setSprintId(2);
		s2.setSprintName("Sprint 2");
		spints = Arrays.asList(s1, s2);

		EpicModel e1 = new EpicModel();
		e1.setEpicId(10064);
		e1.setKey("AUTO-34");
		e1.setEpicName("Epic 1");
		EpicModel e2 = new EpicModel();
		e2.setEpicId(10065);
		e2.setKey("AUTO-35");
		e2.setEpicName("Epic 2");
		epics = Arrays.asList(e1, e2);

		objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);

	}

	// jira credentials adding failed case.already exist
	@Test
	public void addJiraCredentialsTestFailed() throws Exception {

		response = new Response();
		response.setStatusCode(0);
		response.setMessage("Jira Credentials already exist with this User Id:5");
		when(jiraservice.isUserNotExist(5)).thenReturn(false);
		mockMvc.perform(post("/jira/addcredentials").param("username", "rahul.sathiri@nexiilabs.com")
				.param("password", "R@hul95426").param("url", "https://autoteamlife.atlassian.net/")
				.param("fk_user_id", "5").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Jira Credentials already exist with this User Id:5")));

	}

	// jira credentials adding success case.valid credentials
/*	@Test
	public void addJiraCredentialsTest() throws Exception {

		when(jiraservice.isUserNotExist(5)).thenReturn(true);

		response = new Response();
		response.setStatusCode(1);
		response.setMessage("Valid Credentials");

		Response response1 = new Response();
		response1.setStatusCode(1);
		response1.setMessage("Jira Credentials added Succesfully.");

		JiraConfigurationModel jcm1 = new JiraConfigurationModel();
		jcm1.setApplication_url("https://autoteamlife.atlassian.net/");
		jcm1.setFk_user_id(5);
		jcm1.setPassword("R@hul95426");
		jcm1.setUser_name("rahul.sathiri@nexiilabs.com");
		// if (response.getStatusCode() == 1)
		when(agileservice.testJiraCredentials(jcm1)).thenReturn(response);

		when(jiraservice.addJiraCredentials("rahul.sathiri@nexiilabs.com", "R@hul95426",
				"https://autoteamlife.atlassian.net/", 5)).thenReturn(response1);
		mockMvc.perform(post("/jira/addcredentials").param("username", "rahul.sathiri@nexiilabs.com")
				.param("password", "R@hul95426").param("url", "https://autoteamlife.atlassian.net/")
				.param("fk_user_id", "5").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.statusCode", is("1")))
				.andExpect(jsonPath("$.message", is("Jira Credentials added Succesfully.")));

	}*/

	// jira credentials adding .Invalid credentials
/*	@Test
	public void addJiraCredentialsTestInvalid() throws Exception {

		when(jiraservice.isUserNotExist(5)).thenReturn(true);

		response = new Response();
		response.setStatusCode(0);
		response.setMessage("Invalid Jira credentials.Please Enter Valid Jira Credentials.");

		JiraConfigurationModel jcm1 = new JiraConfigurationModel();
		jcm1.setApplication_url("https://autoteamlife.atlassian.net/");
		jcm1.setFk_user_id(5);
		jcm1.setPassword("R@hul9542");
		jcm1.setUser_name("rahul.sathir@nexiilabs.com");
		when(agileservice.testJiraCredentials(jcm1)).thenReturn(response);

		when(jiraservice.addJiraCredentials("rahul.sathir@nexiilabs.com", "R@hul9542",
				"https://autoteamlife.atlassian.net/", 5)).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/jira/addcredentials")
				.param("username", "rahul.sathir@nexiilabs.com").param("password", "R@hul9542")
				.param("url", "https://autoteamlife.atlassian.net/").param("fk_user_id", "5")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("Invalid Jira credentials.Please Enter Valid Jira Credentials.")));

	}*/

	// get jira credentials of user.

	@Test
	public void getJiraCredentialsTest() throws Exception {

		JiraConfigurationModel jcm1 = new JiraConfigurationModel();
		jcm1.setApplication_url("https://autoteamlife.atlassian.net/");
		// jcm1.setFk_user_id(1);
		jcm1.setPassword("R@hul95426");
		jcm1.setUser_name("rahul.sathiri@nexiilabs.com");
		jcm1.setJira_configuration_id(2);
		List<JiraConfigurationModel> jcm2 = new ArrayList<>();
		jcm2.add(jcm1);

		when(jiraservice.getJiraDetails(Mockito.anyInt())).thenReturn(jcm2);

		mockMvc.perform(get("/jira/jiracredentialsofuser").param("fk_user_id", "1").contentType("application/json")
				.accept("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].jira_configuration_id", is(2)))
				.andExpect(jsonPath("$[0].application_url", is("https://autoteamlife.atlassian.net/")))
				.andExpect(jsonPath("$[0].user_name", is("rahul.sathiri@nexiilabs.com")))
				.andExpect(jsonPath("$[0].password", is("R@hul95426")));

	}

	// get Jira credentials failed case.
	@Test
	public void getJiraCredentialsTestFailed() throws Exception {

		JiraConfigurationModel jcm1 = new JiraConfigurationModel();

		List<JiraConfigurationModel> jcm2 = new ArrayList<>();
		jcm2.add(jcm1);

		response = new Response();
		response.setStatusCode(0);
		response.setMessage("No Jira Credentials Found to this User.Please add your Jira Credentials.");
		List<Response> response1 = new ArrayList<>();
		response1.add(response);
		when(jiraservice.getJiraDetails(8)).thenReturn(jcm2);
		mockMvc.perform(get("/jira/jiracredentialsofuser").param("fk_user_id", "8").contentType("application/json")
				.accept("application/json")).andExpect(jsonPath("$.*", Matchers.hasSize(1)));

		/*
		 * andExpect(status().isOk()).andExpect(jsonPath("$[0].statusCode", is(0)))
		 * .andExpect(jsonPath("$[0].message",
		 * is("No Jira Credentials Found to this User.Please add your Jira Credentials."
		 * )));
		 */

	}

	// Jira.get all boards
	@Test
	public void getAllBoards() throws Exception {
		BoardModel b1 = new BoardModel();
		b1.setBoardId(1);
		b1.setBoardName("AUT board");
		boards = new ArrayList<>();
		boards.add(b1);
		JiraConfigurationModel jcm1 = new JiraConfigurationModel();
		jcm1.setApplication_url("https://autoteamlife.atlassian.net/");
		jcm1.setFk_user_id(1);
		jcm1.setPassword("R@hul95426");
		jcm1.setUser_name("rahul.sathiri@nexiilabs.com");
		when(jiraservice.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm1);
		when(agileservice.getJiraBoardDetails(jcm1)).thenReturn(boards);
		mockMvc.perform(get("/jira/getboards").param("fk_user_id", "1").contentType("application/json")
				.accept("application/json")).andExpect(status().isOk()).andExpect(jsonPath("$[0].boardId", is(1)))
				.andExpect(jsonPath("$[0].boardName", is("AUT board")));

	}

	// Jira.get Backlogs of board.
	@Test
	public void getBacklogsofBoards() throws Exception {

		JiraConfigurationModel jcm1 = new JiraConfigurationModel();
		jcm1.setApplication_url("https://autoteamlife.atlassian.net/");
		jcm1.setFk_user_id(1);
		jcm1.setPassword("R@hul95426");
		jcm1.setUser_name("rahul.sathiri@nexiilabs.com");

		i1 = new BackLogsModel();
		i1.setIssueid(10023);
		i1.setIssueKey("AP-24");
		i1.setIssueName("apple product got an issue with battery and heatsup while charging.");
		i1.setIssueTypeName("Story");
		i1.setIssueTypeDescription("Stories track functionality or features expressed as user goals.");
		i1.setIssueAssigneeEmail("rahul.sathiri@nexiilabs.com");
		i1.setIssueAssigneeName("Rahul Sathiri");
		i1.setIssueCreatedon(null);
		i1.setIssueCreaterName("Rahul Sathiri");
		i1.setIssueCreaterEmail("rahul.sathiri@nexiilabs.com");

		issues = new ArrayList<>();
		issues.add(i1);
		when(jiraservice.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm1);
		when(agileservice.getbacklogsByboardId(1, jcm1)).thenReturn(issues);

		mockMvc.perform(get("/jira/getbacklogsbyboardId").param("fk_user_id", "1").param("BoardId", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andDo(print())

				.andExpect(status().isOk()).andExpect(jsonPath("$[0].issueid", is(10023)))
				.andExpect(jsonPath("$[0].issueKey", is("AP-24")))
				.andExpect(jsonPath("$[0].issueName",
						is("apple product got an issue with battery and heatsup while charging.")))
				.andExpect(jsonPath("$[0].issueAssigneeName", is("Rahul Sathiri")))
				.andExpect(jsonPath("$[0].issueAssigneeEmail", is("rahul.sathiri@nexiilabs.com")))
				.andExpect(jsonPath("$[0].issueCreaterName", is("Rahul Sathiri")))
				.andExpect(jsonPath("$[0].issueCreaterEmail", is("rahul.sathiri@nexiilabs.com")))
				.andExpect(jsonPath("$[0].issueTypeName", is("Story"))).andExpect(jsonPath("$[0].issueTypeDescription",
						is("Stories track functionality or features expressed as user goals.")));
		// .andExpect(jsonPath("$[0].issueCreatedon", is("null")));

	}

	// Jira.get all sprints by board.
	@Test
	public void getAllSprintsbyBoard() throws Exception {
		BoardModel b1 = new BoardModel();
		b1.setBoardId(1);
		b1.setBoardName("AUT board");
		boards = new ArrayList<>();
		boards.add(b1);

		s1 = new SprintModel();
		s1.setSprintId(1);
		s1.setSprintName("Sample Sprint 2");
		s1.setBoardIdofSprint(1);

		spints = new ArrayList<>();
		spints.add(s1);

		JiraConfigurationModel jcm1 = new JiraConfigurationModel();
		jcm1.setApplication_url("https://autoteamlife.atlassian.net/");
		jcm1.setFk_user_id(1);
		jcm1.setPassword("R@hul95426");
		jcm1.setUser_name("rahul.sathiri@nexiilabs.com");

		when(jiraservice.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm1);
		when(agileservice.listOfSprintsByBoardId(1, jcm1)).thenReturn(spints);

		mockMvc.perform(get("/jira/listofsprintsbyboardId").param("fk_user_id", "1").param("BoardId", "1")
				.contentType("application/json").accept("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].sprintId", is(1)))
				.andExpect(jsonPath("$[0].sprintName", is("Sample Sprint 2")))
				.andExpect(jsonPath("$[0].boardIdofSprint", is(1)));

	}

	// Jira.get issues of sprint.
	@Test
	public void getIssuesofSprint() throws Exception {

		JiraConfigurationModel jcm1 = new JiraConfigurationModel();
		jcm1.setApplication_url("https://autoteamlife.atlassian.net/");
		jcm1.setFk_user_id(1);
		jcm1.setPassword("R@hul95426");
		jcm1.setUser_name("rahul.sathiri@nexiilabs.com");

		i1 = new BackLogsModel();
		i1.setIssueid(10023);
		i1.setIssueKey("AP-24");
		i1.setIssueName("apple product got an issue with battery and heatsup while charging.");
		i1.setIssueTypeName("Story");
		i1.setIssueTypeDescription("Stories track functionality or features expressed as user goals.");
		i1.setIssueAssigneeEmail("rahul.sathiri@nexiilabs.com");
		i1.setIssueAssigneeName("Rahul Sathiri");
		i1.setIssueCreatedon(null);
		i1.setIssueCreaterName("Rahul Sathiri");
		i1.setIssueCreaterEmail("rahul.sathiri@nexiilabs.com");

		issues = new ArrayList<>();
		issues.add(i1);
		when(jiraservice.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm1);
		when(agileservice.listOfIssuesBySprintId(1, jcm1)).thenReturn(issues);

		mockMvc.perform(get("/jira/listofissuesbysprintId").param("fk_user_id", "1").param("SprintId", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andDo(print())

				.andExpect(status().isOk()).andExpect(jsonPath("$[0].issueid", is(10023)))
				.andExpect(jsonPath("$[0].issueKey", is("AP-24")))
				.andExpect(jsonPath("$[0].issueName",
						is("apple product got an issue with battery and heatsup while charging.")))
				.andExpect(jsonPath("$[0].issueAssigneeName", is("Rahul Sathiri")))
				.andExpect(jsonPath("$[0].issueAssigneeEmail", is("rahul.sathiri@nexiilabs.com")))
				.andExpect(jsonPath("$[0].issueCreaterName", is("Rahul Sathiri")))
				.andExpect(jsonPath("$[0].issueCreaterEmail", is("rahul.sathiri@nexiilabs.com")))
				.andExpect(jsonPath("$[0].issueTypeName", is("Story"))).andExpect(jsonPath("$[0].issueTypeDescription",
						is("Stories track functionality or features expressed as user goals.")));
		// .andExpect(jsonPath("$[0].issueCreatedon", is("null")));

	}

	// Jira.get all epics by board.
	@Test
	public void getAllEpicsbyBoard() throws Exception {
		BoardModel b1 = new BoardModel();
		b1.setBoardId(1);
		b1.setBoardName("AUT board");
		boards = new ArrayList<>();
		boards.add(b1);

		e1 = new EpicModel();
		e1.setEpicId(10064);
		e1.setEpicName("Epic testing");
		e1.setKey("AP-34");
		e1.setSummary("epic testing");

		epics = new ArrayList<>();
		epics.add(e1);

		JiraConfigurationModel jcm1 = new JiraConfigurationModel();
		jcm1.setApplication_url("https://autoteamlife.atlassian.net/");
		jcm1.setFk_user_id(1);
		jcm1.setPassword("R@hul95426");
		jcm1.setUser_name("rahul.sathiri@nexiilabs.com");

		when(jiraservice.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm1);
		when(agileservice.listOfEpicsByBoardId(1, jcm1)).thenReturn(epics);

		mockMvc.perform(get("/jira/listofepicsbyboardId").param("fk_user_id", "1").param("BoardId", "1")
				.contentType("application/json").accept("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].epicId", is(10064))).andExpect(jsonPath("$[0].epicName", is("Epic testing")))
				.andExpect(jsonPath("$[0].key", is("AP-34"))).andExpect(jsonPath("$[0].summary", is("epic testing")));

	}

	// Jira.get issues of epic.
	@Test
	public void getIssuesofEpic() throws Exception {

		JiraConfigurationModel jcm1 = new JiraConfigurationModel();
		jcm1.setApplication_url("https://autoteamlife.atlassian.net/");
		jcm1.setFk_user_id(1);
		jcm1.setPassword("R@hul95426");
		jcm1.setUser_name("rahul.sathiri@nexiilabs.com");

		i1 = new BackLogsModel();
		i1.setIssueid(10023);
		i1.setIssueKey("AP-24");
		i1.setIssueName("apple product got an issue with battery and heatsup while charging.");
		i1.setIssueTypeName("Story");
		i1.setIssueTypeDescription("Stories track functionality or features expressed as user goals.");
		i1.setIssueAssigneeEmail("rahul.sathiri@nexiilabs.com");
		i1.setIssueAssigneeName("Rahul Sathiri");
		i1.setIssueCreatedon(null);
		i1.setIssueCreaterName("Rahul Sathiri");
		i1.setIssueCreaterEmail("rahul.sathiri@nexiilabs.com");

		issues = new ArrayList<>();
		issues.add(i1);
		when(jiraservice.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm1);
		when(agileservice.listOfissuesbyEpicId(1, jcm1)).thenReturn(issues);

		mockMvc.perform(get("/jira/listofissuesbyepicId").param("fk_user_id", "1").param("epicId", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andDo(print())

				.andExpect(status().isOk()).andExpect(jsonPath("$[0].issueid", is(10023)))
				.andExpect(jsonPath("$[0].issueKey", is("AP-24")))
				.andExpect(jsonPath("$[0].issueName",
						is("apple product got an issue with battery and heatsup while charging.")))
				.andExpect(jsonPath("$[0].issueAssigneeName", is("Rahul Sathiri")))
				.andExpect(jsonPath("$[0].issueAssigneeEmail", is("rahul.sathiri@nexiilabs.com")))
				.andExpect(jsonPath("$[0].issueCreaterName", is("Rahul Sathiri")))
				.andExpect(jsonPath("$[0].issueCreaterEmail", is("rahul.sathiri@nexiilabs.com")))
				.andExpect(jsonPath("$[0].issueTypeName", is("Story"))).andExpect(jsonPath("$[0].issueTypeDescription",
						is("Stories track functionality or features expressed as user goals.")));
		// .andExpect(jsonPath("$[0].issueCreatedon", is("null")));

	}

}
