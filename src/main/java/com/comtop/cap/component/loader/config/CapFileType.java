/******************************************************************************
 * Copyright (C) 2015 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/
package com.comtop.cap.component.loader.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.comtop.cap.component.loader.exception.LoadException;

/**
 * CAP文件上传、下载组件，文件类型枚举类 所有通过CAP文件组件上传文件都必须指定文件类型，统一使用本枚举类
 * 
 * @author 李小强
 * @since 1.0
 * @version 2015-11-10 CAP
 */
public class CapFileType {
	
	/** 业务模型中的流程基本对象对应的附件 */
	public static final String BIZ_PROCESS_INFO_KEY = "BIZ_PROCESS_INFO";
	
	/** 业务模型中的流程节点对应的附件 */
	public static final String BIZ_PROCESS_NODE_KEY = "BIZ_PROCESS_NODE";
	
	/** 业务模型中的业务表单对应的附件 */
	public static final String BIZ_FORM_KEY = "BIZ_FORM";
	
	/** 通过ueditor上传的附件 */
	public static final String UEDITOR_FILE_KEY = "ueditor";
	
	/**
	 * 文件类型Map
	 */
	private static Map<String, String> fileTypeMap = new HashMap<String, String>();
	/***
	 * 静态代码块
	 */
	static {
		fileTypeMap.put(BIZ_PROCESS_INFO_KEY, "BIZ_PROCESS_INFO");
		fileTypeMap.put(BIZ_PROCESS_NODE_KEY, "BIZ_PROCESS_NODE");
		fileTypeMap.put(BIZ_FORM_KEY, "BIZ_FORM");
		fileTypeMap.put(UEDITOR_FILE_KEY, "ueditor");
	}

	/** 根据指定文件类型获取对应的文件存储相对路径
	 * @param fileType 文件类型
	 * @return  指定类型的文件配置路径
	 */
	public static String getFilePathByKey(String fileType){
		if(StringUtils.isBlank(fileType)){
			throw new LoadException("文件类型不能为空");
		}
		fileType = fileType.toUpperCase();
		if(!fileTypeMap.containsKey(fileType)){
			throw new LoadException("指定的文件类型不存在，请核对类型："+fileType+",是否指定配置");
		}
		return fileTypeMap.get(fileType);
	}

}
