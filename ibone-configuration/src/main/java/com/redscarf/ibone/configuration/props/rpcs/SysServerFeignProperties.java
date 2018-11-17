package com.redscarf.ibone.configuration.props.rpcs;

import lombok.Data;

/**
 * 系统服务Feign配置
 */
@Data
public class SysServerFeignProperties {
    private String protocol = "http";
    private String name = "ibone-sys-server";  //系统服务名字
}
