package com.inventory.nike.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/6 12:27
 * @Description: TODO
 */

import com.inventory.nike.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "CategoryDTO",description = "商品分类表")
public class CategoryDTO extends BusinessFormDTO {

    @NotBlank(message = "商品分类名称不能为空")
    @ApiModelProperty("商品分类名称")
    private String categoryName;
}
