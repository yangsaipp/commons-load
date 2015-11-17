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
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.comtop.cap.component.loader.LoaderFactory;
import com.comtop.cap.component.loader.config.LoaderConfig;
import com.comtop.cap.component.loader.config.LoaderConfigFactory;

/**
 * loader 工具类
 * @author yangsai
 *
 */
public class LoaderUtil {
	/** 系统配置 */
	private static LoaderConfig sysLoaderConfig = LoaderConfigFactory.createConfigFromProperties();
	
	/**
	 * 根据给定的文件名生成8位随记文件名用于上传文件使用
	 * @param fileName 带有后缀的文件名
	 * @return 随记文件名
	 */
	public static String createRandomFileName(String fileName) {
		if(fileName == null || fileName.indexOf(".") < 0) {
			return null;
		}
		return RandomStringUtils.randomAlphanumeric(16).toUpperCase() + getSuffix(fileName); 
	}
	
	/**
	 * 根据给定的文件名获取文件后缀名(带.)
	 * @param fileName 带有后缀的文件名
	 * @return 后缀名(带.)
	 */
	public static String getSuffix(String fileName) {
		if(fileName == null || fileName.indexOf(".") < 0) {
			return null;
		}
		return fileName.substring(fileName.indexOf("."), fileName.length());
	}
	
	/**
	 * 根据上传code获取对应的上传目录
	 * @param uploadCode 上传code
	 * @return 对应的上传目录
	 */
	public static String getFolderPathByUploadCode(String uploadCode) {
		if(StringUtils.isBlank(uploadCode)) {
			return DateFormatUtils.format(new Date(), "yyyyMMdd");
		}
		String path = "";
		if("document".equals(uploadCode)) {
			path = "document/";
		}
		return path + DateFormatUtils.format(new Date(), "yyyyMMdd");
		
	}
	
	/**
	 * 上传附件到对应的文件夹下
	 * @param inputStream	上传资源的IO
	 * @param folderPath	上传后文件存放的文件夹路径
	 * @param fileName		上传后文件存放的名称
	 */
	public static void upLoad(InputStream inputStream, String folderPath, String fileName) {
		LoaderFactory.createLoader(sysLoaderConfig).upload(inputStream, folderPath, fileName);
	}
	
	/**
	 * 上传附件到对应的文件夹下
	 * @param file   	 上传资源
	 * @param folderPath 上传后文件存放的文件夹路径
	 * @param fileName   上传后文件存放的名称
	 */
	public static void uoload(File file, String folderPath, String fileName) {
		try {
			upLoad(new FileInputStream(file), folderPath, fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 附件下载
	 * @param outputStream  下载资源的IO
	 * @param folderPath	下载的文件所在的文件夹路径
	 * @param fileName	            要下载的文件名称
	 */
	public static void downLoad(OutputStream outputStream, String folderPath, String fileName) {
		LoaderFactory.createLoader(sysLoaderConfig).downLoad(outputStream, folderPath, fileName);
	}
	
	/**
	 * 附件下载
	 * @param file  		下载存放的file对象
	 * @param folderPath	下载的文件所在的文件夹路径
	 * @param fileName	            要下载的文件名称
	 */
	public static void downLoad(File file, String folderPath, String fileName) {
		try {
			downLoad(new FileOutputStream(file), folderPath, fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param folderPath 删除的文件所在的文件夹路径
	 * @param fileName 要删除的文件名称
	 */
	public static void delete(String folderPath, String fileName) {
		LoaderFactory.createLoader(sysLoaderConfig).delete(folderPath, fileName);
	}
}
