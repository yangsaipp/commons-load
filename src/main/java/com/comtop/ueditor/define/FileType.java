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
 * FileType
 * @author yangsai
 *
 */
public class FileType {

	/** jpg */
	public static final String JPG = "JPG";
	
	/** types */
	private static final Map<String, String> types = new HashMap<String, String>(){{
		
		put( FileType.JPG, ".jpg" );
		
	}};
	
	/**
	 * 获取后缀名
	 * @param key key
	 * @return 后缀
	 */
	public static String getSuffix ( String key ) {
		return FileType.types.get( key );
	}
	
	/**
	 * 根据给定的文件名,获取其后缀信息
	 * @param filename filename
	 * @return 后缀信息
	 */
	public static String getSuffixByFilename ( String filename ) {
		return filename.substring( filename.lastIndexOf( "." ) ).toLowerCase();
		
	}
	
}
