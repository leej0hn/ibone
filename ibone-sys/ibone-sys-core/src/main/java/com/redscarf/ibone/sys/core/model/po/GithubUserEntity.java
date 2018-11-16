package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Table(name = "github_user")
public class GithubUserEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Github用户ID
     */
    @Column(name = "github_id")
    private Long githubId;
    /**
     * RBAC用户ID
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * Github帐号
     */
    @Column(name = "login")
    private String login;
    /**
     * Github nodeId
     */
    @Column(name = "node_id")
    private String nodeId;
    /**
     * Github头像
     */
    @Column(name = "avatar_url")
    private String avatarUrl;
    /**
     * Github主页
     */
    @Column(name = "html_url")
    private String htmlUrl;
    /**
     * Github名字
     */
    @Column(name = "name")
    private String name;
    /**
     * Github单位
     */
    @Column(name = "company")
    private String company;
    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;
    /**
     * 博客
     */
    @Column(name = "blog")
    private String blog;
    /**
     * 创建时间
     */
    @Column(name = "add_time")
    private Timestamp addTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Timestamp updateTime;
    /**
     * 版本号
     */
    @Column(name = "version")
    private Integer version;

}
