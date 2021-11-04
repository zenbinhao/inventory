package com.inventory.rayli.manager.query;/*
 * @Author: zeng
 * @Data: 2021/11/4 15:19
 * @Description: TODO
 */

import com.inventory.rayli.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="AccountUserInfoQuery", description="用户信息扩展表")
public class AccountUserInfoQuery extends PageQuery {

    @ApiModelProperty("用户姓名")
    private String userName;
}
