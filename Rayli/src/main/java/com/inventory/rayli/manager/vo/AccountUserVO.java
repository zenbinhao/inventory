package com.inventory.rayli.manager.vo;

import com.inventory.rayli.manager.po.AccountUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户基本信息")
public class AccountUserVO extends AccountUser {
    @ApiModelProperty("身份认证票据")
    private String authToken;
}
