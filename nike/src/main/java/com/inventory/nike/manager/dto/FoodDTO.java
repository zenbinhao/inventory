package com.inventory.nike.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/6 12:49
 * @Description: TODO
 */

import com.inventory.nike.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "FoodDTO" ,description = "商品信息表")
public class FoodDTO extends BusinessFormDTO {

    @NotBlank(message = "标题不能为空")
    @ApiModelProperty("标题")
    private String foodName;

    @NotBlank(message = "介绍不能为空")
    @ApiModelProperty("介绍")
    private String introduce;

    @NotNull(message = "现价(优惠价)不能为空")
    @ApiModelProperty("现价(优惠价)")
    private double nowPrice;

    @NotNull(message = "原价不能为空")
    @ApiModelProperty("原价")
    private double oldPrice;

    @NotBlank(message = "图片组不能为空")
    @ApiModelProperty("图片组路径以分号间隔")
    private String fileList;

    @NotBlank(message = "商品分类id不能为空")
    @ApiModelProperty("商品分类id")
    private String fkCategoryId;
}
