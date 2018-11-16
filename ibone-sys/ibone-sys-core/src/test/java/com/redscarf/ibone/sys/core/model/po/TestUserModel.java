package com.redscarf.ibone.sys.core.model.po;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>function:
 * <p>User: LeeJohn
 * <p>Date: 2017/12/01
 * <p>Version: 1.0
 */
@Data
@Table( name = "testuser")
public class TestUserModel implements Serializable{
    private static final long serialVersionUID = 3881695489406782002L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "subject")
    private String subject;
    @Column(name = "scope")
    private Integer scope;
}
