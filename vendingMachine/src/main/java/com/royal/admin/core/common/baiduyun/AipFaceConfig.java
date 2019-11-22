package com.royal.admin.core.common.baiduyun;

import com.baidu.aip.face.AipFace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AipFaceConfig {

    public static final String APP_ID = "17759330";
    public static final String API_KEY = "0cHQp7OOyIiVxrDdwPrmdrHk";
    public static final String SECRET_KEY = "IvQBxHA5gf7wdH9EGmAROYGqQCpTQalg";
    public static int i = 0;

    @Bean(name = "aipFace")
    @Primary
    public AipFace aipFace() {
        System.out.println(i++);
        AipFace client = new AipFace(APP_ID,API_KEY,SECRET_KEY);
        return  client;
    }
}
