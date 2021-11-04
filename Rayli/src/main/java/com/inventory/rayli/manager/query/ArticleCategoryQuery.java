package com.inventory.rayli.manager.query;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:20
 * @Description: TODO
 */

import com.inventory.rayli.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="ArticleCategoryQuery", description="文章分类表")
public class ArticleCategoryQuery extends PageQuery {

    @ApiModelProperty("文章分类名称")
    private String categoryName;
}
