
package com.comtop.cap.component.loader;

import java.io.InputStream;
import java.net.URI;

import com.comtop.cap.component.loader.util.LoaderUtil;

/**
 * @author yangsai
 *         上传文件信息
 */
public class LoadFile {
    
    /** 上传文件名 */
    private String name;
    
    /** 上传后存放文件名 */
    private String fileName;
    
    /** 上传后文件存放目录 */
    private String folderPath;
    
    /** 文件上传uploadId,各种业务点保存该id*/
    private String uploadId;
    
    /** 文件后缀 */
    private String fileSuffix;
    
    /** 文件contextType */
    private String contextType;
    
    /** 文件大小 */
    private long fileSize;
    
    /** 上传后的文件uri */
    private URI uri;
    
    
    /**
     * get 上传文件名
     * 
     * @return 上传文件名
     */
    public String getName() {
        return name;
    }
    
    /**
     * set 上传文件名
     * 
     * @param name 上传文件名
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * get 上传后存放文件名
     * 
     * @return 上传后存放文件名
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * set 上传后存放文件名
     * 
     * @param fileName 上传后存放文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * get 上传后文件存放目录
     * 
     * @return 上传后文件存放目录
     */
    public String getFolderPath() {
        return folderPath;
    }
    
    /**
     * set 上传后文件存放目录
     * 
     * @param folderPath 上传后文件存放目录
     */
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
    
    /**
     * get 文件类型
     * 
     * @return 文件类型
     */
    public String getFileSuffix() {
        return fileSuffix;
    }
    
    /**
     * set 文件类型
     * 
     * @param fileSuffix 文件类型
     */
    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }
    
    /**
     * get 文件大小
     * 
     * @return 文件大小
     */
    public long getFileSize() {
        return fileSize;
    }
    
    /**
     * set 文件大小
     * 
     * @param fileSize 文件大小
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    /**
     * get 文件contextType
     * 
     * @return 文件contextType
     */
    public String getContextType() {
        return contextType;
    }
    
    /**
     * set 文件contextType
     * 
     * @param contextType 文件contextType
     */
    public void setContextType(String contextType) {
        this.contextType = contextType;
    }
    
    /**
     * @return 获取 uri属性值
     */
    public URI getUri() {
        return uri;
    }
    
    /**
     * @param uri 设置 uri 属性值为参数值 uri
     */
    public void setUri(URI uri) {
        this.uri = uri;
    }
    
    /**
     * 获取对应的文件输入流
     *
     * @return 获取对应的文件输入流
     */
    public InputStream getInputStream() {
        return LoaderUtil.getInputstream(this.folderPath, this.fileName);
    }

    /**
     * get uploadId
     * @return uploadId
     */
	public String getUploadId() {
		return uploadId;
	}

	/**
	 * set uploadId
	 * @param uploadId uploadId
	 */
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}
}
