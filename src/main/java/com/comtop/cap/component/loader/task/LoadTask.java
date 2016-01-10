package com.comtop.cap.component.loader.task;

import com.comtop.cap.component.loader.Loadable;

/**
 * load 任务
 * @author yangsai
 *
 */
public interface LoadTask {
	
	/**
	 * 执行loader任务
	 * @param loader
	 */
	Object doTask(Loadable loader);
}
