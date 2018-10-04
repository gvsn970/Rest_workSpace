package com.nexiilabs.autolifecycle.product;

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

import com.nexiilabs.autolifecycle.jira.BoardModel;
import com.nexiilabs.autolifecycle.jira.JiraController;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")

public class ProductController {
	@Autowired
	private ProductService service;
	@Autowired
	private JiraController jiraController;
	@Autowired
	private AttachmentUploadController attachmentUploadController;
	@Autowired
	Environment environment;

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/Json")
	public Response createProduct(@RequestParam("product_name") String product_name,
			@RequestParam("product_desc") String product_desc,
			@RequestParam(value = "fk_product_line", defaultValue = "0") int fk_product_line,
			// @RequestParam(value = "fk_product_owner", defaultValue = "0") int
			// fk_product_owner,
			@RequestParam(value = "board_id", defaultValue = "0") int boardId,
			@RequestParam(value = "jira_map", defaultValue = "false") boolean jira_Map,
			// @RequestParam(value = "mapingvalue", defaultValue = "0") int mapingvalue,
			@RequestParam(value = "fk_user_id", defaultValue = "0") int fk_user_id,
			// @RequestParam(value = "project_id", defaultValue = "0") String projectId,
			// @RequestParam("board_name") String boardName,
			// @RequestParam("project_name") String projectName,
			@RequestParam("files") MultipartFile[] productUploads, HttpServletRequest request,
			HttpServletResponse res) {
		Response response = new Response();
		com.nexiilabs.autolifecycle.productline.Response res1 = new com.nexiilabs.autolifecycle.productline.Response();

		Product product = null;
		int productId = 0;
		try {
			if (product_name.equals(null) || product_name.trim().equals("") || product_desc.equals(null)
					|| product_desc.trim().equals("") || fk_product_line == 0) {
				response.setStatus(0);
				response.setMessage(environment.getProperty("app.allFieldsManditory"));
				return response;
			}
			if (product_name.length() <= 4 || product_name.length() >= 60) {
				response.setMessage(environment.getProperty("app.productNameLong"));
				return response;
			}
			if (product_desc.length() >= 1000) {
				response.setMessage(environment.getProperty("app.productDescLong"));
				return response;
			}
			product = new Product();
			product.setProduct_name(product_name);
			product.setProduct_desc(product_desc);
			product.setFk_product_line(fk_product_line);
			product.setFk_product_owner(fk_user_id);
			response = service.addProduct(product);
			int productStatusId = response.getStatus();
			productId = response.getProductId();
			String projectName = null;
			String projectId = null;
			String boardName = null;
			if (response.getStatus() == 1) {
				res1 = jiraController.checkboxforJiraCredentialsTest(fk_user_id);
				if (res1.getStatusCode() == 1) {

					if (jira_Map == true) {
						if (boardId == 0) {
							String productNameUpperCase = product_name.toUpperCase().trim();
							String productNameUpperCaseForKey = product_name.toUpperCase().trim().replaceAll(" ", "");
							String key = productNameUpperCaseForKey.substring(0,
									Math.min(productNameUpperCaseForKey.length(),
											(productNameUpperCaseForKey.length() / 2) + 1));
							if (key.length() >= 10) {
								key = key.substring(0, Math.min(key.length(), (key.length() / 2)));
							}
							System.err.println("key::::::::" + key);
							System.err.println("******productNameUpperCaseForKey" + productNameUpperCaseForKey);
							res1 = jiraController.createProject("PROJECT_LEAD", productNameUpperCase, product_desc, key,
									"admin", "com.pyxis.greenhopper.jira:gh-scrum-template", "software", fk_user_id,
									productId);
							if (productStatusId == 1 && res1.getStatusCode() == 1) {
								response.setMessage("Product Created Successfully. " + res1.getMessage());
								response.setStatus(res1.getStatusCode());
								return response;
							} else {
								response.setMessage("Product Created Successfully. " + res1.getMessage());
								response.setStatus(2);
							}

						} else {

							res1 = jiraController.isProductNameConfigurationwithJiraProject(product_name,
									fk_product_line);
							if (res1.getStatusCode() == 1) {

								List<BoardModel> boards = (List<BoardModel>) jiraController.getBoards(fk_user_id);
								for (BoardModel b : boards) {
									if (b.getBoardId() == boardId) {
										boardName = b.getBoardName();
										projectName = b.getProjectName();
										projectId = b.getProjectId();
									}
								}

								res1 = jiraController.boardassignedtoAutoProduct(productId, boardId, projectName,
										projectId, boardName);
								response.setMessage("Product Created Successfully. " + res1.getMessage());
								response.setStatus(res1.getStatusCode());
								// return response;
							} else {
								response.setMessage("Product Created Successfully. " + res1.getMessage());
								response.setStatus(2);
								// return response;
							}
							return response;
						}

					}

				} else if (res1.getStatusCode() == 0) {
					response.setMessage("Product Created Successfully. " + res1.getMessage());
					response.setStatus(2);
				}

				if (productUploads.length == 0) {
					return response;
				} else {

					attachmentUploadController.productFileUpload(productUploads, response.getProductId());
				}
				response.setStatus(1);
				response.setMessage("Product Created Successfully.");
			} else if (response.getStatus() == 2) {
				response.setStatus(2);
				response.setMessage("Product Already exists");
			} else {
				response.setStatus(0);
				response.setMessage("Product creation failed");
			}

		} catch (TransactionSystemException e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/Json")
	public List<ProductListDTO> getAll() {
		return service.allproducts();
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/Json")
	public Response updateProduct(@RequestParam("product_id") int product_id,
			@RequestParam("product_name") String product_name, @RequestParam("product_desc") String product_desc,
			@RequestParam(value = "fk_product_line", defaultValue = "0") int fk_product_line,
			@RequestParam(value = "fk_product_owner", defaultValue = "0") int fk_product_owner,
			@RequestParam("files") MultipartFile[] productUploads, HttpServletRequest request,
			HttpServletResponse res) {
		Response response = new Response();
		ResponseDTO responseDTO = new ResponseDTO();
		Product product = null;

		try {
			if (product_name.equals(null) || product_name.trim().equals("") || product_desc.equals(null)
					|| product_desc.trim().equals("") || fk_product_line == 0) {
				response.setStatus(0);
				response.setMessage(environment.getProperty("app.allFieldsManditory"));
				return response;
			}
			if (product_name.length() <= 4 || product_name.length() > 60) {
				response.setMessage(environment.getProperty("app.productNameLong"));
				return response;
			}
			if (product_desc.length() > 1000) {
				response.setMessage(environment.getProperty("app.productDescLong"));
				return response;
			}
			response = service.updateProduct(product_id, product_name, product_desc, fk_product_line, fk_product_owner);
			if (response.getStatus() == 1 || response.getMessage().equals("No Changes Found")) {
				if (productUploads.length == 0) {
					return response;
				} else {
					responseDTO = attachmentUploadController.updateProductFileUpload(productUploads, product_id);
					if (responseDTO.getMessage().equals("No changes found")) {
						return response;
					} else {
						response.setMessage(responseDTO.getMessage());
						response.setStatus(responseDTO.getStatusCode());
						return response;
					}

				}
			}

		} catch (TransactionSystemException e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetails", method = RequestMethod.GET, produces = "application/Json")
	public List<ProductListDTO> getDetails(@RequestParam("product_id") int product_id) {
		return service.getDetails(product_id);
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getProductDetails", method = RequestMethod.GET, produces = "application/Json")
	public List<ProductListDTO> getProductDetails(@RequestParam("product_id") int product_id) {
		return service.getProductDetails(product_id);
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/Json")
	public ResponseDTO deleteproduct(@RequestParam(value = "product_id", defaultValue = "0") int product_id) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			if (product_id == 0) {
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				responseDTO.setStatusCode(0);
				return responseDTO;
			}
			int rowEffected = service.deleteproduct(product_id);

			if (rowEffected == 1) {
				responseDTO.setStatusCode(1);
				responseDTO.setMessage("Product Deleted successfully");
				return responseDTO;
			} else if (rowEffected == 2) {
				responseDTO.setStatusCode(2);
				responseDTO.setMessage("Product not deleted.");
				return responseDTO;
			} else if (rowEffected == 3) {
				responseDTO.setStatusCode(3);
				responseDTO.setMessage("Product Have Releases First delete The Releases And Then Delete Product.");
				return responseDTO;
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
	@RequestMapping(value = "/deleteproductfiles", method = RequestMethod.POST)
	public Response deleteProductFiles(@RequestParam(value = "fkProductId", defaultValue = "0") int fkProductId,
			@RequestParam(value = "fileId", defaultValue = "0") int fileId) {
		Response response = new Response();
		List<ProductUploadModel> uploads = null;
		ProductUploadModel productUploadModel = null;
		try {
			// System.err.println("fkProductId::::::::::::::::::::"+fkProductId);
			if (fkProductId == 0 || fileId == 0) {
				response.setStatus(0);
				response.setMessage(environment.getProperty("app.allFieldsManditory"));
				return response;
			}
			productUploadModel = new ProductUploadModel();
			productUploadModel.setAttachmentId(fileId);
			productUploadModel.setFkProductId(fkProductId);
			productUploadModel.setDeletedBy(1);
			response = service.deleteProductFiles(productUploadModel);
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllUnmapedProducts", method = RequestMethod.GET, produces = "application/Json")
	public List<ProductListDTO> getAllUnMapedProducts() {
		List<ProductListDTO> unMapedProductsList = null;
		try {
			unMapedProductsList = service.unMapedProductswithJiraBoards();
			if (unMapedProductsList.size() == 0) {
				return unMapedProductsList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unMapedProductsList;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllmapedProducts", method = RequestMethod.GET, produces = "application/Json")
	public List<ProductListDTO> getAllMapedProducts() {
		List<ProductListDTO> MapedProductsList = null;
		try {
			MapedProductsList = service.MapedProductswithJiraBoards();
			if (MapedProductsList.size() == 0) {
				return MapedProductsList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MapedProductsList;
	}

}
