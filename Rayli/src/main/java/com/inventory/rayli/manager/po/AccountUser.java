package com.inventory.rayli.manager.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.inventory.rayli.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户表")
@TableName("rayli_user")
@Data
public class AccountUser extends BusinessPO {

    @ApiModelProperty("用户姓名")
    private String userName;
    @ApiModelProperty("用户账户")
    private String userAccount;
    @ApiModelProperty("激活状态（0禁用，1激活，2待审核，3锁定）")
    private Integer activeStatus;
    @ApiModelProperty("用户密码")
    private String userPassword;
    @ApiModelProperty("用户类型0普通用户，1超级管理员")
    private Integer userType;

}
