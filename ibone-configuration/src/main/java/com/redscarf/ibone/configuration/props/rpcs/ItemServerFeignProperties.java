package com.redscarf.ibone.configuration.props.rpcs;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemServerFeignProperties implements Serializable {
    private String protocol = "http";
    private String name = "ibone-b2b2c-item-server";  //系统服务名字
}
