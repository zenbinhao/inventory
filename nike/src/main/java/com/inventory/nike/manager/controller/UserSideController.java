package com.inventory.nike.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/4 22:07
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.nike.common.aop.AopOperation;
import com.inventory.nike.common.controller.BaseController;
import com.inventory.nike.common.vo.ResultVO;
import com.inventory.nike.manager.dto.AccountUserDTO;
import com.inventory.nike.manager.po.Category;
import com.inventory.nike.manager.query.CategoryQuery;
import com.inventory.nike.manager.query.FoodQuery;
import com.inventory.nike.manager.service.AccountUserService;
import com.inventory.nike.manager.service.CategorySevice;
import com.inventory.nike.manager.service.FoodService;
import com.inventory.nike.manager.vo.FoodVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@Api(
        tags = {"用户端-不用权限的页面展示接口"}
)
@RestController
@RequestMapping("/userSide")
public class UserSideController extends BaseController {

    @Resource
    private AccountUserService accountUserService;

    @Resource
    private FoodService foodService;

    @Resource
    private CategorySevice categorySevice;

    @ApiOperation("分页查询商品的分类 100条查全")
    @PostMapping({"/categoryList"})
    public ResultVO<PageInfo<Category>> pageDate(@RequestBody CategoryQuery query){
        PageInfo info = categorySevice.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @ApiOperation("分页查询 所有商品信息")
    @PostMapping({"/footList"})
    public ResultVO<PageInfo<FoodVO>> pageDate(@RequestBody FoodQuery query){
        PageInfo info = foodService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @ApiOperation("通过商品id查询商品详情")
    @GetMapping({"/{id}"})
    public ResultVO<FoodVO> selectById(@PathVariable String id) {

        FoodVO foodVO = foodService.selectById(id);
        return this.success(foodVO, "查询id为:" + id + "商品成功");
    }

    @ApiOperation("注册接口")
    @PostMapping("/")
    public ResultVO insertData(@Valid @RequestBody AccountUserDTO form) {
        accountUserService.registerAccount(form);
        return this.success("注册成功");
    }
}