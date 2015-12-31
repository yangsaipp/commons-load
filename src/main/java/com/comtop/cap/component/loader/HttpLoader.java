/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.cap.component.loader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.commons.lang.StringUtils;

import com.comtop.cap.component.loader.config.LoaderConfig;
import com.comtop.cap.component.loader.exception.LoadException;

/**
 * http 上传方式
 * 
 * @author sai.yang
 *
 */
public class HttpLoader implements Loadable {
    
    /** 单例对象 */
    static final HttpLoader instance = new HttpLoader();
    
    /** 加载配置对象 */
    private LoaderConfig config;
    
    /** 构造函数 */
    HttpLoader() {
        super();
    }
    
    @Override
    public void downLoad(OutputStream outputStream, String folderPath, String fileName) {
        batchDownLoad(outputStream, folderPath, fileName);
    }
    
    @Override
    public URI upload(InputStream inputStream, String folderPath, String fileName) {
        return batchUpload(inputStream, folderPath, fileName);
    }
    
    @Override
    public boolean delete(String folderPath, String fileName) {
        return batchDelete(folderPath, fileName);
    }
    
//    @Override
    public void configure(LoaderConfig loaderConfig) {
        this.config = loaderConfig;
    }
    
    /**
     * 批量删除所使用的方法,该方法不会自己调用openServer()打开连接,完成之后也不会关闭连接<br>
     * 所以这里需要调用者自己调用openServer方法打开连接，在调用此方法进行上传,等所有删除完后需要自己调用closeServer关闭
     * 
     * @param folderPath 文件路径
     * @param fileName 文件名
     * @return 删除是否成功
     */
    public boolean batchDelete(String folderPath, String fileName) {
        File file = LoaderHelper.getFile(getFullFolderPath(folderPath), fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
    
    /**
     * 批量下载所使用的方法,该方法不会自己调用openServer()打开连接,完成之后也不会关闭连接<br>
     * 所以这里需要调用者自己调用openServer方法打开连接，在调用此方法进行上传,等所有下载完后需要自己调用closeServer关闭
     * 
     * @param outputStream 下载资源的IO
     * @param folderPath 下载的文件所在的文件夹路径
     * @param fileName 要下载的文件名称
     */
    public void batchDownLoad(OutputStream outputStream, String folderPath, String fileName) {
        InputStream inputStream = null;
        try {
            folderPath = getFullFolderPath(folderPath);
            inputStream = new BufferedInputStream(new FileInputStream(LoaderHelper.getFile(folderPath, fileName)));
            LoaderHelper.load(inputStream, outputStream);
            
        } catch (FileNotFoundException e) {
            throw new LoadException("服务器上文件" + fileName + "不存在，文件路径为：" + folderPath);
        } catch (IOException e) {
            // e.printStackTrace();
            throw new LoadException("下载出错!");
        } finally {
            // LoaderHelper.close(outputStream);
            LoaderHelper.close(inputStream);
        }
    }
    
    /**
     * 批量上传所使用的方法,该方法不会自己调用openServer()打开连接,完成之后也不会关闭连接<br>
     * 所以这里需要调用者自己调用openServer方法打开连接，在调用此方法进行上传,等所有上传完后需要自己调用closeServer关闭
     * 
     * @param inputStream 上传资源的IO
     * @param folderPath 上传后文件存放的文件夹路径
     * @param fileName 上传后文件存放的名称
     * @return 上传后的文件存储地址
     */
    public URI batchUpload(InputStream inputStream, String folderPath, String fileName) {
        // 检查文件夹路径是否存在,不存在就创建
        folderPath = getFullFolderPath(folderPath);
        File folderFile = new File(folderPath);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        
        OutputStream os = null;
        try {
            // 建立一个上传文件的输出流
            File file = LoaderHelper.getFile(folderPath, fileName);
            os = new BufferedOutputStream(new FileOutputStream(file));
            LoaderHelper.load(inputStream, os);
            return file.toURI();
        } catch (FileNotFoundException e) { // 文件无法创建
            // e.printStackTrace();
            throw new LoadException("服务器上创建文件" + fileName + "失败，文件创建路径为：" + folderPath);
        } catch (IOException e) {
            // e.printStackTrace();
            throw new LoadException("上传出错!");
        } finally {
            // close
            LoaderHelper.close(os);
        }
    }
    
    /**
     * 
     * @Methodname: getFullFolderPath
     * @Discription: 获取全路径(config里面有contextPath那么就会加上没有就不加)
     * @param folderPath 路径
     * @return 全路径
     *
     */
    private String getFullFolderPath(String folderPath) {
        if (StringUtils.isNotBlank(config.getBasePath())) {
            folderPath = LoaderHelper.replacePathSeparator(folderPath);
            
            if (!folderPath.startsWith(LoaderHelper.separator)) {
                return config.getBasePath() + LoaderHelper.separator + folderPath;
            }
            return config.getBasePath() + folderPath;
        }
        return folderPath;
    }
    
    /**
     * get config
     * 
     * @return 加载配置
     */
    public LoaderConfig getConfig() {
        return config;
    }
    
    /**
     * 获取路径下的文件列表
     * 
     * @param folderPath 路径
     * @return 文件列表
     */
    @Override
    public String[] getFileNamesFromFolder(String folderPath) {
        String[] names = getFileNames(folderPath);
        return names;
    }
    
    /**
     * 获取路径下的文件列表
     * 
     * @param folderPath 路径
     * @return 文件列表
     */
    private String[] getFileNames(String folderPath) {
        folderPath = getFullFolderPath(folderPath);
        File file = new File(folderPath);
        if (file.exists() && file.isDirectory()) {
            return file.list();
        }
        return null;
    }
    
    @Override
    public InputStream getFileInputStream(String folderPath, String fileName) {
        try {
            return new FileInputStream(LoaderHelper.getFile(getFullFolderPath(folderPath), fileName));
        } catch (FileNotFoundException e) {
            throw new LoadException("服务器上文件" + fileName + "不存在，文件路径为：" + folderPath);
        }
    }
    
}
