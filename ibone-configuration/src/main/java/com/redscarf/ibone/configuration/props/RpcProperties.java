package com.redscarf.ibone.configuration.props;

import com.redscarf.ibone.configuration.props.rpcs.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 远程调用配置
 */
@Data
public class RpcProperties implements Serializable {
    /**
     * 系统服务
     */
    private SysServerProperties sysServer = new SysServerProperties();
    private EbPortalServerProperties ebPortalServer = new EbPortalServerProperties();
    private ShopServerProperties shopServer = new ShopServerProperties();
    private TagServerProperties tagServer = new TagServerProperties();
    private ItemServerProperties itemServer = new ItemServerProperties();
    private DecorationServerProperties decorationServer = new DecorationServerProperties();

}
