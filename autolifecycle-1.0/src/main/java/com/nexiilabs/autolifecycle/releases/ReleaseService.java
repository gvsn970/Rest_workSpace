package com.nexiilabs.autolifecycle.releases;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ReleaseService {

	ResponseDTO addRelease(ReleaseModel releaseModel);

	ResponseDTO updateRelease(ReleaseModel releaseModel);

	ResponseDTO deleteRelease(ReleaseModel releaseModel);

	List<ReleaseListModel> getReleaseDetails(int releaseId);

	List<ReleaseListModel> getAllReleases();
	
	List<ReleaseListModel> getAllReleasesStatus();

	boolean saveFileToDisk(MultipartFile file_object, String uPLOADED_FOLDER, String fileName, String filePath);

	ResponseDTO addReleaseUploadDetails(ProductReleaseUploadModel uploadModel);

	ResponseDTO createDirectories(int releaseId, String rELEASE_FOLDER);

	List<ProductReleaseUploadModel> getRleaseFiles(int fkReleaseId);

	ResponseDTO deleteReleaseFiles(ProductReleaseUploadModel productReleaseUploadModel);

	ReleaseListModel getReleaseDetailsForCopy(ReleaseListModel releaseListModel);

	ResponseDTO updateStatus(int releaseId, int statusId);

}
