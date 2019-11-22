package com.royal.admin.core.util;

import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.royal.admin.core.common.constant.state.ZdyMachines;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import org.springframework.util.DigestUtils;

import java.util.Map;

public class ZdySignUtil {

    public static String getSign(Map<String, String> param) {
        String sign = Tools.formatParam(param);
        if (Tools.notEmpty(sign)) {
            sign = sign + "&key=" + ZdyMachines.SECRET.getMessage();
        } else {
            sign = sign + "key=" + ZdyMachines.SECRET.getMessage();
        }
        System.out.println(sign);
        sign = MD5.md5(sign);
        System.out.println(sign);
        sign = sign.toUpperCase();
        return sign;
    }

}
