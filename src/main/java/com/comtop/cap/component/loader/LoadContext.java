package com.comtop.cap.component.loader;

import java.io.InputStream;
import java.net.URI;

import com.comtop.cap.component.loader.config.LoaderConfig;
import com.comtop.cap.component.loader.config.LoaderConfigFactory;

/**
 *  上传下载上下文环境，存放上传下载时需要使用的环境参数
 * @author yangsai
 *
 */
public class LoadContext {
	
	/** loaderConfig */
    private LoaderConfig sysLoaderConfig;
    
    /** 加载的配置文件路径地址 */
    private String configPath;
    
    /** 单例对象 **/
    private static LoadContext instance;
    
    /**
     * 构造方法
     */
    private LoadContext() {
    	
    }
    
    /**
     * 获取loadContext实例
     * @param configPath
     * @return
     */
    public static LoadContext getInstance() {
    	if(instance != null) {
    		return instance;
    	}
    	instance = new LoadContext();
//    	instance.configPath = configPath;
    	instance.sysLoaderConfig = LoaderConfigFactory.createConfigFromProperties();
    	return instance; 
    }
    
    /**
     * 获取LoaderFactory
     * @return Loadable
     */
    public Loadable getLoader() {
    	return LoaderFactory.getLoader(sysLoaderConfig);
    }
    
}
