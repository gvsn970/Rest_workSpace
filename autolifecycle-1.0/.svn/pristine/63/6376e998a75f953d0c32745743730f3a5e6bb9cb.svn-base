package com.nexiilabs.autolifecycle.features;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@Service
public class FeaturesServiceImpl implements FeaturesService {
	@Autowired
	FeaturesDao featuresDao;
	@Override
	public ResponseDTO addFeatures(FeaturesModel featuresModel) {
		// TODO Auto-generated method stub
		return featuresDao.addFeatures(featuresModel);
	}

	@Override
	public ResponseDTO updateFeatures(FeaturesModel featuresModel) {
		// TODO Auto-generated method stub
		return featuresDao.updateFeatures(featuresModel);
	}

	@Override
	public ResponseDTO deleteFeatures(FeaturesModel featuresModel) {
		// TODO Auto-generated method stub
		return featuresDao.deleteFeatures(featuresModel);
	}

	@Override
	public List<FeatureslistModel> getFeatureDetailsByReleaseId(int fkReleaseId) {
		// TODO Auto-generated method stub
		return featuresDao.getFeatureDetailsByReleaseId(fkReleaseId);
	}

	@Override
	public List<FeatureslistModel> getAllFeatures() {
		// TODO Auto-generated method stub
		return featuresDao.getAllFeatures();
	}

	@Override
	public ResponseDTO deletefeatureFiles(FeaturesUploadModel featuresUploadModel) {
		// TODO Auto-generated method stub
		return featuresDao.deletefeatureFiles(featuresUploadModel);
	}

	@Override
	public ResponseDTO updateStatus(int featureId, int statusId) {
		// TODO Auto-generated method stub
		return featuresDao.updateStatus(featureId, statusId);
	}

	@Override
	public int countReadyToShipFeatures() {
		// TODO Auto-generated method stub
		return featuresDao.countReadyToShipFeatures();
	}

	@Override
	public List<FeatureslistModel> getFeaturesIsBackLogOne() {
		// TODO Auto-generated method stub
		return featuresDao.getFeaturesIsBackLogOne();
	}

	@Override
	public List<FeatureslistModel> getFeaturesIsBackLogZero() {
		// TODO Auto-generated method stub
		return featuresDao.getFeaturesIsBackLogZero();
	}

	@Override
	public ResponseDTO movetoBackLogZero(FeaturesModel featuresModel) {
		// TODO Auto-generated method stub
		return featuresDao.movetoBackLogZero(featuresModel);
	}

	@Override
	public ResponseDTO movetoBackLogOne(FeaturesModel featuresModel) {
		// TODO Auto-generated method stub
		return featuresDao.movetoBackLogOne(featuresModel);
	}

	@Override
	public List<FeatureslistModel> getAllFeaturesType() {
		// TODO Auto-generated method stub
		return featuresDao.getAllFeaturesType();
	}

	@Override
	public List<FeatureslistModel> getAllFeaturesStatus() {
		// TODO Auto-generated method stub
		return featuresDao.getAllFeaturesStatus();
	}

	@Override
	public List<FeatureslistModel> getFeatureDetailsByProductId(int fkproductId) {
		// TODO Auto-generated method stub
		return featuresDao.getFeatureDetailsByProductId(fkproductId);
	}

	@Override
	public List<FeatureslistModel> getFeatureDetailsByFeatureId(int featureId) {
		// TODO Auto-generated method stub
		return featuresDao.getFeatureDetailsByFeatureId(featureId);
	}

	@Override
	public List<FeatureslistModel> getFeatureDetailsByStatusIdAndReleaseId(int statusId, int fkReleaseId) {
		// TODO Auto-generated method stub
		return featuresDao.getFeatureDetailsByStatusIdAndReleaseId(statusId, fkReleaseId);
	}

	@Override
	public List<FeatureslistModel> getFeaturesIsBackLogOneByFkProductId(int fkproductId) {
		// TODO Auto-generated method stub
		return featuresDao.getFeaturesIsBackLogOneByFkProductId(fkproductId);
	}

	@Override
	public List<FeatureslistModel> getFeaturesTypeByFeatureTypeId(int fk_feature_typeId) {
		// TODO Auto-generated method stub
		return featuresDao.getFeaturesTypeByFeatureTypeId(fk_feature_typeId);
	}

	@Override
	public List<FeatureslistModel> getAllUnMapedFeatures() {
		// TODO Auto-generated method stub
		return featuresDao.getAllUnMapedFeatures();
	}

	@Override
	public List<FeatureslistModel> getAllMapedFeatures() {
		// TODO Auto-generated method stub
		return featuresDao.getAllMapedFeatures();
	}

}
