package com.nexiilabs.autolifecycle.productline.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.nexiilabs.autolifecycle.resources.ProductLineLogoController;
import com.nexiilabs.autolifecycle.resources.ProductLineLogoService;
import com.nexiilabs.autolifecycle.resources.ProductLineLogoUploadModel;

import com.nexiilabs.autolifecycle.resources.Response;

@RunWith(MockitoJUnitRunner.class)
// @RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class TestProductLineLogoController {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@InjectMocks
	private ProductLineLogoController productLineLogoController;

	@Mock
	private ProductLineLogoService service;

	@Mock
	ObjectMapper objectMapper;

	@Mock
	Environment environment;

	JacksonTester<ProductLineLogoUploadModel> jsonTester;

	List<ProductLineLogoUploadModel> uploads = null;

	ProductLineLogoUploadModel pll = null;

	Response response;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productLineLogoController).build();
		ProductLineLogoUploadModel pll1 = new ProductLineLogoUploadModel();
		pll1.setFk_product_line_id(7);

		objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
		when(environment.getProperty("app.productlinelogouploaddir"))
				.thenReturn("C:/Users/pravesh/Desktop/productlinelogouploads");
	}

	// create(). productline logo

	@Test
	public void createProductLineLogo() throws Exception {

		FileInputStream fis = new FileInputStream("C:\\Rahul_backup\\autolifecyclelogo.png");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "autolifecyclelogo.png", "image/png", fis);

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		response.setMessage("Productline logo created successfully");
		response.setStatus(1);

		Response response1 = new Response();
		response1.setMessage("Productline logo created successfully");
		response1.setStatus(1);

		when(service.createDirectories(3, "C:/Users/pravesh/Desktop/productlinelogouploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productlinelogouploads/Productlinelogos3");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(service.addProductLineUploadDetails(Mockito.any(ProductLineLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/create/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline logo created successfully")));
	}

	// productline logo failed case

	@Test
	public void createProductLineLogoFailedcaseLogoType() throws Exception {

		FileInputStream fis = new FileInputStream("C:\\Rahul_backup\\2057921811589a1.jpg");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "2057921811589a1.jpg", "text/plain", fis);

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		response.setMessage("Only png file types are supported");
		response.setStatus(0);

		Response response1 = new Response();
		response1.setMessage("Only png file types are supported");
		response1.setStatus(0);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/create/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Only png file types are supported")));
	}

	// productline logo failed case
	@Test
	public void createProductLineLogoFailedcasebyLogoPixel() throws Exception {

		FileInputStream fis = new FileInputStream("C:\\Rahul_backup\\rahul logo.png");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "rahul logo.png", "image/png", fis);

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		response.setMessage("productline logo should be 35*35 pixel");
		response.setStatus(0);

		Response response1 = new Response();
		response1.setMessage("productline logo should be 35*35 pixel");
		response1.setStatus(0);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/create/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("productline logo should be 35*35 pixel")));
	}

	// productline logo failed case
	@Test
	public void createProductLineLogoFailedcasebyEmptyFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("logo", "emptyDocument.png", "image/png", "".getBytes());

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		response.setMessage("Productline logo required");
		response.setStatus(0);

		Response response1 = new Response();
		response1.setMessage("Productline logo required");
		response1.setStatus(0);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/create/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline logo required")));
	}

	@Test
	public void createProductLineLogoFailedElseCase() throws Exception {

		FileInputStream fis = new FileInputStream("C:\\Rahul_backup\\autolifecyclelogo.png");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "autolifecyclelogo.png", "image/png", fis);

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		response.setMessage("productline logo  upload Failed due to logo Upload Failure");
		response.setStatus(0);

		Response response1 = new Response();
		response1.setMessage("productline logo  upload Failed due to logo Upload Failure");
		response1.setStatus(0);

		when(service.createDirectories(3, "C:/Users/pravesh/Desktop/productlinelogouploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productlinelogouploads/Productlinelogos3");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		// when(service.addProductLineUploadDetails(Mockito.any(ProductLineLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/create/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0))).andExpect(jsonPath("$.message",
						Matchers.is("productline logo  upload Failed due to logo Upload Failure")));
	}

	// update().

	@Test
	public void updateProductLineLogo() throws Exception {

		FileInputStream fis = new FileInputStream("C:\\Rahul_backup\\autolifecyclelogo.png");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "autolifecyclelogo.png", "image/png", fis);

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		response.setMessage("Productline logo updated successfully");
		response.setStatus(1);

		Response response1 = new Response();
		response1.setMessage("Productline logo updated successfully");
		response1.setStatus(1);
		when(service.updateProductlineLogo(3)).thenReturn(response);
		when(service.createDirectories(3, "C:/Users/pravesh/Desktop/productlinelogouploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productlinelogouploads/Productlinelogos3");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		when(service.addProductLineUploadDetails(Mockito.any(ProductLineLogoUploadModel.class))).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/update/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline logo updated successfully")));
	}

	// update().
	@Test
	public void updateProductLineLogoFailedElsecase() throws Exception {

		FileInputStream fis = new FileInputStream("C:\\Rahul_backup\\autolifecyclelogo.png");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "autolifecyclelogo.png", "image/png", fis);

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		response.setMessage("productline logo  upload Failed due to logo Upload Failure");
		response.setStatus(1);

		Response response1 = new Response();
		response1.setMessage("productline logo  upload Failed due to logo Upload Failure");
		response1.setStatus(0);
		when(service.updateProductlineLogo(3)).thenReturn(response);
		when(service.createDirectories(3, "C:/Users/pravesh/Desktop/productlinelogouploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productlinelogouploads/Productlinelogos3");
		when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/update/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0))).andExpect(jsonPath("$.message",
						Matchers.is("productline logo  upload Failed due to logo Upload Failure")));
	}

	@Test
	public void updateProductLineLogopixelsfailedcase() throws Exception {

		FileInputStream fis = new FileInputStream("C:\\Rahul_backup\\rahul logo.png");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "rahul logo.png", "image/png", fis);

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		// response.setMessage("logo should be 35*35 pixel");
		response.setStatus(1);

		Response response1 = new Response();
		response1.setMessage("logo should be 35*35 pixel");
		response1.setStatus(0);
		when(service.updateProductlineLogo(3)).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/update/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("logo should be 35*35 pixel")));
	}

	@Test
	public void updateProductLineLogoTypefailedcase() throws Exception {

		FileInputStream fis = new FileInputStream("C:\\Rahul_backup\\autolifecyclelogojpg.jpg");

		MockMultipartFile firstFile = new MockMultipartFile("logo", "autolifecyclelogojpg.jpg", "text/plain", fis);

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		response.setMessage("Only png file types are supported");
		response.setStatus(0);

		Response response1 = new Response();
		response1.setMessage("Only png file types are supported");
		response1.setStatus(0);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/update/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Only png file types are supported")));
	}

	@Test
	public void updateProductLineLogoEmptyfailedcase() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("logo", "autolifecyclelogo.png", "image/png",
				"".getBytes());

		ProductLineLogoUploadModel productLineLogoUploadModel = new ProductLineLogoUploadModel();
		productLineLogoUploadModel.setFk_product_line_id(3);
		response = new Response();
		response.setMessage("Productline logo required");
		response.setStatus(1);

		Response response1 = new Response();
		response1.setMessage("Productline logo required");
		response1.setStatus(0);
		when(service.updateProductlineLogo(3)).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productlinelogo/update/").file(firstFile)
				.param("fk_product_line_id", "3").content(firstFile.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline logo required")));
	}
}
