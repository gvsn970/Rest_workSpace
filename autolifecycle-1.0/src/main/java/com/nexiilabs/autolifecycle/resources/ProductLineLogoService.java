package com.nexiilabs.autolifecycle.resources;

import org.springframework.web.multipart.MultipartFile;



public interface ProductLineLogoService {

	Response createDirectories(int fk_product_line_id, String pRODUCT_FOLDER);

	boolean saveFileToDisk(MultipartFile file_object, String uPLOADED_FOLDER, String fileName, String filePath);

	Response addProductLineUploadDetails(ProductLineLogoUploadModel uploadModel);

	Response updateProductlineLogo(int fk_product_line_id);

	ProductLineLogoUploadModel getProductlinelogofile(int fk_product_line_id);

}
