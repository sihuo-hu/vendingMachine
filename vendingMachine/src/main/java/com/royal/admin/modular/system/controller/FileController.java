package com.royal.admin.modular.system.controller;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.royal.admin.core.common.qiniuyun.QiNiuCloudConfig;
import com.royal.admin.core.util.MD5;
import com.royal.admin.modular.system.entity.ImgMd5;
import com.royal.admin.modular.system.model.ImgUploadResult;
import com.royal.admin.modular.system.service.ImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private ImgService imgMd5Service;
    @Resource
    private QiNiuCloudConfig qiNiuCloud;

    /**
     * @param typeName goods=商品
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ImgUploadResult upload(String typeName, HttpServletRequest request) {
        if(ToolUtil.isEmpty(typeName)){
            typeName = "goods";
        }
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            StringBuffer sb = new StringBuffer();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (!file.isEmpty()) {
                    //判断文件上传过没有
                    String md5 = null;
                    try {
                        md5 = MD5.getInputStreamMD5String(file.getInputStream());
                    } catch (IOException e) {
                        log.error("上传文件时，进行MD5失败：", e);
                        return new ImgUploadResult(1, e.getMessage());
                    }
                    if (md5 != null) {
                        ImgMd5 imgMd5 = imgMd5Service.getById(md5);
                        if (imgMd5 != null && ToolUtil.isNotEmpty(imgMd5.getImgUrl())) {
                            sb.append(imgMd5.getImgUrl() + ",");
                            if (imgMd5.getTypeName().indexOf(typeName) < 0) {
                                imgMd5.setTypeName(imgMd5.getTypeName() + "," + typeName);
                                imgMd5Service.updateById(imgMd5);
                            }
                            continue;
                        }
                    }
                    //调用文件上传
                    try {
                        String fileName = file.getOriginalFilename();
                        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                        //上传文件名   文件夹/文件
                        String key = typeName + "/" + System.currentTimeMillis() + "." + suffix;
                        String url = qiNiuCloud.uploadQiNinCloud(file.getInputStream(), key);
                        if (ToolUtil.isEmpty(url)) {
                            return new ImgUploadResult(1, "URL生成失败");
                        }
                        sb.append(url + ",");
                        ImgMd5 imgMd5 = new ImgMd5();
                        imgMd5.setTypeName(typeName);
                        imgMd5.setCreateTime(new Date());
                        imgMd5.setImgId(md5);
                        imgMd5.setImgUrl(url);
                        imgMd5Service.save(imgMd5);
                    } catch (Exception e) {
                        log.error("上传文件失败", e);
                        return new ImgUploadResult(1, e.getMessage());
                    }
                }
            }
            String url = sb.substring(0, sb.length() - 1);
            return new ImgUploadResult("src", url);
        }
        return new ImgUploadResult(1, "文件为空");
    }
}
