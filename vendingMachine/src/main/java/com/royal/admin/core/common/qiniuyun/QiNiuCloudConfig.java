package com.royal.admin.core.common.qiniuyun;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class QiNiuCloudConfig {
    private static final Logger log = LoggerFactory.getLogger (QiNiuCloudConfig.class);
    private static final String QINIU_ACCESS_KEY = "sKmFOwsv4wRrT5uZt1jPjb4HJDf_PjkSa_S5RtJe";
    private static final String QINIU_SECRET_KEY = "e3CiZtMrwhUSoOOVKiqQeZW1llIROLXMYu9z6U5-";
    private static final String QINIU_BUCKET = "vending";
    private static final String QINIU_DOMAIN = "http://q0h4opj59.bkt.clouddn.com/";

    /**
     * @param file 上传文件
     * @param key  文件夹/文件名
     * @return
     * @throws Exception
     */
    public String uploadQiNinCloud(InputStream file, String key) throws Exception {
        //开始上传
        Configuration cfg = new Configuration (Zone.zone0());
        UploadManager uploadManager = new UploadManager (cfg);
        Auth auth = Auth.create (QINIU_ACCESS_KEY, QINIU_SECRET_KEY);
        //生成token
        String upToken = auth.uploadToken (QINIU_BUCKET, key);

        try {
            uploadManager.put (file, key, upToken, null, null);
        } catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        return QINIU_DOMAIN + key;
    }

}
