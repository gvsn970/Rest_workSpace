package com.nexiilabs.autolifecycle.releases;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexiilabs.autolifecycle.jira.JiraController;
import com.nexiilabs.autolifecycle.product.Product;
import com.nexiilabs.autolifecycle.productline.Response;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;

@RestController
@RequestMapping("/releases")
public class ReleaseController {
	private static final Logger log = LogManager.getLogger(ReleaseController.class);
	@Autowired
	ReleaseService releaseService;
	@Autowired
	AttachmentUploadController attachmentUploadController;
	@Autowired
	Environment environment;
	@Autowired
	private JiraController jiraController;

	/*
	 * @RequestMapping("/welcome") public String getMsg() { return "Welcome"; }
	 */
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseDTO addRelease(@RequestParam("releaseName") String releaseName,
			@RequestParam("releaseDesc") String releaseDesc,
			@RequestParam(value = "fkProductId", defaultValue = "0") int fkProductId,
			@RequestParam("internalReleaseDate") String internalReleaseDate,
			@RequestParam("externalReleaseDate") String externalReleaseDate,
			// @RequestParam(value = "fkReleaseOwner", defaultValue = "0") int
			// fkReleaseOwner,
			@RequestParam(value = "fkStatusId", defaultValue = "0") int fkStatusId,
			// @RequestParam(value = "board_id", defaultValue = "0") int boardId,
			@RequestParam(value = "sprint_id", defaultValue = "0") int sprintId,
			@RequestParam(value = "jira_map", defaultValue = "false") boolean jira_Map,
			// @RequestParam(value = "mapingvalue", defaultValue = "0") int mapingvalue,
			@RequestParam(value = "fk_user_id", defaultValue = "0") int fk_user_id,
			@RequestParam("files") MultipartFile[] productReleaseUploads) {
		ResponseDTO responseDTO = new ResponseDTO();
		Response response = new Response();
		ReleaseModel releaseModel = null;
		int releaseId = 0;
		// int userId = 0;
		try {
			if (releaseName.equals(null) || releaseName.trim().equals("") || releaseDesc.equals(null)
					|| releaseDesc.trim().equals("") || fkProductId == 0 || internalReleaseDate.equals(null)
					|| internalReleaseDate.trim().equals("") || externalReleaseDate.equals(null)
					|| externalReleaseDate.trim().equals("") || fk_user_id == 0 || fkStatusId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			if (releaseName.length() <= 4 || releaseName.length() >= 60) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releaseNameLong"));
				return responseDTO;
			}
			if (releaseDesc.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releaseDescLong"));
				return responseDTO;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date internalDate1 = sdf.parse(internalReleaseDate);
			Date externalReleaseDate1 = sdf.parse(externalReleaseDate);

			java.util.Date todaydate = new java.util.Date();
			System.out.println(
					"todaydate:" + todaydate.toString() + ".  internalReleaseDate:" + internalReleaseDate.toString());
			if (internalDate1.after(todaydate)) {
				if (externalReleaseDate1.after(internalDate1)) {
					releaseModel = new ReleaseModel();
					releaseModel.setReleaseName(releaseName);
					releaseModel.setReleaseDescription(releaseDesc);
					releaseModel.setReleaseDateExternal(externalReleaseDate);
					releaseModel.setReleaseDateInternal(internalReleaseDate);
					releaseModel.setFkProductId(fkProductId);
					releaseModel.setFkReleaseOwner(fk_user_id);
					releaseModel.setFkStatusId(fkStatusId);
					releaseModel.setCreatedBy(1);
					responseDTO = releaseService.addRelease(releaseModel);
					// System.err.println("responseDTO::::::::::::"+responseDTO.toString());
					if (responseDTO.getStatusCode() == 1) {
						releaseId = responseDTO.getReleaseId();
						response = jiraController.checkboxforJiraCredentialsTest(fk_user_id);
						if (response.getStatusCode() == 1) {
							if (jira_Map) {
								Response res = new Response();
								if (sprintId > 0) {
									res = jiraController.assignSprinttoRelease(fkProductId, releaseId, sprintId,
											releaseName);
									if (res.getStatusCode() > 0) {
										responseDTO.setStatusCode(res.getStatusCode());
										responseDTO.setMessage("Release created successfully." + res.getMessage());
									} else {

										responseDTO.setStatusCode(2);
										responseDTO.setMessage("Release created successfully." + res.getMessage());

									}
									// System.err.println("inmap::::::::::"+res.toString());
								} else {
									long millis = System.currentTimeMillis();
									java.sql.Date date = new java.sql.Date(millis);
									List<Product> productlist = jiraController
											.getJiraBoardDetailsByAutoProductId(fkProductId);
									System.out.println(" *******product list:" + productlist.toString());
									long boardId = productlist.get(0).getFk_jira_boardid();
									res = jiraController.createSprint(releaseName, boardId, date.toString(),
											internalReleaseDate, releaseDesc, fk_user_id, fkProductId, releaseId);
									if (res.getStatusCode() > 0) {
										responseDTO.setStatusCode(res.getStatusCode());
										responseDTO.setMessage("Release created successfully." + res.getMessage());
									} else {
										responseDTO.setStatusCode(2);
										responseDTO.setMessage("Release created successfully." + res.getMessage());
									}
									// System.err.println("in create new::::::::::"+res.getMessage());
								}
							}

						} else if (response.getStatusCode() == 0) {
							responseDTO.setStatusCode(2);
							responseDTO.setMessage("Release created successfully." + response.getMessage());
						}
						if (productReleaseUploads.length == 0) {
							return responseDTO;
						} else {
							responseDTO = attachmentUploadController.createreleaseFileUpload(productReleaseUploads,
									releaseId);
						}

						responseDTO.setStatusCode(1);
						responseDTO.setMessage("Release created successfully.");

					} else if (responseDTO.getStatusCode() == 2) {
						responseDTO.setStatusCode(2);
						responseDTO.setMessage("Release Already exists");
					} /*
						 * else if (responseDTO.getStatusCode() == 3) { responseDTO.setStatusCode(3);
						 * responseDTO.
						 * setMessage("Release Internal Date Should be Greater Then previous Release External Date"
						 * ); }
						 */else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("Release creation failed");
					}

				} else {
					responseDTO.setStatusCode(0);
					responseDTO.setMessage(
							"External Release Date should be greater than Internal ReleaseDate.Please Choose another ExternalDate.");
				}

			} else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(
						"Internal Release Date should be greater than CurrentDate.Please Choose another InternalDate.");
			}

		} catch (TransactionSystemException e) {
			System.err.println("in catch:::::::::::::::::::");
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured.");
		}
		return responseDTO;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseDTO updateRelease(@RequestParam(value = "releaseId", defaultValue = "0") int releaseId,
			@RequestParam("releaseName") String releaseName, @RequestParam("releaseDesc") String releaseDesc,
			@RequestParam(value = "fkProductId", defaultValue = "0") int fkProductId,
			@RequestParam("internalReleaseDate") String internalReleaseDate,
			@RequestParam("externalReleaseDate") String externalReleaseDate,
			@RequestParam(value = "fkReleaseOwner", defaultValue = "0") int fkReleaseOwner,
			@RequestParam(value = "fkStatusId", defaultValue = "0") int fkStatusId,
			@RequestParam("files") MultipartFile[] productReleaseUploads) {
		ResponseDTO responseDTO = new ResponseDTO();
		ResponseDTO responseDTO1 = new ResponseDTO();
		ReleaseModel releaseModel = null;
		// int userId = 0;
		try {
			if (releaseName.equals(null) || releaseName.trim().equals("") || releaseDesc.equals(null)
					|| releaseDesc.trim().equals("") || fkProductId == 0 || internalReleaseDate.equals(null)
					|| internalReleaseDate.trim().equals("") || externalReleaseDate.equals(null)
					|| externalReleaseDate.trim().equals("") || fkReleaseOwner == 0 || fkStatusId == 0
					|| releaseId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			if (releaseName.length() <= 4 || releaseName.length() > 60) {
				responseDTO.setMessage(environment.getProperty("app.releaseNameLong"));
				return responseDTO;
			}
			if (releaseDesc.length() > 1000) {
				responseDTO.setMessage(environment.getProperty("app.releaseDescLong"));
				return responseDTO;
			}
			releaseModel = new ReleaseModel();
			releaseModel.setReleaseId(releaseId);
			releaseModel.setReleaseName(releaseName);
			releaseModel.setReleaseDescription(releaseDesc);
			releaseModel.setReleaseDateExternal(externalReleaseDate);
			releaseModel.setReleaseDateInternal(internalReleaseDate);
			releaseModel.setFkProductId(fkProductId);
			releaseModel.setFkReleaseOwner(fkReleaseOwner);
			releaseModel.setFkStatusId(fkStatusId);
			releaseModel.setUpdatedBy(1);
			responseDTO = releaseService.updateRelease(releaseModel);
			if (responseDTO.getStatusCode() == 1 || responseDTO.getMessage().equals("No Changes Found")) {
				if (productReleaseUploads.length == 0) {
					return responseDTO;
				} else {
					responseDTO1 = attachmentUploadController.updatereleaseFileUpload(productReleaseUploads, releaseId);
					if (responseDTO1.getMessage().equals("No changes found")) {
						return responseDTO;
					} else {
						return responseDTO1;
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
			responseDTO.setMessage("Exception occured.");
		}
		return responseDTO;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseDTO deleteRelease(@RequestParam(value = "releaseId", defaultValue = "0") int releaseId) {
		ResponseDTO responseDTO = new ResponseDTO();
		ReleaseModel releaseModel = null;
		// int userId = 0;
		try {
			if (releaseId != 0) {
				releaseModel = new ReleaseModel();
				releaseModel.setReleaseId(releaseId);
				releaseModel.setDeletedBy(1);
				releaseModel.setDeleteStatus(1);
				responseDTO = releaseService.deleteRelease(releaseModel);
			} else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
			}

		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetails", method = RequestMethod.GET)
	public List<ReleaseListModel> getReleaseDetails(
			@RequestParam(value = "releaseId", defaultValue = "0") int releaseId) {
		List<ReleaseListModel> releaseModel = null;
		try {
			if (releaseId != 0) {
				releaseModel = releaseService.getReleaseDetails(releaseId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releaseModel;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllReleases", method = RequestMethod.GET)
	public List<ReleaseListModel> getAllReleases() {
		List<ReleaseListModel> releaseModel = null;
		// int userId = 0;
		try {
			releaseModel = releaseService.getAllReleases();
			if (releaseModel.size() == 0) {
				return releaseModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releaseModel;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/deleteReleaseFiles", method = RequestMethod.POST)
	public ResponseDTO deleteReleaseFiles(@RequestParam(value = "fkReleaseId", defaultValue = "0") int fkReleaseId,
			@RequestParam(value = "fileId", defaultValue = "0") int fileId) {
		ResponseDTO responseDTO = new ResponseDTO();
		List<ProductReleaseUploadModel> uploads = null;
		ProductReleaseUploadModel productReleaseUploadModel = null;
		try {
			if (fkReleaseId == 0 || fileId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			productReleaseUploadModel = new ProductReleaseUploadModel();
			productReleaseUploadModel.setAttachmentId(fileId);
			productReleaseUploadModel.setFkReleaseId(fkReleaseId);
			productReleaseUploadModel.setDeletedBy(1);
			responseDTO = releaseService.deleteReleaseFiles(productReleaseUploadModel);
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception Occured");
		}
		return responseDTO;

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/copy", method = RequestMethod.POST)
	public ResponseDTO copyExistingRelease(@RequestParam(value = "releaseId", defaultValue = "0") int releaseId,
			@RequestParam(value = "productId", defaultValue = "0") int productId) {
		ResponseDTO responseDTO = new ResponseDTO();
		ReleaseListModel releaseListModel = new ReleaseListModel();
		ReleaseModel releaseModel = new ReleaseModel();
		try {
			if (releaseId == 0 || productId == 0) {
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				responseDTO.setStatusCode(0);
				return responseDTO;
			}
			releaseListModel.setReleaseId(releaseId);
			ReleaseListModel release = releaseService.getReleaseDetailsForCopy(releaseListModel);
			if (release != null) {
				releaseModel.setReleaseName(release.getReleaseName());
				releaseModel.setFkProductId(productId);
				releaseModel.setFkStatusId(1);
				releaseModel.setReleaseDateExternal(release.getReleaseDateExternal());
				releaseModel.setReleaseDateInternal(release.getReleaseDateInternal());
				releaseModel.setReleaseDescription(release.getReleaseDescription());
				releaseModel.setFkReleaseOwner(release.getFkReleaseOwner());
				responseDTO = releaseService.addRelease(releaseModel);
				if (responseDTO.getStatusCode() == 1) {
					responseDTO.setMessage("Release copied successfully");
					responseDTO.setStatusCode(1);
				} else if (responseDTO.getStatusCode() == 2) {
					responseDTO.setMessage("Release Already Exists");
					responseDTO.setStatusCode(0);
				} else {
					responseDTO.setMessage("Release copying Failed");
					responseDTO.setStatusCode(0);
				}
			} else {
				responseDTO.setMessage("Release copying Failed");
				responseDTO.setStatusCode(0);
			}

		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured.");
		}
		return responseDTO;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public ResponseDTO updateStatus(@RequestParam(value = "releaseId", defaultValue = "0") int releaseId,
			@RequestParam(value = "statusId", defaultValue = "0") int statusId) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			if (releaseId == 0 || statusId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			responseDTO = releaseService.updateStatus(releaseId, statusId);
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception Occured");
		}
		return responseDTO;

	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllReleaseStatus", method = RequestMethod.GET)
	public List<ReleaseListModel> getAllReleasesStatus() {
		List<ReleaseListModel> releaseModel = null;
		// int userId = 0;
		try {
			releaseModel = releaseService.getAllReleasesStatus();
			if (releaseModel.size() == 0) {
				return releaseModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releaseModel;
	}
}
