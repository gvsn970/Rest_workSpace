package com.nexiilabs.autolifecycle.featuresassignment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@RestController
@RequestMapping("/featuresassignment")
public class FeaturesAssignmentController {
	@Autowired
	FeaturesAssignmentService featuresAssignmentService;

	@Autowired
	Environment environment;

	/*--------------------------------CREATE FEATURESASSIGNMENT------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseDTO addFeatures(@RequestParam(value = "fk_feature_id", defaultValue = "0") int fkfeatureId,
			@RequestParam(value = "assigned_to", defaultValue = "0") int assignedTo
	// @RequestParam(value="is_active", defaultValue = "0") int isActive
	) {

		ResponseDTO responseDTO = new ResponseDTO();
		FeaturesAssignmentModel featuresAssignmentModel = null;
		try {

			if (fkfeatureId == 0 || assignedTo == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			featuresAssignmentModel = new FeaturesAssignmentModel();
			featuresAssignmentModel.setFk_feature_id(fkfeatureId);
			featuresAssignmentModel.setAssigned_by(1);
			featuresAssignmentModel.setIs_active(1);
			featuresAssignmentModel.setAssigned_to(assignedTo);
			responseDTO = featuresAssignmentService.addFeaturesAssignment(featuresAssignmentModel);

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

	/*--------------------------------UPDATE FEATURESASSIGNMENT------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseDTO updateFeatures(
			@RequestParam(value = "feature_assignment_id", defaultValue = "0") int featureassignmentId,
			@RequestParam(value = "fk_feature_id", defaultValue = "0") int fkfeatureId,
			@RequestParam(value = "assigned_to", defaultValue = "0") int assignedTo
	// @RequestParam(value="is_active", defaultValue = "0") int isActive
	) {

		ResponseDTO responseDTO = new ResponseDTO();
		FeaturesAssignmentModel featuresAssignmentModel = null;
		try {
			if (fkfeatureId == 0 || assignedTo == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			featuresAssignmentModel = new FeaturesAssignmentModel();
			featuresAssignmentModel.setFeature_assignment_id(featureassignmentId);
			featuresAssignmentModel.setFk_feature_id(fkfeatureId);
			featuresAssignmentModel.setAssigned_by(1);
			// featuresAssignmentModel.setIs_active(isActive);
			featuresAssignmentModel.setAssigned_to(assignedTo);
			responseDTO = featuresAssignmentService.updateFeaturesAssignment(featuresAssignmentModel);

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

	/*-------------------------------- DELETE FEATURESASSIGNMENT  ------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseDTO deleteFeaturesAssignment(
			@RequestParam(value = "feature_assignment_id", defaultValue = "0") int featureassignmentId) {
		ResponseDTO responseDTO = new ResponseDTO();
		FeaturesAssignmentModel featuresAssignmentModel = null;
		// int userId = 0;
		try {
			if (featureassignmentId != 0) {
				featuresAssignmentModel = new FeaturesAssignmentModel();
				featuresAssignmentModel.setFeature_assignment_id(featureassignmentId);
				responseDTO = featuresAssignmentService.deleteFeaturesAssignment(featuresAssignmentModel);
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

	/*--------------------------------GET FEATURESASSIGNMENT------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetails", method = RequestMethod.GET)
	public List<FeaturesAssignmtlistModel> getFeatureAssignmtDetails(@RequestParam(value = "featureassignmentId", defaultValue = "0") int featureassignmentId) {
		List<FeaturesAssignmtlistModel> featureAssignmtModel = null;
		try {
			if (featureassignmentId != 0) {
				featureAssignmtModel = featuresAssignmentService.getFeatureAssignmentDetails(featureassignmentId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return featureAssignmtModel;
	}

	/*--------------------------------GET ALL FEATURESASSIGNMENT ------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllFeaturesAssignmt", method = RequestMethod.GET)
	public List<FeaturesAssignmtlistModel> getAllFeaturesAssignmt() {
		List<FeaturesAssignmtlistModel> featureAssignmtModel = null;
		// int userId = 0;
		try {
			featureAssignmtModel = featuresAssignmentService.getAllFeaturesAssignment();
			if (featureAssignmtModel.size() == 0) {
				return featureAssignmtModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featureAssignmtModel;
	}

}
