package com.nexiilabs.autolifecycle.productline;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;

@RestController
@RequestMapping("/productline")
// @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class ProductLineController {

	@Autowired
	private ProductLineService service;
	@Autowired
	Environment environment;
	@Autowired
	AttachmentUploadController attachmentUploadController;

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/Json")
	public Response createProductLine(@RequestParam("product_line_name") String product_line_name,
			@RequestParam("product_line_desc") String product_line_desc,
			@RequestParam("files") MultipartFile[] productUploads) {
		Response response = new Response();

		String UPLOADED_FOLDER = null;
		String PRODUCT_FOLDER = environment.getProperty("app.productlineuploaddir");
		String fileName = null;
		String filePath = null;
		int productlineId = 0;
		ProductLineUploadModel uploadModel = null;
		try {

			if (product_line_name.equals(null) || product_line_name.trim().equals("") || product_line_desc.equals(null)
					|| product_line_desc.trim().equals("")) {
				response.setStatus(0);
				response.setMessage(environment.getProperty("app.allFieldsManditory"));
				return response;
			}
			if (product_line_name.length() <=4 || product_line_name.length() > 60) {
				response.setMessage(environment.getProperty("app.productlineNameLong"));
				return response;
			}
			if (product_line_desc.length() > 1000) {
				response.setMessage(environment.getProperty("app.productlineDescLong"));
			}
			response = service.addProductline(product_line_name, product_line_desc);
			productlineId = response.getProductlineId();
			if (response.getStatus() == 1) {
				if (productUploads.length == 0) {
					return response;
				} else {
					ResponseDTO responseDTO = attachmentUploadController.createproductLineFileUpload(productUploads,
							productlineId);
					response.setStatus(responseDTO.getStatusCode());
					response.setMessage(responseDTO.getMessage());
				}
			} else if (response.getStatus() == 2) {
				response.setStatus(2);
				response.setMessage(" Productline alredy exist in database");

				// return response;
			} else {
				response.setStatus(0);
				response.setMessage("Productline creation failed");
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			response.setStatusCode(0);
			response.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode(0);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/Json")
	public Response updateProductLine(@RequestParam(value = "product_line_id", defaultValue = "0") int product_line_id,
			@RequestParam("product_line_name") String product_line_name,
			@RequestParam("product_line_desc") String product_line_desc,
			@RequestParam("files") MultipartFile[] productUploads, HttpServletRequest request,
			HttpServletResponse res) {
		Response responseDTO = new Response();
		ProductLineUploadModel uploadModel = null;
		String UPLOADED_FOLDER = null;
		String PRODUCT_FOLDER = environment.getProperty("app.productlineuploaddir");
		String fileName = null;
		String filePath = null;
		try {
			if (product_line_id == 0 || product_line_name.equals(null) || product_line_name.trim().equals("")
					|| product_line_desc.equals(null) || product_line_desc.trim().equals("")) {
				responseDTO.setStatus(0);
				responseDTO.setMessage("All Input fields are manditory.");
				return responseDTO;
			}
			if (product_line_name.length() <=4 || product_line_name.length() > 60) {
				responseDTO.setMessage(environment.getProperty("app.productlineNameLong"));
				return responseDTO;
			}
			if (product_line_desc.length() > 1000) {
				responseDTO.setMessage(environment.getProperty("app.productlineDescLong"));
			}

			responseDTO = service.updateProductLine(product_line_id, product_line_name, product_line_desc);
			if (responseDTO.getStatus() == 1 || responseDTO.getMessage().equals("No changes found")) {
				if (productUploads.length == 0) {
					return responseDTO;
				} else {
					ResponseDTO responseDTO1 = attachmentUploadController.updateproductLineFileUpload(productUploads,
							product_line_id);

					if (responseDTO1.getMessage().equals("No changes found")) {
						return responseDTO;
					} else {
						responseDTO.setStatus(responseDTO1.getStatusCode());
						responseDTO.setMessage(responseDTO1.getMessage());
						return responseDTO;
					}
				}

			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/Json")
	public List<ProductLinePOJO> listProductLine() {
		return service.listofProductLines();
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetails", method = RequestMethod.GET, produces = "application/Json")
	public List<ProductLinePOJO> listofSpecProductLine(
			@RequestParam(value = "product_line_id", defaultValue = "0") int product_line_id) {
		List<ProductLinePOJO> productLinePOJO = new ArrayList<>();
		try {
			if (product_line_id != 0) {

				return service.listOfSpecificProductLine(product_line_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productLinePOJO;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getNamesbyId", method = RequestMethod.GET, produces = "application/Json")
	public List<ProductLinePOJO> listofProductNamesByproductlineId(
			@RequestParam(value = "product_line_id", defaultValue = "0") int product_line_id) {
		List<ProductLinePOJO> productLinePOJO = new ArrayList<>();
		try {
			if (product_line_id != 0) {
				return service.listOfProductNamesByProductlineId(product_line_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productLinePOJO;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/Json")
	public Response deletePrdLine(@RequestParam(value = "product_line_id", defaultValue = "0") int product_line_id) {
		Response response = new Response();
		try {
			if (product_line_id == 0) {
				response.setMessage(environment.getProperty("app.allFieldsManditory"));
				response.setStatus(0);
				return response;
			}
			int roweffected = service.deleteProductLine(product_line_id);
			if (roweffected == 1) {
				response.setStatus(1);
				response.setMessage("ProductLine deleted successfully");
				return response;

			} else if (roweffected == 2) {
				response.setStatus(2);
				response.setMessage("ProductLine not deleted.");
				return response;
			}
			else if (roweffected == 3) {
				response.setStatus(3);
				response.setMessage("ProductLine Have Products First delete The Products And Then Delete ProductLine.");
				return response;
			}else {
				response.setStatus(0);
				response.setMessage("ProductLine not found.empty fields exist");
				return response;
			}

		} catch (TransactionSystemException e) {
			e.printStackTrace();
			response.setStatusCode(0);
			response.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode(0);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/deleteProductLineFiles", method = RequestMethod.POST)
	public Response deleteProductLineFiles(
			@RequestParam(value = "product_line_id", defaultValue = "0") int product_line_id,
			@RequestParam(value = "fileId", defaultValue = "0") int fileId) {
		Response responseDTO = new Response();
		List<ProductLineUploadModel> uploads = null;
		ProductLineUploadModel productLineUploadModel = null;
		try {
			if (product_line_id == 0 || fileId == 0) {
				responseDTO.setStatus(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			productLineUploadModel = new ProductLineUploadModel();
			productLineUploadModel.setAttachmentId(fileId);
			productLineUploadModel.setFk_product_line_id(product_line_id);
			productLineUploadModel.setDeletedBy(1);
			responseDTO = service.deleteProductLineFiles(productLineUploadModel);
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;

	}
}
