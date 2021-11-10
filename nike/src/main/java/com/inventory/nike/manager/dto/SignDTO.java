package com.inventory.nike.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/6 15:58
 * @Description: TODO
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
@ApiModel(value = "SignDTO",description = "收藏表")
public class SignDTO {

    @NotBlank(message = "商品id不能为空")
    @ApiModelProperty("商品id")
    private String fkFoodId;

}
