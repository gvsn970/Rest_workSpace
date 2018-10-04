package com.nexiilabs.autolifecycle.featuresassignment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;
@Service
public class FeaturesAssignmentServiceImpl implements FeaturesAssignmentService {

	@Autowired
	FeaturesAssignmentDao featuresAssignmentDao;
	@Override
	public ResponseDTO addFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel) {
		// TODO Auto-generated method stub
		return featuresAssignmentDao.addFeaturesAssignment(featuresAssignmentModel);
	}
	@Override
	public ResponseDTO updateFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel) {
		// TODO Auto-generated method stub
		return featuresAssignmentDao.updateFeaturesAssignment(featuresAssignmentModel);
	}
	@Override
	public ResponseDTO deleteFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel) {
		// TODO Auto-generated method stub
		return featuresAssignmentDao.deleteFeaturesAssignment(featuresAssignmentModel);
	}
	@Override
	public List<FeaturesAssignmtlistModel> getFeatureAssignmentDetails(int featureassignmentId) {
		// TODO Auto-generated method stub
		return featuresAssignmentDao.getFeatureAssignmentDetails(featureassignmentId);
	}
	@Override
	public List<FeaturesAssignmtlistModel> getAllFeaturesAssignment() {
		// TODO Auto-generated method stub
		return featuresAssignmentDao.getAllFeaturesAssignment();
	}

}
