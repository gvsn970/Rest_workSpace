package com.nexiilabs.autolifecycle.resources;


public class FileDownloadModel {
private String filePath;
private String fileName;
public String getFilePath() {
	return filePath;
}
public void setFilePath(String filePath) {
	this.filePath = filePath;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("FileViewerModel [filePath=");
	builder.append(filePath);
	builder.append(", fileName=");
	builder.append(fileName);
	builder.append("]");
	return builder.toString();
}

}
