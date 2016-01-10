/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.cap.component.loader;

import java.io.InputStream;
import java.net.URI;

import com.comtop.cap.component.loader.util.LoaderUtil;

/**
 * 文件定位类，用于获取上传文件不同访问的路径
 * 
 * @author yangsai
 */
public class FileLocation {
    
    /** 上传后存放文件名 */
    private String fileName;
    
    /** 文件上传uploadId,各种业务点保存该id */
    private String uploadId;
    
    /** 文件上传uploadkey */
    private String uploadKey;
    
    /** 上传后文件存放目录 */
    private String folderPath;
    
    /** 上传后的文件uri */
    private URI uri;
    
    /**
     * 构造方法
     */
    public FileLocation() {
        super();
    }
    
    /**
     * 构造方法
     * 
     * @param uploadId 文件上传uploadId
     * @param folderPath 上传后文件存放目录
     * @param fileName 上传后存放文件名
     */
    public FileLocation(String uploadId, String folderPath, String fileName) {
        super();
        this.uploadId = uploadId;
        this.folderPath = folderPath;
        this.fileName = fileName;
    }
    
    /**
     * 构造方法
     * 
     * @param httpUrl {@link #toHttpUrlString()} 方法生成的httpUrl
     */
    public FileLocation(String httpUrl) {
        super();
        StringBuilder sbUrl = new StringBuilder(httpUrl.trim());
        sbUrl.delete(0, LoaderUtil.getVisitUrl().length());
        if (LoaderHelper.separator.equals(sbUrl.substring(0, 1))) {
            sbUrl.deleteCharAt(0);
        }
        int iLastSepIndex = sbUrl.lastIndexOf(LoaderHelper.separator);
        
        this.fileName = sbUrl.substring(iLastSepIndex + 1, sbUrl.length());
        this.folderPath = sbUrl.substring(0, iLastSepIndex);
        // String[] urlPart = sbUrl.toString().split(LoaderHelper.separator);
        // int length = urlPart.length;
        // Validate.notNull(urlPart[length - 2], "get uploadId fail");
        // this.uploadId = urlPart[length - 2];
        // Validate.notNull(urlPart[length - 3], "get folderPath fail");
        // this.folderPath = urlPart[length - 3] + LoaderHelper.separator + urlPart[length - 2];
    }
    
    /**
     * 转换为Http格式的url
     * 
     * @return Http格式的url
     */
    public String toHttpUrlString() {
        return LoaderUtil.getVisitUrl() + folderPath + "/" + fileName;
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
     * get uploadId
     * 
     * @return uploadId
     */
    public String getUploadId() {
        return uploadId;
    }
    
    /**
     * set uploadId
     * 
     * @param uploadId uploadId
     */
    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
    
    /**
     * get upload key
     * 
     * @return uploadKey
     */
    public String getUploadKey() {
        return uploadKey;
    }
    
    /**
     * @param uploadKey uploadKey
     */
    public void setUploadKey(String uploadKey) {
        this.uploadKey = uploadKey;
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
}
