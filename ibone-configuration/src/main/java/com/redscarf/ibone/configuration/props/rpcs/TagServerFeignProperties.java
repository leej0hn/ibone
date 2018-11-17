package com.redscarf.ibone.configuration.props.rpcs;

import lombok.Data;

/**
 * 标签服务Feign配置
 */
@Data
public class TagServerFeignProperties {
    private String protocol = "http";
    private String name = "ibone-tag-server";  //系统服务名字
}
