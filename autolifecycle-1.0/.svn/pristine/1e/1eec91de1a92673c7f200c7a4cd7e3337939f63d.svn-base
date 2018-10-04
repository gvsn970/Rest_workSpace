package com.nexiilabs.autolifecycle.releasephases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexiilabs.autolifecycle.releasephasesmaping.ReleasePhasesmapingModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;
import com.nexiilabs.autolifecycle.resources.AttachmentUploadController;

@RestController
@RequestMapping("/releasephases")
public class ReleasePhasesController {
	@Autowired
	ReleasePhasesService releasePhasesService;

	@Autowired
	AttachmentUploadController attachmentUploadController;

	@Autowired
	Environment environment;

	/*--------------------------------CREATE RELEASEPHASES FOR PHASE------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createphase", method = RequestMethod.POST)
	public ResponseDTO addReleasePhaseForPhase(@RequestParam("release_phase_name") String releasephaseName,
			@RequestParam("release_phase_description") String releasephaseDesc,
			// @RequestParam(value = "fk_release_phase_type", defaultValue = "0") int
			// fkreleasephaseType,
			// @RequestParam(value = "is_default", defaultValue = "0") int isDefault,
			@RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate,
			@RequestParam("files") MultipartFile[] releasephaseUploads,
			@RequestParam("fk_release_id") int fkreleaseId) {
		ResponseDTO responseDTO = new ResponseDTO();
		int releasephaseId = 0;
		int releasephasemapId = 0;
		int releaseId = 0;
		ReleasePhasesModel releasePhasesModel = null;

		try {
			if (releasephaseName.equals(null) || releasephaseName.trim().equals("") || releasephaseDesc.equals(null)
					|| releasephaseDesc.trim().equals("") || startDate.equals(null) || startDate.trim().equals("")
					|| endDate.equals(null) || endDate.trim().equals("") || fkreleaseId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			if (releasephaseName.length() <= 4 || releasephaseName.length() >= 60) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasesNameLong"));
				return responseDTO;
			}
			if (releasephaseDesc.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasesDescLong"));
				return responseDTO;
			}

			releasePhasesModel = new ReleasePhasesModel();

			releasePhasesModel.setRelease_phase_name(releasephaseName);
			releasePhasesModel.setRelease_phase_description(releasephaseDesc);
			releasePhasesModel.setFk_release_phase_type(1);
			// releasePhasesModel.setIs_default(isDefault);

			releasePhasesModel.setCreated_by(1);
			responseDTO = releasePhasesService.addReleasePhasesForPhase(releasePhasesModel, startDate, endDate,
					fkreleaseId);
			if (responseDTO.getStatusCode() == 1) {
				releasephaseId = responseDTO.getReleasephaseId();
				releasephasemapId = responseDTO.getReleasephasemapId();
				releaseId = responseDTO.getReleaseId();
				// System.err.println("innnnnnnnnnnnnnnn:::::::::::"+releasephaseId+":::::::::::::"+releasephasemapId+"::::::::::::::"+releaseId);
				if (releasephaseUploads.length == 0) {
					return responseDTO;
				} else {
					// System.err.println("releasephaseUploads,releasephaseId"+releasephaseUploads.toString()+"::::::::::::::::::::"+releasephaseId);
					responseDTO = attachmentUploadController.createReleasePhaseFileUpload(releasephaseUploads,
							releasephaseId, releaseId, releasephasemapId);
				}

			} else if (responseDTO.getStatusCode() == 2) {
				responseDTO.setStatusCode(2);
				responseDTO.setMessage("MileStone Name Shoud not Added into phase");
			} else if (responseDTO.getStatusCode() == 3) {
				responseDTO.setStatusCode(3);
				responseDTO.setMessage("Release Phase Already exists");
			} else if (responseDTO.getStatusCode() == 4) {
				responseDTO.setStatusCode(4);
				responseDTO.setMessage("ReleasePhase End Date Should be Less Then Release Internal Date");
			} else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("Release Phase creation failed");
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

	/*--------------------------------CREATE RELEASEPHASES FOR MILESTONE------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/createmilestone", method = RequestMethod.POST)
	public ResponseDTO addReleasePhaseForMileStone(@RequestParam("release_phase_name") String releasephaseName,
			@RequestParam("release_phase_description") String releasephaseDesc,
			// @RequestParam(value = "fk_release_phase_type", defaultValue = "0") int
			// fkreleasephaseType,
			// @RequestParam(value = "is_default", defaultValue = "0") int isDefault,
			@RequestParam("end_date") String endDate, @RequestParam("files") MultipartFile[] releasephaseUploads,
			@RequestParam("fk_release_id") int fkreleaseId) {
		ResponseDTO responseDTO = new ResponseDTO();
		int releasephaseId = 0;
		int releasephasemapId = 0;
		int releaseId = 0;
		ReleasePhasesModel releasePhasesModel = null;

		try {
			if (releasephaseName.equals(null) || releasephaseName.trim().equals("") || releasephaseDesc.equals(null)
					|| releasephaseDesc.trim().equals("") || endDate.equals(null) || endDate.trim().equals("")
					|| fkreleaseId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			if (releasephaseName.length() <= 4 || releasephaseName.length() >= 60) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasesNameLong"));
				return responseDTO;
			}
			if (releasephaseDesc.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasesDescLong"));
				return responseDTO;
			}

			releasePhasesModel = new ReleasePhasesModel();

			releasePhasesModel.setRelease_phase_name(releasephaseName);
			releasePhasesModel.setRelease_phase_description(releasephaseDesc);
			releasePhasesModel.setFk_release_phase_type(2);
			// releasePhasesModel.setIs_default(isDefault);
			releasePhasesModel.setCreated_by(1);
			responseDTO = releasePhasesService.addReleasePhasesForMileStone(releasePhasesModel, endDate, fkreleaseId);
			if (responseDTO.getStatusCode() == 1) {
				releasephaseId = responseDTO.getReleasephaseId();
				releasephasemapId = responseDTO.getReleasephasemapId();
				releaseId = responseDTO.getReleaseId();
				// System.err.println("innnnnnnnnnnnnnnn:::::::::::"+releasephaseId+":::::::::::::"+releasephasemapId+"::::::::::::::"+releaseId);
				if (releasephaseUploads.length == 0) {
					return responseDTO;
				} else {
					// System.err.println("releasephaseUploads,releasephaseId"+releasephaseUploads.toString()+"::::::::::::::::::::"+releasephaseId);
					responseDTO = attachmentUploadController.createReleasePhaseFileUpload(releasephaseUploads,
							releasephaseId, releaseId, releasephasemapId);
				}

			} else if (responseDTO.getStatusCode() == 2) {
				responseDTO.setStatusCode(2);
				responseDTO.setMessage("Phase Name Shoud not Added into mileStone");
			} else if (responseDTO.getStatusCode() == 3) {
				responseDTO.setStatusCode(3);
				responseDTO.setMessage(" MileStone Already exists");
			} else if (responseDTO.getStatusCode() == 4) {
				responseDTO.setStatusCode(4);
				responseDTO.setMessage(" MileStone End Date Should be Less Then Release Internal Date");
			} else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(" MileStone creation failed");
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

	/*--------------------------------UPDATE RELEASEPHASES FOR PHASE------------------------------------------*/

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updatephase", method = RequestMethod.POST)
	public ResponseDTO updateReleasePhaseForPhase(@RequestParam("release_phase_id") int releasephaseId,
			@RequestParam("release_phase_name") String releasephaseName,
			@RequestParam("release_phase_description") String releasephaseDesc,
			// @RequestParam(value = "fk_release_phase_type", defaultValue = "0") int
			// fkreleasephaseType,
			// @RequestParam(value = "is_default", defaultValue = "0") int isDefault,
			@RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate,
			@RequestParam("files") MultipartFile[] releasephaseUploads,
			@RequestParam("fk_release_id") int fkreleaseId) {
		ResponseDTO responseDTO = new ResponseDTO();
		ResponseDTO responseDTO1 = new ResponseDTO();
		// int releasephaseId=0;
		// int releasephaseId=0;
		int releasephasemapId = 0;

		ReleasePhasesModel releasePhasesModel = null;

		try {
			if (releasephaseName.equals(null) || releasephaseName.trim().equals("") || releasephaseDesc.equals(null)
					|| releasephaseDesc.trim().equals("") || fkreleaseId == 0 || startDate.equals(null)
					|| startDate.trim().equals("") || endDate.equals(null) || endDate.trim().equals("")) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			if (releasephaseName.length() <= 4 || releasephaseName.length() >= 60) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasesNameLong"));
				return responseDTO;
			}
			if (releasephaseDesc.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasesDescLong"));
				return responseDTO;
			}

			releasePhasesModel = new ReleasePhasesModel();
			releasePhasesModel.setRelease_phase_id(releasephaseId);

			releasePhasesModel.setRelease_phase_name(releasephaseName);
			releasePhasesModel.setRelease_phase_description(releasephaseDesc);
			releasePhasesModel.setFk_release_phase_type(1);
			// releasePhasesModel.setIs_default(isDefault);
			releasePhasesModel.setUpdated_by(1);
			responseDTO = releasePhasesService.updateReleasePhasesForPhase(releasePhasesModel, startDate, endDate,
					fkreleaseId);
			releasephasemapId = releasePhasesService.getreleaseMapId(releasephaseId, fkreleaseId);
			// System.err.println(":::::::::"+releasephasemapId);
			if (responseDTO.getStatusCode() == 1 || responseDTO.getMessage().equals("No Changes Found")) {

				// releasephasemapId=responseDTO.getReleasephasemapId();

				// System.err.println("in::::::::::::::::::"+releasephaseId+"::::::::::"+releasephasemapId+"::::::::::::::"+fkreleaseId);
				if (releasephaseUploads.length == 0) {
					return responseDTO;
				} else {
					responseDTO1 = attachmentUploadController.updateReleasePhaseFileUpload(releasephaseUploads,
							releasephaseId, fkreleaseId, releasephasemapId);
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

	/*--------------------------------UPDATE RELEASEPHASES FOR MILESTONE------------------------------------------*/

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/updatemilestone", method = RequestMethod.POST)
	public ResponseDTO updateReleasePhaseForMileStone(@RequestParam("release_phase_id") int releasephaseId,
			@RequestParam("release_phase_name") String releasephaseName,
			@RequestParam("release_phase_description") String releasephaseDesc,
			// @RequestParam(value = "fk_release_phase_type", defaultValue = "0") int
			// fkreleasephaseType,
			// @RequestParam(value = "is_default", defaultValue = "0") int isDefault,
			@RequestParam("end_date") String endDate, @RequestParam("files") MultipartFile[] releasephaseUploads,
			@RequestParam("fk_release_id") int fkreleaseId) {
		ResponseDTO responseDTO = new ResponseDTO();
		ResponseDTO responseDTO1 = new ResponseDTO();
		// int releasephaseId=0;
		// int releasephaseId=0;
		int releasephasemapId = 0;

		ReleasePhasesModel releasePhasesModel = null;

		try {
			if (releasephaseName.equals(null) || releasephaseName.trim().equals("") || releasephaseDesc.equals(null)
					|| releasephaseDesc.trim().equals("") || fkreleaseId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			if (releasephaseName.length() <= 4 || releasephaseName.length() >= 60) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasesNameLong"));
				return responseDTO;
			}
			if (releasephaseDesc.length() > 1000) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.releasephasesDescLong"));
				return responseDTO;
			}

			releasePhasesModel = new ReleasePhasesModel();
			releasePhasesModel.setRelease_phase_id(releasephaseId);

			releasePhasesModel.setRelease_phase_name(releasephaseName);
			releasePhasesModel.setRelease_phase_description(releasephaseDesc);
			releasePhasesModel.setFk_release_phase_type(2);
			// releasePhasesModel.setIs_default(isDefault);
			releasePhasesModel.setUpdated_by(1);
			responseDTO = releasePhasesService.updateReleasePhasesForMileStone(releasePhasesModel, endDate,
					fkreleaseId);
			releasephasemapId = releasePhasesService.getreleaseMapId(releasephaseId, fkreleaseId);
			if (responseDTO.getStatusCode() == 1 || responseDTO.getMessage().equals("No Changes Found")) {

				// releasephasemapId=responseDTO.getReleasephasemapId();

				// System.err.println("in::::::::::::::::::"+releasephaseId+"::::::::::"+releasephasemapId+"::::::::::::::"+fkreleaseId);
				if (releasephaseUploads.length == 0) {
					return responseDTO;
				} else {
					responseDTO1 = attachmentUploadController.updateReleasePhaseFileUpload(releasephaseUploads,
							releasephaseId, fkreleaseId, releasephasemapId);
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

	/*-------------------------------- DELETE RELEASEPHASE  ------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseDTO deleteRelease(@RequestParam(value = "release_phase_id", defaultValue = "0") int releasephaseId,
			@RequestParam("fk_release_id") int fkreleaseId) {
		ResponseDTO responseDTO = new ResponseDTO();
		ReleasePhasesmapingModel mapingModel = null;
		// int userId = 0;
		try {
			if (releasephaseId != 0 && fkreleaseId != 0) {
				mapingModel = new ReleasePhasesmapingModel();
				mapingModel.setFk_release_phase_id(releasephaseId);
				mapingModel.setFk_release_id(fkreleaseId);
				mapingModel.setDeleted_by(1);
				mapingModel.setDelete_status(1);
				responseDTO = releasePhasesService.deleteReleasePhases(mapingModel);
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

	/*--------------------------------GET RELEASEPHASE------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getDetails", method = RequestMethod.GET)
	public List<ReleasePhaseslistModel> getReleasephaseDetails(
			@RequestParam(value = "release_phase_id", defaultValue = "0") int releasephaseId) {
		List<ReleasePhaseslistModel> releasephasesModel = null;
		try {
			if (releasephaseId != 0) {
				releasephasesModel = releasePhasesService.getReleasePhaseDetails(releasephaseId);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return releasephasesModel;
	}

	/*--------------------------------GET ALL RELEASEPHASES------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllReleasephases", method = RequestMethod.GET)
	public List<ReleasePhaseslistModel> getAllReleasephases() {
		List<ReleasePhaseslistModel> releasephasesModel = null;
		// int userId = 0;
		try {
			releasephasesModel = releasePhasesService.getAllReleasePhases();
			if (releasephasesModel.size() == 0) {
				return releasephasesModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasephasesModel;
	}

	/*--------------------------------GET ALL RELEASEPHASES By RELEASE ID------------------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllReleasephasesByReleaseId", method = RequestMethod.GET)
	public List<ReleasePhaseslistModel> getAllReleasephasesByReleaseId(
			@RequestParam(value = "fkrelease_id", defaultValue = "0") int fkreleaseId) {
		List<ReleasePhaseslistModel> releasephasesModel = null;
		// int userId = 0;
		try {
			releasephasesModel = releasePhasesService.getAllReleasePhasesByReleaseId(fkreleaseId);
			if (releasephasesModel.size() == 0) {
				return releasephasesModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasephasesModel;
	}

	/*--------------------------------DELETE RELEASEPHASE FILES---------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/deleteReleasephaseFiles", method = RequestMethod.POST)
	public ResponseDTO deletereleasephaseFiles(
			// @RequestParam(value="fkreleasephase_id",defaultValue="0") int
			// fkreleasephaseId,
			@RequestParam(value = "fileId", defaultValue = "0") int fileId
	// ,@RequestParam("fk_release_id") int fkreleaseId
	) {
		ResponseDTO responseDTO = new ResponseDTO();
		List<ReleasePhasesUploadModel> uploads = null;
		ReleasePhasesUploadModel releasePhasesUploadModel = null;
		try {
			if (fileId == 0) {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage(environment.getProperty("app.allFieldsManditory"));
				return responseDTO;
			}
			releasePhasesUploadModel = new ReleasePhasesUploadModel();
			releasePhasesUploadModel.setAttachmentId(fileId);
			// releasePhasesUploadModel.setFkreleasephaseId(fkreleasephaseId);
			// releasePhasesUploadModel.setFkreleaseId(fkreleaseId);
			releasePhasesUploadModel.setDeletedBy(1);
			releasePhasesUploadModel.setDeleteStatus(1);
			responseDTO = releasePhasesService.deleteReleasePhaseFiles(releasePhasesUploadModel);
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

	/*--------------------------------GET ALL RELEASEPHASE TYPE------------------------------*/
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/getAllReleasephastype", method = RequestMethod.GET)
	public List<ReleasePhaseslistModel> getAllReleasephasesType() {
		List<ReleasePhaseslistModel> releasephasesModel = null;
		// int userId = 0;
		try {
			releasephasesModel = releasePhasesService.getAllReleasePhaseType();
			if (releasephasesModel.size() == 0) {
				return releasephasesModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasephasesModel;
	}

}
