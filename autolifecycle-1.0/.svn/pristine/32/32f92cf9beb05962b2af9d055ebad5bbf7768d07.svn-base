package com.nexiilabs.autolifecycle.resources;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface ProductLogoService {
	
	Response createDirectories(int fk_product_id, String pRODUCT_FOLDER);

	boolean saveFileToDisk(MultipartFile file_object, String uPLOADED_FOLDER, String fileName, String filePath);

	Response addProductUploadDetails(ProductLogoUploadModel uploadModel);
	
	Response updateProductUploadDetails(ProductLogoUploadModel uploadModel);
	
	List<ProductLogoUploadModel> getProductLogoById(int fk_product_id);
	

}
