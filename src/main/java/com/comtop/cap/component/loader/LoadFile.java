package com.comtop.cap.component.loader;

/**
 * @author yangsai
 * 上传文件信息
 */
public class LoadFile {
	/** 上传文件名 */
	private String name;
	
	/** 上传后存放文件名  */
	private String fileName;
	
	/** 上传后文件存放目录 */
	private String folderPath;
	
	/** 文件后缀  */ 
	private String fileSuffix;
	
	/** 文件contextType  */
	private String contextType;
	
	/** 文件大小  */
	private long fileSize;

	/**
	 * get 上传文件名
	 * @return 上传文件名
	 */
	public String getName() {
		return name;
	}

	/**
	 * set 上传文件名
	 * @param name 上传文件名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get 上传后存放文件名
	 * @return 上传后存放文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * set 上传后存放文件名
	 * @param fileName 上传后存放文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * get 上传后文件存放目录
	 * @return 上传后文件存放目录
	 */
	public String getFolderPath() {
		return folderPath;
	}

	/**
	 * set 上传后文件存放目录
	 * @param folderPath 上传后文件存放目录
	 */
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	/**
	 * get 文件类型
	 * @return 文件类型
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * set 文件类型
	 * @param fileSuffix 文件类型
	 */
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	/**
	 * get 文件大小
	 * @return 文件大小
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * set 文件大小
	 * @param fileSize 文件大小
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * get 文件contextType
	 * @return 文件contextType
	 */
	public String getContextType() {
		return contextType;
	}
	
	/**
	 * set 文件contextType
	 * @param contextType 文件contextType
	 */
	public void setContextType(String contextType) {
		this.contextType = contextType;
	}
	
}
