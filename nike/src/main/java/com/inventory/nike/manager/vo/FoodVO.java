package com.inventory.nike.manager.vo;/*
 * @Author: zeng
 * @Data: 2021/11/6 12:41
 * @Description: TODO
 */

import com.inventory.nike.manager.po.Food;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "FoodVO",description = "商品信息表")
@Data
public class FoodVO extends Food {

    @ApiModelProperty("商品分类名称")
    private String categoryName;
}
