package com.royal.admin.core.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliyunSMSUtils {

    private static Logger log = LoggerFactory.getLogger(AliyunSMSUtils.class);
    public static final String ACCESS_KEY_ID = "LTAI4FhTLciwtPRvmGpbEcWJ";
    public static final String ACCESS_SECRET="NjAJdwY77CuPbP4ErZeKz3sTMubpZ5";
    public static final String VERIFICATION = "SMS_178985484";
    public static final String SIGN = "成都猛追湾跳舞机";

    public static boolean sendVerification(String phone,String code) {

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SIGN);
        request.putQueryParameter("TemplateCode", VERIFICATION);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_SECRET);
            IAcsClient client = new DefaultAcsClient(profile);
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            if(response.getData().indexOf("\"Code\":\"OK\"")>=0){
                return true;
            }else{
                return false;
            }
        } catch (ServerException e) {
            log.error("短信发送失败：",e);
            return false;
        } catch (ClientException e) {
            log.error("短信发送失败：",e);
            return false;
        }
    }

    public static void main(String[] args) {
        AliyunSMSUtils.sendVerification("17706513351","123456");
    }



}
