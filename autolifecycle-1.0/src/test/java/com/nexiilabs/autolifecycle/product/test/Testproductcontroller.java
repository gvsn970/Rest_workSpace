package com.nexiilabs.autolifecycle.product.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexiilabs.autolifecycle.product.Product;
import com.nexiilabs.autolifecycle.product.ProductController;
import com.nexiilabs.autolifecycle.product.ProductListDTO;
import com.nexiilabs.autolifecycle.product.ProductService;
import com.nexiilabs.autolifecycle.product.ProductUploadModel;
import com.nexiilabs.autolifecycle.product.Response;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
// @RunWith(SpringJUnit4ClassRunner.class)
public class Testproductcontroller {

	private MockMvc mockMvc;

	@InjectMocks
	private ProductController productController;
	@Mock
	AttachmentUploadController attachmentUploadController;

	@Mock
	private ProductService service;

	@Mock
	ObjectMapper objectMapper;

	@Mock
	Environment environment;
	JacksonTester<ProductListDTO> jsonTester;

	List<ProductListDTO> users = null;

	Product p1;
	Response response;
	Product product;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		ProductListDTO p1 = new ProductListDTO();
		p1.setProduct_id(1);
		p1.setProduct_name("product1");
		p1.setProduct_desc("desc1");
		p1.setProduct_line_desc("productline_desc...1");
		p1.setProduct_line_name("productline_name1");
		ProductListDTO p2 = new ProductListDTO();
		p2.setProduct_id(2);
		p2.setProduct_name("product2");
		p2.setProduct_desc("desc2");
		p2.setProduct_line_desc("productline_desc...2");
		p2.setProduct_line_name("productline_name2");
		users = Arrays.asList(p1, p2);
		objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
		when(environment.getProperty("app.allFieldsManditory")).thenReturn("All Input fields are manditory");
		when(environment.getProperty("app.productNameLong")).thenReturn("Product Name is too Long");
		when(environment.getProperty("app.productDescLong")).thenReturn("Product Description is too Long");
	}

	/*@Test
	public void testcreateProduct() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		MockMultipartFile productUploads[] = { firstFile, secondFile };

		response = new Response();
		ResponseDTO responseDTO = new ResponseDTO();
		response.setMessage("Product created successfully");
		response.setStatus(1);
		response.setProductId(1);
		response.setUploadPath("/home/nexii/product_uploads/PRODUCT_1/2018-05-18/");
		when(service.addProduct(product)
				.thenReturn(response);

		response.setUploadPath("/home/nexii/product_uploads/PRODUCT_1/2018-05-18/");

		when(attachmentUploadController.productFileUpload(productUploads, 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/create").file(firstFile).file(secondFile)
				.file(jsonFile).param("product_name", "product12").param("product_desc", "desc...12")
				.param("fk_product_line", "1").param("fk_product_owner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product created successfully")));
	}

	@Test
	public void testcreateProductWithOutFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json",
				"{\"json\": \"someValue\"}".getBytes());
		MockMultipartFile productUploads[] = { firstFile, secondFile };

		response = new Response();
		ResponseDTO responseDTO = new ResponseDTO();
		response.setMessage("Product created successfully");
		response.setStatus(1);
		response.setProductId(1);
		response.setUploadPath("/home/nexii/product_uploads/PRODUCT_1/2018-05-18/");
		when(service.addProduct(product)
				.thenReturn(response);

		response.setUploadPath("/home/nexii/product_uploads/PRODUCT_1/2018-05-18/");

		// when(attachmentUploadController.productFileUpload(productUploads,
		// 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/create").file(jsonFile)
				.param("product_name", "product12").param("product_desc", "desc...12").param("fk_product_line", "1")
				.param("fk_product_owner", "1").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product created successfully")));
	}

	@Test
	public void testproductAlreadyExist() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile productUploads[] = { firstFile, secondFile };
		ResponseDTO responseDTO = new ResponseDTO();
		response = new Response();
		response.setMessage("Product Already exists");
		response.setStatus(2);
		response.setProductId(1);
		when(service.addProduct(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(response);
		response.setUploadPath("/home/nexii/product_uploads/PRODUCT_1/2018-05-18/");
		// when(attachmentUploadController.productFileUpload(productUploads,
		// 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/create").file(firstFile).file(secondFile)
				.param("product_name", "product12").param("product_desc", "desc...12").param("fk_product_line", "1")
				.param("fk_product_owner", "1").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(2)))
				.andExpect(jsonPath("$.message", Matchers.is("Product Already exists")));
	}

	@Test
	public void testcreateProductFail() throws Exception {

		response = new Response();
		response.setMessage("Product creation failed");
		response.setStatus(0);
		response.setProductId(1);
		when(service.addProduct(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/create").param("product_name", "product12")
				.param("product_desc", "desc...12").param("fk_product_line", "1").param("fk_product_owner", "1")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Product creation failed")));
	}

*/	@Test
	public void testUpdateProduct() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile productUploads[] = { firstFile, secondFile };
		ResponseDTO responseDTO = new ResponseDTO();
		response = new Response();
		response.setMessage("Product updated successfully");
		response.setStatus(1);
		response.setProductId(1);
		when(service.updateProduct(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyInt())).thenReturn(response);

		response.setUploadPath("/home/nexii/product_uploads/PRODUCT_1/2018-05-18/");

		when(attachmentUploadController.updateProductFileUpload(productUploads, 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/update").file(firstFile).file(secondFile)
				.param("product_name", "product12").param("product_desc", "desc...12").param("fk_product_line", "1")
				.param("fk_product_owner", "1").param("product_id", "1").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product updated successfully")));
	}

	@Test
	public void testUpdateProductWithOutFile() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile productUploads[] = { firstFile, secondFile };
		ResponseDTO responseDTO = new ResponseDTO();
		response = new Response();
		response.setMessage("Product updated successfully");
		response.setStatus(1);
		response.setProductId(1);
		when(service.updateProduct(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyInt())).thenReturn(response);

		response.setUploadPath("/home/nexii/product_uploads/PRODUCT_1/2018-05-18/");

		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/update").param("product_name", "product12")
				.param("product_desc", "desc...12").param("fk_product_line", "1").param("fk_product_owner", "1")
				.param("product_id", "1").contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print())
				.andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("Product updated successfully")));
	}

	@Test
	public void testUpdateProductFail() throws Exception {

		MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain",
				"some xml".getBytes());
		MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.data", "text/plain",
				"some other type".getBytes());
		MockMultipartFile productUploads[] = { firstFile, secondFile };
		ResponseDTO responseDTO = new ResponseDTO();
		response = new Response();
		response.setMessage("Product Not updated ");
		response.setStatus(0);
		response.setProductId(1);
		when(service.updateProduct(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyInt())).thenReturn(response);

		response.setUploadPath("/home/nexii/product_uploads/PRODUCT_1/2018-05-18/");

		// when(attachmentUploadController.updateProductFileUpload(productUploads,
		// 1)).thenReturn(responseDTO);
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/update").file(firstFile).file(secondFile)
				.param("product_name", "product12").param("product_desc", "desc...12").param("fk_product_line", "1")
				.param("fk_product_owner", "1").param("product_id", "1").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(jsonPath("$.status", Matchers.is(0)))
				.andExpect(jsonPath("$.message", Matchers.is("Product Not updated ")));
	}

	@Test
	public void updateProductNochanges() throws Exception {
		MockMultipartFile file1 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "sample.txt", "text/plain",
				"some dsdsdsfdsf".getBytes());
		MockMultipartFile productUploads[] = { file1, file2 };
		ResponseDTO responseDTO = new ResponseDTO();
		response = new Response();
		response.setMessage("No changes Found");
		response.setStatus(1);
		response.setProductId(1);
		when(service.updateProduct(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyInt())).thenReturn(response);

		response.setUploadPath("/home/nexii/product_uploads/PRODUCT_1/2018-05-18/");

		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/update").file(file1).file(file2)
				.param("product_name", "product12").param("product_desc", "desc...12").param("fk_product_line", "1")
				.param("fk_product_owner", "1").param("product_id", "1").contentType(MediaType.MULTIPART_FORM_DATA))
				.andDo(print()).andExpect(jsonPath("$.status", Matchers.is(1)))
				.andExpect(jsonPath("$.message", Matchers.is("No changes Found")));
	}

	@Test
	public void testgetDetailsById() throws Exception {

		int product_id = 1;

		when(service.getDetails(product_id)).thenReturn(users);

		mockMvc.perform(
				get("/product/getDetails?product_id=1").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].product_desc", is("desc1")))
				.andExpect(jsonPath("$[0].product_line_name", is("productline_name1")))
				.andExpect(jsonPath("$[0].product_line_desc", is("productline_desc...1")));
	}

	@Test
	public void testgetAllProducts() throws Exception {

		when(service.allproducts()).thenReturn(users);

		mockMvc.perform(get("/product/getAll").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].product_name", is("product1")))
				.andExpect(jsonPath("$[0].product_desc", is("desc1"))).andExpect(jsonPath("$[0].product_id", is(1)))
				.andExpect(jsonPath("$[0].product_line_name", is("productline_name1")))
				.andExpect(jsonPath("$[0].product_line_desc", is("productline_desc...1")));

	}

	@Test
	public void testdeleteProdcut() throws Exception {

		response = new Response();
		response.setMessage("Product Deleted successfully");
		response.setStatus(1);
		response.setProductId(1);

		when(service.deleteproduct(Mockito.anyInt())).thenReturn(1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product/delete").param("product_id", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(1)))
				.andExpect(jsonPath("$.message", is("Product Deleted successfully")));

	}

	@Test
	public void testdeleteProdcutFail() throws Exception {

		response = new Response();
		response.setMessage("Product not deleted ");
		response.setStatus(0);
		response.setProductId(1);

		when(service.deleteproduct(Mockito.anyInt())).thenReturn(0);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product/delete").param("product_id", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(0)));

	}

	@Test
	public void deleteProductFiles() throws Exception {
		ProductUploadModel productUploadModel = new ProductUploadModel();
		int fkProductId = 1;
		int fileid = 2;
		productUploadModel.setFkProductId(fkProductId);
		productUploadModel.setAttachmentId(fileid);
		response = new Response();
		response.setMessage("product File Deleted successfully");
		response.setStatus(1);

		when(service.deleteProductFiles(Mockito.any(ProductUploadModel.class))).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product/deleteproductfiles")
				.param("fkProductId", "1").param("fileId", "1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(1)))
				.andExpect(jsonPath("$.message", is("product File Deleted successfully")));
	}

	@Test
	public void deleteProductFilesFail() throws Exception {
		ProductUploadModel productUploadModel = new ProductUploadModel();
		response = new Response();
		response.setMessage("product File Deletion Failed");
		response.setStatus(0);

		when(service.deleteProductFiles(Mockito.any(ProductUploadModel.class))).thenReturn(response);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product/deleteproductfiles")
				.param("fkProductId", "1").param("fileId", "1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(0)))
				.andExpect(jsonPath("$.message", is("product File Deletion Failed")));
	}
	@Test
	public void addProductEmptyValidation() throws Exception {
		String product_name="";
		String product_desc="";
		int fk_product_line=0;
		int  fk_product_owner=0;
	
	    response = new Response();
	    if ( product_name.equals("") || product_name.trim().equals("")  || product_desc.equals("") || product_desc.trim().equals("") || fk_product_line == 0 || fk_product_owner == 0) {
			response.setStatus(0);
			response.setMessage(environment.getProperty("app.allFieldsManditory"));
			
		}
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/create")
				.param("product_name", product_name).param("product_desc", product_desc).param("fk_product_line","")
				.param("fk_product_owner", "")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.status", is(response.getStatus())))
				.andExpect(jsonPath("$.message", is(response.getMessage())));

	}
	@Test
	public void updateProductEmptyValidation() throws Exception {
		String product_name="";
		String product_desc="";
		int fk_product_line=0;
		int  fk_product_owner=0;
		int product_id=2;
	
	    response = new Response();
	    if ( product_name.equals("") || product_name.trim().equals("")  || product_desc.equals("") || product_desc.trim().equals("") || fk_product_line == 0 || fk_product_owner == 0) {
			response.setStatus(0);
			response.setMessage(environment.getProperty("app.allFieldsManditory"));
			
		}
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/update")
				.param("product_id", "2")
				.param("product_name", product_name).param("product_desc", product_desc).param("fk_product_line","")
				.param("fk_product_owner", "")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.status", is(response.getStatus())))
				.andExpect(jsonPath("$.message", is(response.getMessage())));

	}
	@Test
	public void addProductNameLengthValidation() throws Exception {
		
		String product_name="sjdfksfkdsfksdgsd"
				+ "sdfsdfsdfdsgfsdgsdgfdsgfdsgkfdshgjfdsg"
				+ "sfsdgfdgfdgfdg"
				+ "sdfsdgfdsgfdgfd"
				+ "sdfsdfgdsggdfgdfgdf"
				+ "sfsdgfdgfdgfdgdf";
		String product_desc="product_desc..";
		int fk_product_line=0;
		int  fk_product_owner=0;
	
	    response = new Response();
	    if (product_name.length() > 60) {
			response.setMessage(environment.getProperty("app.productNameLong"));
			
		}
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/create")
				.param("product_name", product_name).param("product_desc", product_desc).param("fk_product_line","3")
				.param("fk_product_owner", "4")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.status", is(response.getStatus())))
				.andExpect(jsonPath("$.message", is(response.getMessage())));

	}
	@Test
	public void updateProductNameLengthValidation() throws Exception {
		
		String product_name="sjdfksfkdsfksdgsd"
				+ "sdfsdfsdfdsgfsdgsdgfdsgfdsgkfdshgjfdsg"
				+ "sfsdgfdgfdgfdg"
				+ "sdfsdgfdsgfdgfd"
				+ "sdfsdfgdsggdfgdfgdf"
				+ "sfsdgfdgfdgfdgdf";
		String product_desc="product_desc..";
	
	    response = new Response();
	    if (product_name.length() > 60) {
			response.setMessage(environment.getProperty("app.productNameLong"));
			
		}
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/update")
				.param("product_id", "2")
				.param("product_name", product_name).param("product_desc", product_desc).param("fk_product_line","3")
				.param("fk_product_owner", "4")
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.status", is(response.getStatus())))
				.andExpect(jsonPath("$.message", is(response.getMessage())));

	}
	@Test
	public void addProductDescLengthValidation() throws Exception {
		
		String product_name="productName";
		
		String product_desc="sadsadsdsads88888888899988"
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
				+ "ksafskdfsdhfsdfsdfskdffghfgjgfjghjgjsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "fghffghfghfhfdhfdhfdhfhjfjfgsdfdszvxfcnhgfjgfjfgdgdgdhgfdh"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfsgfhfhfdhfdhfdghkdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsgfhgfhfghfdhjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitifghfghfhyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskgfhgfhgfhgfhgfhgfhdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjcfgffghfhghdsiuewyreututrewtuewddddddddd";
		
	String fk_product_owner="4";
	String fk_product_line="3";
	    response = new Response();
	    response.setProductId(1);
		if (product_desc.length() > 1000) {
			response.setMessage(environment.getProperty("app.productDescLong"));
			
		}
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/create")
				.param("product_name", product_name).param("product_desc", product_desc).param("fk_product_line",fk_product_line)
				.param("fk_product_owner", fk_product_owner)
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.status", is(response.getStatus())))
				.andExpect(jsonPath("$.message", is(response.getMessage())));

	}
	@Test
	public void updateProductDescLengthValidation() throws Exception {
		
		String product_name="productName";
		
		String product_desc="sadsadsdsads88888888899988"
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
				+ "ksafskdfsdhfsdfsdfskdffghfgjgfjghjgjsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "fghffghfghfhfdhfdhfdhfhjfjfgsdfdszvxfcnhgfjgfjfgdgdgdhgfdh"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfsgfhfhfdhfdhfdghkdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsgfhgfhfghfdhjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjghdsiuewyreututrewtuewddddddddd"
				+ "dgfdgdddddddddddddddddddddddddddddddhjgjgjjjjjjjjjjjjhtyutyutututgfhgjgkjlkjl"
				+ "jgjhgjhyiyutyrdetersrdtfyuuutytdtdttytuyitutdyfgyu"
				+ "guytuyiuyiyiyiuytuturutitifghfghfhyiutftyuytiuytiyi"
				+ "iuyiuyyuiyiiuyuytytuyfgtfsasdfgwertyuioiyutresdfghdddd"
				+ "ksafskdfsdhfsdfsdfskgfhgfhgfhgfhgfhgfhdfsdhfs"
				+ "sdfsdfdsfsdfsdkfdskhfkdsjfskdhskdjfdsfsd"
				+ "sdfsdkfdsfkjsdghsdhgsdjcfgffghfhghdsiuewyreututrewtuewddddddddd";
		
	String fk_product_owner="4";
	String fk_product_line="3";
	    response = new Response();
	    response.setProductId(1);
		if (product_desc.length() > 1000) {
			response.setMessage(environment.getProperty("app.productDescLong"));
			
		}
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/product/update")
				.param("product_id", "2")
				.param("product_name", product_name).param("product_desc", product_desc).param("fk_product_line",fk_product_line)
				.param("fk_product_owner", fk_product_owner)
				.contentType(MediaType.MULTIPART_FORM_DATA)).andDo(print()).andExpect(jsonPath("$.status", is(response.getStatus())))
				.andExpect(jsonPath("$.message", is(response.getMessage())));

	}
	@Test
	public void deleteProductEmptyValidation() throws Exception {
		String fileId="";
		String fkProductId="";
		response = new Response();
		if(fileId.equals("")|| fkProductId.equals("")){
	    	response.setStatus(0);
			response.setMessage(environment.getProperty("app.allFieldsManditory"));
	    }
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product/deleteproductfiles").param("fkProductId",fkProductId ).param("fileId", fileId)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(jsonPath("$.status", is(response.getStatus())))
				.andExpect(jsonPath("$.message", is(response.getMessage())));

	}

}
