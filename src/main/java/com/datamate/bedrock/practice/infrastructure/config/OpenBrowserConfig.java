package com.datamate.bedrock.practice.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.awt.Desktop;
import java.net.URI;

@Configuration
public class OpenBrowserConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser() {
        // Only launch on your local machine (dev environment)
        // In a real app, you might check active profiles: if (env.acceptsProfiles("dev"))

        System.setProperty("java.awt.headless", "false"); // Ensure AWT can run

        try {
            String url = "http://localhost:" + serverPort + "/swagger-ui/index.html";

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("🚀 Browser launched: " + url);
            }
        } catch (Exception e) {
            // Silently fail if we are on a server without a screen (like Linux/Docker)
            System.err.println("Could not launch browser: " + e.getMessage());
        }
    }
}