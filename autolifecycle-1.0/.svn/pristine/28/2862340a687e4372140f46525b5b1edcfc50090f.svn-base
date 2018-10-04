/*package com.nexiilabs.autolifecycle.release.test;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexiilabs.autolifecycle.releases.ProductReleaseUploadModel;
import com.nexiilabs.autolifecycle.releases.ReleaseController;
import com.nexiilabs.autolifecycle.releases.ReleaseListModel;
import com.nexiilabs.autolifecycle.releases.ReleaseModel;
import com.nexiilabs.autolifecycle.releases.ReleaseService;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;

@WebAppConfiguration
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ReleaseControllerTest {
	private MockMvc mockMvc;
	@InjectMocks
	private ReleaseController releaseController;

	@Mock
	ReleaseService releaseService;
	@Mock
	AttachmentUploadController attachmentUploadController;
	@Mock
	ObjectMapper objectMapper;

	@Mock
	private Environment environment;
	JacksonTester<ReleaseListModel> jsonTester;
	List<ReleaseListModel> releases = null;
	List<ReleaseListModel> releases1 = null;
	ReleaseListModel release;
	ReleaseModel addReleaseModel;
	ResponseDTO responseDTO;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(releaseController).build();
		ReleaseListModel release = new ReleaseListModel();
		release.setReleaseName("Release7");
		release.setFkProductId(1);
		release.setReleaseDateInternal("2018-07-02 00:00:00");
		release.setReleaseDateExternal("2018-08-02 00:00:00");
		release.setReleaseDescription("release");
		release.setFkReleaseOwner(1);
		release.setFkStatusId(1);
		release.setProductName("product1");

		ReleaseListModel release1 = new ReleaseListModel();
		release1.setReleaseName("Release18");
		release1.setFkProductId(1);
		release1.setReleaseDateInternal("2018-07-02 00:00:00");
		release1.setReleaseDateExternal("2018-08-02 00:00:00");
		release1.setReleaseDescription("release18");
		release1.setFkReleaseOwner(1);
		release1.setFkStatusId(1);
		release1.setProductName("product2");
		release1.setOwner("owner1");
		releases = Arrays.asList(release, release1);
		objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
		addReleaseModel = new ReleaseModel();
		addReleaseModel.setReleaseName("Release18");
		addReleaseModel.setFkProductId(1);
		addReleaseModel.setReleaseDateInternal("2018-07-02 00:00:00");
		addReleaseModel.setReleaseDateExternal("2018-08-02 00:00:00");
		addReleaseModel.setReleaseDescription("release18");
		addReleaseModel.setFkReleaseOwner(1);
		addReleaseModel.setFkStatusId(1);
		releases1= Arrays.asList();
		
		when(environment.getProperty("app.allFieldsManditory")).thenReturn("All Input fields are manditory");
		when(environment.getProperty("app.releaseNameLong")).thenReturn("Release Name is too Long");
		when(environment.getProperty("app.releaseDescLong")).thenReturn("Release Description is too Long");

	}

	@Test
	public void testGetReleases() throws Exception {
		when(releaseService.getAllReleases()).thenReturn(releases);
		mockMvc.perform(get("/releases/getAllReleases").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].releaseName", is("Release7")))
				.andExpect(jsonPath("$[0].fkProductId", is(1)))
				.andExpect(jsonPath("$[0].releaseDateInternal", is("2018-07-02 00:00:00")))
				.andExpect(jsonPath("$[0].releaseDateExternal", is("2018-08-02 00:00:00")))
				.andExpect(jsonPath("$[0].releaseDescription", is("release")))
				.andExpect(jsonPath("$[0].fkReleaseOwner", is(1))).andExpect(jsonPath("$[0].fkStatusId", is(1)));
	}

	@Test
	public void testGetReleasesFail() throws Exception {
		when(releaseService.getAllReleases()).thenReturn(releases1);
		mockMvc.perform(get("/releases/getAllReleases").contentType("application/json").accept("application/json"))
		.andExpect(jsonPath("$.*", Matchers.hasSize(0)));

	}

	@Test
	public void testGetReleaseDetails() throws Exception {
		List<ReleaseListModel> releases=new ArrayList<ReleaseListModel> ();
		ReleaseListModel release = new ReleaseListModel();
		release.setReleaseName("Release7");
		release.setFkProductId(1);
		release.setReleaseDateInternal("2018-07-02 00:00:00");
		release.setReleaseDateExternal("2018-08-02 00:00:00");
		release.setReleaseDescription("release");
		release.setFkReleaseOwner(1);
		release.setFkStatusId(1);
		release.setProductName("product1");
		releases.add(release);
		when(releaseService.getReleaseDetails(Mockito.anyInt())).thenReturn(releases);
		mockMvc.perform(get("/releases/getDetails").param("releaseId", "1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].releaseName", is("Release7"))).andExpect(jsonPath("$[0].fkProductId", is(1)))
				.andExpect(jsonPath("$[0].releaseDateInternal", is("2018-07-02 00:00:00")))
				.andExpect(jsonPath("$[0].releaseDateExternal", is("2018-08-02 00:00:00")))
				.andExpect(jsonPath("$[0].releaseDescription", is("release")))
				.andExpect(jsonPath("$[0].fkReleaseOwner", is(1))).andExpect(jsonPath("$[0].fkStatusId", is(1)));
	}

	@Test
	public void addReleaseSuccess() throws Exception {
		MockMultipartFile file1= new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfddsfdsfssf".getBytes());
		MockMultipartFile productReleaseUploads[] ={file1,file2};
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		responseDTO.setMessage("Release file uploaded Successfully");
		responseDTO.setReleaseId(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.addRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		when(attachmentUploadController.createreleaseFileUpload(productReleaseUploads, 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/create").file(file1).file(file2)
				.param("releaseName", "release12").param("releaseDesc", "desc").param("fkProductId", "1")
				.param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(1)))
				.andExpect(jsonPath("$.message", is("Release file uploaded Successfully")));

	}
	@Test
	public void addReleaseWithOneFile() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		responseDTO.setMessage("Release file uploaded Successfully");
		responseDTO.setReleaseId(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		MockMultipartFile productReleaseUploads[] ={file1};
		when(releaseService.addRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		when(attachmentUploadController.createreleaseFileUpload(productReleaseUploads, 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/create").file(file1)
				.param("releaseName", "release1").param("releaseDesc", "desc").param("fkProductId", "1")
				.param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(1)))
				.andExpect(jsonPath("$.message", is("Release file uploaded Successfully")));

	}
	@Test
	public void addReleaseWithoutFile() throws Exception {
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		responseDTO.setMessage("Release added successfully");
		responseDTO.setReleaseId(1);
		//responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.addRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/releases/create")
				.param("releaseName", "release1").param("releaseDesc", "desc").param("fkProductId", "1")
				.param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(1)))
				.andExpect(jsonPath("$.message", is("Release added successfully")));

	}
	@Test
	public void addReleaseFail() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfddsfdsfssf".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(0);
		responseDTO.setMessage("Release creation failed");
		responseDTO.setReleaseId(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.addRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/create").file(file1).file(file2)
				.param("releaseName", "release1").param("releaseDesc", "desc").param("fkProductId", "1")
				.param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("Release creation failed")));

	}
	@Test
	public void addReleaseFailMultipleFileUpload() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfddsfdsfssf".getBytes());
	    responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		responseDTO.setReleaseId(1);
		ResponseDTO responseDTO1=new ResponseDTO();
		responseDTO1.setMessage("Release file upload Failed due to File Upload Failure");
		responseDTO1.setStatusCode(0);
		when(releaseService.addRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		MultipartFile[] productReleaseUploads={file1,file2};
		when(attachmentUploadController.createreleaseFileUpload(productReleaseUploads, 1)).thenReturn(responseDTO1);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/create").file(file1).file(file2)
				.param("releaseName", "release1").param("releaseDesc", "desc").param("fkProductId", "1")
				.param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("Release file upload Failed due to File Upload Failure")));
	}
	@Test
	public void addReleaseFailSingleFileUpload() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
	    responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		responseDTO.setReleaseId(1);
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setMessage("Release file upload Failed due to File Upload Failure");
		responseDTO1.setStatusCode(0);
		when(releaseService.addRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		MultipartFile[] productReleaseUploads={file1};
		when(attachmentUploadController.createreleaseFileUpload(productReleaseUploads, 1)).thenReturn(responseDTO1);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/create").file(file1)
				.param("releaseName", "release1").param("releaseDesc", "desc").param("fkProductId", "1")
				.param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("Release file upload Failed due to File Upload Failure")));
	}
	@Test
	public void addReleaseAlreadyExists() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfddsfdsfssf".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(2);
		responseDTO.setMessage("Release Already exists");
		responseDTO.setReleaseId(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.addRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/create").file(file1).file(file2)
				.param("releaseName", "release1").param("releaseDesc", "desc").param("fkProductId", "1")
				.param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(2)))
				.andExpect(jsonPath("$.message", is("Release Already exists")));

	}

	@Test
	public void updateReleaseSuccess() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MultipartFile[] productReleaseUploads={file1,file2};
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		responseDTO.setMessage("Release file uploaded Successfully");
		responseDTO.setReleaseId(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.updateRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		when(attachmentUploadController.updatereleaseFileUpload(productReleaseUploads, 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/update").file(file1).file(file2)
				.param("releaseId", "1").param("releaseName", "release1").param("releaseDesc", "desc")
				.param("fkProductId", "1").param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(1)))
				.andExpect(jsonPath("$.message", is("Release file uploaded Successfully")));
	}
	
	@Test
	public void updateReleaseWithoutFile() throws Exception {
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		responseDTO.setMessage("Release updated successfully");
		responseDTO.setReleaseId(1);
		//responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.updateRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/releases/update")
				.param("releaseId", "1").param("releaseName", "release1").param("releaseDesc", "desc")
				.param("fkProductId", "1").param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(1)))
				.andExpect(jsonPath("$.message", is("Release updated successfully")));

	}
	@Test
	public void updateReleaseSucessWithSingleFile() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MultipartFile[] productReleaseUploads={file1};
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		responseDTO.setMessage("Release file uploaded Successfully");
		responseDTO.setReleaseId(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.updateRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		when(attachmentUploadController.updatereleaseFileUpload(productReleaseUploads, 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/update").file(file1)
				.param("releaseId", "1").param("releaseName", "release1").param("releaseDesc", "desc")
				.param("fkProductId", "1").param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(1)))
				.andExpect(jsonPath("$.message", is("Release file uploaded Successfully")));

	}
	@Test
	public void updateReleaseFailedWithSingleFile() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setStatusCode(0);
		responseDTO1.setMessage("Release file upload Failed due to File Upload Failure");
		responseDTO1.setReleaseId(1);
		responseDTO1.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.updateRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		MultipartFile[] productReleaseUploads={file1};
		when(attachmentUploadController.updatereleaseFileUpload(productReleaseUploads, 1)).thenReturn(responseDTO1);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/update").file(file1)
				.param("releaseId", "1").param("releaseName", "release1").param("releaseDesc", "desc")
				.param("fkProductId", "1").param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("Release file upload Failed due to File Upload Failure")));

	}
	@Test
	public void updateReleaseFailedWithMultipleFile() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2= new MockMultipartFile("files", "sample.txt", "text/plain",
				"somesfssdfgdf".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setStatusCode(0);
		responseDTO1.setMessage("Release file upload Failed due to File Upload Failure");
		responseDTO1.setReleaseId(1);
		responseDTO1.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.updateRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		MultipartFile[] productReleaseUploads={file1,file2};
		when(attachmentUploadController.updatereleaseFileUpload(productReleaseUploads, 1)).thenReturn(responseDTO1);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/update").file(file1).file(file2)
				.param("releaseId", "1").param("releaseName", "release1").param("releaseDesc", "desc")
				.param("fkProductId", "1").param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("Release file upload Failed due to File Upload Failure")));

	}
	@Test
	public void updateReleaseFailed() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfddsfdsfssf".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(0);
		responseDTO.setMessage("Release updation failed");
		responseDTO.setReleaseId(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.updateRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/update").file(file1).file(file2).file(jsonFile)
				.param("releaseId", "1").param("releaseName", "release1").param("releaseDesc", "desc")
				.param("fkProductId", "1").param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("Release updation failed")));

	}
	@Test
	public void updateReleaseWithNoChanges() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MultipartFile[] productReleaseUploads={file1,file2};
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(0);
		responseDTO.setMessage("No changes Found");
		responseDTO.setReleaseId(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(releaseService.updateRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		//when(attachmentUploadController.updatereleaseFileUpload(productReleaseUploads, 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/update").file(file1).file(file2)
				.param("releaseId", "1").param("releaseName", "release1").param("releaseDesc", "desc")
				.param("fkProductId", "1").param("fkStatusId", "1").param("internalReleaseDate", "2018-05-03")
				.param("externalReleaseDate", "2018-07-09").param("fkReleaseOwner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("No changes Found")));
	}
	@Test
	public void deleteRelease() throws Exception {
		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release Deleted Successfully");
		responseDTO.setStatusCode(1);
		Mockito.when(releaseService.deleteRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/releases/delete").param("releaseId", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.statusCode", is(1)))
				.andExpect(jsonPath("$.message", is("Release Deleted Successfully")));

	}
	@Test
	public void deleteReleaseFailed() throws Exception {
		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release Deletion Failed");
		responseDTO.setStatusCode(0);
		Mockito.when(releaseService.deleteRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/releases/delete").param("releaseId", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("Release Deletion Failed")));

	}
	@Test
	public void copyReleaseSuccess() throws Exception {
		ReleaseListModel release=new ReleaseListModel();
		release.setReleaseId(1);
		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release copied successfully");
		responseDTO.setStatusCode(1);
		Mockito.when(releaseService.getReleaseDetailsForCopy(Mockito.any(ReleaseListModel.class))).thenReturn(release);
		when(releaseService.addRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/releases/copy").param("releaseId", "1").param("productId", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.statusCode", is(1)))
				.andExpect(jsonPath("$.message", is("Release copied successfully")));

	}
	@Test
	public void copyReleaseFail() throws Exception {
		ReleaseListModel release=new ReleaseListModel();
		release.setReleaseId(1);
		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release copying Failed");
		responseDTO.setStatusCode(0);
		Mockito.when(releaseService.getReleaseDetailsForCopy(Mockito.any(ReleaseListModel.class))).thenReturn(release);
		when(releaseService.addRelease(Mockito.any(ReleaseModel.class))).thenReturn(responseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/releases/copy").param("releaseId", "1").param("productId", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("Release copying Failed")));

	}
	@Test
	public void deleteReleaseFileSuccess() throws Exception {
		ProductReleaseUploadModel release=new ProductReleaseUploadModel();
		responseDTO = new ResponseDTO();
		responseDTO.setMessage("File Deleted successfully");
		responseDTO.setStatusCode(1);
		when(releaseService.deleteReleaseFiles(Mockito.any(ProductReleaseUploadModel.class))).thenReturn(responseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/releases/deleteReleaseFiles").param("fkReleaseId", "16").param("fileId", "16")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.statusCode", is(1)))
				.andExpect(jsonPath("$.message", is("File Deleted successfully")));

	}
	@Test
	public void deleteReleaseFileFail() throws Exception {
		ProductReleaseUploadModel release=new ProductReleaseUploadModel();
		responseDTO = new ResponseDTO();
		responseDTO.setMessage("File Deletion Failed");
		responseDTO.setStatusCode(0);
		when(releaseService.deleteReleaseFiles(Mockito.any(ProductReleaseUploadModel.class))).thenReturn(responseDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/releases/deleteReleaseFiles").param("fkReleaseId", "16").param("fileId", "16")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.statusCode", is(0)))
				.andExpect(jsonPath("$.message", is("File Deletion Failed")));

	}
	@Test
	public void addReleaseEmptyValidation() throws Exception {
		String releaseName="";
		String releaseDesc="";
		String externalReleaseDate="";
		String internalReleaseDate="";
		String fkReleaseOwner="";
	    String fkProductId="";
	    String fkStatusId="";
	    responseDTO = new ResponseDTO();
	   if(releaseName.equals("")||releaseDesc.equals("")||externalReleaseDate.equals("")||internalReleaseDate.equals("")||fkReleaseOwner.equals("")||fkProductId.equals("")||fkStatusId.equals(""))
	   {
	    	responseDTO.setStatusCode(0);
			responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
	    }
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/create")
				.param("releaseName", releaseName).param("releaseDesc", releaseDesc).param("fkProductId", fkProductId)
				.param("fkStatusId", fkStatusId).param("internalReleaseDate", internalReleaseDate)
				.param("externalReleaseDate", externalReleaseDate).param("fkReleaseOwner", fkReleaseOwner)
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(responseDTO.getStatusCode())))
				.andExpect(jsonPath("$.message", is(responseDTO.getMessage())));

	}
	@Test
	public void updateReleaseEmptyValidation() throws Exception {
		String releaseName="";
		String releaseDesc="sadsadsdsadsaf";
		String externalReleaseDate="";
		String internalReleaseDate="";
		String fkReleaseOwner="1";
	    String fkProductId="2";
	    String fkStatusId="3";
	    String releaseId="";
		responseDTO = new ResponseDTO();
	    if(releaseName.equals("")||releaseDesc.equals("")||externalReleaseDate.equals("")||internalReleaseDate.equals("")||fkReleaseOwner.equals("")||fkProductId.equals("")||fkStatusId.equals("")|| releaseId.equals(""))
		  {
		    	responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
		   }
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/update")
				.param("releaseId", releaseId).param("releaseName", releaseName).param("releaseDesc", releaseDesc)
				.param("fkProductId", fkProductId).param("fkStatusId", fkStatusId).param("internalReleaseDate",internalReleaseDate)
				.param("externalReleaseDate", externalReleaseDate).param("fkReleaseOwner", fkReleaseOwner)
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(responseDTO.getStatusCode())))
				.andExpect(jsonPath("$.message", is(responseDTO.getMessage())));
	}
	@Test
	public void addReleaseNameLengthValidation() throws Exception {
		String releaseName="sjdfksfkdsfksdgsd"
				+ "sdfsdfsdfdsgfsdgsdgfdsgfdsgkfdshgjfdsg"
				+ "sfsdgfdgfdgfdg"
				+ "sdfsdgfdsgfdgfd"
				+ "sdfsdfgdsggdfgdfgdf"
				+ "sfsdgfdgfdgfdgdf";
		String releaseDesc="hghgjuguuguuy";
		String externalReleaseDate="2018-08-07";
		String internalReleaseDate="2018-07-23";
		String fkReleaseOwner="1";
	    String fkProductId="2";
	    String fkStatusId="3";
	    responseDTO = new ResponseDTO();
	     if(releaseName.length()>60){
	    	 responseDTO.setStatusCode(0);
			responseDTO.setMessage(environment.getProperty("app.releaseNameLong"));
	    }
		responseDTO.setReleaseId(1);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/create")
				.param("releaseName", releaseName).param("releaseDesc", releaseDesc).param("fkProductId", fkProductId)
				.param("fkStatusId", fkStatusId).param("internalReleaseDate", internalReleaseDate)
				.param("externalReleaseDate", externalReleaseDate).param("fkReleaseOwner", fkReleaseOwner)
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(responseDTO.getStatusCode())))
				.andExpect(jsonPath("$.message", is(responseDTO.getMessage())));
	}
	@Test
	public void updateReleaseNameLengthValidation() throws Exception {
		String releaseName="ddfhjfjsajfaksfasfdhaksfahsfkasjdfdsfalsdafj"
				+ "dfdsfsdfsdfsdfsdfsdffffffffffffffffffffffffffffffasdsadsa"
				+ "sdfsdfsdddddddddddddddasdsafasfasfafsafas"
				+ "ksafsafhsakfdshfdsfsadasasasfasfasfweqqrwwwwwwwwwtesadfqwerqwerqwera"
				+ "sdfASDFGSDAFSDFDSFsdafsdfsdewrwerwerweweew";
		String releaseDesc="sadsadsdsadsaf";
		String externalReleaseDate="2018-08-07";
		String internalReleaseDate="2018-07-23";
		String fkReleaseOwner="1";
	    String fkProductId="2";
	    String fkStatusId="3";
	    String releaseId="3";
		responseDTO = new ResponseDTO();
		 if(releaseName.length()>60){
	    	 responseDTO.setStatusCode(0);
	    	 responseDTO.setMessage(environment.getProperty("app.releaseNameLong"));
	    }
		 responseDTO.setReleaseId(1);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/update")
				.param("releaseId", releaseId).param("releaseName", releaseName).param("releaseDesc", releaseDesc)
				.param("fkProductId", fkProductId).param("fkStatusId", fkStatusId).param("internalReleaseDate",internalReleaseDate)
				.param("externalReleaseDate", externalReleaseDate).param("fkReleaseOwner", fkReleaseOwner)
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(responseDTO.getStatusCode())))
				.andExpect(jsonPath("$.message", is(responseDTO.getMessage())));
	}
	@Test
	public void addReleaseDescLengthValidation() throws Exception {
		String releaseName="hghjgj";
		String releaseDesc="sadsadsdsads88888888899988"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd";
		String externalReleaseDate="2018-08-07";
		String internalReleaseDate="2018-07-23";
		String fkReleaseOwner="1";
	    String fkProductId="2";
	    String fkStatusId="3";
	    responseDTO = new ResponseDTO();
	     if(releaseDesc.length()>1000){
	    	responseDTO.setStatusCode(0);
			responseDTO.setMessage(environment.getProperty("app.releaseDescLong"));
	    }
		responseDTO.setReleaseId(1);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/create")
				.param("releaseName", releaseName).param("releaseDesc", releaseDesc).param("fkProductId", fkProductId)
				.param("fkStatusId", fkStatusId).param("internalReleaseDate", internalReleaseDate)
				.param("externalReleaseDate", externalReleaseDate).param("fkReleaseOwner", fkReleaseOwner)
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(responseDTO.getStatusCode())))
				.andExpect(jsonPath("$.message", is(responseDTO.getMessage())));

	}
	@Test
	public void updateReleaseDescLengthValidation() throws Exception {
		String releaseName="ddfhjfjsajfaksfasf";
		String releaseDesc="sadsadsdsads88888888899988"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "sdfsfdsfsgggggggggggggggfsdfsdfsdfsffsfsfgfdhfdhgfhgfhf"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitiyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd";
		String externalReleaseDate="2018-08-07";
		String internalReleaseDate="2018-07-23";
		String fkReleaseOwner="1";
	    String fkProductId="2";
	    String fkStatusId="3";
	    String releaseId="2";
	    responseDTO = new ResponseDTO();
	    if(releaseDesc.length()>1000){
	    	responseDTO.setStatusCode(0);
			responseDTO.setMessage(environment.getProperty("app.releaseDescLong"));
	    }
	    mockMvc.perform(MockMvcRequestBuilders.fileUpload("/releases/update")
				.param("releaseId", releaseId).param("releaseName", releaseName).param("releaseDesc", releaseDesc)
				.param("fkProductId", fkProductId).param("fkStatusId", fkStatusId).param("internalReleaseDate",internalReleaseDate)
				.param("externalReleaseDate", externalReleaseDate).param("fkReleaseOwner", fkReleaseOwner)
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.statusCode", is(responseDTO.getStatusCode())))
				.andExpect(jsonPath("$.message", is(responseDTO.getMessage())));
	}
	@Test
	public void deleteReleaseEmptyValidation() throws Exception {
		String fileId="";
		String fkReleaseId="";
		responseDTO = new ResponseDTO();
		if(fileId.equals("")|| fkReleaseId.equals("")){
	    	responseDTO.setStatusCode(0);
			responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
			
	    }
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/releases/deleteReleaseFiles").param("fkReleaseId",fkReleaseId ).param("fileId", fileId)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.statusCode", is(responseDTO.getStatusCode())))
				.andExpect(jsonPath("$.message", is(responseDTO.getMessage())));

	}
	@Test
	public void copyReleaseValidation() throws Exception {
		String productId="";
		String releaseId="";
		responseDTO = new ResponseDTO();
		if(productId.equals("")|| releaseId.equals("")){
	    	responseDTO.setStatusCode(0);
			responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
	    }
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/releases/copy").param("releaseId",releaseId ).param("productId", productId)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.statusCode", is(responseDTO.getStatusCode())))
				.andExpect(jsonPath("$.message", is(responseDTO.getMessage())));
	}
	
}*/