package com.nexiilabs.autolifecycle.resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nexiilabs.autolifecycle.features.FeaturesUploadModel;
import com.nexiilabs.autolifecycle.product.ProductUploadModel;
import com.nexiilabs.autolifecycle.productline.ProductLineUploadModel;
import com.nexiilabs.autolifecycle.releasephases.ReleasePhasesUploadModel;
import com.nexiilabs.autolifecycle.releases.ProductReleaseUploadModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@Service
public class AttachmentUploadServiceImpl implements AttachmentUploadService
{
	@Autowired
	AttachmentUploadRepository attachmentUploadRepository;
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
		return attachmentUploadRepository.addReleaseUploadDetails(uploadModel);
	}

	@Override
	public ResponseDTO createDirectories(int id, String FOLDER) {
		ResponseDTO userResponseDTO = new ResponseDTO();
		//System.err.println("in create directories:::::::::::::::::::::::"+id);
		try {
			if (id != 0 && FOLDER!=null ) 
			{
				File file = new File(FOLDER+"_" +id);
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
				File file1 = new File(file.getPath()+"/"+java.time.LocalDate.now());
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
		//System.err.println("In create directory:::::::::::::"+userResponseDTO.getMessage()+"path::::::::::"+userResponseDTO.getUploadPath());
		return userResponseDTO;
	}

	@Override
	public ResponseDTO addProductUploadDetails(ProductUploadModel uploadModel) {
		return attachmentUploadRepository.addProductUploadDetails(uploadModel);
	}

	@Override
	public ResponseDTO deleteReleaseRecordInDatabase(int releaseId) {
		return attachmentUploadRepository.deleteReleaseRecordInDatabase(releaseId);
	}

	@Override
	public ResponseDTO deleteProductRecordInDatabase(int productId) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.deleteProductRecordInDatabase(productId);
	}

	@Override
	public ResponseDTO addProductLineUploadDetails(ProductLineUploadModel uploadModel) {
		
		return attachmentUploadRepository.addProductLineUploadDetails(uploadModel);
	}

	@Override
	public ResponseDTO deleteProductLineRecordInDatabase(int productlineId) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.deleteProductLineRecordInDatabase(productlineId);
	}
	@Override
	public boolean checkFileExistancyForReleaseFile(int releaseId, String filePath) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.checkFileExistancyForReleaseFile(releaseId, filePath);
	}

	@Override
	public boolean checkFileExistancyForProductFile(int productId, String filePath) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.checkFileExistancyForProductFile(productId, filePath);
	}

	@Override
	public boolean checkFileExistancyForProductLineFile(int product_line_id, String filePath) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.checkFileExistancyForProductLineFile(product_line_id,filePath);
	}
	
	@Override
	public boolean checkFileExistancyForFeaturesFile(int featureId, String filePath) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.checkFileExistancyForFeaturesFile(featureId, filePath);
	}

	@Override
	public ResponseDTO addFeaturesUploadDetails(FeaturesUploadModel uploadModel) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.addFeaturesUploadDetails(uploadModel);
	}

	@Override
	public ResponseDTO deleteFeaturesRecordInDatabase(int featureId) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.deleteFeaturesRecordInDatabase(featureId);
	}

	@Override
	public ResponseDTO addReleasePhaseUploadDetails(ReleasePhasesUploadModel uploadModel) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.addReleasePhaseUploadDetails(uploadModel);
	}

	@Override
	public ResponseDTO deleteReleasePhaseRecordInDatabase(int releasephaseId) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.deleteReleasePhaseRecordInDatabase(releasephaseId);
	}

	@Override
	public boolean checkFileExistancyForReleasePhaseFile(int releasephaseId, String filePath) {
		// TODO Auto-generated method stub
		return attachmentUploadRepository.checkFileExistancyForReleasePhaseFile(releasephaseId, filePath);
	}



}
