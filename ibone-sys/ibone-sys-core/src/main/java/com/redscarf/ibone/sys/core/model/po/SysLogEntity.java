package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;

/**
 * <p>function: 操作日志表
 * @Author: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Data
@Table(name = "sys_log")
public class SysLogEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 操作描述
     */
    @Column(name = "description")
    private String description;
    /**
     *操作用户
     */
    @Column(name = "username")
    private String username;
    /**
     * 操作时间
     */
    @Column(name = "start_time")
    private Long startTime;
    /**
     * 消耗时间
     */
    @Column(name = "spend_time")
    private Integer spendTime;

    /**
     * 根路径
     */
    @Column(name = "base_path")
    private String basePath;

    /**
     * URI
     */
    @Column(name = "uri")
    private String uri;
    /**
     * URL
     */
    @Column(name = "url")
    private String url;
    /**
     * 请求类型
     */
    @Column(name = "method")
    private String method;
    /**
     *
     */
    @Column(name = "parameter")
    private String parameter;
    /**
     * 用户标识
     */
    @Column(name = "user_agent")
    private String userAgent;
    /**
     * IP地址
     */
    @Column(name = "ip")
    private String ip;
    /**
     *
     */
    @Column(name = "result")
    private String result;
    /**
     * 权限值
     */
    @Column(name = "permissions")
    private String permissions;


}
