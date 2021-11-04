package com.inventory.rayli.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:22
 * @Description: TODO
 */

import com.inventory.rayli.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "ArticleCategoryDTO",description = "文章分类表")
public class ArticleCategoryDTO extends BusinessFormDTO {

    @NotBlank(message = "文章分类名称不能为空")
    @ApiModelProperty("文章分类名称")
    private String categoryName;
}
