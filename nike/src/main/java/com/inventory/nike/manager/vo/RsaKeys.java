package com.inventory.nike.manager.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("RSA秘钥存储对象类")
public class RsaKeys implements Serializable {
    @ApiModelProperty("rsa秘钥标识符")
    private String rsaKey;
    @ApiModelProperty("公钥")
    private String publicKey;
    @ApiModelProperty(
        value = "私钥",
        hidden = true
    )
    private String privateKey;
}
