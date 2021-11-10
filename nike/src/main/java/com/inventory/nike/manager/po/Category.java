package com.inventory.nike.manager.po;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:02
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.inventory.nike.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("商品分类表")
@TableName("nike_category")
@Data
public class Category extends BusinessPO {

    @ApiModelProperty("商品分类名称")
    private String categoryName;

}
