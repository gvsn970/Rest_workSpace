package com.nexiilabs.autolifecycle.releasephasesmaping;

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
@RequestMapping("/releasephasesmaping")
public class ReleasePhasesmapingController {

	@Autowired
	ReleasePhasesmapingService releasePhasesmapingService;
	@Autowired
	Environment environment;

	/*--------------------------------CREATE RELEASEPHASEMAPING FOR PHASE------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createphase", method = RequestMethod.POST)
	public ResponseDTO addReleasePhasesmapingForPhase(

			@RequestParam(value = "fk_release_phase_id", defaultValue = "0") int fkreleasephaseId,
			@RequestParam(value = "fk_release_phase_type_id", defaultValue = "0") int fkreleasephasetypeId,
			@RequestParam(value = "fk_release_id", defaultValue = "0") int fkreleaseId,
			@RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate,
			@RequestParam(value = "fk_status_id", defaultValue = "0") int fkstatusId,
			@RequestParam("description") String description) {
		ResponseDTO responseDTO = new ResponseDTO();
		ReleasePhasesmapingModel mapingModel = null;

		try {
			if (fkreleasephaseId == 0 || fkreleasephasetypeId == 0 || fkreleaseId == 0 || startDate.equals(null)
					|| startDate.trim().equals("") || endDate.equals(null) || endDate.trim().equals("")
					|| fkstatusId == 0 || description.equals(null) || description.trim().equals("")) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}

			if (description.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasemapingDescLong"));
				return responseDTO;
			}

			mapingModel = new ReleasePhasesmapingModel();
			
			mapingModel.setCreated_by(1);
			mapingModel.setFk_release_id(fkreleaseId);
			mapingModel.setFk_release_phase_id(fkreleasephaseId);
			mapingModel.setFk_release_phase_type_id(fkreleasephasetypeId);
			mapingModel.setStart_date(startDate);
			mapingModel.setEnd_date(endDate);
			mapingModel.setFk_status_id(fkstatusId);
			mapingModel.setDescription(description);
		
			responseDTO = releasePhasesmapingService.addReleasePhasesmaping(mapingModel);
	
			if (responseDTO.getStatusCode() == 1) {
			
				responseDTO.setStatusCode(1);
				responseDTO.setMessage("ReleasePhasesmaping added successfully");

			} else if (responseDTO.getStatusCode() == 2) {
				responseDTO.setStatusCode(2);
				responseDTO.setMessage("ReleasePhasesmaping Already exists");
			} 
			else if (responseDTO.getStatusCode() == 3) {
				responseDTO.setStatusCode(3);
				responseDTO.setMessage("ReleasePhasesmaping End Date is Greater Then Release Internal Date");
			}else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("ReleasePhasesmaping creation failed");
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
	/*--------------------------------CREATE RELEASEPHASEMAPING FOR MILESTONE------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createmilestone", method = RequestMethod.POST)
	public ResponseDTO addReleasePhasesmapingForMilestone(

			@RequestParam(value = "fk_release_phase_id", defaultValue = "0") int fkreleasephaseId,
			@RequestParam(value = "fk_release_phase_type_id", defaultValue = "0") int fkreleasephasetypeId,
			@RequestParam(value = "fk_release_id", defaultValue = "0") int fkreleaseId,
			@RequestParam("end_date") String endDate,
			@RequestParam(value = "fk_status_id", defaultValue = "0") int fkstatusId,
			@RequestParam("description") String description) {
		ResponseDTO responseDTO = new ResponseDTO();
		int release_phase_map_id = 0;
		ReleasePhasesmapingModel mapingModel = null;

		try {
			if (fkreleasephaseId == 0 || fkreleasephasetypeId == 0 || fkreleaseId == 0
				|| endDate.equals(null) || endDate.trim().equals("")
					|| fkstatusId == 0 || description.equals(null) || description.trim().equals("")) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}

			if (description.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasemapingDescLong"));
				return responseDTO;
			}

			mapingModel = new ReleasePhasesmapingModel();
			
			mapingModel.setCreated_by(1);
			mapingModel.setFk_release_id(fkreleaseId);
			mapingModel.setFk_release_phase_id(fkreleasephaseId);
			mapingModel.setFk_release_phase_type_id(fkreleasephasetypeId);
			//mapingModel.setStart_date(startDate);
			mapingModel.setEnd_date(endDate);
			mapingModel.setFk_status_id(fkstatusId);
			mapingModel.setDescription(description);
			responseDTO = releasePhasesmapingService.addReleasePhasesmaping(mapingModel);
			if (responseDTO.getStatusCode() == 1) {
				// release_phase_map_id = responseDTO.getFeature_id();
				responseDTO.setStatusCode(1);
				responseDTO.setMessage("ReleasePhasesmaping added successfully");

			} else if (responseDTO.getStatusCode() == 2) {
				responseDTO.setStatusCode(2);
				responseDTO.setMessage("ReleasePhasesmaping Already exists");
			} 
			else if (responseDTO.getStatusCode() == 3) {
				responseDTO.setStatusCode(3);
				responseDTO.setMessage("ReleasePhasesmaping End Date is Greater Then Release Internal Date");
			}else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("ReleasePhasesmaping creation failed");
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
	
	/*--------------------------------UPDATE RELEASEPHASEMAPING FOR PHASE------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updatephase", method = RequestMethod.POST)
	public ResponseDTO updateReleasePhasesmapingForPhase(
			@RequestParam(value = "release_phase_map_id", defaultValue = "0") int releasephasemapId,
			@RequestParam(value = "fk_release_phase_id", defaultValue = "0") int fkreleasephaseId,
			@RequestParam(value = "fk_release_phase_type_id", defaultValue = "0") int fkreleasephasetypeId,
			@RequestParam(value = "fk_release_id", defaultValue = "0") int fkreleaseId,
			@RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate,
			@RequestParam(value = "fk_status_id", defaultValue = "0") int fkstatusId,
			@RequestParam("description") String description) {
		ResponseDTO responseDTO = new ResponseDTO();
		//int release_phase_map_id = 0;
		ReleasePhasesmapingModel mapingModel = null;

		try {
			if (fkreleasephaseId == 0 || fkreleasephasetypeId == 0 || fkreleaseId == 0 || startDate.equals(null)
					|| startDate.trim().equals("") || endDate.equals(null) || endDate.trim().equals("")
					|| fkstatusId == 0 || description.equals(null) || description.trim().equals("")) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}

			if (description.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasemapingDescLong"));
				return responseDTO;
			}

			mapingModel = new ReleasePhasesmapingModel();
			
			mapingModel.setUpdated_by(1);
			mapingModel.setRelease_phase_map_id(releasephasemapId);
			mapingModel.setFk_release_id(fkreleaseId);
			mapingModel.setFk_release_phase_id(fkreleasephaseId);
			mapingModel.setFk_release_phase_type_id(fkreleasephasetypeId);
			mapingModel.setStart_date(startDate);
			mapingModel.setEnd_date(endDate);
			mapingModel.setFk_status_id(fkstatusId);
			mapingModel.setDescription(description);
			responseDTO = releasePhasesmapingService.updateReleasePhasesmaping(mapingModel);
		
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

	/*-------------------------------- DELETE RELEASEPHASEMAPING  ------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseDTO deletereleasephasemaping(@RequestParam(value = "release_phase_map_id", defaultValue = "0") int releasephasemapId) {
		ResponseDTO responseDTO = new ResponseDTO();
		ReleasePhasesmapingModel mapingModel = null;
		// int userId = 0;
		try {
			if (releasephasemapId != 0) {
				mapingModel = new ReleasePhasesmapingModel();
				mapingModel.setRelease_phase_map_id(releasephasemapId);
				mapingModel.setDeleted_by(1);
				mapingModel.setDelete_status(1);
				responseDTO = releasePhasesmapingService.deleteReleasePhasesmaping(mapingModel);
			} else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
			}

		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		}
		catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}
	/*--------------------------------GET RELEASEPHASEMAPING------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetails", method = RequestMethod.GET)
	public List<ReleasePhasesmapingListModel> getReleasePhasemapingDetails(@RequestParam(value = "release_phase_map_id", defaultValue = "0") int releasephasemapId) {
		List<ReleasePhasesmapingListModel> mapingModel = null;
		try {
			if (releasephasemapId != 0) {
				mapingModel = releasePhasesmapingService.getReleasePhasesmapingDetails(releasephasemapId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return mapingModel;
	}
	
	/*--------------------------------GET ALL RELEASEPHASEMAPING ------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllreleasePhasesmaps", method = RequestMethod.GET)
	public List<ReleasePhasesmapingListModel> getAllReleasePhasesmaps() {
		List<ReleasePhasesmapingListModel> mapingModel = null;
		// int userId = 0;
		try {
			mapingModel = releasePhasesmapingService.getAllReleasePhasesmapings();
			if (mapingModel.size() == 0) {
				return mapingModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapingModel;
	}

}
