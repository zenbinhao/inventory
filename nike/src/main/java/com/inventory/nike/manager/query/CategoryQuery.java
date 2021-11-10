package com.inventory.nike.manager.query;/*
 * @Author: zeng
 * @Data: 2021/11/6 12:26
 * @Description: TODO
 */

import com.inventory.nike.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="CategoryQuery", description="商品分类表")
public class CategoryQuery extends PageQuery {

    @ApiModelProperty("商品分类名称")
    private String categoryName;
}
