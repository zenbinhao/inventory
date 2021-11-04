package com.inventory.rayli.manager.vo;/*
 * @Author: zeng
 * @Data: 2021/11/4 20:21
 * @Description: TODO
 */

import com.inventory.rayli.manager.po.Ranking;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "RankingVO",description = "排行表")
@Data
public class RankingVO extends Ranking {

    @ApiModelProperty("标题")
    private String foodName;
}
