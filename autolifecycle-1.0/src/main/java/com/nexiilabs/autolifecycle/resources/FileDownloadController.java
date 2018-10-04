package com.nexiilabs.autolifecycle.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fileViewer")
public class FileDownloadController {
	@Autowired
	FileDownloadService fileDownloadService;
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<?> getSteamingFile(@RequestParam("file") String typeOfFile,
			@RequestParam("fileId") int fileId, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		FileDownloadModel fileViewerModel = new FileDownloadModel();
		try {
				if (typeOfFile != null && fileId != 0) {
					if (typeOfFile.equals("release")) {
						fileViewerModel = fileDownloadService.getReleasefile(fileId);
					}else if (typeOfFile.equals("productLine")) {
						fileViewerModel = fileDownloadService.getProductLinefile(fileId);
					}else if(typeOfFile.equals("product")){
						fileViewerModel = fileDownloadService.getProductfile(fileId);
					}
					else if(typeOfFile.equals("feature")){
						fileViewerModel = fileDownloadService.getFeatureFile(fileId);
					}else if(typeOfFile.equals("releasephase")){
						fileViewerModel = fileDownloadService.getReleasephaseFile(fileId);
					}
					if (fileViewerModel.getFilePath() != null) {
						File file = new File(fileViewerModel.getFilePath());
						InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
						if (file.exists()) {
							String mediaType = Files.probeContentType(file.toPath());
							return ResponseEntity.ok()
									.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
									.contentType(MediaType.parseMediaType(mediaType)).contentLength(file.length())
									.body(resource);
						} else {
							return new ResponseEntity<String>("File not found to download", HttpStatus.OK);
						}
					} else {
						return new ResponseEntity<String>("No files are found  to download", HttpStatus.OK);
					}
				} else {
					return new ResponseEntity<String>("All input fields are Mandatory", HttpStatus.OK);
				}
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("No files are found  to download", HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("", HttpStatus.ACCEPTED);
	}
}
