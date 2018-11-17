package com.redscarf.ibone.configuration.props.rpcs;

import lombok.Data;

import java.io.Serializable;

@Data
public class DecorationServerFeignProperties implements Serializable {
    private String protocol = "http";
    private String name = "ibone-b2b2c-decoration-server";  //系统服务名字
}
