package com.nexiilabs.autolifecycle.releasephases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexiilabs.autolifecycle.releasephasesmaping.ReleasePhasesmapingModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@Service
public class ReleasePhasesServiceImpl implements ReleasePhasesService {

	@Autowired
	ReleasePhasesDao releasePhasesDao;
	
	@Override
	public ResponseDTO addReleasePhasesForPhase(ReleasePhasesModel releasePhasesModel, String startDate, String endDate,
			int fkreleaseId) {
		// TODO Auto-generated method stub
		return releasePhasesDao.addReleasePhasesForPhase(releasePhasesModel, startDate, endDate, fkreleaseId);
	}
	@Override
	public ResponseDTO addReleasePhasesForMileStone(ReleasePhasesModel releasePhasesModel, String endDate,
			int fkreleaseId) {
		// TODO Auto-generated method stub
		return releasePhasesDao.addReleasePhasesForMileStone(releasePhasesModel, endDate, fkreleaseId);
	}
	@Override
	public ResponseDTO updateReleasePhasesForPhase(ReleasePhasesModel releasePhasesModel, String startDate, String endDate,int fkreleaseId) {
		// TODO Auto-generated method stub
		return releasePhasesDao.updateReleasePhasesForPhase(releasePhasesModel, startDate, endDate, fkreleaseId);
	}
	@Override
	public ResponseDTO updateReleasePhasesForMileStone(ReleasePhasesModel releasePhasesModel, String endDate,
			int fkreleaseId) {
		// TODO Auto-generated method stub
		return releasePhasesDao.updateReleasePhasesForMileStone(releasePhasesModel, endDate, fkreleaseId);
	}
	@Override
	public ResponseDTO deleteReleasePhases(ReleasePhasesmapingModel mapingModel) {
		// TODO Auto-generated method stub
		return releasePhasesDao.deleteReleasePhases(mapingModel);
	}

	@Override
	public List<ReleasePhaseslistModel> getReleasePhaseDetails(int releasephaseId) {
		// TODO Auto-generated method stub
		return releasePhasesDao.getReleasePhaseDetails(releasephaseId);
	}

	@Override
	public List<ReleasePhaseslistModel> getAllReleasePhases() {
		// TODO Auto-generated method stub
		return releasePhasesDao.getAllReleasePhases();
	}

	@Override
	public ResponseDTO deleteReleasePhaseFiles(ReleasePhasesUploadModel releasePhasesUploadModel) {
		// TODO Auto-generated method stub
		return releasePhasesDao.deleteReleasePhaseFiles(releasePhasesUploadModel);
	}
	@Override
	public List<ReleasePhaseslistModel> getAllReleasePhasesByReleaseId(int fkreleaseId) {
		// TODO Auto-generated method stub
		return releasePhasesDao.getAllReleasePhasesByReleaseId(fkreleaseId);
	}
	@Override
	public int getreleaseMapId(int releasephaseId, int fkreleaseId) {
		// TODO Auto-generated method stub
		return releasePhasesDao.getreleaseMapId(releasephaseId, fkreleaseId);
	}
	@Override
	public List<ReleasePhaseslistModel> getAllReleasePhaseType() {
		// TODO Auto-generated method stub
		return releasePhasesDao.getAllReleasePhaseType();
	}

	



	

	/*@Override
	public ResponseDTO updateStatus(int releasephaseId, int statusId) {
		// TODO Auto-generated method stub
		return releasePhasesDao.updateStatus(releasephaseId, statusId);
	}*/

	
	

}
