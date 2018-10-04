package com.nexiilabs.autolifecycle.productline;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;



public interface ProductLineService {

	public List<ProductLinePOJO> listofProductLines();

	public List<ProductLinePOJO> listOfSpecificProductLine(int product_line_id);

	public int deleteProductLine(int product_line_id);

	public Response addProductline(String product_line_name, String product_line_desc);

	public Response createDirectories(int productlineId, String PRODUCT_FOLDER);

	public boolean saveFileToDisk(MultipartFile file_object, String UPLOADED_FOLDER, String fileName, String filePath);

	public Response addProductLineUploadDetails(ProductLineUploadModel uploadModel);

	public Response updateProductLine(int product_line_id, String product_line_name, String product_line_desc);

	public List<ProductLinePOJO> listOfProductNamesByProductlineId(int product_line_id);

	public Response deleteProductLineFiles(ProductLineUploadModel productLineUploadModel);



}
