package com.nexiilabs.autolifecycle.productline;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nexiilabs.autolifecycle.productline.Response;

@Service
public class ProductLineServiceImpl implements ProductLineService {
	@Autowired
	private ProductLineRepository repository;

	@Override
	public List<ProductLinePOJO> listofProductLines() {

		return repository.listofProductLines();
	}

	@Override
	public List<ProductLinePOJO> listOfSpecificProductLine(int product_line_id) {
		return repository.listOfSpecificProductLine(product_line_id);
	}

	@Override
	public int deleteProductLine(int product_line_id) {

		return repository.deleteProductLine(product_line_id);
	}

	@Override
	public Response addProductline(String product_line_name, String product_line_desc) {

		return repository.addProductLines(product_line_name, product_line_desc);
	}

	@Override
	public Response createDirectories(int productlineId, String PRODUCT_FOLDER) {
		Response userResponse = new Response();
		// System.err.println("productId::"+productId+"PRODUCT_FOLDER:::"+PRODUCT_FOLDER);
		try {
			if (productlineId != 0 && PRODUCT_FOLDER != null) {
				File file = new File(PRODUCT_FOLDER + "/Productline" + productlineId);
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

				File file1 = new File(
						PRODUCT_FOLDER + "/Productline" + productlineId + "/" + java.time.LocalDate.now());
				if (!file1.exists()) {
					if (file1.mkdir()) {
						userResponse.setStatus(1);
						userResponse.setMessage("Date Directory is created");
						userResponse.setUploadPath(file1.getPath() + "/");
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
		// System.err.println("in create directory:::"+userResponse.toString());
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
	public Response addProductLineUploadDetails(ProductLineUploadModel uploadModel) {

		return repository.addProductLineUploadDetails(uploadModel);
	}

	@Override
	public Response updateProductLine(int product_line_id, String product_line_name, String product_line_desc) {

		return repository.updateProductLine(product_line_id, product_line_name, product_line_desc);
	}

	@Override
	public List<ProductLinePOJO> listOfProductNamesByProductlineId(int product_line_id) {
		
		return repository.listOfProductNamesByProductlineId(product_line_id);
	}

	@Override
	public Response deleteProductLineFiles(ProductLineUploadModel productLineUploadModel) {
		
		return repository.deleteProductLineFiles(productLineUploadModel);
	}

}
