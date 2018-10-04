package com.nexiilabs.autolifecycle.releasephases;

import java.util.List;

import com.nexiilabs.autolifecycle.releasephasesmaping.ReleasePhasesmapingModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

public interface ReleasePhasesDao {
	ResponseDTO addReleasePhasesForPhase(ReleasePhasesModel releasePhasesModel, String startDate, String endDate,int fkreleaseId);
	ResponseDTO addReleasePhasesForMileStone(ReleasePhasesModel releasePhasesModel, String endDate,int fkreleaseId);
	ResponseDTO updateReleasePhasesForPhase(ReleasePhasesModel releasePhasesModel, String startDate, String endDate,int fkreleaseId);
	ResponseDTO deleteReleasePhases(ReleasePhasesmapingModel mapingModel);
	List<ReleasePhaseslistModel> getReleasePhaseDetails(int releasephaseId);
	List<ReleasePhaseslistModel> getAllReleasePhases();
	public List<ReleasePhasesUploadModel> getReleasePhaseFile(int fkreleasephaseId,int fkreleaseId);
	public ResponseDTO deleteReleasePhaseFiles(ReleasePhasesUploadModel releasePhasesUploadModel);
	//ResponseDTO updateStatus(int releasephaseId, int statusId);
	List<ReleasePhaseslistModel> getAllReleasePhasesByReleaseId(int fkreleaseId);
	
	public int getreleaseMapId(int releasephaseId,int fkreleaseId);
	ResponseDTO updateReleasePhasesForMileStone(ReleasePhasesModel releasePhasesModel, String endDate,int fkreleaseId);
	List<ReleasePhaseslistModel> getAllReleasePhaseType();
	
}
