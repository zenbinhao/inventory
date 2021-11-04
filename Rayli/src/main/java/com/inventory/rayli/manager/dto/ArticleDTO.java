package com.inventory.rayli.manager.dto;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:56
 * @Description: TODO
 */

import com.inventory.rayli.common.dto.BusinessFormDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "ArticleDTO" ,description = "文章信息表")
public class ArticleDTO extends BusinessFormDTO {

    @NotBlank(message = "标题不能为空")
    @ApiModelProperty("标题")
    private String title;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty("内容")
    private String content;

    @NotBlank(message = "封面图片附件不能为空")
    @ApiModelProperty("封面图片附件")
    private String filepath;

    @NotBlank(message = "文章分类名称不能为空")
    @ApiModelProperty("文章分类id")
    private String fkCategoryId;
}
