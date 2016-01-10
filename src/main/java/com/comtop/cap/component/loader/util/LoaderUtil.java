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
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.comtop.cap.component.loader.FileLocation;
import com.comtop.cap.component.loader.config.CapFileType;
import com.comtop.cap.component.loader.config.LoaderConfig;
import com.comtop.cap.component.loader.config.LoaderConfigFactory;
import com.comtop.cap.component.loader.task.LoadExecutorFactory;

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
     * @param uploadKey 文件key
     * @param uploadId  上传时生成的id
     * @param fileName 上传后文件存放的名称
     * @return 上传的文件位置
     */
    public static FileLocation upLoad(InputStream inputStream, String uploadKey, String uploadId, String fileName) {
    	FileLocation fileLocation = new FileLocation(uploadId, getFolderPath(uploadKey, uploadId), fileName);
    	fileLocation.setUri(LoadExecutorFactory.createExecutor().executeUpLoad(inputStream, fileLocation.getFolderPath(), fileLocation.getFileName()));
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
    	try {
			fileLocation.setUri(LoadExecutorFactory.createExecutor().executeUpLoad(new FileInputStream(file), fileLocation.getFolderPath(), fileLocation.getFileName()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
    	LoadExecutorFactory.createExecutor().executeDownLoad(outputStream, LoaderUtil.getFolderPath(uploadKey, uploadId), fileName);
    }
    
    
    /**
     * 删除附件
     * @param uploadKey 文件key
     * @param uploadId 上传时生成的id
     * @param fileName 要删除的文件名称， 若为空则删除文件夹路径下的所有文件
     * @return true 成功  false失败
     */
    public static boolean delete(String uploadKey, String uploadId, String fileName) {
        return LoadExecutorFactory.createExecutor().executeDelete(getFolderPath(uploadKey, uploadId), fileName);
    }
    
    /**
     * 删除附件
     * @param uploadKey 文件key
     * @param uploadId 上传时生成的id
     * @return true 成功  false失败
     */
    public static boolean delete(String uploadKey, String uploadId) {
    	return delete(uploadKey, uploadId, null);
    }
    
    /**
     * 获取对应的文件列表
     * @param uploadKey 文件key
     * @param uploadId  上传时生成的id
     * @return 文件列表
     */
    public static String[] getFileNames(String uploadKey, String uploadId) {
    	return LoadExecutorFactory.createExecutor().executeListFile(getFolderPath(uploadKey, uploadId));
    }
    
    /**
     * 获取配置的visitUrl
     * 
     * @return visitUrl
     */
    public static String getVisitUrl() {
        return StringUtils.defaultString(sysLoaderConfig.getVisitUrl());
    }
}
