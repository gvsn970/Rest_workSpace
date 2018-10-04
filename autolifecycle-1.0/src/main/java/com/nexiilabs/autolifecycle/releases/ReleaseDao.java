package com.nexiilabs.autolifecycle.releases;

import java.util.List;

public interface ReleaseDao {

	ResponseDTO addRelease(ReleaseModel releaseModel);

	ResponseDTO updateRelease(ReleaseModel releaseModel);

	ResponseDTO deleteRelease(ReleaseModel releaseModel);

	List<ReleaseListModel> getReleaseDetails(int releaseId);

	List<ReleaseListModel> getAllReleases();
	List<ReleaseListModel> getAllReleasesStatus();

	ResponseDTO addReleaseUploadDetails(ProductReleaseUploadModel uploadModel);

	List<ProductReleaseUploadModel> getRleaseFiles(int fkReleaseId);

	ResponseDTO deleteReleaseFiles(ProductReleaseUploadModel productReleaseUploadModel);

	ReleaseListModel getReleaseDetailsForCopy(ReleaseListModel releaseListModel);

	ResponseDTO updateStatus(int releaseId, int statusId);

}
