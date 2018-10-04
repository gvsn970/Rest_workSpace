package com.nexiilabs.autolifecycle.featuresassignment;

import java.util.List;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;

public interface FeaturesAssignmentService {
	
	ResponseDTO addFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel);
	
	ResponseDTO updateFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel);
	
	ResponseDTO deleteFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel);
	
	List<FeaturesAssignmtlistModel> getFeatureAssignmentDetails(int featureassignmentId);
	
	List<FeaturesAssignmtlistModel> getAllFeaturesAssignment();
	

}
