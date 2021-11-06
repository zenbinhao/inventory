package com.inventory.nike.manager.query;/*
 * @Author: zeng
 * @Data: 2021/11/4 14:26
 * @Description: TODO
 */

import com.inventory.nike.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="AccountUserQuery", description="用户基础表")
public class AccountUserQuery extends PageQuery {

    @ApiModelProperty("用户姓名")
    private String userName;
}
