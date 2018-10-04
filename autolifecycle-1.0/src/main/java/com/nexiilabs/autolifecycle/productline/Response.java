package com.nexiilabs.autolifecycle.productline;

import java.util.List;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;

public class Response {
	private int productlineId;
	private String uploadPath;
	private int status;
	private String message;
	private int statusCode;
	private long issue_id;
	private String issuekey;
	private ResponseDTO responseDTO;
	private List<ResponseDTO> listofResponseDTO;

	
	public List<ResponseDTO> getListofResponseDTO() {
		return listofResponseDTO;
	}

	public void setListofResponseDTO(List<ResponseDTO> listofResponseDTO) {
		this.listofResponseDTO = listofResponseDTO;
	}

	public ResponseDTO getResponseDTO() {
		return responseDTO;
	}

	public void setResponseDTO(ResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}

	public long getIssue_id() {
		return issue_id;
	}

	public void setIssue_id(long issue_id) {
		this.issue_id = issue_id;
	}

	public String getIssuekey() {
		return issuekey;
	}

	public void setIssuekey(String issuekey) {
		this.issuekey = issuekey;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getProductlineId() {
		return productlineId;
	}

	public void setProductlineId(int productlineId) {
		this.productlineId = productlineId;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
