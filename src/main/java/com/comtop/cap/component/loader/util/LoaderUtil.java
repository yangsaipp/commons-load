/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.cap.component.loader.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.comtop.cap.component.loader.FileLocation;
import com.comtop.cap.component.loader.LoaderFactory;
import com.comtop.cap.component.loader.LoaderHelper;
import com.comtop.cap.component.loader.config.CapFileType;
import com.comtop.cap.component.loader.config.LoaderConfig;
import com.comtop.cap.component.loader.config.LoaderConfigFactory;

/**
 * loader 工具类
 * 
 * @author yangsai
 *
 */
public class LoaderUtil {
    
    /** 系统配置 */
    private static LoaderConfig sysLoaderConfig = LoaderConfigFactory.createConfigFromProperties();
    
    /**
     * 根据给定的文件名生成8位随记文件名用于上传文件使用
     * 
     * @param fileName 带有后缀的文件名
     * @return 随记文件名
     */
    public static String createRandomFileName(String fileName) {
        if (fileName == null || fileName.indexOf(".") < 0) {
            return null;
        }
        return RandomStringUtils.randomAlphanumeric(16).toUpperCase() + getSuffix(fileName);
    }
    
    /**
     * 根据给定的文件名获取文件后缀名(带.)
     * 
     * @param fileName 带有后缀的文件名
     * @return 后缀名(带.)
     */
    public static String getSuffix(String fileName) {
        if (fileName == null || fileName.indexOf(".") < 0) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }
    
    /**
     * @return	生成的UploadId
     */
    public static String generateUploadId() {
    	return RandomStringUtils.randomAlphanumeric(16).toUpperCase();
    }
    
    /**
     * 获取服务器上文件的下载流
     * 
     * @param folderPath 上传后文件存放的文件夹路径
     * @param fileName 上传后文件存放的名称
     * @return 服务器上文件的下载流
     */
    public static InputStream getFileInputStream(String folderPath, String fileName) {
    	return LoaderFactory.getLoader(sysLoaderConfig).getFileInputStream(folderPath, fileName);
    }
    
    /**
     * 根据上传code获取对应的上传目录
     * 
     * @param uploadKey 上传业务key
     * @param uploadId 上传id
     * @return 对应的上传目录
     */
    public static String getFolderPath(String uploadKey, String uploadId) {
    	Validate.notEmpty(uploadId, "upload must be not empty.");
        return "/" + CapFileType.getFilePathByKey(uploadKey) + "/" + uploadId;
        
    }
    
    /**
     * 上传附件到对应的文件夹下
     * 
     * @param inputStream 上传资源的IO
     * @param folderPath 上传后文件存放的文件夹路径
     * @param fileName 上传后文件存放的名称
     * @return 上传的文件路径
     */
    public static URI upLoad(InputStream inputStream, String folderPath, String fileName) {
    	try {
    		return LoaderFactory.getLoader(sysLoaderConfig).upload(inputStream, folderPath, fileName);
    	}finally {
           	LoaderHelper.close(inputStream);
    	}
    }

    /**
     * 上传附件到对应的文件夹下
     * 
     * @param file 上传资源
     * @param folderPath 上传后文件存放的文件夹路径
     * @param fileName 上传后文件存放的名称
     * @return 上传的文件路径
     */
    public static URI upLoad(File file, String folderPath, String fileName) {
		try {
			return upLoad(new FileInputStream(file), folderPath, fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * 上传附件到对应的文件夹下
     * 
     * @param inputStream 上传资源的IO
     * @param uploadKey 文件key
     * @param uploadId  上传时生成的id
     * @param fileName 上传后文件存放的名称
     * @return 上传的文件位置
     */
    public static FileLocation upLoad(InputStream inputStream, String uploadKey, String uploadId, String fileName) {
    	FileLocation fileLocation = new FileLocation(uploadId, getFolderPath(uploadKey, uploadId), fileName);
    	fileLocation.setUri(upLoad(inputStream, fileLocation.getFolderPath(), fileLocation.getFileName()));
    	return fileLocation;
    }
    
    /**
     * 上传附件到对应的文件夹下
     * 
     *  @param file 上传资源
     * @param uploadKey 文件key
     * @param uploadId  上传时生成的id
     * @param fileName 上传后文件存放的名称
     * @return 上传的文件位置
     */
    public static FileLocation upLoad(File file, String uploadKey, String uploadId, String fileName) {
    	FileLocation fileLocation = new FileLocation(uploadId, getFolderPath(uploadKey, uploadId), fileName);
    	fileLocation.setUri(upLoad(file, fileLocation.getFolderPath(), fileLocation.getFileName()));
    	return fileLocation;
    }
    
    /**
     * 附件下载
     * 
     * @param outputStream 下载资源的IO
     * @param uploadKey 文件key
     * @param uploadId 上传时生成的id
     * @param fileName 要下载的文件名称
     */
    public static void downLoad(OutputStream outputStream, String uploadKey, String uploadId, String fileName) {
        LoaderFactory.getLoader(sysLoaderConfig).downLoad(outputStream, LoaderUtil.getFolderPath(uploadKey, uploadId), fileName);
    }
    
    /**
     * 附件下载
     * 
     * @param outputStream 下载资源的IO
     * @param folderPath 下载的文件所在的文件夹路径
     * @param fileName 要下载的文件名称
     */
    public static void downLoad(OutputStream outputStream, String folderPath, String fileName) {
        LoaderFactory.getLoader(sysLoaderConfig).downLoad(outputStream, folderPath, fileName);
    }
    
    /**
     * 附件下载
     * 
     * @param file 下载存放的file对象
     * @param folderPath 下载的文件所在的文件夹路径
     * @param fileName 要下载的文件名称
     */
    public static void downLoad(File file, String folderPath, String fileName) {
        try {
            downLoad(new FileOutputStream(file), folderPath, fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 删除附件
     * @param uploadKey 文件key
     * @param uploadId 上传时生成的id
     * @param fileName 要删除的文件名称
     * @return 文件删除状态 true 删除成功 false不成功
     */
    public static boolean delete(String uploadKey, String uploadId, String fileName) {
        return LoaderFactory.getLoader(sysLoaderConfig).delete(getFolderPath(uploadKey, uploadId), fileName);
    }
    
    /**
     * 删除附件
     * @param uploadKey 文件key
     * @param uploadId 上传时生成的id
     */
    public static void delete(String uploadKey, String uploadId) {
    	String[] fileName = getFileNames(uploadKey, uploadId);
    	String folderPath = getFolderPath(uploadKey, uploadId);
    	for(String file : fileName) {
    		LoaderFactory.getLoader(sysLoaderConfig).delete(folderPath, file);
    	}
    }
    
    /**
     * 删除附件
     * @param folderPath 删除的文件所在的文件夹路径
     * @param fileName 要删除的文件名称
     */
    public static void deleteAttachment(String folderPath, String fileName) {
        LoaderFactory.getLoader(sysLoaderConfig).delete(folderPath, fileName);
    }
    
    /**
     * 获取对应的文件列表
     * @param uploadKey 文件key
     * @param uploadId  上传时生成的id
     * @return 文件列表
     */
    public static String[] getFileNames(String uploadKey, String uploadId) {
    	return LoaderFactory.getLoader(sysLoaderConfig).getFileNamesFromFolder(getFolderPath(uploadKey, uploadId));
    }
    
    /**
     * 获取配置的visitUrl
     * 
     * @return visitUrl
     */
    public static String getVisitUrl() {
        return StringUtils.defaultString(sysLoaderConfig.getVisitUrl());
    }
    
    /**
     * 获取对应的文件输入流
     * 
     * @param folderPath 下载的文件所在的文件夹路径
     * @param fileName 要下载的文件名称
     * @return 获取对应的文件输入流
     */
    public static InputStream getInputstream(String folderPath, String fileName) {
        return LoaderFactory.getLoader(sysLoaderConfig).getFileInputStream(folderPath, fileName);
    }
}
