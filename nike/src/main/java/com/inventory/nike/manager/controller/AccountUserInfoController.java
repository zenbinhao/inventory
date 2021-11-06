package com.inventory.nike.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/4 15:51
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.nike.common.aop.AopOperation;
import com.inventory.nike.common.controller.BaseController;
import com.inventory.nike.common.vo.ResultVO;
import com.inventory.nike.manager.dto.AccountUserInfoDTO;
import com.inventory.nike.manager.query.AccountUserInfoQuery;
import com.inventory.nike.manager.service.AccountUserInfoService;
import com.inventory.nike.manager.vo.AccountUserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-角色管理"}
)
@RestController
@RequestMapping("/userinfo")
public class AccountUserInfoController extends BaseController {

    @Resource
    private AccountUserInfoService accountUserInfoService;

    @AopOperation(type = "新增",
            checkPermission = true)
    @ApiOperation("新增信息")
    @PostMapping("/")
    public ResultVO insertData(@Valid @RequestBody AccountUserInfoDTO form) {
        accountUserInfoService.insertData(form);
        return this.success("新增信息成功");
    }


    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<AccountUserInfoVO>> pageDate(@RequestBody AccountUserInfoQuery query){
        PageInfo info = accountUserInfoService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<AccountUserInfoVO> selectById(@PathVariable String id) {

        AccountUserInfoVO employee = accountUserInfoService.selectById(id);
        return this.success(employee, "查询id为:" + id + "用户成功");
    }



    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@Valid @RequestBody AccountUserInfoDTO form) {
        accountUserInfoService.updateData(form);
        return this.success("修改信息成功");
    }


    @AopOperation(
            type = "批量删除",
            checkPermission = true
    )
    @ApiOperation("批量删除")
    @DeleteMapping({"/delete/{ids}"})
    public ResultVO delete(@PathVariable("ids") String ids) {
        accountUserInfoService.delete(ids);
        return this.success("批量删除成功");
    }
}
