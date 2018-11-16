package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>function: 组织表
 * @Author: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Data
@Table(name = "rbac_organization")
public class RbacOrganizationEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 所属上级
     */
    @Column(name = "pid")
    private Integer pid;
    /**
     * 组织名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 组织描述
     */
    @Column(name = "description")
    private String description;
    @Column(name = "add_time")
    private Timestamp addTime;
    @Column(name = "update_time")
    private Timestamp updateTime;
    @Column(name = "version")
    private Integer version;


}
