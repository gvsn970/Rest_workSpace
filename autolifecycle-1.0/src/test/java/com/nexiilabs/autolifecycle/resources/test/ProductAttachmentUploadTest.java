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

import com.nexiilabs.autolifecycle.product.ProductUploadModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadService;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class ProductAttachmentUploadTest {
	private MockMvc mockMvc;

	@InjectMocks
	private AttachmentUploadController attachmentUploadController;
	@Mock
	private AttachmentUploadService service;

	@Mock
	Environment environment;
	ResponseDTO responseDTO;
	ProductUploadModel uploadModel = null;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(attachmentUploadController).build();
		when(environment.getProperty("app.productuploaddir")).thenReturn("/home/nexii/product_uploads/");
	}
	@Test
	public void createProductSingleFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		uploadModel=new ProductUploadModel();
		responseDTO.setMessage("Product upload file Created successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setProductId(2);
		when(service.createDirectories(2, "/home/nexii/product_uploads/Product")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(service.addProductUploadDetails(Mockito.any())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createproductfile").file(firstFile)
				.file(jsonFile).param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product upload file Created successfully")));
	}
	@Test
	public void createProductMultiFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		uploadModel=new ProductUploadModel();
		responseDTO.setMessage("Product file upload successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setProductId(2);
		when(service.createDirectories(2, "/home/nexii/product_uploads/Product")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(service.addProductUploadDetails(Mockito.any())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createproductfile").file(firstFile).file(secondFile)
				.file(jsonFile).param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product file upload successfully")));
	}
	@Test
	public void createProductSingleFileFail() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());

		responseDTO = new ResponseDTO();
		responseDTO.setMessage("product upload file not Created");
		responseDTO.setStatusCode(0);
		responseDTO.setProductId(2);
	
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.createDirectories(2, "/home/nexii/product_uploads/Product")).thenReturn(responseDTO);
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		when(service.deleteProductRecordInDatabase(Mockito.anyInt())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createproductfile").file(firstFile)
				.param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("product upload file not Created")));
	}
	@Test
	public void createProductMultiFail() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some second file".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		uploadModel=new ProductUploadModel();
		responseDTO.setMessage("product file upload Failed due to File Upload Failure");
		responseDTO.setStatusCode(0);
		responseDTO.setProductId(2);
		when(service.createDirectories(2, "/home/nexii/product_uploads/Product")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		//when(service.addProductUploadDetails(Mockito.any())).thenReturn(response);
		when(service.deleteProductRecordInDatabase(Mockito.anyInt())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/createproductfile").file(firstFile).file(secondFile)
				.file(jsonFile).param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("product file upload Failed due to File Upload Failure")));
	}
	@Test
	public void updateProductMultiFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		uploadModel=new ProductUploadModel();
		responseDTO.setMessage("Product file upload successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setProductId(2);
		when(service.createDirectories(2, "/home/nexii/product_uploads/Product")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(service.addProductUploadDetails(Mockito.any())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductfile").file(firstFile).file(secondFile)
				.file(jsonFile).param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product file upload successfully")));
	}

	@Test
	public void updateProductSingleFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		uploadModel=new ProductUploadModel();
		responseDTO.setMessage("Product upload file Created successfully");
		responseDTO.setStatusCode(1);
		responseDTO.setProductId(2);
		when(service.createDirectories(2, "/home/nexii/product_uploads/Product")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(service.addProductUploadDetails(Mockito.any())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductfile").file(firstFile)
				.file(jsonFile).param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product upload file Created successfully")));
	}
	@Test
	public void updateProductSingleFileFail() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());

		responseDTO = new ResponseDTO();
		responseDTO.setMessage("product upload file not Created");
		responseDTO.setStatusCode(0);
		responseDTO.setProductId(2);
	
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.createDirectories(2, "/home/nexii/product_uploads/Product")).thenReturn(responseDTO);
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		when(service.deleteProductRecordInDatabase(Mockito.anyInt())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductfile").file(firstFile)
				.param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("product upload file not Created")));
	}
	@Test
	public void updateProductMultiFail() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some second file".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		uploadModel=new ProductUploadModel();
		responseDTO.setMessage("product file upload Failed due to File Upload Failure");
		responseDTO.setStatusCode(0);
		responseDTO.setProductId(2);
		when(service.createDirectories(2, "/home/nexii/product_uploads/Product")).thenReturn(responseDTO);
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		//when(service.addProductUploadDetails(Mockito.any())).thenReturn(response);
		when(service.deleteProductRecordInDatabase(Mockito.anyInt())).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductfile").file(firstFile).file(secondFile)
				.file(jsonFile).param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("product file upload Failed due to File Upload Failure")));
	}
	@Test
	public void updateProductMultiFileNoChangesFound() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some second file".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		responseDTO = new ResponseDTO();
		uploadModel=new ProductUploadModel();
		responseDTO.setMessage("No changes found");
		responseDTO.setStatusCode(0);
		responseDTO.setProductId(2);
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setMessage("No changes found");
		responseDTO1.setStatusCode(0);
		responseDTO1.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.createDirectories(2, "/home/nexii/product_uploads/Product")).thenReturn(responseDTO);
	
		
		when(service.checkFileExistancyForProductFile(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductfile").file(firstFile).file(secondFile)
				.file(jsonFile).param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("No changes found")));
	}
	
	@Test
	public void UpdateProductSingleFileUploadNoChangesFound() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		responseDTO = new ResponseDTO();
		responseDTO.setProductId(1);
		responseDTO.setStatusCode(1);
		responseDTO.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		ResponseDTO responseDTO1 = new ResponseDTO();
		responseDTO1.setMessage("No changes found");
		responseDTO1.setStatusCode(0);
		responseDTO1.setUploadPath("/home/nexii/product_uploads/Product_2/2018-05-16/");
		when(service.createDirectories(Mockito.anyInt(), Mockito.anyString())).thenReturn(responseDTO);
		when(service.checkFileExistancyForProductFile(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/updateproductfile").file(firstFile).param("fk_product_id", "2")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.statusCode", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("No changes found")));
	}

}
