package com.inventory.nike.manager.po;/*
 * @Author: zeng
 * @Data: 2021/11/6 13:31
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.inventory.nike.common.po.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("收藏表")
@TableName("nike_sign")
@Data
public class Sign extends BasePO {

    @ApiModelProperty("商品id")
    private String fkFoodId;

    @ApiModelProperty("用户id")
    private String fkUserId;
}
