//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inventory.nike.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("返回结果类")
public class ResultVO<T> implements Serializable {
    @ApiModelProperty("响应状态码")
    private String code;
    @ApiModelProperty("响应消息")
    private String message;
    @ApiModelProperty("权限值")
    private List<String> permissions;
    @ApiModelProperty("响应结果")
    private T data;

    public static ResultVO success(Object data, String msg) {
        ResultVO res = new ResultVO();
        res.setMessage(msg);
        res.setData(data);
        res.setCode("0");
        return res;
    }

    public static ResultVO success(String msg) {
        return success((Object)null, msg);
    }

    public ResultVO() {
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResultVO)) {
            return false;
        } else {
            ResultVO<?> other = (ResultVO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label59;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label59;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$permissions = this.getPermissions();
                Object other$permissions = other.getPermissions();
                if (this$permissions == null) {
                    if (other$permissions != null) {
                        return false;
                    }
                } else if (!this$permissions.equals(other$permissions)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResultVO;
    }


    public String toString() {
        return "ResultVO(code=" + this.getCode() + ", message=" + this.getMessage() + ", permissions=" + this.getPermissions() + ", data=" + this.getData() + ")";
    }
}
