package com.inventory.rayli.manager.po;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:09
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.inventory.rayli.common.po.BusinessPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("文章表")
@TableName("rayli_article")
@Data
public class Article extends BusinessPO {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("封面图片附件")
    private String filepath;

    @ApiModelProperty("文章分类id")
    private String fkCategoryId;
}
