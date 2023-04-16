package com.ey.filterfiles.entities;

public class FailedFiles {

	String fileName;
	String reason;

	public FailedFiles() {

	}

	public FailedFiles(String fileName, String reason) {
		
		this.fileName = fileName;
		this.reason = reason;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
