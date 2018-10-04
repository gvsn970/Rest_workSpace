package com.nexiilabs.autolifecycle.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
// @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/productlinelogo")
public class ProductLineLogoController {
	@Autowired
	private ProductLineLogoService service;

	@Autowired
	Environment environment;

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/Json")
	public Response createProductLineLogo(
			@RequestParam(value = "fk_product_line_id", defaultValue = "0") int fk_product_line_id,
			@RequestParam("logo") MultipartFile productUploads) {

		Response response = new Response();
		try {
			if (fk_product_line_id == 0) {
				response.setMessage(environment.getProperty("app.allFieldsManditory"));
				response.setStatus(0);
				return response;
			}
			MultipartFile file = (MultipartFile) productUploads;
			BufferedImage image = ImageIO.read(file.getInputStream());
			if ((file.getContentType().toLowerCase().equals("image/png"))) {
				String UPLOADED_FOLDER = null;
				String PRODUCT_FOLDER = environment.getProperty("app.productlinelogouploaddir");
				String fileName = null;
				String filePath = null;
				int productlineId = 0;
				ProductLineLogoUploadModel uploadModel = null;
				if (!(file.isEmpty() || file.getSize() == 0)) {
					System.out.println("Logo width: " + image.getWidth());
					System.out.println("Logo heiht: " + image.getHeight());
					if (image.getWidth() <= 35 && image.getHeight() <= 35) {
						response = service.createDirectories(fk_product_line_id, PRODUCT_FOLDER);
						System.out.println(PRODUCT_FOLDER);
						UPLOADED_FOLDER = response.getUploadPath();
						MultipartFile file_object = productUploads;
						if (!file_object.isEmpty()) {
							fileName = file_object.getOriginalFilename();
							filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
							uploadModel = new ProductLineLogoUploadModel();
							uploadModel.setLogoName(fileName);
							uploadModel.setLogoLocation(filePath);
							uploadModel.setFk_product_line_id(fk_product_line_id);
							uploadModel.setCreatedBy(1);
							uploadModel.setLogoType(FilenameUtils.getExtension(fileName));
							if (service.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
								response = service.addProductLineUploadDetails(uploadModel);
								response.setStatus(1);
								response.setMessage("Productline logo created successfully");
							} else {

								response.setStatus(0);
								response.setMessage("productline logo  upload Failed due to logo Upload Failure");
							}
						}
					} else {
						response.setStatus(0);
						response.setMessage("productline logo should be 35*35 pixel");
					}
				} else {
					response.setStatus(0);
					response.setMessage("Productline logo required");
				}

			}

			else {
				response.setStatus(0);
				response.setMessage("Only png file types are supported");
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/Json")
	public Response editProductLineLogo(
			@RequestParam(value = "fk_product_line_id", defaultValue = "0") int fk_product_line_id,
			@RequestParam("logo") MultipartFile productUploads) {

		Response response = new Response();
		try {
			if (fk_product_line_id == 0) {
				response.setMessage(environment.getProperty("app.allFieldsManditory"));
				response.setStatus(0);
				return response;
			}
			MultipartFile file = (MultipartFile) productUploads;
			if ((file.getContentType().toLowerCase().equals("image/png"))) {
				String UPLOADED_FOLDER = null;
				String PRODUCT_FOLDER = environment.getProperty("app.productlinelogouploaddir");
				String fileName = null;
				String filePath = null;
				ProductLineLogoUploadModel uploadModel = null;
				response = service.updateProductlineLogo(fk_product_line_id);
				if (response.getStatus() > 0) {
					BufferedImage image = ImageIO.read(file.getInputStream());
					if (!(file.isEmpty() || file.getSize() == 0)) {
						System.out.println("Logo width: " + image.getWidth());
						System.out.println("Logo heiht: " + image.getHeight());
						if (image.getWidth() <= 35 && image.getHeight() <= 35) {
							response = service.createDirectories(fk_product_line_id, PRODUCT_FOLDER);

							System.out.println(PRODUCT_FOLDER);
							UPLOADED_FOLDER = response.getUploadPath();
							MultipartFile file_object = productUploads;
							if (!file_object.isEmpty()) {
								fileName = file_object.getOriginalFilename();
								filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
								uploadModel = new ProductLineLogoUploadModel();
								uploadModel.setLogoName(fileName);
								uploadModel.setLogoLocation(filePath);
								uploadModel.setFk_product_line_id(fk_product_line_id);
								uploadModel.setCreatedBy(1);
								uploadModel.setLogoType(FilenameUtils.getExtension(fileName));
								if (service.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
									response = service.addProductLineUploadDetails(uploadModel);
									response.setStatus(1);
									response.setMessage("Productline logo updated successfully");
								} else {

									response.setStatus(0);
									response.setMessage("productline logo  upload Failed due to logo Upload Failure");
								}
							}
						} else {
							response.setStatus(0);
							response.setMessage("logo should be 35*35 pixel");
						}
					} else {
						response.setStatus(0);
						response.setMessage("Productline logo required");
					}
				}
			} else {
				response.setStatus(0);
				response.setMessage("Only png file types are supported");
			}

		} catch (TransactionSystemException e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	@RequestMapping("/imageViewer")
	public ResponseEntity<?> getProductLineLogo(
			@RequestParam(value = "fk_product_line_id", defaultValue = "0") int fk_product_line_id) throws IOException {
		try {
			if (fk_product_line_id == 0) {
				return new ResponseEntity<String>("All Fields are mandatory", HttpStatus.OK);
			}
			ProductLineLogoUploadModel list = service.getProductlinelogofile(fk_product_line_id);
			String productlinepath = null;
			if (list != null) {
				productlinepath = list.getLogoLocation();
				if (!(productlinepath.isEmpty())) {
					productlinepath = list.getLogoLocation();
					File file = new File(productlinepath);
					if (file.exists()) {
						System.out.println("filename: " + list.getLogoName());
						System.out.println("filepath: " + productlinepath);
						InputStreamResource resource = new InputStreamResource(new FileInputStream(productlinepath));
						return ResponseEntity.ok()
								.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + productlinepath)
								.contentType(MediaType.IMAGE_PNG).body(resource);
					} else {
						return new ResponseEntity<String>("Productline Logo Not Found", HttpStatus.OK);
					}
				} else {
					return new ResponseEntity<String>("Productline Logo Not Found", HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<String>("Productline Logo Not Found", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Productline Logo Not Found", HttpStatus.OK);
	}
}
