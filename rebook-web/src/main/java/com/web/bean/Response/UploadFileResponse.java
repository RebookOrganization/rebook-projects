package com.web.bean.Response;

public class UploadFileResponse {

  private String username;
  private String fileName;
  private String fileDownloadUri;
  private String fileType;
  private long size;

  public UploadFileResponse() {
  }

  public UploadFileResponse(String username, String fileName, String fileDownloadUri, String fileType, long size) {
    this.username = username;
    this.fileName = fileName;
    this.fileDownloadUri = fileDownloadUri;
    this.fileType = fileType;
    this.size = size;
  }

  public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
    this.fileName = fileName;
    this.fileDownloadUri = fileDownloadUri;
    this.fileType = fileType;
    this.size = size;
  }

  public String getUsername() { return username; }

  public void setUsername(String username) { this.username = username; }

  public String getFileName() { return fileName; }

  public void setFileName(String fileName) { this.fileName = fileName; }

  public String getFileDownloadUri() { return fileDownloadUri; }

  public void setFileDownloadUri(String fileDownloadUri) { this.fileDownloadUri = fileDownloadUri; }

  public String getFileType() { return fileType; }

  public void setFileType(String fileType) { this.fileType = fileType; }

  public long getSize() { return size; }

  public void setSize(long size) { this.size = size; }
}
