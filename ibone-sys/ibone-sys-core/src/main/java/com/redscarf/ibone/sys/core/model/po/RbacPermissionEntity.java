package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>function: 权限表
 * @Author: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Data
@Table(name = "rbac_permission")
public class RbacPermissionEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 所属系统
     */
    @Column(name = "system_id")
    private Integer systemId;
    /**
     * 所属上级
     */
    @Column(name = "menu_id")
    private Integer menuId;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 类型(1:功能,2:菜单,3:按钮)
     */
    @Column(name = "type")
    private Integer type;
    /**
     * 权限值
     */
    @Column(name = "permission_value")
    private String permissionValue;

    @Column(name = "add_time")
    private Timestamp addTime;
    @Column(name = "update_time")
    private Timestamp updateTime;
    @Column(name = "version")
    private Integer version;

}
