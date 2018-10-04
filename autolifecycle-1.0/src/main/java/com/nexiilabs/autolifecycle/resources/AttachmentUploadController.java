package com.nexiilabs.autolifecycle.resources;

import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexiilabs.autolifecycle.features.FeaturesUploadModel;
import com.nexiilabs.autolifecycle.product.ProductUploadModel;
import com.nexiilabs.autolifecycle.productline.ProductLineUploadModel;
import com.nexiilabs.autolifecycle.releasephases.ReleasePhasesUploadModel;
import com.nexiilabs.autolifecycle.releases.ProductReleaseUploadModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@RestController
@RequestMapping("/upload")
public class AttachmentUploadController {
	@Autowired
	AttachmentUploadService attachmentUploadService;
	@Autowired
	Environment environment;

	@RequestMapping(value = "/createreleaseFile", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO createreleaseFileUpload(@RequestParam("files") MultipartFile[] productReleaseUploads,
			@RequestParam("releaseId") int releaseId) {
		ResponseDTO responseDTO = new ResponseDTO();
		String UPLOADED_FOLDER = null;
		String RELEASE_FOLDER = environment.getProperty("app.productreleaseuploaddir");
		String fileName = null;
		String filePath = null;
		ProductReleaseUploadModel uploadModel = null;
		try {
			RELEASE_FOLDER = RELEASE_FOLDER + "Release";
			responseDTO = attachmentUploadService.createDirectories(releaseId, RELEASE_FOLDER);
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			if (productReleaseUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(productReleaseUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ProductReleaseUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkReleaseId(releaseId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addReleaseUploadDetails(uploadModel);
					} else {
						allUploadStatus = false;
					}
				}
				if (allUploadStatus) {
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("Release file uploaded Successfully");
				} else {
					responseDTO = attachmentUploadService.deleteReleaseRecordInDatabase(releaseId);
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("Release file upload Failed due to File Upload Failure");
				}
			} else if (productReleaseUploads.length == 1) {
				MultipartFile file_object = productReleaseUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ProductReleaseUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkReleaseId(releaseId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addReleaseUploadDetails(uploadModel);
					} else {
						responseDTO = attachmentUploadService.deleteReleaseRecordInDatabase(releaseId);
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("Release file upload Failed due to File Upload Failure");
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

	@RequestMapping(value = "/updatereleaseFile", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO updatereleaseFileUpload(@RequestParam("files") MultipartFile[] productReleaseUploads,
			@RequestParam("releaseId") int releaseId) {
		ResponseDTO responseDTO = new ResponseDTO();
		String UPLOADED_FOLDER = null;
		String RELEASE_FOLDER = environment.getProperty("app.productreleaseuploaddir");
		String fileName = null;
		String filePath = null;
		ProductReleaseUploadModel uploadModel = null;
		try {
			RELEASE_FOLDER = RELEASE_FOLDER + "Release";
			responseDTO = attachmentUploadService.createDirectories(releaseId, RELEASE_FOLDER);
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			if (productReleaseUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(productReleaseUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ProductReleaseUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkReleaseId(releaseId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (!attachmentUploadService.checkFileExistancyForReleaseFile(releaseId, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addReleaseUploadDetails(uploadModel);
						} else {
							allUploadStatus = false;
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
						return responseDTO;
					}
				}
				if (allUploadStatus) {
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("Release file uploaded Successfully");
				} else {
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("Release file upload Failed due to File Upload Failure");
				}
			} else if (productReleaseUploads.length == 1) {
				MultipartFile file_object = productReleaseUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ProductReleaseUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkReleaseId(releaseId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (!attachmentUploadService.checkFileExistancyForReleaseFile(releaseId, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addReleaseUploadDetails(uploadModel);
						} else {
							responseDTO.setStatusCode(0);
							responseDTO.setMessage("Release file upload Failed due to File Upload Failure");
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

	@RequestMapping(value = "/createproductfile", method = RequestMethod.POST, produces = "application/Json")
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO productFileUpload(@RequestParam("files") MultipartFile[] productUploads,
			@RequestParam("fk_product_id") int productId) {
		// HttpSession userSession = request.getSession(true);

		String UPLOADED_FOLDER = null;
		String PRODUCT_FOLDER = environment.getProperty("app.productuploaddir");
		String fileName = null;
		String filePath = null;
		// int productId = 0;
		ResponseDTO responseDTO = new ResponseDTO();
		ProductUploadModel uploadModel = null;
		try {
			// System.err.println("PRODUCT_FOLDER::::"+PRODUCT_FOLDER);
			PRODUCT_FOLDER = PRODUCT_FOLDER + "Product";
			// System.err.println("PRODUCT_FOLDER1::::"+PRODUCT_FOLDER);
			responseDTO = attachmentUploadService.createDirectories(productId, PRODUCT_FOLDER);
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			// System.err.println("in product attachment
			// controller::::"+UPLOADED_FOLDER);
			if (productUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(productUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					// System.err.println("filePath::::" + filePath);
					uploadModel = new ProductUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkProductId(productId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setProduct_file_size(file_object.getSize() + " bytes");
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addProductUploadDetails(uploadModel);
					} else {
						allUploadStatus = false;
					}
				}
				// System.err.println("allUploadStatus::::::" + allUploadStatus);
				if (allUploadStatus) {
					// System.err.println("response in controller::::::::" +
					// response);
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("Product file upload successfully");
				} else {
					responseDTO = attachmentUploadService.deleteProductRecordInDatabase(productId);
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("product file upload Failed due to File Upload Failure");
				}

			} else if (productUploads.length == 1) {
				MultipartFile file_object = productUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ProductUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkProductId(productId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setProduct_file_size(file_object.getSize() + " bytes");
					// System.err.println("filePath::::"+filePath);
					// System.err.println("UPLOADED_FOLDER::"+UPLOADED_FOLDER);
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addProductUploadDetails(uploadModel);
						/*
						 * responseDTO.setStatusCode(1);
						 * responseDTO.setMessage("Product file upload successfully" );
						 */
					} else {
						responseDTO = attachmentUploadService.deleteProductRecordInDatabase(productId);
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("product upload file not Created");
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		// System.err.println("response::::::::"+responseDTO.toString());
		return responseDTO;
	}

	@RequestMapping(value = "/updateproductfile", method = RequestMethod.POST, produces = "application/Json")
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO updateProductFileUpload(@RequestParam("files") MultipartFile[] productUploads,
			@RequestParam("fk_product_id") int productId) {
		// HttpSession userSession = request.getSession(true);

		String UPLOADED_FOLDER = null;
		String PRODUCT_FOLDER = environment.getProperty("app.productuploaddir");
		String fileName = null;
		String filePath = null;
		ResponseDTO responseDTO = new ResponseDTO();
		ProductUploadModel uploadModel = null;
		try {
			// System.err.println("PRODUCT_FOLDER::::"+PRODUCT_FOLDER);
			PRODUCT_FOLDER = PRODUCT_FOLDER + "Product";
			// System.err.println("PRODUCT_FOLDER1::::"+PRODUCT_FOLDER);
			responseDTO = attachmentUploadService.createDirectories(productId, PRODUCT_FOLDER);
			// System.err.println("responseDTO:::"+responseDTO.toString());
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			// System.err.println("in product attachment
			// controller::::"+UPLOADED_FOLDER);
			if (productUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(productUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					// System.err.println("filePath::::" + filePath);
					uploadModel = new ProductUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkProductId(productId);
					uploadModel.setCreatedBy(1);
					
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					//uploadModel.setFileSize();
					uploadModel.setProduct_file_size(file_object.getSize() + " bytes");
		
					if (!attachmentUploadService.checkFileExistancyForProductFile(productId, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addProductUploadDetails(uploadModel);
						} else {
							allUploadStatus = false;
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
						return responseDTO;
					}
				}
				// System.err.println("allUploadStatus::::::" + allUploadStatus);
				if (allUploadStatus) {
					// System.err.println("response in controller::::::::" +
					// response);
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("Product file upload successfully");
				} else {
					responseDTO = attachmentUploadService.deleteProductRecordInDatabase(productId);
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("product file upload Failed due to File Upload Failure");
				}

			} else if (productUploads.length == 1) {
				MultipartFile file_object = productUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ProductUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkProductId(productId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setProduct_file_size(file_object.getSize() + " bytes");
					if (!attachmentUploadService.checkFileExistancyForProductFile(productId, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addProductUploadDetails(uploadModel);

						} else {
							responseDTO = attachmentUploadService.deleteProductRecordInDatabase(productId);
							responseDTO.setStatusCode(0);
							responseDTO.setMessage("product upload file not Created");
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
						return responseDTO;
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		// System.err.println("res::::::::::"+responseDTO.toString());
		return responseDTO;
	}

	@RequestMapping(value = "/createproductlineFile", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO createproductLineFileUpload(@RequestParam("files") MultipartFile[] productUploads,
			@RequestParam("productlineId") int productlineId) {
		ResponseDTO responseDTO = new ResponseDTO();

		String UPLOADED_FOLDER = null;
		String PRODUCT_FOLDER = environment.getProperty("app.productlineuploaddir");
		String fileName = null;
		String filePath = null;
		// int productlineId = 0;
		ProductLineUploadModel uploadModel = null;
		try {
			PRODUCT_FOLDER = PRODUCT_FOLDER + "ProductLine";
			responseDTO = attachmentUploadService.createDirectories(productlineId, PRODUCT_FOLDER);
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			// System.out.println(UPLOADED_FOLDER+"::::::UPLOADED_FOLDER");
			if (productUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(productUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					// System.err.println("filePath::::" + filePath);
					uploadModel = new ProductLineUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFk_product_line_id(productlineId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addProductLineUploadDetails(uploadModel);
					} else {
						allUploadStatus = false;
					}
				}
				if (allUploadStatus) {
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("ProductLine file uploaded Successfully");
				} else {
					responseDTO = attachmentUploadService.deleteProductLineRecordInDatabase(productlineId);
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("ProductLine file upload Failed due to File Upload Failure");
				}

			} else if (productUploads.length == 1) {
				MultipartFile file_object = productUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ProductLineUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFk_product_line_id(productlineId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					// System.err.println("filePath::::"+filePath);
					// System.err.println("UPLOADED_FOLDER::"+UPLOADED_FOLDER);
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addProductLineUploadDetails(uploadModel);
						responseDTO.setStatusCode(1);
						responseDTO.setMessage("Productline created successfully");
					} else {
						responseDTO = attachmentUploadService.deleteProductLineRecordInDatabase(productlineId);
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("productline file upload Failed due to File Upload Failure");
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

	@RequestMapping(value = "/updateproductLineFile", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO updateproductLineFileUpload(@RequestParam("files") MultipartFile[] productUploads,
			@RequestParam("product_line_id") int product_line_id) {
		ResponseDTO responseDTO = new ResponseDTO();

		String UPLOADED_FOLDER = null;
		String PRODUCT_FOLDER = environment.getProperty("app.productlineuploaddir");
		String fileName = null;
		String filePath = null;
		ProductLineUploadModel uploadModel = null;
		try {
			// RELEASE_FOLDER = RELEASE_FOLDER + "Release";
			PRODUCT_FOLDER = PRODUCT_FOLDER + "ProductLine";
			responseDTO = attachmentUploadService.createDirectories(product_line_id, PRODUCT_FOLDER);
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			System.err.println("UPLOADED_FOLDER:::" + UPLOADED_FOLDER);
			if (productUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(productUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ProductLineUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFk_product_line_id(product_line_id);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (!attachmentUploadService.checkFileExistancyForProductLineFile(product_line_id, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addProductLineUploadDetails(uploadModel);
						} else {
							allUploadStatus = false;
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
						return responseDTO;
					}
				}
				if (allUploadStatus) {
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("Productline file uploaded Successfully");
				} else {
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("Productline file upload Failed due to File Upload Failure");
				}
			} else if (productUploads.length == 1) {
				MultipartFile file_object = productUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ProductLineUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFk_product_line_id(product_line_id);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (!attachmentUploadService.checkFileExistancyForProductLineFile(product_line_id, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addProductLineUploadDetails(uploadModel);
						} else {
							responseDTO.setStatusCode(0);
							responseDTO.setMessage("Productline file upload Failed due to File Upload Failure");
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
						return responseDTO;
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	@RequestMapping(value = "/createfeaturesFile", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO createFeaturesFileUpload(@RequestParam("files") MultipartFile[] featuresUploads,
			@RequestParam("feature_id") int featureId) {
		ResponseDTO responseDTO = new ResponseDTO();
		String UPLOADED_FOLDER = null;
		String FEATURES_FOLDER = environment.getProperty("app.featuresuploaddir");
		String fileName = null;
		String filePath = null;
		FeaturesUploadModel uploadModel = null;
		try {
			FEATURES_FOLDER = FEATURES_FOLDER + "Features";
			responseDTO = attachmentUploadService.createDirectories(featureId, FEATURES_FOLDER);
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			if (featuresUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(featuresUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new FeaturesUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkfeatureId(featureId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					//System.err.println("innnnnnnnnnnnnn::::::::::::::File Size"+uploadModel.getFileSize());
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addFeaturesUploadDetails(uploadModel);
					} else {
						allUploadStatus = false;
					}
				}
				if (allUploadStatus) {
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("Features file uploaded Successfully");
				} else {
					responseDTO = attachmentUploadService.deleteFeaturesRecordInDatabase(featureId);
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("Features file upload Failed due to File Upload Failure");
				}
			} else if (featuresUploads.length == 1) {
				MultipartFile file_object = featuresUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new FeaturesUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkfeatureId(featureId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addFeaturesUploadDetails(uploadModel);
					} else {
						responseDTO = attachmentUploadService.deleteFeaturesRecordInDatabase(featureId);
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("Features file upload Failed due to File Upload Failure");
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

	@RequestMapping(value = "/updatefeaturesFile", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO updatefeaturesFileUpload(@RequestParam("files") MultipartFile[] featuresUploads,
			@RequestParam("feature_id") int featureId) {
		ResponseDTO responseDTO = new ResponseDTO();
		String UPLOADED_FOLDER = null;
		String FEATURES_FOLDER = environment.getProperty("app.featuresuploaddir");
		String fileName = null;
		String filePath = null;
		FeaturesUploadModel uploadModel = null;
		try {
			FEATURES_FOLDER = FEATURES_FOLDER + "Features";
			responseDTO = attachmentUploadService.createDirectories(featureId, FEATURES_FOLDER);
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			if (featuresUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(featuresUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new FeaturesUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkfeatureId(featureId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (!attachmentUploadService.checkFileExistancyForFeaturesFile(featureId, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addFeaturesUploadDetails(uploadModel);
						} else {
							allUploadStatus = false;
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
						return responseDTO;
					}
				}
				if (allUploadStatus) {
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("Features file uploaded Successfully");
				} else {
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("Features file upload Failed due to File Upload Failure");
				}
			} else if (featuresUploads.length == 1) {
				MultipartFile file_object = featuresUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new FeaturesUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkfeatureId(featureId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (!attachmentUploadService.checkFileExistancyForFeaturesFile(featureId, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addFeaturesUploadDetails(uploadModel);
						} else {
							responseDTO.setStatusCode(0);
							responseDTO.setMessage("Features file upload Failed due to File Upload Failure");
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	@RequestMapping(value = "/createreleasephaseFile", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO createReleasePhaseFileUpload(@RequestParam("files") MultipartFile[] releasephaseUploads,
			@RequestParam("fk_release_phase_id") int releasephaseId,
			@RequestParam("fk_release_id") int fkreleaseId,
			@RequestParam("fk_release_phase_map_id") int fkreleasephasemapId) {
		ResponseDTO responseDTO = new ResponseDTO();
		String UPLOADED_FOLDER = null;
		String RELEASEPHASE_FOLDER = environment.getProperty("app.releasephasesuploaddir");
		String fileName = null;
		String filePath = null;
		ReleasePhasesUploadModel uploadModel = null;
		try {
			RELEASEPHASE_FOLDER = RELEASEPHASE_FOLDER + "ReleasePhase";
			responseDTO = attachmentUploadService.createDirectories(releasephaseId, RELEASEPHASE_FOLDER);
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			if (releasephaseUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(releasephaseUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ReleasePhasesUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkreleasephaseId(releasephaseId);
					uploadModel.setFkreleaseId(fkreleaseId);
					uploadModel.setFkreleasephasemapId(fkreleasephasemapId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addReleasePhaseUploadDetails(uploadModel);
					} else {
						allUploadStatus = false;
					}
				}
				if (allUploadStatus) {
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("ReleasePhase file uploaded Successfully");
				} else {
					responseDTO = attachmentUploadService.deleteReleasePhaseRecordInDatabase(releasephaseId);
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("ReleasePhase file upload Failed due to File Upload Failure");
				}
			} else if (releasephaseUploads.length == 1) {
				MultipartFile file_object = releasephaseUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ReleasePhasesUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkreleasephaseId(releasephaseId);
					uploadModel.setFkreleaseId(fkreleaseId);
					uploadModel.setFkreleasephasemapId(fkreleasephasemapId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					//System.err.println("File Size::::::::"+file_object.getBytes().toString()+"size:::::::"+file_object.getSize());
					if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
						responseDTO = attachmentUploadService.addReleasePhaseUploadDetails(uploadModel);
					} else {
						responseDTO = attachmentUploadService.deleteReleasePhaseRecordInDatabase(releasephaseId);
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("ReleasePhase file upload Failed due to File Upload Failure");
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@RequestMapping(value = "/updatereleasephaseFile", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
	public ResponseDTO updateReleasePhaseFileUpload(@RequestParam("files") MultipartFile[] releasephaseUploads,
			@RequestParam("fk_release_phase_id") int releasephaseId,
			@RequestParam("fk_release_id") int fkreleaseId,
			@RequestParam("fk_release_phase_map_id") int fkreleasephasemapId) {
		ResponseDTO responseDTO = new ResponseDTO();
		String UPLOADED_FOLDER = null;
		String RELEASEPHASE_FOLDER = environment.getProperty("app.releasephasesuploaddir");
		String fileName = null;
		String filePath = null;
		ReleasePhasesUploadModel uploadModel = null;
		try {
			RELEASEPHASE_FOLDER = RELEASEPHASE_FOLDER + "ReleasePhase";
			responseDTO = attachmentUploadService.createDirectories(releasephaseId, RELEASEPHASE_FOLDER);
			UPLOADED_FOLDER = responseDTO.getUploadPath();
			if (releasephaseUploads.length > 1) {
				boolean allUploadStatus = true;
				for (MultipartFile file_object : Arrays.asList(releasephaseUploads)) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ReleasePhasesUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkreleasephaseId(releasephaseId);
					uploadModel.setFkreleaseId(fkreleaseId);
					uploadModel.setFkreleasephasemapId(fkreleasephasemapId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (!attachmentUploadService.checkFileExistancyForReleasePhaseFile(releasephaseId, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addReleasePhaseUploadDetails(uploadModel);
						} else {
							allUploadStatus = false;
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
						return responseDTO;
					}
				}
				if (allUploadStatus) {
					responseDTO.setStatusCode(1);
					responseDTO.setMessage("ReleasePhase file uploaded Successfully");
				} else {
					responseDTO = attachmentUploadService.deleteReleasePhaseRecordInDatabase(releasephaseId);
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("ReleasePhase file upload Failed due to File Upload Failure");
				}
			} else if (releasephaseUploads.length == 1) {
				MultipartFile file_object = releasephaseUploads[0];
				if (!file_object.isEmpty()) {
					fileName = file_object.getOriginalFilename();
					filePath = Paths.get(UPLOADED_FOLDER, fileName).toString();
					uploadModel = new ReleasePhasesUploadModel();
					uploadModel.setFileName(fileName);
					uploadModel.setFileLocation(filePath);
					uploadModel.setFkreleasephaseId(releasephaseId);
					uploadModel.setFkreleaseId(fkreleaseId);
					uploadModel.setFkreleasephasemapId(fkreleasephasemapId);
					uploadModel.setCreatedBy(1);
					uploadModel.setFileType(FilenameUtils.getExtension(fileName));
					uploadModel.setFileSize(file_object.getSize() + " bytes");
					if (!attachmentUploadService.checkFileExistancyForReleasePhaseFile(releasephaseId, filePath)) {
						if (attachmentUploadService.saveFileToDisk(file_object, UPLOADED_FOLDER, fileName, filePath)) {
							responseDTO = attachmentUploadService.addReleasePhaseUploadDetails(uploadModel);
						} else {
							responseDTO.setStatusCode(0);
							responseDTO.setMessage("ReleasePhase file upload Failed due to File Upload Failure");
						}
					} else {
						responseDTO.setStatusCode(0);
						responseDTO.setMessage("No changes found");
					}
				}
			}
		} catch (TransactionSystemException e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Error While inserting to database");
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

}
