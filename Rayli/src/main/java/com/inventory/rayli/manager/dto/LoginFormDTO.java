package com.inventory.rayli.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ApiModel("登录表单")
@Data
public class LoginFormDTO implements Serializable {
    @NotEmpty(
            message = "账号不能为空"
    )
    @ApiModelProperty(
            value = "账号/手机号码/邮箱",
            required = true
    )
    private String userAccount;
    @NotEmpty(
            message = "密码/验证码不能为空"
    )
    @ApiModelProperty(
            value = "密码/验证码",
            required = true
    )
    private String password;
    @ApiModelProperty("rsa秘钥标识符")
    private String rsaKey;
    @ApiModelProperty("登陆类型：0web、1微信")
    private Integer loginType;
    @ApiModelProperty("站点/终端")
    private String siteCode;
}
