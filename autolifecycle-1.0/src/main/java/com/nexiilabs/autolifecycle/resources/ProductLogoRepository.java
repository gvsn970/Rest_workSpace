package com.nexiilabs.autolifecycle.resources;

import java.util.List;

public interface ProductLogoRepository {
	
	Response addProductUploadDetails(ProductLogoUploadModel uploadModel);
	
	Response updateProductUploadDetails(ProductLogoUploadModel uploadModel);
	List<ProductLogoUploadModel> getProductLogoById(int fk_product_id);
}
