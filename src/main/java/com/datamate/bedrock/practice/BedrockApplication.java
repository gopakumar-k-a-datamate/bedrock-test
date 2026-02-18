package com.datamate.bedrock.practice;

import com.datamate.bedrock.framework.common.notifications.config.NotificationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(NotificationProperties.class)
@org.springframework.data.jpa.repository.config.EnableJpaRepositories(basePackages = {
                "com.datamate.bedrock.practice",
                "com.datamate.bedrock.framework"
})
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = {
                "com.datamate.bedrock.practice",
                "com.datamate.bedrock.framework"
})
public class BedrockApplication {

        public static void main(String[] args) {
                SpringApplication.run(BedrockApplication.class, args);
        }

}
