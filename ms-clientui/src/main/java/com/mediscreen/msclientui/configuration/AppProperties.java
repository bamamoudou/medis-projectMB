package com.mediscreen.msclientui.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mediscreen-ms-clientui")
public class AppProperties {
    private String defaultUrl;

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }
}
