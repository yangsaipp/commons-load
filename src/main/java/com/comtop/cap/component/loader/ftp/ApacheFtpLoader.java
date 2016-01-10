/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.cap.component.loader.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comtop.cap.component.loader.ConnectLoadable;
import com.comtop.cap.component.loader.LoaderHelper;
import com.comtop.cap.component.loader.config.LoaderConfig;
import com.comtop.cap.component.loader.exception.LoadException;

/**
 * FTPLoader实现<br>
 * 采用的是Apache的ftpClient实现
 * 
 * @author sai.yang
 * 
 */
public class ApacheFtpLoader implements ConnectLoadable {
    
    /**
     * log
     */
    private final static Logger LOG = LoggerFactory.getLogger(ApacheFtpLoader.class);
    
    /** ApacheFtpLoader 对象 单例 */
    public static ApacheFtpLoader instance = null;
    
    /** 上传下载配置对象 */
    private LoaderConfig config;
    
    /** ftp路径分隔符 */
    private final static String ftp_separator = "/";
    
    /** ftp client */
    private FTPClient ftpClient;
    
    /**
     * 构造方法
     */
    ApacheFtpLoader() {
        super();
        connect();
    }
    
    /**
     * 构造方法
     * 
     * @param config
     *            配置
     */
    public ApacheFtpLoader(LoaderConfig config) {
        super();
        this.config = config;
        connect();
    }
    
    /**
     * 将保存的服务器连接关闭掉
     */
    @Override
    public void disconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
                // System.out.println("ftp断开");
            } catch (IOException e) {
                // System.out.println("ftp关闭失败");
                e.printStackTrace();
            }
        }
    }
    
//    @Override
//    protected void finalize() throws Throwable {
//        closeServer();
//    }
    
    /**
     * 打开一个服务器连接,用来进行上传和下载.<br>
     * 主要用于批量上传和下载
     */
    public void connect() {
        if (config == null) {
            RuntimeException e = new LoadException("找不到相关ftp连接配置参数,无法连接到ftp服务器！");
            LOG.error(e.getMessage(), e);
            throw e;
        }
        
        // 要是ftpClient是活动的就不在创建连接
        if (ftpClient != null && ftpClient.isAvailable()) {
            return;
        }
        
        ftpClient = new FTPClient();
        
        // 设置对中文文件名上传下载的支持
        ftpClient.setControlEncoding(config.getEncoding());
        // FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
        // ftpClient.configure(conf);
        
        // conf.setServerLanguageCode("zh");
        
        try {
            // 连接远程ftp服务器
            if (config.getPort() == -1) {
                ftpClient.connect(config.getHost());
            } else {
                ftpClient.connect(config.getHost(), config.getPort());
            }
            // 登录
            if (!ftpClient.login(config.getUsername(), config.getPassword())) {
                RuntimeException e = new LoadException("ftp服务器登录失败，请检查连接的用户名和密码！");
                LOG.error(e.getMessage(), e);
                throw e;
            }
            
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(config.getMainDirectory());
            
            // System.out.println("登录成功！server 地址：" + config.getHost() +
            // " port:" + config.getPort() );
        } catch (SocketException e) {
            RuntimeException loadException = new LoadException("ftp服务器连接失败，请检查连接配置。 " + getFtpConfig(config), e);
            LOG.error(loadException.getMessage(), loadException);
            throw loadException;
        } catch (IOException e) {
            RuntimeException loadException = new LoadException("ftp服务器连接失败，请检查连接配置。" + getFtpConfig(config), e);
            LOG.error(loadException.getMessage(), loadException);
            throw loadException;
        }
        
    }
    
    @Override
    public void configure(LoaderConfig loaderConfig) {
        this.config = loaderConfig;
    }
    
    @Override
    public void downLoad(OutputStream outputStream, String folderPath, String fileName) {
        // 获取client
//        connect();
        try {
            String path = LoaderHelper.getFilePath(folderPath, fileName);
            if (!ftpClient.retrieveFile(path, outputStream)) {
                RuntimeException loadException = new LoadException("请求下载的资源不存在!资源文件为："
                    + ftpClient.printWorkingDirectory() + path);
                LOG.error(loadException.getMessage(), loadException);
                throw loadException;
            }
            // LoaderHelper.close(outputStream); //放到外层UploadUtil去关闭
        } catch (IOException e) {
            RuntimeException loadException = new LoadException("ftp下载出错!", e);
            LOG.error(loadException.getMessage(), loadException);
            throw loadException;
        } finally {
            // 关闭连接
             LoaderHelper.close(outputStream); //放到外层UploadUtil去关闭
//            disconnect();
        }
    }
    
    @Override
    public URI upload(InputStream inputStream, String folderPath, String fileName) {
//        connect();
        try {
            // 如果目录不存在
            if (!isDirectoryExists(folderPath)) {
                // 创建
                mkd(folderPath);
            }
            boolean uploaded = ftpClient.storeFile(LoaderHelper.getFilePath(folderPath, fileName), inputStream);
            if (!uploaded) {
                RuntimeException loadException = new LoadException("ftp上传出错!请检查服务器上传目录是否存在："
                    + ftpClient.printWorkingDirectory() + folderPath);
                LOG.error(loadException.getMessage(), loadException);
                throw loadException;
            }
            StringBuffer buffer = new StringBuffer();
            
            buffer.append("ftp://").append(config.getUsername()).append(":").append(config.getPassword()).append("@");
            buffer.append(config.getHost());
            buffer.append(folderPath);
            buffer.append(fileName);
            return new URI(URLEncoder.encode(buffer.toString(), "ISO-8859-1"));
        } catch (IOException e) {
            RuntimeException loadException = new LoadException("ftp上传出错!", e);
            LOG.error(loadException.getMessage(), loadException);
            throw loadException;
        } catch (Exception e) {
            RuntimeException loadException = new LoadException("ftp上传出错!", e);
            LOG.error(loadException.getMessage(), loadException);
            throw loadException;
        } finally {
            // 关闭连接
//            disconnect();
        }
        
    }
    
    @Override
    public boolean delete(String folderPath, String fileName) {
//        connect();
        String filePath = LoaderHelper.getFilePath(folderPath, fileName);
        try {
            return ftpClient.deleteFile(filePath);
        } catch (IOException e) {
            RuntimeException loadException = new LoadException(String.format("ftp删除文件  %s 出错.%n", filePath), e);
            LOG.error(loadException.getMessage(), loadException);
            throw loadException;
        } finally {
//            disconnect();
        }
        
    }
    
    /**
     * 判断对应的文件夹目录在服务器端是否存在
     * 
     * @param folderPath
     *            目录
     * @return true 存在 false 不存在
     * @throws IOException
     *             IO异常
     */
    public boolean isDirectoryExists(String folderPath) throws IOException {
        if (ftp_separator.equals(folderPath)) {
            return true;
        }
        
        FTPFile[] ftpFileArr = ftpClient.listDirectories(folderPath);
        return ftpFileArr.length > 0;
        
        /*
         * for(FTPFile f : ftpFileArr) { //System.out.println(f.getName()); }
         */
    }
    
    /**
     * 通过给定的文件夹路径创建文件夹(文件定位的开始是用当前的工作目录)<br>
     * 要是父文件夹不存在也会创建父文件夹
     * 
     * @param folderPath
     *            目录
     * @throws IOException
     *             IO异常
     */
    public void mkd(String folderPath) throws IOException {
        // 获取到文件夹路径中每个文件夹的名称
        List<String> folderList = LoaderHelper.getFolderPathList(folderPath);
        // 获取ftp当前的工作目录
        String ftpWorkingDirectory = ftpClient.printWorkingDirectory();
        
        for (String folder : folderList) {
            // 如果不存在就创建
            if (!isDirectoryExists(folder)) {
                ftpClient.makeDirectory(folder);
                // System.out.println("创建目录：" + folder);
            }
            // 并切换为当前的工作目录
            ftpClient.changeWorkingDirectory(folder);
        }
        // 创建完成后切换回原来的工作目录
        ftpClient.changeWorkingDirectory(ftpWorkingDirectory);
    }
    
    /**
     * 批量删除所使用的方法,该方法不会自己调用openServer()打开连接,完成之后也不会关闭连接<br>
     * 所以这里需要调用者自己调用openServer方法打开连接，在调用此方法进行上传,等所有删除完后需要自己调用closeServer关闭
     * 
     * @param folderPath
     *            文件路径
     * @param fileName
     *            文件名
     */
    public void batchDelete(String folderPath, String fileName) {
        try {
            ftpClient.deleteFile(LoaderHelper.getFilePath(folderPath, fileName));
        } catch (IOException e) {
            RuntimeException loadException = new LoadException("ftp上传出错!", e);
            LOG.error(loadException.getMessage(), loadException);
            throw loadException;
        }
    }
    
    /**
     * 批量下载所使用的方法,该方法不会自己调用openServer()打开连接,完成之后也不会关闭连接<br>
     * 所以这里需要调用者自己调用openServer方法打开连接，在调用此方法进行上传,等所有下载完后需要自己调用closeServer关闭
     * 
     * @param outputStream
     *            下载资源的IO
     * @param folderPath
     *            下载的文件所在的文件夹路径
     * @param fileName
     *            要下载的文件名称
     */
    public void batchDownLoad(OutputStream outputStream, String folderPath, String fileName) {
        try {
            String path = LoaderHelper.getFilePath(folderPath, fileName);
            if (!ftpClient.retrieveFile(path, outputStream)) {
                RuntimeException loadException = new LoadException("请求下载的资源不存在!资源文件为："
                    + ftpClient.printWorkingDirectory() + path);
                LOG.error(loadException.getMessage(), loadException);
                throw loadException;
            }
        } catch (IOException e) {
            RuntimeException loadException = new LoadException("ftp下载出错!", e);
            LOG.error(loadException.getMessage(), loadException);
            throw loadException;
        }
    }
    
    /**
     * 批量上传所使用的方法,该方法不会自己调用openServer()打开连接,完成之后也不会关闭连接<br>
     * 所以这里需要调用者自己调用openServer方法打开连接，在调用此方法进行上传,等所有上传完后需要自己调用closeServer关闭
     * 
     * @param inputStream
     *            上传资源的IO
     * @param folderPath
     *            上传后文件存放的文件夹路径
     * @param fileName
     *            上传后文件存放的名称
     */
    public void batchUpload(InputStream inputStream, String folderPath, String fileName) {
        try {
            // 如果目录不存在
            if (!isDirectoryExists(folderPath)) {
                // 创建
                mkd(folderPath);
            }
            
            if (!ftpClient.storeFile(LoaderHelper.getFilePath(folderPath, fileName), inputStream)) {
                throw new LoadException("ftp上传出错!请检查服务器上传目录是否存在：" + ftpClient.printWorkingDirectory() + folderPath);
            }
            // LoaderHelper.close(outputStream); //放到外层UploadUtil去关闭
        } catch (IOException e) {
            throw new LoadException("ftp上传出错!");
        }
    }
    
    @Override
    public String[] getFileNamesFromFolder(String folderPath) {
        String[] names = getFileNames(folderPath);
        return names;
    }
    
    /**
     * 获取路径下的文件列表
     * 
     * @param folderPath
     *            路径
     * @return 文件列表
     */
    private String[] getFileNames(String folderPath) {
        try {
//            connect();
            FTPFile[] objFTPFileArray = ftpClient.listFiles(folderPath);
            disconnect();
            if (objFTPFileArray.length > 0) {
                String[] fileNames = new String[objFTPFileArray.length];
                int index = 0;
                for (FTPFile ftpFile : objFTPFileArray) {
                    fileNames[index] = ftpFile.getName();
                }
                return fileNames;
            }
            return null;
        } catch (IOException e) {
            return null;
        } finally {
//            disconnect();
        }
    }
    
    /**
     * 获取config中ftp的信息
     * 
     * @param loaderConfig
     *            配置
     * @return ftp的配置信息
     */
    public String getFtpConfig(LoaderConfig loaderConfig) {
        return "host:" + loaderConfig.getHost() + " username:" + loaderConfig.getUsername() + " password:"
            + loaderConfig.getPassword() + " port:" + loaderConfig.getPort();
    }
    
    @Override
    public InputStream getFileInputStream(String folderPath, String fileName) {
        connect();
        try {
            String path = LoaderHelper.getFilePath(folderPath, fileName);
            return ftpClient.retrieveFileStream(path);
        } catch (Exception e) {
            RuntimeException loadException = new LoadException("ftp下载出错!");
            LOG.error(loadException.getMessage(), loadException);
            throw loadException;
        }
        
    }
}
