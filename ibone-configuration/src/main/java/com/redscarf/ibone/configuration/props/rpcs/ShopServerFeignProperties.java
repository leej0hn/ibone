package com.redscarf.ibone.configuration.props.rpcs;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopServerFeignProperties implements Serializable {
    private String protocol = "http";
    private String name = "ibone-b2b2c-shop-server";  //系统服务名字
}
