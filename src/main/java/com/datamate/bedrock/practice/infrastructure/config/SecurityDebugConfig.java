package com.datamate.bedrock.practice.infrastructure.config;

import com.datamate.bedrock.framework.common.security.config.SecurityProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SecurityDebugConfig {

    @Bean
    public CommandLineRunner debugSecurityProperties(SecurityProperties securityProps,
            com.datamate.bedrock.framework.common.monitoring.config.MonitoringProperties monitoringProps) {
        return args -> {
            System.out.println("================ SECURITY DEBUG ================");
            System.out.println("Security Enabled: " + securityProps.isEnabled());
            System.out.println("Permit All Paths:");
            if (securityProps.getPermitAll() != null) {
                Arrays.stream(securityProps.getPermitAll()).forEach(path -> System.out.println(" - " + path));
            } else {
                System.out.println(" - (null)");
            }
            System.out.println("------------------------------------------------");
            System.out.println("Monitoring Enabled: " + monitoringProps.isEnabled());
            System.out.println("Monitoring Impl: " + monitoringProps.getImplementation());
            System.out.println("================================================");
        };
    }
}
