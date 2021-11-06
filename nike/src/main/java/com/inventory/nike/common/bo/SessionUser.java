package com.inventory.nike.common.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SessionUser implements Serializable {

    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("认证id")
    private String authToken;
    @ApiModelProperty("用户账号")
    private String userAccount;
    @ApiModelProperty("用户姓名")
    private String userName;
}
