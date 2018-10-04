package com.nexiilabs.autolifecycle.resources.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadService;

@WebAppConfiguration
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)

public class ReleaseAttachmentUploadTest {
	private MockMvc mockMvc;
	@InjectMocks
	private AttachmentUploadController attachmentUploadController;
	@Mock
	AttachmentUploadService attachmentUploadService;
	@Mock
	private Environment environment;
	ResponseDTO responseDTO;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(attachmentUploadController).build();
		when(environment.getProperty("app.productreleaseuploaddir")).thenReturn("/home/nexii/release_uploads/");
	}
	@Test
	public void CreateReleaseMultipleFileUpload() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release file uploaded Successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setReleaseId(2);
		when(attachmentUploadService.createDirectories(2, "/home/nexii/release_uploads/Release")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/release_uploads/Release_2/2018-05-16/");
		when(attachmentUploadService.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(attachmentUploadService.addReleaseUploadDetails(Mockito.any())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createreleaseFile").file(firstFile).file(secondFile)
				.file(jsonFile).param("releaseId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Release file uploaded Successfully")));
	}
	@Test
	public void CreateReleaseSingleFileUpload() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release file uploaded Successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setReleaseId(2);
		when(attachmentUploadService.createDirectories(2, "/home/nexii/release_uploads/Release")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/release_uploads/Release_2/2018-05-16/");
		when(attachmentUploadService.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(attachmentUploadService.addReleaseUploadDetails(Mockito.any())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createreleaseFile").file(firstFile)
				.param("releaseId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Release file uploaded Successfully")));
	}
	@Test
	public void CreateReleaseMultipleFileUploadFail() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release file upload Failed due to File Upload Failure");
		responseDTO.setStatusCode(0);
		responseDTO.setReleaseId(2);
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setStatusCode(1);
		responseDTO1.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(attachmentUploadService.createDirectories(2, "/home/nexii/release_uploads/Release")).thenReturn(responseDTO1);
		when(attachmentUploadService.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		when(attachmentUploadService.deleteReleaseRecordInDatabase(Mockito.anyInt())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createreleaseFile").file(firstFile).file(secondFile)
				.file(jsonFile).param("releaseId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Release file upload Failed due to File Upload Failure")));
	}
	@Test
	public void CreateReleaseSingleFileUploadFail() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release file upload Failed due to File Upload Failure");
		responseDTO.setStatusCode(0);
		responseDTO.setReleaseId(2);
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setStatusCode(1);
		responseDTO1.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(attachmentUploadService.createDirectories(2, "/home/nexii/release_uploads/Release")).thenReturn(responseDTO1);
		when(attachmentUploadService.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		when(attachmentUploadService.deleteReleaseRecordInDatabase(Mockito.anyInt())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createreleaseFile").file(firstFile)
				.param("releaseId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Release file upload Failed due to File Upload Failure")));
	}
	@Test
	public void UpdateReleaseMultipleFileUpload() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release file uploaded Successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setReleaseId(2);
		when(attachmentUploadService.createDirectories(2, "/home/nexii/release_uploads/Release")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/release_uploads/Release_2/2018-05-16/");
		when(attachmentUploadService.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(attachmentUploadService.addReleaseUploadDetails(Mockito.any())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updatereleaseFile").file(firstFile).file(secondFile)
				.file(jsonFile).param("releaseId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Release file uploaded Successfully")));
	}
	@Test
	public void UpdateReleaseSingleFileUpload() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());

		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release file uploaded Successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setReleaseId(2);
		when(attachmentUploadService.createDirectories(2, "/home/nexii/release_uploads/Release")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/release_uploads/Release_2/2018-05-16/");
		when(attachmentUploadService.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(attachmentUploadService.addReleaseUploadDetails(Mockito.any())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updatereleaseFile").file(firstFile)
				.param("releaseId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Release file uploaded Successfully")));
	}
	@Test
	public void UpdateReleaseMultipleFileUploadFail() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release file upload Failed due to File Upload Failure");
		responseDTO.setStatusCode(0);
		responseDTO.setReleaseId(2);
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setStatusCode(1);
		responseDTO1.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(attachmentUploadService.createDirectories(2, "/home/nexii/release_uploads/Release")).thenReturn(responseDTO1);
		when(attachmentUploadService.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updatereleaseFile").file(firstFile).file(secondFile)
				.file(jsonFile).param("releaseId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Release file upload Failed due to File Upload Failure")));
	}
	@Test
	public void UpdateReleaseSingleFileUploadFail() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setMessage("Release file upload Failed due to File Upload Failure");
		responseDTO.setStatusCode(0);
		responseDTO.setReleaseId(2);
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setStatusCode(1);
		responseDTO1.setUploadPath("/home/nexii/release_uploads/RELEASE_1/2018-05-18/");
		when(attachmentUploadService.createDirectories(2, "/home/nexii/release_uploads/Release")).thenReturn(responseDTO1);
		when(attachmentUploadService.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updatereleaseFile").file(firstFile)
				.param("releaseId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Release file upload Failed due to File Upload Failure")));
	}
	@Test
	public void UpdateReleaseMultipleFileUploadNoChangesFound() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setReleaseId(1);
		responseDTO.setStatusCode(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/Release_1/2018-05-18/");
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setMessage("No changes found");
		responseDTO1.setStatusCode(0);
		responseDTO1.setUploadPath("/home/nexii/release_uploads/Release_1/2018-05-18/");
		when(attachmentUploadService.createDirectories(Mockito.anyInt(), Mockito.anyString())).thenReturn(responseDTO);
		when(attachmentUploadService.checkFileExistancyForReleaseFile(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updatereleaseFile").file(firstFile).file(secondFile)
				.file(jsonFile).param("releaseId", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("No changes found")));
	}
	@Test
	public void UpdateReleaseSingleFileUploadNoChangesFound() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setReleaseId(1);
		responseDTO.setStatusCode(1);
		responseDTO.setUploadPath("/home/nexii/release_uploads/Release_1/2018-05-18/");
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setMessage("No changes found");
		responseDTO1.setStatusCode(0);
		responseDTO1.setUploadPath("/home/nexii/release_uploads/Release_1/2018-05-18/");
		when(attachmentUploadService.createDirectories(Mockito.anyInt(), Mockito.anyString())).thenReturn(responseDTO);
		when(attachmentUploadService.checkFileExistancyForReleaseFile(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updatereleaseFile").file(firstFile).param("releaseId", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("No changes found")));
	}
}
