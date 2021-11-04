package com.inventory.rayli.manager.query;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:55
 * @Description: TODO
 */

import com.inventory.rayli.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ArticleQuery" ,description = "文章信息表")
public class ArticleQuery extends PageQuery {

    @ApiModelProperty("文章分类id")
    private String fkCategoryId;
}
