package com.nexiilabs.autolifecycle.features;

import java.util.List;

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
import com.nexiilabs.autolifecycle.jira.JiraReleaseSprintDTO;
import com.nexiilabs.autolifecycle.jira.JiraService;
import com.nexiilabs.autolifecycle.product.Product;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;

@RestController
@RequestMapping("/features")
public class FeaturesController {
	private static final Logger log = LogManager.getLogger(FeaturesController.class);
	@Autowired
	FeaturesService featuresService;
	@Autowired
	JiraService jiraService;
	@Autowired
	private JiraController jiraController;
	@Autowired
	AttachmentUploadController attachmentUploadController;
	@Autowired
	Environment environment;

	/*--------------------------------CREATE FEATURES------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseDTO addFeatures(@RequestParam("feature_name") String featuresName,
			@RequestParam("feature_description") String featureDesc,
			@RequestParam(value = "story_points", defaultValue = "0") int story_points,
			@RequestParam(value = "fk_feature_type_id", defaultValue = "0") int fk_feature_typeId,
			// @RequestParam(value = "fk_feature_status_id", defaultValue = "0") int
			// fk_feature_statusId,
			@RequestParam(value = "fk_product_id", defaultValue = "0") int fk_productId,
			@RequestParam(value = "fk_release_id", defaultValue = "0") int fk_releaseId,
			@RequestParam(value = "jira_map", defaultValue = "false") boolean jira_Map,
			@RequestParam(value = "issue_id", defaultValue = "0") int issueId,
			@RequestParam(value = "fk_user_id", defaultValue = "0") int fk_user_id,
			// @RequestParam(value = "jira_issue_type", defaultValue = "0") String
			// jiraIssueType,
			@RequestParam("files") MultipartFile[] featuresUploads) {
		ResponseDTO responseDTO = new ResponseDTO();
		int featureId = 0;
		FeaturesModel featuresModel = null;

		try {
			if (featuresName.equals(null) || featuresName.trim().equals("") || featureDesc.equals(null)
					|| featureDesc.trim().equals("") || story_points == 0 || fk_feature_typeId == 0
					|| fk_releaseId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			if (story_points > 100 || story_points <= 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("Story Point Should Be 1 to 100");
				return responseDTO;
			}
			if (featuresName.length() <= 4 || featuresName.length() >= 60) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.featuresNameLong"));
				return responseDTO;
			}
			if (featureDesc.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.featuresDescLong"));
				return responseDTO;
			}
			com.nexiilabs.autolifecycle.productline.Response res1 = new com.nexiilabs.autolifecycle.productline.Response();
			featuresModel = new FeaturesModel();
			featuresModel.setFeature_name(featuresName);
			featuresModel.setFeature_description(featureDesc);
			featuresModel.setStory_points(story_points);
			featuresModel.setFk_feature_type_id(fk_feature_typeId);
			featuresModel.setFk_feature_status_id(1);
			featuresModel.setFk_assignment_id(0);
			featuresModel.setIs_backlog(0);
			featuresModel.setAssigned_To(0);
			featuresModel.setFk_release_id(fk_releaseId);
			featuresModel.setFk_product_id(fk_productId);
			featuresModel.setCreated_by(1);
			responseDTO = featuresService.addFeatures(featuresModel);
			int featureStatusId = responseDTO.getStatusCode();
			featureId = responseDTO.getFeature_id();
			List<FeatureslistModel> featuresTypeList = featuresService
					.getFeaturesTypeByFeatureTypeId(fk_feature_typeId);
			String jiraIssueType = featuresTypeList.get(0).getJira_issue_type();
			if (responseDTO.getStatusCode() == 1) {
				res1 = jiraController.checkboxforJiraCredentialsTest(fk_user_id);
				if (res1.getStatusCode() == 1) {
					if (jira_Map == true) {
						if (issueId > 0) {
							featureId = responseDTO.getFeature_id();
							res1 = jiraController.issueassignedtoAutoFeature(featureId, fk_releaseId, issueId,
									jiraIssueType,fk_user_id);
							if (featureStatusId == 1 && res1.getStatusCode() == 1) {
								responseDTO.setStatusCode(res1.getStatusCode());
								responseDTO.setMessage("Feature Created Successfully. " + res1.getMessage());
							} else {
								responseDTO.setStatusCode(2);
								responseDTO.setMessage("Feature Created Successfully. " + res1.getMessage());
							}
						} else {
							List<JiraReleaseSprintDTO> releaselist = jiraService
									.getProductReleaseofJiraByReleaseId(fk_releaseId);

							long SprintId = releaselist.get(0).getFk_jira_sprint_id();
							List<Product> productlist = jiraController.getJiraBoardDetailsByAutoProductId(fk_productId);
							String projectId = productlist.get(0).getFk_jira_projectid();
							String boardName = productlist.get(0).getJiraBoardName().replace(" board", "");

							String featuresNameUpperCase = featuresName.toUpperCase();
							res1 = jiraController.createIssue(featuresNameUpperCase, boardName, jiraIssueType,
									fk_user_id, SprintId, featureId, fk_releaseId,story_points);
							// res1 = jiraController.createIssue(featuresName, projectId, issueType,
							// fk_user_id, sprintId, featureId, releaseId)
							if (featureStatusId == 1 && res1.getStatusCode() == 1) {
								responseDTO.setStatusCode(res1.getStatusCode());
								responseDTO.setMessage("Feature Created Successfully. " + res1.getMessage());
							} else {
								responseDTO.setStatusCode(2);
								responseDTO.setMessage("Feature Created Successfully. " + res1.getMessage());
							}
						}
					}
				} else if (res1.getStatusCode() == 0) {
					responseDTO.setMessage("Feature Created Successfully. " +res1.getMessage());
					responseDTO.setStatusCode(2);
				}
				if (featuresUploads.length == 0) {
					return responseDTO;
				} else {
					responseDTO = attachmentUploadController.createFeaturesFileUpload(featuresUploads, featureId);
				}
				responseDTO.setStatusCode(1);
				responseDTO.setMessage("Feature Created Successfully.");

			} else if (responseDTO.getStatusCode() == 2) {
				responseDTO.setStatusCode(2);
				responseDTO.setMessage("Feature Already exists");
			} else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("Feature creation failed");
			}
			return responseDTO;

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

	/*--------------------------------UPDATE FEATURES------------------------------------------*/

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseDTO updateFeatures(@RequestParam(value = "feature_id", defaultValue = "0") int featureId,
			@RequestParam("feature_name") String featuresName, @RequestParam("feature_description") String featureDesc,
			@RequestParam(value = "story_points", defaultValue = "0") int story_points,
			@RequestParam(value = "fk_feature_type_id", defaultValue = "0") int fk_feature_typeId,
			@RequestParam(value = "fk_feature_status_id", defaultValue = "0") int fk_feature_statusId,
			@RequestParam(value = "assigned_To", defaultValue = "0") int assigned_To,
			@RequestParam(value = "fk_release_id", defaultValue = "0") int fk_releaseId,
			@RequestParam("files") MultipartFile[] featuresUploads) {
		ResponseDTO responseDTO = new ResponseDTO();
		ResponseDTO responseDTO1 = new ResponseDTO();
		FeaturesModel featuresModel = null;
		// int featureId=0;
		try {
			if (story_points > 100 || story_points <= 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("Story Point Should Be 1 to 100");
				return responseDTO;
			}
			if (featuresName.equals(null) || featuresName.trim().equals("") || featureDesc.equals(null)
					|| featureDesc.trim().equals("") || story_points == 0 || fk_feature_typeId == 0
					|| fk_releaseId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			if (featuresName.length() <= 4 || featuresName.length() > 60) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.featuresNameLong"));
				return responseDTO;
			}
			if (featureDesc.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.featuresDescLong"));
				return responseDTO;
			}

			featuresModel = new FeaturesModel();
			featuresModel.setFeature_id(featureId);
			featuresModel.setFeature_name(featuresName);
			featuresModel.setFeature_description(featureDesc);
			featuresModel.setStory_points(story_points);
			featuresModel.setFk_feature_type_id(fk_feature_typeId);
			featuresModel.setFk_feature_status_id(fk_feature_statusId);
			featuresModel.setAssigned_To(assigned_To);

			featuresModel.setFk_release_id(fk_releaseId);
			featuresModel.setUpdated_by(1);
			responseDTO = featuresService.updateFeatures(featuresModel);
			if (responseDTO.getStatusCode() == 1 || responseDTO.getMessage().equals("No Changes Found")) {
				if (featuresUploads.length == 0) {
					return responseDTO;
				} else {
					responseDTO1 = attachmentUploadController.updatefeaturesFileUpload(featuresUploads, featureId);
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
			responseDTO.setMessage("Error While updateing in to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured.");
		}
		return responseDTO;
	}

	/*-------------------------------- DELETE FEATURE  ------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseDTO deletefeature(@RequestParam(value = "feature_id", defaultValue = "0") int featureId
	// ,@RequestParam(value = "fk_release_id", defaultValue = "0") int fk_releaseId
	) {
		ResponseDTO responseDTO = new ResponseDTO();
		FeaturesModel featuresModel = null;
		// int userId = 0;
		try {
			if (featureId != 0) {
				featuresModel = new FeaturesModel();
				featuresModel.setFeature_id(featureId);
				// featuresModel.setFk_release_id(fk_releaseId);
				featuresModel.setDeleted_by(1);
				featuresModel.setDelete_status(1);
				responseDTO = featuresService.deleteFeatures(featuresModel);
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

	/*--------------------------------GET FEATURES BY FEATUREID------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetailsByFeatureId", method = RequestMethod.GET)
	public List<FeatureslistModel> getFeatureDetailsByFeatureId(
			@RequestParam(value = "featureId", defaultValue = "0") int featureId) {
		List<FeatureslistModel> featureModel = null;
		try {
			if (featureId != 0) {
				featureModel = featuresService.getFeatureDetailsByFeatureId(featureId);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return featureModel;
	}

	/*--------------------------------GET FEATURES BY FK RELEASEID------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetails", method = RequestMethod.GET)
	public List<FeatureslistModel> getFeatureDetails(
			@RequestParam(value = "fkReleaseId", defaultValue = "0") int fkReleaseId) {
		List<FeatureslistModel> featureModel = null;
		try {
			if (fkReleaseId != 0) {
				featureModel = featuresService.getFeatureDetailsByReleaseId(fkReleaseId);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return featureModel;
	}

	/*--------------------------------GET FEATURES BY FKPRODUCTID------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetailsByProductId", method = RequestMethod.GET)
	public List<FeatureslistModel> getFeatureDetailsByProductId(
			@RequestParam(value = "fkproductId", defaultValue = "0") int fkproductId) {
		List<FeatureslistModel> featureModel = null;
		try {
			if (fkproductId != 0) {
				featureModel = featuresService.getFeatureDetailsByProductId(fkproductId);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return featureModel;
	}

	/*--------------------------------GET FEATURES BY STATUSID AND FKRELEASEID------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetailsBystatusIdAndFkReleaseId", method = RequestMethod.GET)
	public List<FeatureslistModel> getFeatureDetailsByStatusIdAndReleaseId(
			@RequestParam(value = "statusId", defaultValue = "0") int statusId,
			@RequestParam(value = "fkReleaseId", defaultValue = "0") int fkReleaseId) {
		List<FeatureslistModel> featureModel = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			if (statusId == 0 || fkReleaseId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
			}
			if (statusId != 0 || fkReleaseId != 0) {
				featureModel = featuresService.getFeatureDetailsByStatusIdAndReleaseId(statusId, fkReleaseId);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return featureModel;
	}

	/*--------------------------------GET ALL FEATURES ------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllFeatures", method = RequestMethod.GET)
	public List<FeatureslistModel> getAllFeatures() {
		List<FeatureslistModel> featureModel = null;
		// int userId = 0;
		try {
			featureModel = featuresService.getAllFeatures();
			if (featureModel.size() == 0) {
				return featureModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featureModel;
	}

	/*--------------------------------DELETE FEATURES FILES------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/deleteFeatureFiles", method = RequestMethod.POST)
	public ResponseDTO deleteFeatureFiles(@RequestParam(value = "fkfeature_id", defaultValue = "0") int fkfeatureId,
			@RequestParam(value = "fileId", defaultValue = "0") int fileId) {
		ResponseDTO responseDTO = new ResponseDTO();
		List<FeaturesUploadModel> uploads = null;
		FeaturesUploadModel featuresUploadModel = null;
		try {
			if (fkfeatureId == 0 || fileId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			featuresUploadModel = new FeaturesUploadModel();
			featuresUploadModel.setAttachmentId(fileId);
			featuresUploadModel.setFkfeatureId(fkfeatureId);
			featuresUploadModel.setDeletedBy(1);
			responseDTO = featuresService.deletefeatureFiles(featuresUploadModel);
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

	/*--------------------------------UPDATE FEATURES STATUS------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public ResponseDTO updateStatus(@RequestParam(value = "featureId", defaultValue = "0") int featureId,
			@RequestParam(value = "statusId", defaultValue = "0") int statusId) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			if (featureId == 0 || statusId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			responseDTO = featuresService.updateStatus(featureId, statusId);
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

	/*--------------------------------Count READY TO SHIP  FEATUREs------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/countreadyship", method = RequestMethod.GET)
	public int countReadyTOShipFeatures() {
		int countReadyToShip = 0;
		// int userId = 0;
		try {
			countReadyToShip = featuresService.countReadyToShipFeatures();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return countReadyToShip;

	}

	/*--------------------------------GET LIST OF  FEATURES IS BACKLOG ONE------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")

	@RequestMapping(value = "/getFeaturesIsbacklogOne", method = RequestMethod.GET)
	public List<FeatureslistModel> getFeaturesIsBackLogOne() {
		List<FeatureslistModel> featureModel = null;
		// int userId = 0;
		try {
			featureModel = featuresService.getFeaturesIsBackLogOne();
			if (featureModel.size() == 0) {
				return featureModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featureModel;
	}

	/*--------------------------------GET LIST OF  FEATURES IS BACKLOG ZERO------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")

	@RequestMapping(value = "/getFeaturesIsbacklogZero", method = RequestMethod.GET)
	public List<FeatureslistModel> getFeaturesIsBackLogZero() {
		List<FeatureslistModel> featureModel = null;
		// int userId = 0;
		try {
			featureModel = featuresService.getFeaturesIsBackLogZero();
			if (featureModel.size() == 0) {
				return featureModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featureModel;
	}

	/*--------------------------------MOVE TO BACKLOG ZERO------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")

	@RequestMapping(value = "/movetoBackLogZero", method = RequestMethod.POST)
	public ResponseDTO MoveToBackLogZero(@RequestParam(value = "feature_id", defaultValue = "0") int featureId,
			@RequestParam(value = "fk_release_id", defaultValue = "0") int fkreleaseId) {

		ResponseDTO responseDTO = new ResponseDTO();
		FeaturesModel featuresModel = null;
		try {
			if (featureId == 0 || fkreleaseId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			featuresModel = new FeaturesModel();
			featuresModel.setFk_release_id(fkreleaseId);
			featuresModel.setFeature_id(featureId);
			featuresModel.setUpdated_by(1);
			responseDTO = featuresService.movetoBackLogZero(featuresModel);

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

	/*--------------------------------MOVE TO BACKLOG ONE------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")

	@RequestMapping(value = "/movetoBackLogOne", method = RequestMethod.POST)
	public ResponseDTO MoveToBackLogOne(@RequestParam(value = "feature_id", defaultValue = "0") int featureId,
			@RequestParam(value = "fk_release_id", defaultValue = "0") int fkreleaseId) {

		ResponseDTO responseDTO = new ResponseDTO();
		FeaturesModel featuresModel = null;
		try {
			if (featureId == 0 || fkreleaseId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			featuresModel = new FeaturesModel();
			featuresModel.setFk_release_id(fkreleaseId);
			featuresModel.setFeature_id(featureId);
			featuresModel.setUpdated_by(1);
			responseDTO = featuresService.movetoBackLogOne(featuresModel);

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

	/*--------------------------------GET LIST OF  FEATURES IS BACKLOG ONE BY FKPRODUCT ID------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")

	@RequestMapping(value = "/getFeaturesIsbacklogOneByfkProductId", method = RequestMethod.GET)
	public List<FeatureslistModel> getFeaturesIsBackLogOneByProductId(
			@RequestParam(value = "fk_product_id", defaultValue = "0") int fkproductId) {
		List<FeatureslistModel> featureModel = null;
		// int userId = 0;
		try {
			if (fkproductId != 0) {
				featureModel = featuresService.getFeaturesIsBackLogOneByFkProductId(fkproductId);
				if (featureModel.size() == 0) {
					return featureModel;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featureModel;
	}

	/*--------------------------------GET LIST OF  FEATURES TYPE ------------------------------------------*/

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllFeaturesType", method = RequestMethod.GET)
	public List<FeatureslistModel> getFeaturesType() {
		List<FeatureslistModel> featureModel = null;
		// int userId = 0;
		try {
			featureModel = featuresService.getAllFeaturesType();
			if (featureModel.size() == 0) {
				return featureModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featureModel;
	}

	/*--------------------------------GET LIST OF  FEATURES STATUS ------------------------------------------*/

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllFeaturesStatus", method = RequestMethod.GET)
	public List<FeatureslistModel> getFeaturesStatus() {
		List<FeatureslistModel> featureModel = null;
		// int userId = 0;
		try {
			featureModel = featuresService.getAllFeaturesStatus();
			if (featureModel.size() == 0) {
				return featureModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featureModel;
	}

	/*--------------------------------GET ALL UNMAPED FEATURES LIST ------------------------------------------*/

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllUnMapedFeatures", method = RequestMethod.GET)
	public List<FeatureslistModel> getAllUnMapedFeatures() {
		List<FeatureslistModel> unmapedfeatureModel = null;
		// int userId = 0;
		try {
			unmapedfeatureModel = featuresService.getAllUnMapedFeatures();
			if (unmapedfeatureModel.size() == 0) {
				return unmapedfeatureModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unmapedfeatureModel;
	}

	/*--------------------------------GET ALL MAPED FEATURES LIST ------------------------------------------*/

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllMapedFeatures", method = RequestMethod.GET)
	public List<FeatureslistModel> getAllMapedFeatures() {
		List<FeatureslistModel> mapedfeatureModel = null;
		// int userId = 0;
		try { 
			mapedfeatureModel = featuresService.getAllMapedFeatures();
			if (mapedfeatureModel.size() == 0) {
				return mapedfeatureModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapedfeatureModel;
	}
}
