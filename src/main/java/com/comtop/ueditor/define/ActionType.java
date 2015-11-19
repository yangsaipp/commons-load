package com.comtop.ueditor.define;

/**
 * 根据Ueditor官网文档定义的 action type enum
 * @author yangsai
 *
 */
public enum ActionType {
	/** 获取配置 */
	CONFIG,
	/** 上传图片 */
	UPLOADIMAGE,
	/** 上传涂鸦 */
	UPLOADSCRAWL,
	/** 上传视频 */
	UPLOADVIDEO,
	/** 上传附件 */
	UPLOADFILE,
	/** 图片列表 */
	LISTIMAGE,
	/** 附件列表 */
	LISTFILE,
	/** 网上抓取图片 */
	CATCHIMAGE;
}