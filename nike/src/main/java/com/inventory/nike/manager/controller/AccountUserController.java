package com.inventory.nike.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/10/17 20:45
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.nike.common.aop.AopOperation;
import com.inventory.nike.common.controller.BaseController;
import com.inventory.nike.common.vo.ResultVO;
import com.inventory.nike.manager.dto.AccountUserDTO;
import com.inventory.nike.manager.dto.AccountUserUpdateDTO;
import com.inventory.nike.manager.po.AccountUser;
import com.inventory.nike.manager.query.AccountUserQuery;
import com.inventory.nike.manager.service.AccountUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-用户管理"}
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


    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<AccountUser>> pageDate(@RequestBody AccountUserQuery query){
        PageInfo info = accountUserService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<AccountUser> selectById(@PathVariable String id) {

        AccountUser employee = accountUserService.selectById(id);
        return this.success(employee, "查询id为:" + id + "用户成功");
    }



    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@Valid @RequestBody AccountUserUpdateDTO form) {
        accountUserService.updateData(form);
        return this.success("修改信息成功");
    }


    @AopOperation(
            type = "批量删除",
            checkPermission = true
    )
    @ApiOperation("批量删除")
    @DeleteMapping({"/delete/{ids}"})
    public ResultVO delete(@PathVariable("ids") String ids) {
        accountUserService.delete(ids);
        return this.success("批量删除成功");
    }
}
