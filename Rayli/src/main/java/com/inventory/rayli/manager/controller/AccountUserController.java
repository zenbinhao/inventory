package com.inventory.rayli.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/10/17 20:45
 * @Description: TODO
 */

import com.inventory.rayli.common.aop.AopOperation;
import com.inventory.rayli.common.controller.BaseController;
import com.inventory.rayli.common.vo.ResultVO;
import com.inventory.rayli.manager.dto.AccountUserDTO;
import com.inventory.rayli.manager.service.AccountUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理员接口"}
)
@RestController
@RequestMapping("/user")
public class AccountUserController extends BaseController {

    @Resource
    AccountUserService accountUserService;

    @AopOperation(type = "添加用户",
    checkPermission = true)
    @ApiOperation("添加用户接口")
    @PostMapping("/")
    public ResultVO insertData(@Valid @RequestBody AccountUserDTO form) {
        accountUserService.registerAccount(form);
        return this.success("新增信息成功");
    }
}
