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
 * mimeType
 * @author yangsai
 *
 */
public class MIMEType {
	
	/** types */
	public static final Map<String, String> types = new HashMap<String, String>(){{
		put( "image/gif", ".gif" );
		put( "image/jpeg", ".jpg" );
		put( "image/jpg", ".jpg" );
		put( "image/png", ".png" );
		put( "image/bmp", ".bmp" );
	}};
	
	/**
	 * 根据mine获取后缀
	 * @param mime mime
	 * @return  后缀
	 */
	public static String getSuffix ( String mime ) {
		return MIMEType.types.get( mime );
	}
	
}
