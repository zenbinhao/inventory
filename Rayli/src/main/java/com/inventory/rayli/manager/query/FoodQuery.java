package com.inventory.rayli.manager.query;/*
 * @Author: zeng
 * @Data: 2021/11/4 19:45
 * @Description: TODO
 */

import com.inventory.rayli.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "FoodQuery" ,description = "产品信息表")
public class FoodQuery extends PageQuery {

    @ApiModelProperty("标题")
    private String foodName;
}
