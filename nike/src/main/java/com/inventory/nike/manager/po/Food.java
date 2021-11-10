package com.inventory.nike.manager.po;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:09
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.inventory.nike.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("商品表")
@TableName("nike_food")
@Data
public class Food extends BusinessPO {

    @ApiModelProperty("标题")
    private String foodName;

    @ApiModelProperty("介绍")
    private String introduce;

    @ApiModelProperty("现价(优惠价)")
    private double nowPrice;

    @ApiModelProperty("原价")
    private double oldPrice;

    @ApiModelProperty("图片组路径以分号间隔")
    private String fileList;

    @ApiModelProperty("商品分类id")
    private String fkCategoryId;
}
