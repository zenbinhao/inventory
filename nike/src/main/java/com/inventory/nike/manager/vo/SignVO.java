package com.inventory.nike.manager.vo;/*
 * @Author: zeng
 * @Data: 2021/11/6 15:44
 * @Description: TODO
 */

import com.inventory.nike.manager.po.Sign;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SignVO",description = "收藏表")
@Data
public class SignVO extends Sign {

    @ApiModelProperty("标题")
    private String foodName;

    @ApiModelProperty("现价(优惠价)")
    private double nowPrice;

    @ApiModelProperty("原价")
    private double oldPrice;

    @ApiModelProperty("图片组路径以分号间隔")
    private String fileList;

}
