package com.inventory.rayli.manager.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.inventory.rayli.common.po.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("fish_userInfo")
@ApiModel("用户信息扩展表")
@Data
public class AccountUserInfo extends BasePO {

    @ApiModelProperty("用户id")
    private String fkUserId;
//    @ApiModelProperty("员工号")
//    private String workId;
    @ApiModelProperty("性别 默认0未确定，1男，2女")
    private Integer sex;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("其他联系方式")
    private String contact;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("身高")
    private String height;
    @ApiModelProperty("体重")
    private String weight;
    @ApiModelProperty("微信")
    private String wechat;
    @ApiModelProperty("头像附件路径")
    private String headPath;
    @ApiModelProperty("关联账户id")
    private String openId;
}
