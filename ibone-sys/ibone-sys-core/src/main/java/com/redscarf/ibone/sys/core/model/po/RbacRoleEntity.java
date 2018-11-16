package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>function: 角色表
 * @Author: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Data
@Table(name = "rbac_role")
public class RbacRoleEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 角色名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 角色标题
     */
    @Column(name = "title")
    private String title;
    /**
     * 角色描述
     */
    @Column(name = "description")
    private String description;
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


}
