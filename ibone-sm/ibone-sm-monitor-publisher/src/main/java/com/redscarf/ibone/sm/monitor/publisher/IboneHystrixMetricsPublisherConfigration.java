package com.redscarf.ibone.sm.monitor.publisher;

import com.netflix.hystrix.strategy.HystrixPlugins;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 指标发布器配置类，用于初始化发布器
 */
@Configuration
public class IboneHystrixMetricsPublisherConfigration {
    @Bean
    public IboneHystrixMetricsPublisher getJboneHystrixMetricsPublisher(){
        IboneHystrixMetricsPublisher publisher = new IboneHystrixMetricsPublisher();
        HystrixPlugins.getInstance().registerMetricsPublisher(publisher);
        return publisher;
    }
}
