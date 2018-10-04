package com.nexiilabs.autolifecycle.resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductLineLogoServiceImpl implements ProductLineLogoService {
	@Autowired
	private ProductLineLogoRepository repository;

	@Override
	public Response createDirectories(int fk_product_line_id, String PRODUCT_FOLDER) {
		Response userResponse = new Response();
		 System.err.println("productId::"+fk_product_line_id+"PRODUCT_FOLDER:::"+PRODUCT_FOLDER);
		try {
			if (fk_product_line_id != 0 && PRODUCT_FOLDER != null) {
				File file = new File(PRODUCT_FOLDER + "/Productlinelogos" + fk_product_line_id+"/");
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
	 System.err.println("in create directory:::"+userResponse.getMessage()+"path::::::::::::"+userResponse.getUploadPath());
		return userResponse;
	}

	@Override
	public boolean saveFileToDisk(MultipartFile file_object, String UPLOADED_FOLDER, String fileName, String filePath) {
		try {
			byte[] bytes = file_object.getBytes();
			new File(UPLOADED_FOLDER + fileName);
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
	public Response addProductLineUploadDetails(ProductLineLogoUploadModel uploadModel) {

		return repository.addProductLineUploadDetails(uploadModel);
	}

	@Override
	public Response updateProductlineLogo(int fk_product_line_id) {
		
		return repository.updateProductlineLogo(fk_product_line_id);
	}

	@Override
	public ProductLineLogoUploadModel getProductlinelogofile(int fk_product_line_id) {

		return repository.getProductlinelogofile(fk_product_line_id);
	}


}
