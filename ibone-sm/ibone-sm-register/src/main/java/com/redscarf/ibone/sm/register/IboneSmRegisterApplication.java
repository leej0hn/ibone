package com.redscarf.ibone.sm.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 启动类
 * @Author : LeeJohn
 * Date : 2018-11-15
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableEurekaServer
@Slf4j
public class IboneSmRegisterApplication implements CommandLineRunner {
    @Value("${spring.application.name}")
    private String appName;

    public static void main(String[] args) {
        new SpringApplicationBuilder(IboneSmRegisterApplication.class)
                .banner(new IboneSmRegisterBanner())
                .logStartupInfo(true)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("{} boot successfully", this.appName);
    }
}
