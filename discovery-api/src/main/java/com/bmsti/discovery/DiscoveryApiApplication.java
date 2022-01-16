package com.bmsti.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApiApplication.class, args);
    }

}
