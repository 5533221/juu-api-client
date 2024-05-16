package com.sdk;

import com.sdk.client.JunClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO 配置
 * @date 2024/5/5 20:18
 */
@Configuration
// 能够读取application.yml的配置,读取到配置之后,把这个读到的配置设置到我们这里的属性中,
@ConfigurationProperties("api.client")
@ComponentScan
@Data
public class ApiConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public JunClient junClient(){

        return new JunClient(accessKey,secretKey);
    }

}
