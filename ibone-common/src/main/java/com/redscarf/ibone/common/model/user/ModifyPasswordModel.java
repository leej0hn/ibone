package com.redscarf.ibone.common.model.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ModifyPasswordModel {

    @Min(value = 1,message = "id不能为空,且必须大于0")
    private int id;
    @Length(max = 100,message = "密码最长不能超过100")
    @NotBlank(message = "密码不能为空")
    private String password;

}
