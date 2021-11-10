package com.inventory.nike.manager.query;/*
 * @Author: zeng
 * @Data: 2021/11/6 12:41
 * @Description: TODO
 */

import com.inventory.nike.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "FoodQuery" ,description = "商品信息表")
public class FoodQuery extends PageQuery {

    @ApiModelProperty("商品分类id")
    private String fkCategoryId;

    @ApiModelProperty("商品标题")
    private String foodName;
}
