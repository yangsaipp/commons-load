/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.cap.component.loader;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import com.comtop.cap.component.loader.exception.LoadException;

/**
 * 上传下载超级接口,定义了上传下载的方法<br>
 * 所有实现了该接口的类都必须实现文件上传下载删除等功能
 * 
 * @author sai.yang
 */
public interface Loadable {
    
    /**
     * 上传 ,该方法会调用openServer()方法打开一个连接 在下载完后会关闭连接<br>
     * 注意：该方法的实现有义务在上传完(不管成功还是失败)后将inputStream流关闭
     * 
     * @param inputStream 上传资源的IO
     * @param folderPath 上传后文件存放的文件夹路径
     * @param fileName 上传后文件存放的名称
     * @return 上传后的文件存储地址
     * @exception LoadException 当上传失败的时候会抛出<code>LoadException</code>
     */
    URI upload(InputStream inputStream, String folderPath, String fileName);
    
    /**
     * 下载,该方法会调用openServer()方法打开一个连接 在下载完后会关闭连接<br>
     * 注意：该方法的实现有义务在上传完(不管成功还是失败)后将outputStream流关闭
     * 
     * @param outputStream 下载资源的IO
     * @param folderPath 下载的文件所在的文件夹路径
     * @param fileName 要下载的文件名称
     * @exception LoadException 当获取InputStream失败的时候会抛出<code>LoadException</code>
     */
    void downLoad(OutputStream outputStream, String folderPath, String fileName);
    
    /**
     * 获取对应的文件输入流
     * 
     * @param folderPath 文件路径
     * @param fileName 文件名
     * @return 文件输入流
     */
    InputStream getFileInputStream(String folderPath, String fileName);
    
    /**
     * 删除服务器上的资源
     * 
     * @param folderPath 要删除的文件的路径
     * @param fileName 要删除的文件的名称
     * @return 删除是否成功
     * @exception LoadException 当删除失败的时候会抛出<code>LoadException</code>
     */
    boolean delete(String folderPath, String fileName);
    
    /**
     * 
     * @Methodname: getFileNamesFromFolder
     * @Discription: 获取文件夹下所有文件名
     * @param folderPath 路径
     * @return 文件夹下的所有文件名
     */
    String[] getFileNamesFromFolder(String folderPath);
}
