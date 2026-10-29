package com.hexora.network;

import com.hexora.network.service.NetworkMonitorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(NetworkMonitorProperties.class)
public class NetworkMonitorConfiguration {
}
