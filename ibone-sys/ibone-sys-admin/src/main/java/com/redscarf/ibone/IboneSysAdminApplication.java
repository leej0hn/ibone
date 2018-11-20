package com.redscarf.ibone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@Slf4j
public class IboneSysAdminApplication implements CommandLineRunner {
    @Value("${spring.application.name}")
    private String appName;
    public static void main(String[] args) {

        new SpringApplicationBuilder(IboneSysAdminApplication.class).banner(new IboneSysAdminBanner()).run(args);
    }
    @Override
    public void run(String... args) throws Exception {
        log.info("{} boot successfully", this.appName);
    }
}
