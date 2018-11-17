package com.redscarf.ibone.errors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IboneErrorsPageConfiguration {
    @Bean
    public IboneErrorPageRegister getJboneErrorPageRigister(){
        return new IboneErrorPageRegister();
    }


}
