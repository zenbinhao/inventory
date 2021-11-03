package com.inventory.rayli.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/10/17 20:35
 * @Description: TODO 用户注册数据传输层
 */

import com.inventory.rayli.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "AccountUserDTO",description = "用户注册表")
public class AccountUserDTO extends BusinessFormDTO {

    @NotBlank(message = "用户姓名不能为空")
    @ApiModelProperty("用户姓名")
    private String userName;

    @NotBlank(message = "用户账户不能为空")
    @ApiModelProperty("用户账户")
    private String userAccount;

    @NotBlank(message = "用户密码不能为空")
    @ApiModelProperty("用户密码")
    private String userPassword;
}
