package com.redscarf.ibone;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class IboneSysAdminInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(IboneSysAdminApplication.class).banner(new IboneSysAdminBanner()).logStartupInfo(true);
    }
}
