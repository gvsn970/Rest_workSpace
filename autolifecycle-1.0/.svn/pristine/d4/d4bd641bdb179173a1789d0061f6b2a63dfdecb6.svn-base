package com.nexiilabs.autolifecycle.product;

import java.util.List;

public interface ProductRepository {
	
	public Response addProduct(Product product);
	public List<ProductListDTO> allproducts();
	public Response updateProduct( int product_id, String product_name,String product_desc,int fk_product_line,int fk_product_owner);
	public List<ProductListDTO> getDetails(int product_id);
	public List<ProductListDTO> getProductDetails(int product_id);
	public int deleteproduct( int product_id);
	
	public Response addProductUploadDetails(ProductUploadModel uploadModel);
	List<ProductUploadModel> getProductFile(int fk_product_id);
	
	public Response deleteProductFiles(ProductUploadModel productUploadModel);
	public List<ProductListDTO> unMapedProductswithJiraBoards();
	public List<ProductListDTO> MapedProductswithJiraBoards();

}
