package com.redscarf.ibone.configuration;

import com.redscarf.ibone.configuration.props.CasProperties;
import com.redscarf.ibone.configuration.props.RpcProperties;
import com.redscarf.ibone.configuration.props.SysProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 所有配置的入口
 */
@Configuration
@ConfigurationProperties(prefix = "ibone")
public class IboneConfiguration {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 系统管理配置
     */
    private SysProperties sys = new SysProperties();

    /**
     * CAS配置
     */
    private CasProperties cas = new CasProperties();

    /**
     * 远程调用配置
     */
    private RpcProperties rpc = new RpcProperties();


    public SysProperties getSys() {
        return sys;
    }

    public void setSys(SysProperties sys) {
        this.sys = sys;
    }

    public CasProperties getCas() {
        return cas;
    }

    public void setCas(CasProperties cas) {
        this.cas = cas;
    }

    public RpcProperties getRpc() {
        return rpc;
    }

    public void setRpc(RpcProperties rpc) {
        this.rpc = rpc;
    }

    @Override
    public String toString() {
        return "IboneConfiguration{" +
                "sys=" + sys +
                ", cas=" + cas +
                ", rpc=" + rpc +
                '}';
    }

    @PostConstruct
    public void initProperty(){
        logger.info(this.toString());
    }
}
