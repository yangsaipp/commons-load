package com.comtop.cap.component.loader.task;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.commons.lang.StringUtils;

import com.comtop.cap.component.loader.ConnectLoadable;
import com.comtop.cap.component.loader.LoadContext;
import com.comtop.cap.component.loader.Loadable;
import com.comtop.cap.component.loader.LoaderHelper;

/**
 * load 执行器
 * @author yangsai
 *
 */
public class LoadExecutor {

	/**
	 * 构造方法
	 */
	LoadExecutor() {
		
	}
    
	/**
	 * 执行
	 * @param task 
	 * @return 执行结果
	 */
    public Object execute(LoadTask task) {
    	Loadable loader = LoadContext.getInstance().getLoader();
    	connect(loader);
    	try {
    		return task.doTask(loader);
    	}finally {
           	disconnect(loader);
    	}
    }
   
    /**
     * 获取文件夹下所有文件名
     * @param folderPath 文件夹路径
     * @return 文件夹下的所有文件名
     */
    public String[] executeListFile(final String folderPath) {
    	 return (String[]) execute(new LoadTask() {
 			@Override
 			public Object doTask(Loadable loader) {
 				return loader.getFileNamesFromFolder(folderPath);
 			}
 		});
    }
    
    /**
     * 删除附件
     * @param folderPath 删除的文件所在的文件夹路径
     * @param fileName 要删除的文件名称， 若为空则删除文件夹路径下的所有文件
     * @return true 成功  false失败
     */
    public boolean executeDelete(final String folderPath, final String fileName) {
    	 return (boolean) execute(new LoadTask() {
 			@Override
 			public Object doTask(Loadable loader) {
 				
 				if(StringUtils.isNotBlank(fileName)) {
 					return loader.delete(folderPath, fileName);
 				}else {
 		    		for(String name :loader.getFileNamesFromFolder(folderPath)) {
 		    			if(!loader.delete(folderPath, name)) {
 		    				return false;
 		    			}
 		    		}
 		    		return true;
 				}
 			}
 		});
    }
    
    /**
     * 附件下载
     * 
     * @param outputStream 下载资源的IO
     * @param folderPath 下载的文件所在的文件夹路径
     * @param fileName 要下载的文件名称
     */
    public void executeDownLoad(final OutputStream outputStream, final String folderPath, final String fileName) {
        execute(new LoadTask() {
			@Override
			public Object doTask(Loadable loader) {
				try {
					loader.downLoad(outputStream, folderPath, fileName);
					return null;
				} finally {
					LoaderHelper.close(outputStream);
				}
			}
		});
    }
    
    /**
     * 上传附件到对应的文件夹下
     * 
     * @param inputStream 上传资源的IO
     * @param folderPath 上传后文件存放的文件夹路径
     * @param fileName 上传后文件存放的名称
     * @return 上传的文件路径
     */
    public URI executeUpLoad(final InputStream inputStream, final String folderPath, final String fileName) {
    	return (URI)execute(new LoadTask() {
			@Override
			public Object doTask(Loadable loader) {
				try {
					return loader.upload(inputStream, folderPath, fileName);
				} finally {
					LoaderHelper.close(inputStream);
				}
			}
		});
    }
    
    /**
     * connect
     * @param loader loader
     */
    private void connect(Loadable loader) {
    	if (loader instanceof ConnectLoadable) {
    		ConnectLoadable connectLoadable = (ConnectLoadable) loader;
    		connectLoadable.connect();
		}
    }
    
    /**
     * disconnect
     * @param loader loader
     */
    private void disconnect(Loadable loader) {
    	if (loader instanceof ConnectLoadable) {
    		ConnectLoadable connectLoadable = (ConnectLoadable) loader;
    		connectLoadable.disconnect();
		}
    }
}
