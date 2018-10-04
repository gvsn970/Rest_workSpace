package com.nexiilabs.autolifecycle.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Response addProduct(Product product) {

		return productRepository.addProduct(product);
	}

	@Override
	public List<ProductListDTO> allproducts() {

		return productRepository.allproducts();
	}

	@Override 
	public List<ProductListDTO> getDetails(int product_id) {
		// TODO Auto-generated method stub
		return productRepository.getDetails(product_id);
	}

	@Override
	public int deleteproduct(int product_id) {
		// TODO Auto-generated method stub
		return productRepository.deleteproduct(product_id);
	}

	@Override
	public Response updateProduct(int product_id, String product_name, String product_desc,int fk_product_line,int fk_product_owner) {
		// TODO Auto-generated method stub
		return productRepository.updateProduct(product_id, product_name, product_desc,fk_product_line, fk_product_owner);
	}

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
	public Response addProductUploadDetails(ProductUploadModel uploadModel) {
		// TODO Auto-generated method stub
		return productRepository.addProductUploadDetails(uploadModel);
	}

	@Override
	public Response createDirectories(int productId, String PRODUCT_FOLDER) {
		
		Response userResponse = new Response();
		//System.err.println("productId::"+productId+"PRODUCT_FOLDER:::"+PRODUCT_FOLDER);
		try {
			if (productId != 0 &&PRODUCT_FOLDER!=null ) 
			{
				File file = new File(PRODUCT_FOLDER+"/Product" +productId);
				if (!file.exists()) {
					if (file.mkdir()) {
						userResponse.setStatus(1);
						userResponse.setMessage("User Directory is created");
					} else {
						userResponse.setStatus(0);
						userResponse.setMessage("Failed to create  User directory");
					}
				} else {
					userResponse.setStatus(2);
					userResponse.setMessage("User Directory already exists");
				}
				
				File file1 = new File(PRODUCT_FOLDER+"/Product"+productId+"/"+java.time.LocalDate.now());
				if (!file1.exists()) {
					if (file1.mkdir()) {
						userResponse.setStatus(1);
						userResponse.setMessage("Date Directory is created");
						userResponse.setUploadPath(file1.getPath()+"/");
					} else {
						userResponse.setStatus(0);
						userResponse.setMessage("Failed to create Date directory");
					}
				} else {
					userResponse.setStatus(2);
					userResponse.setMessage("Date directory already exists");
					userResponse.setUploadPath(file1.getPath());
				}

			} else {
				userResponse.setStatus(0);
				userResponse.setMessage("All input fields are required");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.err.println("in create directory:::"+userResponse.toString());
		return userResponse;
	}

	@Override
	public Response deleteProductFiles(ProductUploadModel productUploadModel) {
		// TODO Auto-generated method stub
		return productRepository.deleteProductFiles(productUploadModel);
	}

	@Override
	public List<ProductListDTO> getProductDetails(int product_id) {
		// TODO Auto-generated method stub
		return productRepository.getProductDetails(product_id);
	}

	@Override
	public List<ProductListDTO> unMapedProductswithJiraBoards() {
		// TODO Auto-generated method stub
		return productRepository.unMapedProductswithJiraBoards();
	}

	@Override
	public List<ProductListDTO> MapedProductswithJiraBoards() {
		// TODO Auto-generated method stub
		return productRepository.MapedProductswithJiraBoards();
	}

}
