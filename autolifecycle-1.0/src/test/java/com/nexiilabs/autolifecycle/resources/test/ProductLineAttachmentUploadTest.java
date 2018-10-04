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

import com.nexiilabs.autolifecycle.productline.ProductLineUploadModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadService;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class ProductLineAttachmentUploadTest {
	private MockMvc mockMvc;

	@InjectMocks
	private AttachmentUploadController attachmentUploadController;
	@Mock
	private AttachmentUploadService service;

	@Mock
	Environment environment;
	ResponseDTO responseDTO;
	ProductLineUploadModel uploadModel = null;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(attachmentUploadController).build();
		when(environment.getProperty("app.productlineuploaddir")).thenReturn("C:/Users/pravesh/Desktop/productline_uploads/");
	}
	@Test
	public void createProductLineSingleFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());

		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		
		uploadModel=new ProductLineUploadModel();
		
		responseDTO.setMessage("Productline created successfully");
		responseDTO.setStatusCode(1);
		
		responseDTO.setProductLineId(2);
		
		ResponseDTO responseDTO2=new ResponseDTO();
		responseDTO2.setStatusCode(1);
		responseDTO2.setMessage("Productline created successfully");
		
		when(service.createDirectories(2, "C:/Users/pravesh/Desktop/productline_uploads/ProductLine")).thenReturn(responseDTO);
		
		responseDTO.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/ProductLine_2/2018-05-28/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		
		when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(responseDTO2);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createproductlineFile").file(firstFile)
				.file(jsonFile).param("productlineId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline created successfully")));
	}
	
	@Test
	public void createProductLineMultiFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		
		uploadModel=new ProductLineUploadModel();
		
		responseDTO.setMessage("ProductLine file uploaded Successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setProductLineId(2);
ResponseDTO responseDTO1 = new ResponseDTO();
		
		responseDTO1.setMessage("ProductLine file uploaded Successfully");
		responseDTO1.setStatusCode(1);
		responseDTO1.setProductLineId(2);
				
		when(service.createDirectories(2, "C:/Users/pravesh/Desktop/productline_uploads/ProductLine")).thenReturn(responseDTO);
		
		responseDTO.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/ProductLine_2/2018-05-17/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		
		when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(responseDTO1);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createproductlineFile").file(firstFile).file(secondFile)
				.file(jsonFile).param("productlineId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("ProductLine file uploaded Successfully")));
	}
	
	//no file create
	@Test
	public void createProductLineMultiFileFailedCase() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		uploadModel=new ProductLineUploadModel();
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		
		ResponseDTO responseDTO1=new ResponseDTO();		
		responseDTO1.setMessage("ProductLine file upload Failed due to File Upload Failure");
		responseDTO1.setStatusCode(0);		
		responseDTO1.setProductLineId(2);
		
		when(service.createDirectories(2, "C:/Users/pravesh/Desktop/productline_uploads/ProductLine")).thenReturn(responseDTO);
		
		responseDTO.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/ProductLine_2/2018-05-17/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		
		when(service.deleteProductLineRecordInDatabase(Mockito.anyInt())).thenReturn(responseDTO1);
		
		//when(service.addProductUploadDetails(Mockito.any())).thenReturn(responseDTO2);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createproductlineFile").file(firstFile).file(secondFile)
				.file(jsonFile).param("productlineId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("ProductLine file upload Failed due to File Upload Failure")));
	}
	
	//no file create
	@Test
	public void createProductLineSingleFileFailedCase() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
	
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		uploadModel=new ProductLineUploadModel();
		responseDTO = new ResponseDTO();
		responseDTO.setStatusCode(1);
		
		ResponseDTO responseDTO1=new ResponseDTO();		
		responseDTO1.setMessage("productline file upload Failed due to File Upload Failure");
		responseDTO1.setStatusCode(0);		
		responseDTO1.setProductLineId(2);
		
		when(service.createDirectories(2, "C:/Users/pravesh/Desktop/productline_uploads/ProductLine")).thenReturn(responseDTO);
		
		responseDTO.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/ProductLine_2/2018-05-17/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		
		when(service.deleteProductLineRecordInDatabase(Mockito.anyInt())).thenReturn(responseDTO1);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createproductlineFile").file(firstFile)
				.file(jsonFile).param("productlineId", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("productline file upload Failed due to File Upload Failure")));
	}
	
	
	@Test
	public void updateProductLineMultiFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		
		uploadModel=new ProductLineUploadModel();
		
		responseDTO.setMessage("Productline file uploaded Successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setProductLineId(2);
		ResponseDTO responseDTO1 = new ResponseDTO();
		
		responseDTO1.setMessage("Productline file uploaded Successfully");
		responseDTO1.setStatusCode(1);
		responseDTO1.setProductLineId(2);
				
		when(service.createDirectories(2, "C:/Users/pravesh/Desktop/productline_uploads/ProductLine")).thenReturn(responseDTO);
		
		responseDTO.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/ProductLine_2/2018-05-17/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		
		when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(responseDTO1);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductLineFile").file(firstFile).file(secondFile)
				.file(jsonFile).param("product_line_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline file uploaded Successfully")));
	}
	
	@Test
	public void updateProductLineSingleFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		/*MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());*/
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		
		uploadModel=new ProductLineUploadModel();
		
		responseDTO.setMessage("Productline file uploaded Successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setProductLineId(2);
		ResponseDTO responseDTO1 = new ResponseDTO();
		
		responseDTO1.setMessage("Productline file uploaded Successfully");
		responseDTO1.setStatusCode(1);
		responseDTO1.setProductLineId(2);
				
		when(service.createDirectories(2, "C:/Users/pravesh/Desktop/productline_uploads/ProductLine")).thenReturn(responseDTO);
		
		responseDTO.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/ProductLine_2/2018-05-17/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		
		when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(responseDTO1);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductLineFile").file(firstFile)
				.file(jsonFile).param("product_line_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline file uploaded Successfully")));
	}
	
	@Test
	public void updateProductLineMultiFileFailedCase() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		
		uploadModel=new ProductLineUploadModel();
		
		responseDTO.setMessage("Productline file upload Failed due to File Upload Failure");
		responseDTO.setStatusCode(1);
		responseDTO.setProductLineId(2);
		ResponseDTO responseDTO1 = new ResponseDTO();
		
		responseDTO1.setMessage("Productline file upload Failed due to File Upload Failure");
		responseDTO1.setStatusCode(0);
		responseDTO1.setProductLineId(2);
				
		when(service.createDirectories(2, "C:/Users/pravesh/Desktop/productline_uploads/ProductLine")).thenReturn(responseDTO);
		
		responseDTO.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/ProductLine_2/2018-05-17/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		
		//when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(responseDTO1);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductLineFile").file(firstFile).file(secondFile)
				.file(jsonFile).param("product_line_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline file upload Failed due to File Upload Failure")));
	}
	
	@Test
	public void updateProductLineSingleFileFailedCase() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
	
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		
		uploadModel=new ProductLineUploadModel();
		
		responseDTO.setMessage("Productline file upload Failed due to File Upload Failure");
		responseDTO.setStatusCode(1);
		responseDTO.setProductLineId(2);
		ResponseDTO responseDTO1 = new ResponseDTO();
		
		responseDTO1.setMessage("Productline file upload Failed due to File Upload Failure");
		responseDTO1.setStatusCode(0);
		responseDTO1.setProductLineId(2);
				
		when(service.createDirectories(2, "C:/Users/pravesh/Desktop/productline_uploads/ProductLine")).thenReturn(responseDTO);
		
		responseDTO.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/ProductLine_2/2018-05-17/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		
		//when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(responseDTO1);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductLineFile").file(firstFile)
				.file(jsonFile).param("product_line_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline file upload Failed due to File Upload Failure")));
	}
}
