package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>function: 系统表
 * @Author: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Data
@Table(name = "rbac_system")
public class RbacSystemEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     根目录
     */
    @Column(name = "basepath")
    private String basepath;
    /**
     状态(-1:黑名单,1:正常)
     */
    @Column(name = "status")
    private Byte status;
    /**
     系统名称
     */
    @Column(name = "name")
    private String name;
    /**
     系统标题
     */
    @Column(name = "title")
    private String title;
    /**
     系统描述
     */
    @Column(name = "description")
    private String description;
    /**
     排序
     */
    @Column(name = "orders")
    private Long orders;
    /**
     创建时间
     */
    @Column(name = "add_time")
    private Timestamp addTime;
    /**
     更新时间
     */
    @Column(name = "update_time")
    private Timestamp updateTime;
    /**
     版本号
     */
    @Column(name = "version")
    private Integer version;
    /**
     是否注册为CAS服务
     */
    @Column(name = "service_registered")
    private Integer serviceRegistered;
    /**
     服务CAS过滤器路径，对应CAS的service_id
     */
    @Column(name = "service_cas_filter")
    private String serviceCasFilter;
    /**
     主题皮肤服务，用于加载服务
     */
    @Column(name = "service_theme_server")
    private String serviceThemeServer;
    /**
     服务I
     */
    @Column(name = "service_id")
    private Integer serviceId;
    /**
     服务描述
     */
    @Column(name = "service_description")
    private String serviceDescription;
    /**
     服务皮肤加载权重
     */
    @Column(name = "service_evaluation_order")
    private Integer serviceEvaluationOrder;
    /**
     服务名称
     */
    @Column(name = "service_name")
    private String serviceName;
    /**
     服务皮肤路径
     */
    @Column(name = "service_theme_path")
    private String serviceThemePath;

}
