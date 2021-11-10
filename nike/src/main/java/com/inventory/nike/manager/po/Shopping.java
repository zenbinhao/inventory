package com.inventory.nike.manager.po;/*
 * @Author: zeng
 * @Data: 2021/11/6 13:28
 * @Description: TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.inventory.nike.common.po.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("购物车表")
@TableName("nike_shopping")
@Data
public class Shopping extends BasePO {

    @ApiModelProperty("商品id")
    private String fkFoodId;

    @ApiModelProperty("用户id")
    private String fkUserId;
}
