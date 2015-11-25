/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.ueditor.define;

/**
 * 处理状态接口
 * @author hancong03@baidu.com
 *
 */
public interface State {
	/**
	 * is Success
	 * @return 是否成功
	 */
	public boolean isSuccess ();
	
	/**
	 * put info
	 * @param name name
	 * @param val val
	 */
	public void putInfo( String name, String val );
	
	/**
	 * put info
	 * @param name name
	 * @param val val
	 */
	public void putInfo ( String name, long val );
	
	/**
	 * to JsonString
	 * @return json string
	 */
	public String toJSONString ();

}
