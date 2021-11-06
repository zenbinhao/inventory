package com.inventory.nike.common.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CacheBean implements Serializable {

    @ApiModelProperty("生效时间")
    private Long create;
    @ApiModelProperty("失效时间")
    private Long timeout;
    @ApiModelProperty("有效时间/秒")
    private Long validTimeSecond;
    @ApiModelProperty("是否自动更新超时时间")
    private boolean autoUpdate;
    @ApiModelProperty("是否有效")
    private boolean flag;
    @ApiModelProperty("缓存数据")
    private Object data;
}
