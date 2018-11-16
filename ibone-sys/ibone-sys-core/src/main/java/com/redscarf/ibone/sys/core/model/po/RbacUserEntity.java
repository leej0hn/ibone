package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>function: 用户表
 * @Author: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Data
@Table(name = "rbac_user")
public class RbacUserEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "realname")
    private String realname;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "nick_name",length = 200)
    private String nickName;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "locked")
    private Integer locked;
    @Column(name = "add_time")
    private Timestamp addTime;
    @Column(name = "update_time")
    private Timestamp updateTime;
    @Column(name = "version")
    private Integer version;
    @Column(name = "salt")
    private String salt;
}
