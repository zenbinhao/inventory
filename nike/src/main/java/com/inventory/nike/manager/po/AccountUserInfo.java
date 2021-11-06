package com.inventory.nike.manager.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.inventory.nike.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("nike_userInfo")
@ApiModel("用户信息扩展表")
@Data
public class AccountUserInfo extends BusinessPO {

    @ApiModelProperty("用户id")
    private String fkUserId;
    @ApiModelProperty("性别 默认0未确定，1男，2女")
    private Integer sex;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("头像附件路径")
    private String headPath;
}
