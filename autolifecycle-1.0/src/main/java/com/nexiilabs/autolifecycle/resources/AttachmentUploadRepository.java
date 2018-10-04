package com.nexiilabs.autolifecycle.resources;

import com.nexiilabs.autolifecycle.features.FeaturesUploadModel;
import com.nexiilabs.autolifecycle.product.ProductUploadModel;
import com.nexiilabs.autolifecycle.productline.ProductLineUploadModel;
import com.nexiilabs.autolifecycle.releasephases.ReleasePhasesUploadModel;
import com.nexiilabs.autolifecycle.releases.ProductReleaseUploadModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

public interface AttachmentUploadRepository
{

	ResponseDTO addReleaseUploadDetails(ProductReleaseUploadModel uploadModel);
	public ResponseDTO addProductUploadDetails(ProductUploadModel uploadModel);
	ResponseDTO deleteReleaseRecordInDatabase(int releaseId);
	ResponseDTO deleteProductRecordInDatabase(int productId);
	ResponseDTO addProductLineUploadDetails(ProductLineUploadModel uploadModel);
	ResponseDTO deleteProductLineRecordInDatabase(int productlineId);
	public boolean checkFileExistancyForReleaseFile(int releaseId ,String uploadPath);
	public boolean checkFileExistancyForProductFile(int productId, String uploadPath);
	boolean checkFileExistancyForProductLineFile(int product_line_id, String filePath);
	ResponseDTO addFeaturesUploadDetails(FeaturesUploadModel uploadModel);
	ResponseDTO deleteFeaturesRecordInDatabase(int featureId);
	boolean checkFileExistancyForFeaturesFile(int featureId, String filePath);
	ResponseDTO addReleasePhaseUploadDetails(ReleasePhasesUploadModel uploadModel);
	ResponseDTO deleteReleasePhaseRecordInDatabase(int releasephaseId);
	boolean checkFileExistancyForReleasePhaseFile(int releasephaseId, String filePath);

}
