package com.inventory.nike.manager.vo;/*
 * @Author: zeng
 * @Data: 2021/11/6 14:34
 * @Description: TODO
 */

import com.inventory.nike.manager.po.Shopping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ShoppingVO",description = "购物车表")
@Data
public class ShoppingVO extends Shopping {

    @ApiModelProperty("标题")
    private String foodName;

    @ApiModelProperty("现价(优惠价)")
    private double nowPrice;

    @ApiModelProperty("原价")
    private double oldPrice;

    @ApiModelProperty("图片组路径以分号间隔")
    private String fileList;
}
