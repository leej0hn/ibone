package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;

/**
 * <p>function: 用户角色关联表
 * @Author: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Data
@Table(name = "rbac_user_role")
public class RbacUserRoleEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "role_id")
    private Integer roleId;

}
