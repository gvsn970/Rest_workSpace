package com.nexiilabs.autolifecycle.product;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

	public Response addProduct(Product product);
	public List<ProductListDTO> allproducts();
	public Response updateProduct(int product_id, String product_name,String product_desc,int fk_product_line,int fk_product_owner);
	public List<ProductListDTO> getDetails( int product_id);
	public List<ProductListDTO> getProductDetails(int product_id);
	public int deleteproduct(int product_id);
	
	boolean saveFileToDisk(MultipartFile file_object, String UPLOADED_FOLDER, String fileName, String filePath);

	public Response addProductUploadDetails(ProductUploadModel uploadModel);

	Response createDirectories(int productId, String PRODUCT_FOLDER);
	public Response deleteProductFiles(ProductUploadModel productUploadModel);
	public List<ProductListDTO> unMapedProductswithJiraBoards();
	public List<ProductListDTO> MapedProductswithJiraBoards();

}
