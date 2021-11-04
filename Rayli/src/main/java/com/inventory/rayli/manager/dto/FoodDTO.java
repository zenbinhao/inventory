package com.inventory.rayli.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/4 19:48
 * @Description: TODO
 */

import com.inventory.rayli.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "FoodDTO",description = "产品信息表")
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

    @NotBlank(message = "图片组路径不能为空")
    @ApiModelProperty("图片组路径以分号间隔")
    private String fileList;
}
