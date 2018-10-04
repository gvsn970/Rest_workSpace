package com.nexiilabs.autolifecycle.resources;

public interface FileDownloadService {

	FileDownloadModel getReleasefile(int fileId);

	FileDownloadModel getProductfile(int fileId);

	FileDownloadModel getProductLinefile(int fileId);
	
	FileDownloadModel getFeatureFile(int fileId);
	
	FileDownloadModel getReleasephaseFile(int fileId);

}
