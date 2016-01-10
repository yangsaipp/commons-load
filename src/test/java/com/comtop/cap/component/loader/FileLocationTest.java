/******************************************************************************
 * Copyright (C) 2015 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package com.comtop.cap.component.loader;

import org.junit.Assert;
import org.junit.Test;

/**
 * FIXME 类注释信息
 *
 * @author lizhongwen
 * @since jdk1.6
 * @version 2015年12月21日 lizhongwen
 */
public class FileLocationTest {
    
    /**
     * FIXME 方法注释信息
     *
     */
    @Test
    public void testHttpUrl() {
        FileLocation fl = new FileLocation(
            "http://10.10.5.223:8090/cap-ftp/DOC_EMBED_OBJECT_VIEW/5711E785B16C4AB599D64BE42376E8EE/业务流程图/image29.emf");
        System.out.println(fl.getFileName());
        System.out.println(fl.getFolderPath());
//        Assert.assertNotNull(fl.toInputStream());
    }
    
}
