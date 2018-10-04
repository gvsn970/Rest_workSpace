package com.nexiilabs.autolifecycle.resources;

import org.springframework.web.multipart.MultipartFile;

import com.nexiilabs.autolifecycle.features.FeaturesUploadModel;
import com.nexiilabs.autolifecycle.product.ProductUploadModel;
import com.nexiilabs.autolifecycle.productline.ProductLineUploadModel;
import com.nexiilabs.autolifecycle.releasephases.ReleasePhasesUploadModel;
import com.nexiilabs.autolifecycle.releases.ProductReleaseUploadModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

public interface AttachmentUploadService {

	boolean saveFileToDisk(MultipartFile file_object, String UPLOADED_FOLDER, String fileName, String filePath);

	ResponseDTO addReleaseUploadDetails(ProductReleaseUploadModel uploadModel);

	ResponseDTO createDirectories(int releaseId, String RELEASE_FOLDER);

	public ResponseDTO addProductUploadDetails(ProductUploadModel uploadModel);

	ResponseDTO deleteReleaseRecordInDatabase(int releaseId);
	
	ResponseDTO deleteProductRecordInDatabase(int productId);

	ResponseDTO addProductLineUploadDetails(ProductLineUploadModel uploadModel);

	ResponseDTO deleteProductLineRecordInDatabase(int productlineId);
	boolean checkFileExistancyForReleaseFile(int releaseId, String filePath);
	boolean checkFileExistancyForProductFile(int productId, String filePath);

	boolean checkFileExistancyForProductLineFile(int product_line_id, String filePath);
	
	ResponseDTO addFeaturesUploadDetails(FeaturesUploadModel uploadModel);
	ResponseDTO deleteFeaturesRecordInDatabase(int featureId);
	boolean checkFileExistancyForFeaturesFile(int featureId, String filePath);
	
	ResponseDTO addReleasePhaseUploadDetails(ReleasePhasesUploadModel uploadModel);
	ResponseDTO deleteReleasePhaseRecordInDatabase(int releasephaseId);
	boolean checkFileExistancyForReleasePhaseFile(int releasephaseId, String filePath);
	

}
