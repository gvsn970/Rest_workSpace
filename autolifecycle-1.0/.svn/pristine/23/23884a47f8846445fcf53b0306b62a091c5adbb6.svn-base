package com.nexiilabs.autolifecycle.releasephasesmaping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@Service
public class ReleasePhasesmapingServiceImpl implements ReleasePhasesmapingService {

	@Autowired
	ReleasePhasesmapingDao releasePhasesmapingDao;

	@Override
	public ResponseDTO addReleasePhasesmaping(ReleasePhasesmapingModel mapingModel) {
		// TODO Auto-generated method stub
		return releasePhasesmapingDao.addReleasePhasesmaping(mapingModel);
	}

	@Override
	public ResponseDTO updateReleasePhasesmaping(ReleasePhasesmapingModel mapingModel) {
		// TODO Auto-generated method stub
		return releasePhasesmapingDao.updateReleasePhasesmaping(mapingModel);
	}

	@Override
	public ResponseDTO deleteReleasePhasesmaping(ReleasePhasesmapingModel mapingModel) {
		// TODO Auto-generated method stub
		return releasePhasesmapingDao.deleteReleasePhasesmaping(mapingModel);
	}

	@Override
	public List<ReleasePhasesmapingListModel> getReleasePhasesmapingDetails(int releasephasemapId) {
		// TODO Auto-generated method stub
		return releasePhasesmapingDao.getReleasePhasesmapingDetails(releasephasemapId);
	}

	@Override
	public List<ReleasePhasesmapingListModel> getAllReleasePhasesmapings() {
		// TODO Auto-generated method stub
		return releasePhasesmapingDao.getAllReleasePhasesmapings();
	}

}
