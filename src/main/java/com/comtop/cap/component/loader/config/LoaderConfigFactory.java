/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.cap.component.loader.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * loader config factory 用于构建不同config
 * @author yangsai
 */
public class LoaderConfigFactory {
	
	/**
	 * 获取classpath中upload.properties文件内容并构建成config对象
	 * @return loaderConfig
	 */
	public static LoaderConfig createConfigFromProperties() {
		Properties prop = new Properties();
		try {
			prop.load(LoaderConfigFactory.class.getClassLoader().getResourceAsStream("fileload.properties"));
			String basePath = (String) prop.get("basepath");
			if(StringUtils.isNotBlank(basePath)){
				return new LoaderConfig(basePath);
			}

			String host = (String) prop.get("host");
			String username = (String) prop.get("username");
			String password = (String) prop.get("password");
			int port = Integer.parseInt((String)prop.get("port"));
			String encoding = (String) prop.get("encoding");
			String mainDirectory = (String) prop.get("mainDirectory");
			String visitUrl = (String) prop.get("visitUrl");
			LoaderConfig config = new LoaderConfig(host, username, password, port, encoding, mainDirectory);
			config.setVisitUrl(visitUrl);
			return config;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
