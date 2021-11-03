package com.inventory.rayli.common.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("业务表po")
public class BusinessPO extends BasePO implements Serializable {
    @JsonIgnore
    @ApiModelProperty(
            value = "创建人id",
            hidden = true
    )
    private String createUserId;


    @ApiModelProperty("创建人姓名")
    private String createUserName;


    @JsonIgnore
    @ApiModelProperty(
            value = "修改人id",
            hidden = true
    )
    private String updateUserId;


    @ApiModelProperty("修改人姓名")
    private String updateUserName;


    @TableLogic
    @JsonIgnore
    @ApiModelProperty(
            value = "状态，0默认，1表示删除",
            hidden = true
    )
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @Version
    @ApiModelProperty("乐观锁,新增时自动填充")
    private Integer version;


    @ApiModelProperty("备注")
    private String memo;

    public BusinessPO() {
    }

}