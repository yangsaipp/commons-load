/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.cap.component.loader.exception;

/**
 * 上传下载异常类
 * @author yangsai
 */
public class LoadException extends RuntimeException {

	/** UID */
	private static final long serialVersionUID = 4616260031828120365L;
	
	/**
	 * 构造方法
	 * @param message 异常信息
	 * @param cause 原始异常
	 */
	public LoadException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造方法
	 * @param message 异常信息
	 */
	public LoadException(String message) {
		super(message);
	}
	
	
}
