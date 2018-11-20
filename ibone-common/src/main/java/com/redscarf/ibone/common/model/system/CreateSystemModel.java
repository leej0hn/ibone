package com.redscarf.ibone.common.model.system;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateSystemModel {

    @NotBlank(message = "系统路径不能为空.")
    private String basepath;
    private Byte status;
    @NotBlank(message = "系统名不能为空.")
    private String name;
    @NotBlank(message = "系统标题不能为空.")
    private String title;
    private String description;
    private long orders;
}
