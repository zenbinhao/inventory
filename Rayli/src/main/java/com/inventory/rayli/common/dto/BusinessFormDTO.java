package com.inventory.rayli.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("业务表单公共dto")
public class BusinessFormDTO implements Serializable {
    @ApiModelProperty("主键id，修改必填")
    private String id;
    @ApiModelProperty("乐观锁,修改必填")
    private Integer version;
    @ApiModelProperty("备注")
    private String memo;

    public BusinessFormDTO() {
    }

    public String getId() {
        return this.id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BusinessFormDTO)) {
            return false;
        } else {
            BusinessFormDTO other = (BusinessFormDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label47;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label47;
                    }

                    return false;
                }

                Object this$version = this.getVersion();
                Object other$version = other.getVersion();
                if (this$version == null) {
                    if (other$version != null) {
                        return false;
                    }
                } else if (!this$version.equals(other$version)) {
                    return false;
                }

                Object this$memo = this.getMemo();
                Object other$memo = other.getMemo();
                if (this$memo == null) {
                    if (other$memo != null) {
                        return false;
                    }
                } else if (!this$memo.equals(other$memo)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BusinessFormDTO;
    }



    public String toString() {
        return "BusinessFormDTO(id=" + this.getId() + ", version=" + this.getVersion() + ", memo=" + this.getMemo() + ")";
    }
}