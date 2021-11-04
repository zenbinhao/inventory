package com.inventory.rayli.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/4 15:43
 * @Description: TODO
 */

import com.inventory.rayli.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "AccountUserInfoDTO",description = "用户基础信息表")
public class AccountUserInfoDTO extends BusinessFormDTO {
    @NotBlank(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private String fkUserId;

    @NotNull(message = "性别不能为空")
    @ApiModelProperty("性别 默认0未确定，1男，2女")
    private Integer sex;

    @NotBlank(message = "联系电话不能为空")
    @ApiModelProperty("联系电话")
    private String phone;

    @NotNull(message = "年龄不能为空")
    @ApiModelProperty("年龄")
    private Integer age;

    @NotBlank(message = "头像附件路径不能为空")
    @ApiModelProperty("头像附件路径")
    private String headPath;
}
