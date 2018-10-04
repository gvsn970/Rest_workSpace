package com.nexiilabs.autolifecycle.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileDownloadServiceImpl implements FileDownloadService
{
	@Autowired
	FileDownloadRepository fileDownloadRepository;
	@Override
	public FileDownloadModel getReleasefile(int fileId) {
		return fileDownloadRepository.getReleasefile(fileId);
	}

	@Override
	public FileDownloadModel getProductfile(int fileId) {
		//System.err.println("in service:::::::::::::::::::::::::::::");
		return fileDownloadRepository.getProductfile(fileId);
	}

	@Override
	public FileDownloadModel getProductLinefile(int fileId) {
		return fileDownloadRepository.getProductLinefile(fileId);
	}

	@Override
	public FileDownloadModel getFeatureFile(int fileId) {
		// TODO Auto-generated method stub
		return fileDownloadRepository.getFeatureFile(fileId);
	}

	@Override
	public FileDownloadModel getReleasephaseFile(int fileId) {
		// TODO Auto-generated method stub
		return fileDownloadRepository.getReleasephaseFile(fileId);
	}

}
