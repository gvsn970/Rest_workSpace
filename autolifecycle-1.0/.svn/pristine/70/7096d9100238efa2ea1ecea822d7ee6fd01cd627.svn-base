package com.nexiilabs.autolifecycle.product.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.util.ArrayList;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexiilabs.autolifecycle.resources.ProductLogoController;
import com.nexiilabs.autolifecycle.resources.ProductLogoService;
import com.nexiilabs.autolifecycle.resources.ProductLogoUploadModel;
import com.nexiilabs.autolifecycle.resources.Response;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class TestProductLogoController {
	
	private MockMvc mockMvc;
	@InjectMocks
	ProductLogoController productLogoController;
	
	@Mock
	private ProductLogoService service;
	@Mock
	ObjectMapper objectMapper;

	@Mock
	Environment environment;
	Response response =null;
	ProductLogoUploadModel productLogoUploadModel =null;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productLogoController).build();
		
		objectMapper = new ObjectMapper();
	
		JacksonTester.initFields(this, objectMapper);
		when(environment.getProperty("app.productlogouploaddir")).thenReturn("/home/nexii/productlogouploads/");
	}
	@Test
	public void testcreateProductLogo() throws Exception {
		 FileInputStream fis = new FileInputStream("/home/nexii/Desktop/if_Show_131782.png");
         MockMultipartFile firstFile = new MockMultipartFile("logo", "if_Show_131782.png", "image/png", fis);

		productLogoUploadModel=new ProductLogoUploadModel();
		productLogoUploadModel.setFkProductId(3);
		response=new Response(); 
		response.setMessage("Product logo created successfully");
		response.setStatus(1);
		//response.setProductId(2);
		response.setUploadPath("/home/nexii/productlogouploads/Productlogos3/");
		
		when(service.createDirectories(3, "/home/nexii/productlogouploads/")).thenReturn(response);
		
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(service.addProductUploadDetails(Mockito.any(ProductLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/create/").file(firstFile)
				.param("fk_product_id", "3")
				.content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product logo created successfully")));
	}
	@Test
	public void testcreateProductLogoFail() throws Exception {
		 FileInputStream fis = new FileInputStream("/home/nexii/Desktop/if_Show_131782.png");
         MockMultipartFile firstFile = new MockMultipartFile("logo", "if_Show_131782.png", "image/png", fis);

		productLogoUploadModel=new ProductLogoUploadModel();
		productLogoUploadModel.setFkProductId(3);
		response=new Response(); 
		response.setMessage("product logo  upload Failed due to logo Upload Failure");
		response.setStatus(0);
		//response.setProductId(2);
		response.setUploadPath("/home/nexii/productlogouploads/Productlogos3/");
		
		//response.setProductId(2);
		when(service.createDirectories(3, "/home/nexii/productlogouploads/")).thenReturn(response);
		
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
	//	when(service.addProductUploadDetails(Mockito.any(ProductLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/create/").file(firstFile)
				.param("fk_product_id", "3")
				.content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("product logo  upload Failed due to logo Upload Failure")));
	}
	@Test
	public void testcreateProductLogoOnlyPNG() throws Exception {
		
		// FileInputStream fis = new FileInputStream("/home/nexii/Desktop/download.png");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "filename.png", "text/plain",
				"some xml".getBytes());

		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		response = new Response();
		productLogoUploadModel = new ProductLogoUploadModel();
		response.setMessage(" Only png file types are supported");
		response.setStatus(0);
		response.setProductId(3);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/create").file(firstFile).file(jsonFile)
				.param("fk_product_id", "3").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is(" Only png file types are supported")));
	}
	@Test
	public void testcreateProductLogoPixelSize() throws Exception {
		 FileInputStream fis = new FileInputStream("/home/nexii/Desktop/download.png");
         MockMultipartFile firstFile = new MockMultipartFile("logo", "download.png", "image/png", fis);

		productLogoUploadModel=new ProductLogoUploadModel();
		productLogoUploadModel.setFkProductId(3);
		response=new Response(); 
		response.setMessage("logo should be 35*35 pixel");
		response.setStatus(0);
		//response.setProductId(2);
		response.setUploadPath("/home/nexii/productlogouploads/Productlogos3/");
		
		//response.setProductId(2);
	//	when(service.createDirectories(3, "/home/nexii/productlogouploads/")).thenReturn(response);

	//	when(service.addProductUploadDetails(Mockito.any(ProductLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/create/").file(firstFile)
				.param("fk_product_id", "3")
				.content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("logo should be 35*35 pixel")));
	}
	
	@Test
	public void testcreateProductLogoReuired() throws Exception {
		 FileInputStream fis = new FileInputStream("/home/nexii/Desktop/empty.png");
         MockMultipartFile firstFile = new MockMultipartFile("logo","empty.png", "image/png", fis);

		productLogoUploadModel=new ProductLogoUploadModel();
		productLogoUploadModel.setFkProductId(3);
		response=new Response(); 
		response.setMessage("Product logo required");
		response.setStatus(0);
		//response.setProductId(2);
		response.setUploadPath("/home/nexii/productlogouploads/Productlogos3/");
		
		//response.setProductId(2);
		//when(service.createDirectories(3, "/home/nexii/productlogouploads/")).thenReturn(response);

		//when(service.addProductUploadDetails(Mockito.any(ProductLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/create/").file(firstFile)
				.param("fk_product_id", "3")
				
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Product logo required")));
	}


	@Test
	public void testUpdateProductLogo() throws Exception {
		 FileInputStream fis = new FileInputStream("/home/nexii/Desktop/if_Show_131782.png");
         MockMultipartFile firstFile = new MockMultipartFile("logo", "if_Show_131782.png", "image/png", fis);

		productLogoUploadModel=new ProductLogoUploadModel();
		productLogoUploadModel.setFkProductId(3);
		response=new Response(); 
		response.setMessage("Product logo updated successfully");
		response.setStatus(1);
		//response.setProductId(2);
		response.setUploadPath("/home/nexii/productlogouploads/Productlogos3/");
		
		when(service.createDirectories(3, "/home/nexii/productlogouploads/")).thenReturn(response);
		
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(service.updateProductUploadDetails(Mockito.any(ProductLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/update/").file(firstFile)
				.param("fk_product_id", "3")
				.content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product logo updated successfully")));
	}
	@Test
	public void testupdateProductLogoFail() throws Exception {
		 FileInputStream fis = new FileInputStream("/home/nexii/Desktop/if_Show_131782.png");
         MockMultipartFile firstFile = new MockMultipartFile("logo", "if_Show_131782.png", "image/png", fis);

		productLogoUploadModel=new ProductLogoUploadModel();
		productLogoUploadModel.setFkProductId(3);
		response=new Response(); 
		response.setMessage("product logo  upload Failed due to logo Upload Failure");
		response.setStatus(0);
		//response.setProductId(2);
		response.setUploadPath("/home/nexii/productlogouploads/Productlogos3/");
		
		//response.setProductId(2);
		when(service.createDirectories(3, "/home/nexii/productlogouploads/")).thenReturn(response);
		
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
	//	when(service.addProductUploadDetails(Mockito.any(ProductLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/update/").file(firstFile)
				.param("fk_product_id", "3")
				.content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("product logo  upload Failed due to logo Upload Failure")));
	}
	@Test
	public void testUpdateProductLogoPixelSize() throws Exception {
		 FileInputStream fis = new FileInputStream("/home/nexii/Desktop/download.png");
         MockMultipartFile firstFile = new MockMultipartFile("logo", "download.png", "image/png", fis);

		productLogoUploadModel=new ProductLogoUploadModel();
		productLogoUploadModel.setFkProductId(3);
		response=new Response(); 
		response.setMessage("logo should be 35*35 pixel");
		response.setStatus(0);
		//response.setProductId(2);
		response.setUploadPath("/home/nexii/productlogouploads/Productlogos3/");
		
		//response.setProductId(2);
		//when(service.createDirectories(3, "/home/nexii/productlogouploads/")).thenReturn(response);

		
	//	when(service.addProductUploadDetails(Mockito.any(ProductLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/update").file(firstFile)
				.param("fk_product_id", "3")
				.content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("logo should be 35*35 pixel")));
	}
	@Test
	public void testupdateProductLogoReuired() throws Exception {
		 FileInputStream fis = new FileInputStream("/home/nexii/Desktop/empty.png");
         MockMultipartFile firstFile = new MockMultipartFile("logo","empty.png", "image/png", fis);

		productLogoUploadModel=new ProductLogoUploadModel();
		productLogoUploadModel.setFkProductId(3);
		response=new Response(); 
		response.setMessage("Product logo required");
		response.setStatus(0);
		//response.setProductId(2);
		response.setUploadPath("/home/nexii/productlogouploads/Productlogos3/");
		
		//response.setProductId(2);
	//	when(service.createDirectories(3, "/home/nexii/productlogouploads/")).thenReturn(response);

		//when(service.addProductUploadDetails(Mockito.any(ProductLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/update/").file(firstFile)
				.param("fk_product_id", "3")
				
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Product logo required")));
	}


	@Test
	public void testUpdateProductLogoOnlyPNG() throws Exception {
		
		// FileInputStream fis = new FileInputStream("/home/nexii/Desktop/download.png");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "filename.png", "text/plain",
				"some xml".getBytes());

		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		response = new Response();
		productLogoUploadModel = new ProductLogoUploadModel();
		response.setMessage("Only png file types are supported");
		response.setStatus(0);
		response.setProductId(3);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlogo/update").file(firstFile).file(jsonFile)
				.param("fk_product_id", "3").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Only png file types are supported")));
	}
	
	@Test
	public void testDownloadFile()  throws Exception{
		 FileInputStream fis = new FileInputStream("/home/nexii/Desktop/download.png");
		 
         MockMultipartFile firstFile = new MockMultipartFile("logo", "download.png", "image/png", fis);
         
         List<ProductLogoUploadModel> productLogoUploadModel=new ArrayList<ProductLogoUploadModel>();
         int fk_product_id=7;
 
 		when(service.getProductLogoById(fk_product_id)).thenReturn(productLogoUploadModel);
 		mockMvc.perform(
				get("/productlogo/getproduclogotbyId?fk_product_id=7").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk());
        		 
		
	}
	

}
