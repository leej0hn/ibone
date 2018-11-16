package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * <p>function: 用户安全问题，用于找回密码表
 * @Author: LeeJohn
 * <p>Date: 2018/11/16
 * <p>Version: 1.0
 */
@Data
@Table(name = "rbac_user_securityquestions")
public class RbacUserSecurityQuestionsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 问题
     */
    @Column(name = "question")
    private String question;

    /**
     * 答案
     */
    @Column(name = "answer")
    private String answer;

    /**
     *
     */
    @Column(name = "add_time")
    private Timestamp addTime;


    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "version")
    private Integer version;
}
