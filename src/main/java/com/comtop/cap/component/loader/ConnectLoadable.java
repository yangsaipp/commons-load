package com.comtop.cap.component.loader;

import com.comtop.cap.component.loader.config.LoaderConfig;

/**
 * 需要建立链接的上传方式，比如FTP
 * @author yangsai
 */
public interface ConnectLoadable extends Loadable {
	
	/**
     * 用于配置loader的相关参数,若loader没有相关参数,可以不理会
     * 
     * @param config 带有loader的相关配置信息
     */
    void configure(LoaderConfig config);
    
    /**
     * 打开一个服务器连接,用来进行文件操作
     */
    void connect();
    
    /**
     * 关闭连接
     */
     void disconnect();
    
}
