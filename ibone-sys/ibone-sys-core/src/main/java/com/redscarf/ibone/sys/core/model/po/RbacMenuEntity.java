package com.redscarf.ibone.sys.core.model.po;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>function: 系统菜单表
 * @Author: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Data
@Table(name = "rbac_menu")
public class RbacMenuEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "system_id")
    private Integer systemId;
    /**
     * 父菜单
     */
    @Column(name = "pid")
    private Integer pid;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 链接
     */
    @Column(name = "url")
    private String url;
    /**
     * 打开方式
     */
    @Column(name = "target")
    private String target;
    /**
     * 排序
     */
    @Column(name = "orders")
    private Long orders;

    @Column(name = "add_time")
    private Timestamp addTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "version")
    private Integer version;
    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

}
