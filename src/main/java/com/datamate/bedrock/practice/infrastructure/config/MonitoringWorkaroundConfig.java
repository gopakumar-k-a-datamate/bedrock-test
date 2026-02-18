package com.datamate.bedrock.practice.infrastructure.config;

import com.datamate.bedrock.framework.common.monitoring.prometheus.config.PrometheusMonitoringAutoConfiguration;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import io.prometheus.metrics.model.registry.PrometheusRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * Workaround configuration to enable Monitoring and fix Registry connection.
 * 
 * 1. Imports framework configuration (which is missing its auto-config file).
 * 2. Explicitly defines PrometheusRegistry and PrometheusMeterRegistry bits
 * to ensure Actuator scrapes the same registry that usage metrics are written
 * to.
 */
@Configuration
@Import(PrometheusMonitoringAutoConfiguration.class)
public class MonitoringWorkaroundConfig {

    /**
     * Expose the underlying Prometheus Registry as a bean.
     * This ensures Spring Boot Actuator can locate the correct registry instance to
     * scrape.
     */
    @Bean
    public PrometheusRegistry prometheusRegistry() {
        return new PrometheusRegistry();
    }

    /**
     * Override the PrometheusMeterRegistry to use the shared PrometheusRegistry
     * bean.
     * This replaces the one from PrometheusMonitoringAutoConfiguration
     * (via @ConditionalOnMissingBean).
     */
    @Bean
    @Primary
    public PrometheusMeterRegistry prometheusMeterRegistry(PrometheusRegistry prometheusRegistry) {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT, prometheusRegistry,
                io.micrometer.core.instrument.Clock.SYSTEM);
    }
}
