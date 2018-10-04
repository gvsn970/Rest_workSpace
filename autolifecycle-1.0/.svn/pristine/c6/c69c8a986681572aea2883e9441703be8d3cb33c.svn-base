package com.nexiilabs.autolifecycle.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/productlogo")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class ProductLogoController {
	@Autowired
	private ProductLogoService service;

	@Autowired
	Environment environment;

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/Json")
	public Response createProductLogo(@RequestParam("fk_product_id") int fk_product_id,
			@RequestParam("logo") MultipartFile productUploads, HttpServletRequest request, HttpServletResponse res) {

		MultipartFile file = (MultipartFile) productUploads;
		Response response = new Response();
		HttpSession userSession = request.getSession(true);
		String UPLOADED_FOLDER = null;
		String PRODUCT_FOLDER = environment.getProperty("app.productlogouploaddir");
		String fileName = null;
		String filePath = null;
		int productlogoId = 0;
		ProductLogoUploadModel uploadModel = null;

		// response = service.addProductlineLogo(fk_product_line_id);
		// PRODUCT_FOLDER=PRODUCT_FOLDER+"Productlogos";
		
		try {
			BufferedImage image = ImageIO.read(file.getInputStream());
			if ((file.getContentType().toLowerCase().equals("image/png"))) { 
			if (!(file.isEmpty() || file.getSize() == 0)) {
				// System.out.println("Logo width: " + image.getWidth());
				// System.out.println("Logo heiht: " + image.getHeight());
				if (image.getWidth() <= 35 && image.getHeight() <= 35) {
					//System.err.println("PRODUCT_FOLDER::::::::::::::::"+PRODUCT_FOLDER);
					response = service.createDirectories(fk_product_id, PRODUCT_FOLDER);
						UPLOADED_FOLDER = response.getUploadPath();
						MultipartFile file_object = productUploads;
						if (!file_object.isEmpty()) {
							fileName = file_object.getOriginalFilename();
							filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
							uploadModel = new ProductLogoUploadModel();
							uploadModel.setLogoName(fileName);
							uploadModel.setLogoLocation(filePath);
							uploadModel.setFkProductId(fk_product_id);
							uploadModel.setCreatedBy(1);
							uploadModel.setLogoType(FilenameUtils.getExtension(fileName));
							// System.err.println("filePath::::"+filePath);
							// System.err.println("UPLOADED_FOLDER::"+UPLOADED_FOLDER);
							if (service.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
								response = service.addProductUploadDetails(uploadModel);
								response.setStatus(1);
								response.setMessage("Product logo created successfully");
							} else {

								response.setStatus(0);
								response.setMessage("product logo  upload Failed due to logo Upload Failure");
							}
						}
					} else {
						response.setStatus(0);
						response.setMessage("logo should be 35*35 pixel");
					}
				} else {
					response.setStatus(0);
					response.setMessage("Product logo required");
				}
			} else {
				response.setStatus(0);
				response.setMessage(" Only png file types are supported");
			}
		} catch (IOException e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/Json")
	public Response updateProductLogo(@RequestParam("fk_product_id") int fk_product_id,
			@RequestParam("logo") MultipartFile productUploads, HttpServletRequest request, HttpServletResponse res) {
		MultipartFile file = (MultipartFile) productUploads;
		Response response = new Response();
		HttpSession userSession = request.getSession(true);
		String UPLOADED_FOLDER = null;
		String PRODUCT_FOLDER = environment.getProperty("app.productlogouploaddir");
		String fileName = null;
		String filePath = null;
		int productlogoId = 0;
		ProductLogoUploadModel uploadModel = null;

		// response=service.updateProductUploadDetails(uploadModel);
		// if(response.getStatus() !=0)
		try {
			BufferedImage image = ImageIO.read(file.getInputStream());
			if ((file.getContentType().toLowerCase().equals("image/png"))) {
			if (!(file.isEmpty() || file.getSize() == 0)) {
				// System.out.println("Logo width: " + image.getWidth());
				// System.out.println("Logo heiht: " + image.getHeight());
				if (image.getWidth() <= 35 && image.getHeight() <= 35) {
					response = service.createDirectories(fk_product_id, PRODUCT_FOLDER);
						// System.out.println(PRODUCT_FOLDER);
						UPLOADED_FOLDER = response.getUploadPath();
						MultipartFile file_object = productUploads;
						if (!file_object.isEmpty()) {
							fileName = file_object.getOriginalFilename();
							filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
							uploadModel = new ProductLogoUploadModel();
							uploadModel.setLogoName(fileName);
							uploadModel.setLogoLocation(filePath);
							uploadModel.setFkProductId(fk_product_id);
							uploadModel.setCreatedBy(1);
							uploadModel.setLogoType(FilenameUtils.getExtension(fileName));
							// System.err.println("filePath::::"+filePath);
							// System.err.println("UPLOADED_FOLDER::"+UPLOADED_FOLDER);
							if (service.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
								// response = service.addProductUploadDetails(uploadModel);
								response = service.updateProductUploadDetails(uploadModel);
								response.setStatus(1);
								response.setMessage("Product logo updated successfully");
							} else {

								response.setStatus(0);
								response.setMessage("product logo  upload Failed due to logo Upload Failure");
							}
						}
					} else {
						response.setStatus(0);
						response.setMessage("logo should be 35*35 pixel");
					}
				} else {
					response.setStatus(0);
					response.setMessage("Product logo required");
				}
			} else {
				response.setStatus(0);
				response.setMessage("Only png file types are supported");
			}
		}

		catch (IOException e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping("/getproduclogotbyId")
	public ResponseEntity<?> downloadFile1(@RequestParam("fk_product_id") int fk_product_id) throws IOException {
		List<ProductLogoUploadModel> list = service.getProductLogoById(fk_product_id);
		// Response response=new Response();
		String path = null;
		if (list.size() > 0) {
			path = list.get(0).getLogoLocation();
			File file = new File(path);
			// System.out.println("fileName: " + path);
			if (path != null) {
				if (file.exists()) {
					InputStreamResource resource = new InputStreamResource(new FileInputStream(path));
					return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + path)
							.contentType(MediaType.IMAGE_PNG).body(resource);
				} else {
					return new ResponseEntity<String>("Product Logo Not Found", HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<String>("Product Logo Not Found", HttpStatus.OK);

			}
		} else {
			return new ResponseEntity<String>("Product Logo Not Found", HttpStatus.OK);

		}
	}

}
