package com.inventory.rayli.manager.po;/*
 * @Author: zeng
 * @Data: 2021/11/4 19:03
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("排行榜")
@TableName("rayli_ranking")
@Data
public class Ranking {
    @NotNull(message = "排名不能为空")
    @ApiModelProperty("排名")
    private Integer rank;

    @NotNull(message = "外键产品id不能为空")
    @ApiModelProperty("外键产品id")
    private String fkFoodId;
}
