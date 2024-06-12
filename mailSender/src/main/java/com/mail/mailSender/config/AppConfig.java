package com.mail.mailSender.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public Cloudinary getCloudinary(){
        Map config = new HashMap();

        config.put("cloud_name","dtk2b3edv");
        config.put("api_key","586135674745544");
        config.put("api_secret","X-rSiQmB7c2MqkRieq7U6yYjCCQ");
        config.put("secure",true);

        return new Cloudinary(config);
    }
}
