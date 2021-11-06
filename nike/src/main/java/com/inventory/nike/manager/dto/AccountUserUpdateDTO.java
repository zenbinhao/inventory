package com.inventory.nike.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/4 14:35
 * @Description: TODO
 */

import com.inventory.nike.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "AccountUserUpdateDTO",description = "用户基础表")
public class AccountUserUpdateDTO extends BusinessFormDTO {

    @NotBlank(message = "用户姓名不能为空")
    @ApiModelProperty("用户姓名")
    private String userName;

    @NotBlank(message = "用户密码不能为空")
    @ApiModelProperty("用户密码")
    private String userPassword;

    @NotNull(message = "激活状态不能为空")
    @ApiModelProperty("激活状态（0禁用，1激活，2待审核，3锁定）")
    private Integer activeStatus;

//    @NotNull
//    @ApiModelProperty("用户类型0普通用户，1超级管理员")
//    private Integer userType;
}
