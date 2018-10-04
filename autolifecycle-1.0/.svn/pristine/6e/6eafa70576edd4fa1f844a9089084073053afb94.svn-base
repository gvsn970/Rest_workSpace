package com.nexiilabs.autolifecycle.jira.test;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nexiilabs.autolifecycle.jira.AgileResource;
import com.nexiilabs.autolifecycle.jira.Issue;
import com.nexiilabs.autolifecycle.jira.JiraAgileClientUtility;
import com.nexiilabs.autolifecycle.jira.JiraConfigurationModel;
import com.nexiilabs.autolifecycle.jira.JiraController;
import com.nexiilabs.autolifecycle.jira.JiraService;
import com.nexiilabs.autolifecycle.productline.Response;
import com.nexiilabs.autolifecycle.releases.ReleaseModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

@WebAppConfiguration
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class JiraExportTest {
	private MockMvc mockMvc;
	@InjectMocks
	private JiraController jiraController;
	@Mock
	JiraAgileClientUtility agileClientUtility;
	@Mock
	JiraService service;
	RestClient restclient;
	List priorities=null;
	List projects=new ArrayList<>();
	List issueTypes=new ArrayList<>();
	List avatarIds=new ArrayList<>();
	Response response=null;
	@Before
	public void init() throws JiraException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(jiraController).build();
		 priorities=new ArrayList<>();
		 JSONObject jsonData1 = new JSONObject();
		 jsonData1.put("description","This problem will block progress.");
		 jsonData1.put("priorityId", "1");
		 jsonData1.put("selfURL", "https://autoteamlife.atlassian.net/rest/api/2/priority/1");
		 priorities.add(jsonData1);
		 JSONObject project=new JSONObject();
		 project.put("key", "AUT");
		 project.put("name", "AutoTeam");
		 project.put("selfURL", "https://autoteamlife.atlassian.net/rest/api/2/project/10000");
		 projects.add(project);
		 JSONObject issueType=new JSONObject();
		 issueType.put("name", "Story");
		 issueType.put("issueTypeId", "10001");
		 issueType.put("selfURL", "https://autoteamlife.atlassian.net/rest/api/2/issuetype/10001");
		 issueTypes.add(issueType);
		 JSONObject avatarId=new JSONObject();
		 avatarId.put("avatarid", "10400");
		 avatarIds.add(avatarId);
		 //System.err.println("priorities::::"+issueTypes.toString());
	}
	@Test
	public void testGetPriorityList() throws Exception {
		JiraConfigurationModel jcm=new JiraConfigurationModel();
		when(service.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm);
		when(agileClientUtility.priorityList(Mockito.any(JiraConfigurationModel.class))).thenReturn(priorities);
		mockMvc.perform(get("/jira/getpriorityList?fk_user_id=1").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].priorityId", is("1")))
				.andExpect(jsonPath("$[0].selfURL", is("https://autoteamlife.atlassian.net/rest/api/2/priority/1")));
	}
	@Test
	public void testGetProjectList() throws Exception {
		JiraConfigurationModel jcm=new JiraConfigurationModel();
		when(service.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm);
		when(agileClientUtility.getProjectsList(Mockito.any(JiraConfigurationModel.class))).thenReturn(projects);
		mockMvc.perform(get("/jira/getProjectsList?fk_user_id=2").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name", is("AutoTeam")))
				.andExpect(jsonPath("$[0].selfURL", is("https://autoteamlife.atlassian.net/rest/api/2/project/10000")));
	}
	@Test
	public void testgetIssueTypes() throws Exception {
		JiraConfigurationModel jcm=new JiraConfigurationModel();
		when(service.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm);
		when(agileClientUtility.getIssueTypes(Mockito.any(JiraConfigurationModel.class))).thenReturn(issueTypes);
		mockMvc.perform(get("/jira/getIssueTypes?fk_user_id=2").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].issueTypeId", is("10001")))
				.andExpect(jsonPath("$[0].selfURL", is("https://autoteamlife.atlassian.net/rest/api/2/issuetype/10001")));
	}
	@Test
	public void testgetAvatarIdsForProject() throws Exception {
		JiraConfigurationModel jcm=new JiraConfigurationModel();
		when(service.getJiraCredentialsByUserId(Mockito.anyInt())).thenReturn(jcm);
		when(agileClientUtility.getAvatarIdsForProject(Mockito.any(JiraConfigurationModel.class))).thenReturn(avatarIds);
		mockMvc.perform(get("/jira/getAvatarIdsForProject?fk_user_id=2").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].avatarid", is("10400")));
	}
	/*@Test
	public void createIssue() throws Exception {
		JSONObject json=new JSONObject();
		json.put("name", "AUT-50");
		json.put("id", 0);
		Issue res = mock(Issue.class);
		when(res.getName()).thenReturn ("AUT-50");
		//Issue res=new Issue(restclient, json);
		//Issue res1 = res.createIssue(restclient, "sprintissuetest", "AUT", 10004, "1", "admin", 2);
		response=new Response();
		response.setStatusCode(1);
		response.setMessage("Issue created successfully.");
		when(agileClientUtility.createIssue(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(),Mockito.anyString(),Mockito.anyString(), Mockito.anyInt(), Mockito.any(JiraConfigurationModel.class), Mockito.anyInt() )).thenReturn(res);
		mockMvc.perform(MockMvcRequestBuilders.post("/jira/createIssue")
				.param("summary", "sprintissuetest").param("projectId", "AUT").param("issueType", "10004")
				.param("priority", "1").param("assignee", "admin")
				.param("fk_user_id", "2").param("sprintId", "1")
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(jsonPath("$.status", is(response.getStatus())))
				.andExpect(jsonPath("$.message", is(response.getMessage())));
	}
	*/
}
