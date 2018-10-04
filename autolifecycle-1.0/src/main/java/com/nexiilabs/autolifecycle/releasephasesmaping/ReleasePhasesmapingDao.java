package com.nexiilabs.autolifecycle.releasephasesmaping;

import java.util.List;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;

public interface ReleasePhasesmapingDao {

	ResponseDTO addReleasePhasesmaping(ReleasePhasesmapingModel mapingModel);
	ResponseDTO updateReleasePhasesmaping(ReleasePhasesmapingModel mapingModel);
	ResponseDTO deleteReleasePhasesmaping(ReleasePhasesmapingModel mapingModel);
	List<ReleasePhasesmapingListModel> getReleasePhasesmapingDetails(int releasephasemapId);
	List<ReleasePhasesmapingListModel> getAllReleasePhasesmapings();
}
