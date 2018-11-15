package com.redscarf.ibone.sm.register;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class IboneSmRegisterInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder
                .sources(IboneSmRegisterApplication.class)
                .banner(new IboneSmRegisterBanner())
                .logStartupInfo(true);
    }
}
