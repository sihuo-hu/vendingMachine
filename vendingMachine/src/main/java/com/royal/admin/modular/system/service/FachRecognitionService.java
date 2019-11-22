package com.royal.admin.modular.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.baiduyun.AipFaceConfig;
import com.royal.admin.core.common.constant.state.GoodsStatus;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.JSONUtils;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.mapper.CommodityMapper;
import com.royal.admin.modular.system.model.baiduyun.BdyResult;
import com.royal.admin.modular.system.model.baiduyun.DetectResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 百度云人脸识别
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class FachRecognitionService extends ServiceImpl<CommodityMapper, Commodity> {
    @Autowired
    private AipFaceConfig aipFaceConfig;
//quality
    public BdyResult detect(String base64Img) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age,quality");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "HIGH");

        String imageType = "BASE64";

        // 人脸检测
        JSONObject res = aipFaceConfig.aipFace().detect(base64Img, imageType, options);
        System.out.println(res.toString(2));
        DetectResult detectResult= DetectResult.toDetectResult(res);
        BdyResult bdyResult = BdyResult.toDetectResult(res,detectResult);
        if(DetectResult.isQualified(bdyResult)){

        }
        return bdyResult;
    }
}
