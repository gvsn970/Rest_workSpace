package com.nexiilabs.autolifecycle.productline.test;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexiilabs.autolifecycle.product.Product;
import com.nexiilabs.autolifecycle.productline.ProductLineController;
import com.nexiilabs.autolifecycle.productline.ProductLinePOJO;
import com.nexiilabs.autolifecycle.productline.ProductLineService;
import com.nexiilabs.autolifecycle.productline.ProductLineUploadModel;
import com.nexiilabs.autolifecycle.productline.Response;
import com.nexiilabs.autolifecycle.releases.ProductReleaseUploadModel;
import com.nexiilabs.autolifecycle.releases.ReleaseModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;

@RunWith(MockitoJUnitRunner.class)
// @RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class Testproductlinecontroller {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@InjectMocks
	private ProductLineController productlineController;

	@Mock
	private ProductLineService service;
	
	@Mock
	AttachmentUploadController attachmentUploadController;

	@Mock
	ObjectMapper objectMapper;

	@Mock
	Environment environment;
	JacksonTester<ProductLinePOJO> jsonTester;

	List<ProductLinePOJO> users = null;
	List<ProductLinePOJO> products = null;
	ProductLinePOJO p1;
	Response response;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productlineController).build();
		ProductLinePOJO p1 = new ProductLinePOJO();
		p1.setProduct_line_id(1);
		p1.setProduct_line_name("productline_name1");
		p1.setProduct_line_desc("productline_desc...1");
		ProductLinePOJO p2 = new ProductLinePOJO();
		p2.setProduct_line_id(2);
		p2.setProduct_line_name("productline_name2");
		p2.setProduct_line_desc("productline_desc...2");
		users = Arrays.asList(p1, p2);

		ProductLinePOJO product1 = new ProductLinePOJO();
		product1.setProduct_name("product1");
		ProductLinePOJO product2 = new ProductLinePOJO();
		product2.setProduct_name("product2");
		products = Arrays.asList(product1, product2);
		objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
		when(environment.getProperty("app.productlineuploaddir"))
				.thenReturn("C:/Users/pravesh/Desktop/productline_uploads");

	}

	@Test
	public void getAllProductsLines() throws Exception {

		when(service.listofProductLines()).thenReturn(users);

		mockMvc.perform(get("/productline/getAll").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].product_line_name", is("productline_name1")))
				.andExpect(jsonPath("$[0].product_line_desc", is("productline_desc...1")))
				.andExpect(jsonPath("$[0].product_line_id", is(1)));
	}

	@Test
	public void getDetails() throws Exception {

		int product_line_id = 1;

		when(service.listOfSpecificProductLine(product_line_id)).thenReturn(users);

		mockMvc.perform(get("/productline/getDetails?product_line_id=1").contentType("application/json")
				.accept("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].product_line_id", is(1)))
				.andExpect(jsonPath("$[0].product_line_name", is("productline_name1")))
				.andExpect(jsonPath("$[0].product_line_desc", is("productline_desc...1")));

	}

	// list of product names by productline Id
	@Test
	public void listofProductNamesByproductlineId() throws Exception {

		int product_line_id = 1;

		when(service.listOfProductNamesByProductlineId(product_line_id)).thenReturn(products);

		mockMvc.perform(get("/productline/getNamesbyId?product_line_id=1").contentType("application/json")
				.accept("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].product_name", is("product1")));

	}

	// for delete().
	@Test
	public void deleteProductLine() throws Exception {
		response = new Response();
		response.setMessage("ProductLine deleted successfully");
		response.setStatus(1);
		Mockito.when(service.deleteProductLine(Mockito.anyInt())).thenReturn(1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/productline/delete").param("product_line_id", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(1)))
				.andExpect(jsonPath("$.message", is("ProductLine deleted successfully")));

	}

	// for delete(). deletion failed
	@Test
	public void deleteProductLinefailed() throws Exception {
		response = new Response();
		response.setMessage("ProductLine not deleted.");
		response.setStatus(2);
		Mockito.when(service.deleteProductLine(Mockito.anyInt())).thenReturn(2);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/productline/delete").param("product_line_id", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(2)))
				.andExpect(jsonPath("$.message", is("ProductLine not deleted.")));

	}

	// for delete(). productlinenotfound
	@Test
	public void deleteProductLineNotFound() throws Exception {
		response = new Response();
		response.setMessage("ProductLine not found.empty fields exist");
		response.setStatus(0);
		Mockito.when(service.deleteProductLine(Mockito.anyInt())).thenReturn(0);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/productline/delete").param("product_line_id", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(0)))
				.andExpect(jsonPath("$.message", is("ProductLine not found.empty fields exist")));

	}

	// for create()
	@Test
	public void multifilescreate() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		String product_line_name = "productline_name1";
		String product_line_desc = "productline_desc...1";
		response = new Response();
		response.setMessage("Productline created successfully");
		response.setStatus(1);
		response.setProductlineId(1);

		when(service.addProductline(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		//when(service.createDirectories(1, "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		//when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		//		.thenReturn(true);
		//when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").file(firstFile).file(secondFile)
				.file(jsonFile).param("product_line_name", "cpool15")
				.param("product_line_desc", "consultancy pool version15").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline created successfully")));
	}

	// for create()
	@Test
	public void singlefilecreate() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		String product_line_name = "productline_name1";
		String product_line_desc = "productline_desc...1";
		response = new Response();
		response.setMessage("Productline created successfully");
		response.setStatus(1);
		response.setProductlineId(1);

		when(service.addProductline(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		//when(service.createDirectories(1, "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		//when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		//		.thenReturn(true);
		//when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").file(firstFile).file(jsonFile)
				.param("product_line_name", "cpool15").param("product_line_desc", "consultancy pool version15")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline created successfully")));
	}

	// for create()
	@Test
	public void noFileCreate() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		String product_line_name = "productline_name1";
		String product_line_desc = "productline_desc...1";
		response = new Response();
		response.setMessage("Productline creation failed");
		response.setStatus(0);
		response.setProductlineId(1);

		when(service.addProductline(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").param("product_line_name", "cpool15")
				.param("product_line_desc", "consultancy pool version15").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline creation failed")));
	}

	// for create(). productline creation failed case
	@Test
	public void createFailed() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		String product_line_name = "productline_name1";
		String product_line_desc = "productline_desc...1";
		response = new Response();
		response.setMessage("Productline creation failed");
		response.setStatus(0);
		response.setProductlineId(1);

		when(service.addProductline(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").param("product_line_name", "cpool15")
				.param("product_line_desc", "consultancy pool version15").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline creation failed")));
	}

	// for create(). productline already existance check
	@Test
	public void existanceCheck() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());

		String product_line_name = "productline_name1";
		String product_line_desc = "productline_desc...1";
		response = new Response();
		response.setMessage(" Productline alredy exist in database");
		response.setStatus(2);
		response.setProductlineId(1);

		when(service.addProductline(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").param("product_line_name", "cpool15")
				.param("product_line_desc", "consultancy pool version15").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(jsonPath("$.status", Matchers.is(2)))
				.andExpect(jsonPath("$.message", Matchers.is(" Productline alredy exist in database")));
	}

	// for create(). multifiles and singlefile creation failed case
	@Test
	public void creationFailed() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		String product_line_name = "productline_name1";
		String product_line_desc = "productline_desc...1";
		response = new Response();
		response.setMessage("Productline creation failed");
		response.setStatus(0);
		response.setProductlineId(1);

		when(service.addProductline(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").file(firstFile).file(secondFile)
				.file(jsonFile).param("product_line_name", "cpool15")
				.param("product_line_desc", "consultancy pool version16").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline creation failed")));
	}

	// for create(). multifiles creation failed case
	@Test
	public void multifilesFailed() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		String product_line_name = "productline_name1";
		String product_line_desc = "productline_desc...1";
		
		//ProductLineUploadModel[] productLineUploadModel=null;
		response = new Response();
		response.setMessage("Productline Not created");
		response.setStatus(1);
		response.setProductlineId(1);
		
		ResponseDTO response1 = new ResponseDTO();
		response1.setMessage("Productline Not created");
		response1.setStatusCode(0);
		
		when(service.addProductline(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		MultipartFile[] productLineUploadModel={firstFile,secondFile};
		
		when(attachmentUploadController.createproductLineFileUpload(productLineUploadModel, 1)).thenReturn(response1);
		//when(attachmentUploadController.createproductLineFileUpload(Mockito.anyObject(), 1);
		
		//when(service.createDirectories(1, "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
		
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		//when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
			//	.thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").file(firstFile).file(secondFile)
				.file(jsonFile).param("product_line_name", "cpool15")
				.param("product_line_desc", "consultancy pool version16").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline Not created")));
	}

	// for create(). singlefiles creation failed case
	@Test
	public void singlefilesFailed() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		String product_line_name = "productline_name1";
		String product_line_desc = "productline_desc...1";
		response = new Response();
		response.setMessage("Productline creation failed");
		response.setStatus(0);
		response.setProductlineId(1);
		Response response1 = new Response();
		response1.setMessage("Productline creation failed");
		response1.setStatus(0);
		when(service.addProductline(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		//when(service.createDirectories(1, "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		//when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
			//	.thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").file(firstFile).file(jsonFile)
				.param("product_line_name", "cpool15").param("product_line_desc", "consultancy pool version16")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0))).
				andExpect(jsonPath("$.message",Matchers.is("Productline creation failed")));
	}

	/*
	 * // for create(). singlefile case no file upload failure//In progress
	 * 
	 * @Test public void singleNofilesUploaded() throws Exception {
	 * 
	 * MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt",
	 * "text/plain", "".getBytes()); String product_line_name = "productline_name1";
	 * String product_line_desc = "productline_desc...1"; response = new Response();
	 * response.setMessage("Please upload productline file"); response.setStatus(1);
	 * response.setProductlineId(1); Response response1 = new Response();
	 * response1.setMessage("Please upload productline file");
	 * response1.setStatus(0); when(service.addProductline(Mockito.anyString(),
	 * Mockito.anyString())).thenReturn(response); when(service.createDirectories(1,
	 * "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
	 * response.setUploadPath(
	 * "C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17"); //
	 * when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), //
	 * Mockito.anyString(), Mockito.anyString())) // .thenReturn(false); //
	 * when(service.addProductline(Mockito.anyString(), //
	 * Mockito.anyString())).thenReturn(response);
	 * mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").file
	 * (firstFile) .param("product_line_name", "cpool15").param("product_line_desc",
	 * "consultancy pool version16")
	 * .contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
	 * .andExpect(jsonPath("$.status", Matchers.is(0)))
	 * .andExpect(jsonPath("$.message",
	 * Matchers.is("Please upload productline file"))); }
	 */

	// for update(). multifiles update
	@Test
	public void updatemultifiles() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		response = new Response();
		response.setMessage("Productline file uploaded Successfully");
		response.setStatus(1);
		response.setProductlineId(1);
		when(service.updateProductLine(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(response);
		//when(service.createDirectories(1, "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		//when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		//		.thenReturn(true);
		//when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/update").file(firstFile).file(secondFile)
				.file(jsonFile).param("product_line_id", "1").param("product_line_name", "product12")
				.param("product_line_desc", "desc...12").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline file uploaded Successfully")));
	}

	// for update(). multifiles update failed case
	@Test
	public void updatemultifilesFailed() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		response = new Response();
		response.setMessage("Productline file upload Failed due to File Upload Failure");
		response.setStatus(0);
		response.setProductlineId(1);
		Response response1 = new Response();
		response1.setMessage("Productline file upload Failed due to File Upload Failure");
		response1.setStatus(0);
		when(service.updateProductLine(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(response);
		//when(service.createDirectories(1, "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		//when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
			//	.thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/update").file(firstFile).file(secondFile)
				.file(jsonFile).param("product_line_id", "1").param("product_line_name", "product12")
				.param("product_line_desc", "desc...12").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0))).andExpect(jsonPath("$.message",
						Matchers.is("Productline file upload Failed due to File Upload Failure")));
	}

	// for singlefile update
	@Test
	public void updatesinglefile() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		response = new Response();
		response.setMessage("Productline file uploaded Successfully");
		response.setStatus(1);
		response.setProductlineId(1);
		when(service.updateProductLine(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(response);
		//when(service.createDirectories(1, "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		//when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
			//	.thenReturn(true);
		//when(service.addProductLineUploadDetails(Mockito.any())).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/update").file(firstFile).file(jsonFile)
				.param("product_line_id", "1").param("product_line_name", "product12")
				.param("product_line_desc", "desc...12").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline file uploaded Successfully")));
	}

	// for update(). singlefile update failed case
	@Test
	public void updatesinglefileFailed() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		response = new Response();
		response.setMessage("Productline file upload Failed due to File Upload Failure");
		response.setStatus(0);
		response.setProductlineId(1);
		Response response1 = new Response();
		response1.setMessage("Productline file upload Failed due to File Upload Failure");
		response1.setStatus(0);
		when(service.updateProductLine(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(response);
		//when(service.createDirectories(1, "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		//when(service.saveFileToDisk(Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
			//	.thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/update").file(firstFile).file(jsonFile)
				.param("product_line_id", "1").param("product_line_name", "product12")
				.param("product_line_desc", "desc...12").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0))).andExpect(jsonPath("$.message",
						Matchers.is("Productline file upload Failed due to File Upload Failure")));
	}

	// for update().for nofile update
	@Test
	public void updatenofile() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		response = new Response();
		response.setMessage("Productline updation failed");
		response.setStatus(0);
		response.setProductlineId(1);
		when(service.updateProductLine(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/update").param("product_line_id", "1")
				.param("product_line_name", "product12").param("product_line_desc", "desc...12")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Productline updation failed")));
	}

	// for update(). In singlefile, existance check
	@Test
	public void updatesingleExistancecheck() throws Exception {
		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain", "".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		response = new Response();
		response.setMessage("Please upload Productline file");
		response.setStatus(1);
		response.setProductlineId(1);
		Response response1 = new Response();
		response1.setMessage("Please upload Productline file");
		response1.setStatus(0);
		when(service.updateProductLine(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(response);
		//when(service.createDirectories(1, "C:/Users/pravesh/Desktop/productline_uploads")).thenReturn(response);
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/update").file(firstFile)
				.param("product_line_id", "1").param("product_line_name", "product12")
				.param("product_line_desc", "desc...12").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Please upload Productline file")));
	}

	// create() productline with validations

	@Test
	public void addProductLineValidation() throws Exception {
		String product_line_name = "jasjsadsadasdasdsajhadasjhdaskhdksadjksahajfgdsufiuweiwuuiwowqasdaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaarefdsgfdshfhdsfgdsfsddfdshfdsfdsfdsfhg";

		String product_line_desc = "sadsadsdsadsaf";
		response = new Response();

		// verify(productlineController).createProductLine(product_line_name,
		// product_line_desc, productUploads, request, res)
		if (product_line_name.equals("") || product_line_desc.equals("")) {
			System.err.println("Productline Name" + product_line_name);
			when(environment.getProperty("app.allFieldsManditory")).thenReturn("All Input fields are manditory");
			response.setStatus(0);
			response.setMessage("All Input fields are manditory");
			return;
		}
		if (product_line_name.length() > 60) {
			//when(environment.getProperty("app.productlineNameLong")).thenReturn("ProductLine Name is too Long");
			System.err.println("ProductLine Name:::::::::" + product_line_name.length());
			response.setStatus(0);
			response.setMessage("ProductLine Name is too Long");
			return;
		}
		if (product_line_desc.length() > 1000) {
			when(environment.getProperty("app.productlineDescLong")).thenReturn("ProductLine Description is too Long");
			response.setStatus(0);
			response.setMessage("ProductLine Description is too Long");
			return;
		}

		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfddsfdsfssf".getBytes());
		response.setProductlineId(1);
		response.setUploadPath("C:/Users/pravesh/Desktop/productline_uploads/Productline15/2018-05-17");
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/productline/create").file(file1).file(file2)
				.param("product_line_name", product_line_name).param("product_line_desc", product_line_desc)
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", is(response.getStatus())))
				.andExpect(jsonPath("$.message", is(response.getMessage())));

	}

	@Test
	public void deleteProductLinefiles() throws Exception {

		ProductLineUploadModel productLineUploadModel = new ProductLineUploadModel();
		response = new Response();
		response.setStatus(1);
		response.setMessage("ProductLine Deleted successfully");
		Response response1=new Response();
		response1.setStatus(1);
		response1.setMessage("ProductLine Deleted successfully");

		productLineUploadModel.setFk_product_line_id(1);
		productLineUploadModel.setAttachmentId(1);
		productLineUploadModel.setDeletedBy(1);
		Mockito.when(service.deleteProductLineFiles(Mockito.any(ProductLineUploadModel.class))).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/productline/deleteProductLineFiles")
				.param("product_line_id", "1").param("fileId", "1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(1)))
				.andExpect(jsonPath("$.message", is("ProductLine Deleted successfully")));

	}

	@Test
	public void deleteProductLinefilesFailedCase() throws Exception {
		response = new Response();
		response.setMessage("ProductLine Deletion Failed");
		response.setStatus(0);
		ProductLineUploadModel productLineUploadModel = new ProductLineUploadModel();

		productLineUploadModel.setFk_product_line_id(1);
		productLineUploadModel.setAttachmentId(1);
		productLineUploadModel.setDeletedBy(1);

		Mockito.when(service.deleteProductLineFiles(Mockito.any(ProductLineUploadModel.class))).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/productline/deleteProductLineFiles")
				.param("product_line_id", "1").param("fileId", "1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(0)))
				.andExpect(jsonPath("$.message", is("ProductLine Deletion Failed")));
	}

}
