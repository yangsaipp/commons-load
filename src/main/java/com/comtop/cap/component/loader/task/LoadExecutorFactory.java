package com.comtop.cap.component.loader.task;

/**
 * LoadExecutor 工厂类
 * @author yangsai
 *
 */
public class LoadExecutorFactory {
	/**
	 * LoadExcutor 单例 
	 */
	private static LoadExecutor intance = new LoadExecutor();
	
	/**
	 * 创建默认的LoadExecutor
	 * @return
	 */
	public static LoadExecutor createExecutor() {
		return intance;
	}
	
}
