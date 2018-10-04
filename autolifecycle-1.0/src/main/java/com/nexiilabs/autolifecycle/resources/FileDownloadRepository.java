package com.nexiilabs.autolifecycle.resources;

public interface FileDownloadRepository {

	FileDownloadModel getReleasefile(int fileId);
	
	FileDownloadModel getProductfile(int fileId);

	FileDownloadModel getProductLinefile(int fileId);
	
	FileDownloadModel getFeatureFile(int fileId);
	
	FileDownloadModel getReleasephaseFile(int fileId);

}
