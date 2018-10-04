package com.nexiilabs.autolifecycle.releases;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReleaseServiceImpl implements ReleaseService{
@Autowired
ReleaseDao releaseDao;
	@Override
	public ResponseDTO addRelease(ReleaseModel releaseModel) {
		return releaseDao.addRelease(releaseModel);
	}
	@Override
	public ResponseDTO updateRelease(ReleaseModel releaseModel) {
		return releaseDao.updateRelease(releaseModel);
	}
	@Override
	public ResponseDTO deleteRelease(ReleaseModel releaseModel) {
		return releaseDao.deleteRelease(releaseModel);
	}
	@Override
	public  List<ReleaseListModel> getReleaseDetails(int releaseId) {
		return releaseDao.getReleaseDetails(releaseId);
	}
	@Override
	public List<ReleaseListModel> getAllReleases() {
		return releaseDao.getAllReleases();
	}
	@Override
	public boolean saveFileToDisk(MultipartFile file_object, String UPLOADED_FOLDER, String fileName, String filePath) {
		try{
			byte[] bytes = file_object.getBytes();
			new File(UPLOADED_FOLDER + fileName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			stream.write(bytes);
			stream.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public ResponseDTO addReleaseUploadDetails(ProductReleaseUploadModel uploadModel) {
		return releaseDao.addReleaseUploadDetails(uploadModel);
	}
	@Override
	public ResponseDTO createDirectories(int releaseId, String RELEASE_FOLDER) {
		ResponseDTO userResponseDTO = new ResponseDTO();
		try {
			if (releaseId != 0 &&RELEASE_FOLDER!=null ) 
			{
				File file = new File(RELEASE_FOLDER+"/Release_" +releaseId);
				if (!file.exists()) {
					if (file.mkdir()) {
						userResponseDTO.setStatusCode(1);
						userResponseDTO.setMessage("User Directory is created");
					} else {
						userResponseDTO.setStatusCode(0);
						userResponseDTO.setMessage("Failed to create  User directory");
					}
				} else {
					userResponseDTO.setStatusCode(2);
					userResponseDTO.setMessage("User Directory already exists");
				}
				
				File file1 = new File(RELEASE_FOLDER+"/Release_"+releaseId+"/"+java.time.LocalDate.now());
				if (!file1.exists()) {
					if (file1.mkdir()) {
						userResponseDTO.setStatusCode(1);
						userResponseDTO.setMessage("Date Directory is created");
						userResponseDTO.setUploadPath(file1.getPath()+"/");
					} else {
						userResponseDTO.setStatusCode(0);
						userResponseDTO.setMessage("Failed to create Date directory");
					}
				} else {
					userResponseDTO.setStatusCode(2);
					userResponseDTO.setMessage("Date directory already exists");
					userResponseDTO.setUploadPath(file1.getPath());
				}

			} else {
				userResponseDTO.setStatusCode(0);
				userResponseDTO.setMessage("All input fields are required");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userResponseDTO;
	}
	@Override
	public List<ProductReleaseUploadModel> getRleaseFiles(int fkReleaseId) {
		return releaseDao.getRleaseFiles(fkReleaseId);
	}
	@Override
	public ResponseDTO deleteReleaseFiles(ProductReleaseUploadModel productReleaseUploadModel) {
		return releaseDao.deleteReleaseFiles(productReleaseUploadModel);
	}
	@Override
	public ReleaseListModel getReleaseDetailsForCopy(ReleaseListModel releaseListModel) {
		return releaseDao.getReleaseDetailsForCopy(releaseListModel);
	}
	@Override
	public ResponseDTO updateStatus(int releaseId, int statusId) {
		return releaseDao.updateStatus(releaseId,statusId);
	}
	@Override
	public List<ReleaseListModel> getAllReleasesStatus() {
		// TODO Auto-generated method stub
		return releaseDao.getAllReleasesStatus();
	}

}
