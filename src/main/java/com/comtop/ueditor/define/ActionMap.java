/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.ueditor.define;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义请求action类型
 */
@SuppressWarnings("serial")
public final class ActionMap {

	/** action map */
	public static final Map<String, Integer> mapping;
	// 获取配置请求
	/** config */
	public static final int CONFIG = 0;
	/** upload image */
	public static final int UPLOAD_IMAGE = 1;
	/** upload scrawl */
	public static final int UPLOAD_SCRAWL = 2;
	/** upload video */
	public static final int UPLOAD_VIDEO = 3;
	/** upload file */
	public static final int UPLOAD_FILE = 4;
	/** catch_image */
	public static final int CATCH_IMAGE = 5;
	/** list_file */
	public static final int LIST_FILE = 6;
	/** list_image */
	public static final int LIST_IMAGE = 7;
	
	static {
		mapping = new HashMap<String, Integer>(){{
			put( "config", ActionMap.CONFIG );
			put( "uploadimage", ActionMap.UPLOAD_IMAGE );
			put( "uploadscrawl", ActionMap.UPLOAD_SCRAWL );
			put( "uploadvideo", ActionMap.UPLOAD_VIDEO );
			put( "uploadfile", ActionMap.UPLOAD_FILE );
			put( "catchimage", ActionMap.CATCH_IMAGE );
			put( "listfile", ActionMap.LIST_FILE );
			put( "listimage", ActionMap.LIST_IMAGE );
		}};
	}
	
	/**
	 * get type
	 * @param key	key
	 * @return value
	 */
	public static int getType ( String key ) {
		return ActionMap.mapping.get( key );
	}
	
}
