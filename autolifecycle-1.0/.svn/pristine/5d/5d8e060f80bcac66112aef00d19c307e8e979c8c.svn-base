package com.nexiilabs.autolifecycle.features;

import java.util.List;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;

public interface FeaturesService {

	ResponseDTO addFeatures(FeaturesModel featuresModel);
	ResponseDTO updateFeatures(FeaturesModel featuresModel);
	ResponseDTO deleteFeatures(FeaturesModel featuresModel);
	List<FeatureslistModel> getFeatureDetailsByFeatureId(int featureId);
	List<FeatureslistModel> getFeatureDetailsByReleaseId(int fkReleaseId);
	List<FeatureslistModel> getFeatureDetailsByProductId(int fkproductId);
	List<FeatureslistModel> getFeatureDetailsByStatusIdAndReleaseId(int statusId,int fkReleaseId);
	List<FeatureslistModel> getAllFeatures();
	public int countReadyToShipFeatures();
	public ResponseDTO deletefeatureFiles(FeaturesUploadModel featuresUploadModel);
	ResponseDTO updateStatus(int featureId, int statusId);
	List<FeatureslistModel> getFeaturesIsBackLogOne();
	List<FeatureslistModel> getFeaturesIsBackLogOneByFkProductId(int fkproductId);
	List<FeatureslistModel> getFeaturesIsBackLogZero();
	ResponseDTO movetoBackLogZero(FeaturesModel featuresModel);
	ResponseDTO movetoBackLogOne(FeaturesModel featuresModel);
	List<FeatureslistModel> getAllFeaturesType();
	List<FeatureslistModel> getAllFeaturesStatus();
	List<FeatureslistModel> getFeaturesTypeByFeatureTypeId(int fk_feature_typeId);
	List<FeatureslistModel> getAllUnMapedFeatures();
	List<FeatureslistModel> getAllMapedFeatures();

}
