package com.nexiilabs.autolifecycle.resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class ProductLogoServiceImpl implements ProductLogoService {

	@Autowired
	private ProductLogoRepository repository;
	
	@Override
	public Response createDirectories(int fk_product_id, String pRODUCT_FOLDER) {
		Response userResponse = new Response(); 
		 //System.err.println("productId::"+fk_product_id+"PRODUCT_FOLDER:::"+pRODUCT_FOLDER);
		try {
			if (fk_product_id != 0 && pRODUCT_FOLDER != null) {
				File file = new File(pRODUCT_FOLDER + "/Productlogos" + fk_product_id+"/");
				if (!file.exists()) {
					if (file.mkdir()) {
						userResponse.setStatus(1);
						userResponse.setMessage("User Directory is created");
						userResponse.setUploadPath(file.getPath() + "/");
					} else {
						userResponse.setStatus(0);
						userResponse.setMessage("Failed to create  User directory");
					}
				} else {
					userResponse.setStatus(2);
					userResponse.setMessage("User Directory already exists");
					userResponse.setUploadPath(file.getPath() + "/");
				}

			} else {
				userResponse.setStatus(0);
				userResponse.setMessage("All input fields are required");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	 //System.err.println("in create directory:::"+userResponse.getMessage()+"path::::::::::::"+userResponse.getUploadPath());
		return userResponse;
	}

	@Override
	public boolean saveFileToDisk(MultipartFile file_object, String uPLOADED_FOLDER, String fileName, String filePath) {
		try {
			byte[] bytes = file_object.getBytes();
			new File(uPLOADED_FOLDER + fileName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			stream.write(bytes);
			stream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Response addProductUploadDetails(ProductLogoUploadModel uploadModel) {
		// TODO Auto-generated method stub
		return repository.addProductUploadDetails(uploadModel);
	}

	@Override
	public Response updateProductUploadDetails(ProductLogoUploadModel uploadModel) {
		// TODO Auto-generated method stub
		return repository.updateProductUploadDetails(uploadModel);
	}

	@Override
	public List<ProductLogoUploadModel> getProductLogoById(int fk_product_id) {
		// TODO Auto-generated method stub
		return repository.getProductLogoById(fk_product_id);
	}

	


}
