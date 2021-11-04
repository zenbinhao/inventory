package com.inventory.rayli.manager.vo;/*
 * @Author: zeng
 * @Data: 2021/11/4 15:20
 * @Description: TODO
 */

import com.inventory.rayli.manager.po.AccountUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="AccountUserInfoVO", description="用户信息扩展表")
public class AccountUserInfoVO extends AccountUserInfo {

    @ApiModelProperty("用户姓名")
    private String userName;
}
