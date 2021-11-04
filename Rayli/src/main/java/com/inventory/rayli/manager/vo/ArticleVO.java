package com.inventory.rayli.manager.vo;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:53
 * @Description: TODO
 */

import com.inventory.rayli.manager.po.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "ArticleVO",description = "文章信息表")
@Data
public class ArticleVO extends Article {

    @ApiModelProperty("文章分类名称")
    private String categoryName;
}
