/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.ueditor.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.comtop.cap.component.loader.config.CapFileType;
import com.comtop.cap.component.loader.util.LoaderUtil;
import com.comtop.ueditor.define.BaseState;
import com.comtop.ueditor.define.FileType;
import com.comtop.ueditor.define.State;

/**
 * 附件上传类
 * 
 * @author yangsai
 *
 */
public class FileUploader {
	/**
	 * 上传配置
	 */
	private Map<String, Object> configMap;

	/**
	 * 构造方法
	 */
	public FileUploader() {	
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param configMap
	 *            configMap
	 */
	public FileUploader(Map<String, Object> configMap) {
		super();
		this.configMap = configMap;
	}

	/**
	 * 根据给定的上传参数上传文件，并返回对应信息
	 * 
	 * @param request 请求
	 *            
	 * @return 上传信息
	 */
	public State upload(HttpServletRequest request) {
		InputStream is = null;
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, 5);
		}
		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());
		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}
		try {

			List<FileItem> fileItems = upload.parseRequest(request);
			for (FileItem fileItem : fileItems) {
				if (!fileItem.isFormField()) {

//					String savePath = (String) configMap.get("savePath");
					String originFileName = fileItem.getName();
					String suffix = FileType.getSuffixByFilename(originFileName);
					if (!validType(suffix,(String[]) configMap.get("allowFiles"))) {
						return new BaseState(false, 8);
					}

					is = fileItem.getInputStream();
					String folderPath = LoaderUtil.getFolderPath(CapFileType.UEDITOR_FILE_KEY, LoaderUtil.generateUploadId());
					String fileName = LoaderUtil.createRandomFileName(originFileName);
					LoaderUtil.upLoad(is, folderPath, fileName);
					State storageState = new BaseState(true);
					storageState.putInfo("url", LoaderUtil.getVisitUrl() + folderPath + "/" + fileName);
					storageState.putInfo("type", suffix);
					storageState.putInfo("original", originFileName);
					storageState.putInfo("size", fileItem.getSize());
					storageState.putInfo("title", fileName);
					
					return storageState;
				}
			}
			return new BaseState(false, 4);
		} catch (FileUploadException e) {
			return new BaseState(false, 6);
		} catch (IOException localIOException) {
			return new BaseState(false, 6);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取对应的配置
	 * 
	 * @param key
	 *            配置key
	 * @return 配置值
	 */
	public Object getConfig(String key) {
		return configMap.get(key);
	}

	/**
	 * 验证是文件类型是否符合要求
	 * 
	 * @param type
	 *            待验证的文件后缀名
	 * @param allowTypes
	 *            允许的后缀名列表
	 * @return true 合格 false 不合格
	 */
	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);
		return list.contains(type);
	}
}
