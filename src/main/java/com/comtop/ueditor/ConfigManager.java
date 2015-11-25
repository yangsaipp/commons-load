/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.ueditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.comtop.cip.json.JSON;
import com.comtop.cip.json.JSONArray;
import com.comtop.cip.json.JSONObject;
import com.comtop.ueditor.define.ActionType;


/**
 * ConfigManager
 * @author yangsai
 *	
 */
public final class ConfigManager {
	/** rootPath */
	private final String rootPath;
	/** originalPath */
	private final String originalPath;
	/** configFileName */
	private static final String configFileName = "config.json";
	/** parentPath */
	private String parentPath = null;
	/** jsonConfig */
	private JSONObject jsonConfig = null;

	/**
	 * ConfigManager
	 * @param rootPath rootPath
	 * @param contextPath contextPath
	 * @param uri uri
	 */
	private ConfigManager(String rootPath, String contextPath, String uri) {
		rootPath = rootPath.replace("\\", "/");

		this.rootPath = rootPath;
		if (contextPath.length() > 0)
			this.originalPath = (this.rootPath + uri.substring(contextPath.length()));
		else {
			this.originalPath = (this.rootPath + uri);
		}

		initEnv();
	}

	/**
	 * getInstance
	 * @param rootPath rootPath
	 * @param contextPath contextPath
	 * @param uri uri
	 * @return ConfigManager
	 */
	public static ConfigManager getInstance(String rootPath,
			String contextPath, String uri) {
		try {
			return new ConfigManager(rootPath, contextPath, uri);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * @return 验证结果
	 */
	public boolean valid() {
		return this.jsonConfig != null;
	}

	/**
	 * @return jsonConfig
	 */
	public JSONObject getAllConfig() {
		return this.jsonConfig;
	}

	/**
	 * @param actionType actiontype
	 * @return Map
	 */
	public Map<String, Object> getConfig(ActionType actionType) {
		Map<String, Object> conf = new HashMap<String, Object>();
		String savePath = null;

		switch (actionType) {
		case UPLOADFILE:
			conf.put("isBase64", "false");
			conf.put("maxSize",Long.valueOf(this.jsonConfig.getLong("fileMaxSize")));
			conf.put("allowFiles", getArray("fileAllowFiles"));
			conf.put("fieldName", this.jsonConfig.getString("fileFieldName"));
			savePath = this.jsonConfig.getString("filePathFormat");
			break;
		case UPLOADIMAGE:
			conf.put("isBase64", "false");
			conf.put("maxSize",
					Long.valueOf(this.jsonConfig.getLong("imageMaxSize")));
			conf.put("allowFiles", getArray("imageAllowFiles"));
			conf.put("fieldName", this.jsonConfig.getString("imageFieldName"));
			savePath = this.jsonConfig.getString("imagePathFormat");
			break;
		case UPLOADVIDEO:
			conf.put("maxSize",
					Long.valueOf(this.jsonConfig.getLong("videoMaxSize")));
			conf.put("allowFiles", getArray("videoAllowFiles"));
			conf.put("fieldName", this.jsonConfig.getString("videoFieldName"));
			savePath = this.jsonConfig.getString("videoPathFormat");
			break;
		case UPLOADSCRAWL:
			conf.put("filename", "scrawl");
			conf.put("maxSize",Long.valueOf(this.jsonConfig.getLong("scrawlMaxSize")));
			conf.put("fieldName", this.jsonConfig.getString("scrawlFieldName"));
			conf.put("isBase64", "true");
			savePath = this.jsonConfig.getString("scrawlPathFormat");
			break;
		case CATCHIMAGE:
			conf.put("filename", "remote");
			conf.put("filter", getArray("catcherLocalDomain"));
			conf.put("maxSize",Long.valueOf(this.jsonConfig.getLong("catcherMaxSize")));
			conf.put("allowFiles", getArray("catcherAllowFiles"));
			conf.put("fieldName", this.jsonConfig.getString("catcherFieldName") + "[]");
			savePath = this.jsonConfig.getString("catcherPathFormat");
			break;
		case LISTIMAGE:
			conf.put("allowFiles", getArray("imageManagerAllowFiles"));
			conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
			conf.put("count", Integer.valueOf(this.jsonConfig.getInteger("imageManagerListSize")));
			break;
		case LISTFILE:
			conf.put("allowFiles", getArray("fileManagerAllowFiles"));
			conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
			conf.put("count", Integer.valueOf(this.jsonConfig.getInteger("fileManagerListSize")));
		}

		conf.put("savePath", savePath);
		conf.put("rootPath", this.rootPath);
		return conf;
	}

	/**
	 * initEnv
	 */
	private void initEnv() {
		File file = new File(this.originalPath);

		if (!file.isAbsolute()) {
			file = new File(file.getAbsolutePath());
		}
		this.parentPath = file.getParent();
		String configContent = readFile(getConfigPath());
		jsonConfig = JSON.parseObject(configContent);
	}

	/**
	 * @return String
	 */
	private String getConfigPath() {
		return this.parentPath + File.separator + configFileName;
	}

	/**
	 * @param key key
	 * @return String[]
	 */
	private String[] getArray(String key) {
		JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
		String[] result = new String[jsonArray.size()];

		int i = 0;
		for (int len = jsonArray.size(); i < len; i++) {
			result[i] = jsonArray.getString(i);
		}

		return result;
	}

	/**
	 * @param path path
	 * @return String
	 */
	private String readFile(String path) {
		String tmpContent = null;
		StringBuilder builder = new StringBuilder();	
		BufferedReader bfReader = null;
			try {
				bfReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
				while ((tmpContent = bfReader.readLine()) != null) {
					builder.append(tmpContent);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if(bfReader != null) {
						bfReader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		return filter(builder.toString());
	}

	/**
	 * @param input input
	 * @return String
	 */
	private String filter(String input) {
		return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
	}
}