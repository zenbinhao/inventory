package com.inventory.rayli.manager.po;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:02
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.inventory.rayli.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户表")
@TableName("rayli_articleCategory")
@Data
public class ArticleCategory extends BusinessPO {

    @ApiModelProperty("文章分类名称")
    private String categoryName;

}
