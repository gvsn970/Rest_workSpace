package com.nexiilabs.autolifecycle.productline;

import java.util.List;



public interface ProductLineRepository {

	List<ProductLinePOJO> listofProductLines();

	List<ProductLinePOJO> listOfSpecificProductLine(int product_line_id);
	
	int deleteProductLine(int product_line_id);

	Response addProductLines(String product_line_name, String product_line_desc);

	Response addProductLineUploadDetails(ProductLineUploadModel uploadModel);

	Response updateProductLine(int product_line_id, String product_line_name, String product_line_desc);
	List<ProductLinePOJO> listOfProductNamesByProductlineId(int product_line_id);

	Response deleteProductLineFiles(ProductLineUploadModel productLineUploadModel);

}
