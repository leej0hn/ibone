package com.redscarf.ibone.configuration.props;

import java.io.Serializable;

public class SysProperties implements Serializable {
    /**
     * jdbc配置
     */
    private IdbcProperties jdbc = new IdbcProperties();

    /**
     * 系统名字（注册中心）
     * @return
     */
    private String serverName;

    public IdbcProperties getJdbc() {
        return jdbc;
    }

    public void setJdbc(IdbcProperties jdbc) {
        this.jdbc = jdbc;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String toString() {
        return "SysProperties{" +
                "jdbc=" + jdbc +
                ", serverName='" + serverName + '\'' +
                '}';
    }
}
