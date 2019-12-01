package com.royal.admin.core.common.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.baidu.aip.face.AipFace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Aliyunconfig {

    String ACCESS_KEY_ID = "LTAI4FhTLciwtPRvmGpbEcWJ";
    String ACCESS_SECRET="NjAJdwY77CuPbP4ErZeKz3sTMubpZ5";

    public static int i = 0;

    @Bean(name = "iAcsClient")
    @Primary
    public IAcsClient iAcsClient() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        return  client;
    }
}
