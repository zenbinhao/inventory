package com.inventory.rayli.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/4 22:07
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.rayli.common.aop.AopOperation;
import com.inventory.rayli.common.controller.BaseController;
import com.inventory.rayli.common.vo.ResultVO;
import com.inventory.rayli.manager.dto.AccountUserDTO;
import com.inventory.rayli.manager.po.ArticleCategory;
import com.inventory.rayli.manager.po.Food;
import com.inventory.rayli.manager.query.ArticleCategoryQuery;
import com.inventory.rayli.manager.query.ArticleQuery;
import com.inventory.rayli.manager.query.FoodQuery;
import com.inventory.rayli.manager.service.AccountUserService;
import com.inventory.rayli.manager.service.ArticleCategorySevice;
import com.inventory.rayli.manager.service.ArticleService;
import com.inventory.rayli.manager.service.FoodService;
import com.inventory.rayli.manager.vo.ArticleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"用户端-接口"}
)
@RestController
@RequestMapping("/userSide")
public class UserSideController extends BaseController {

    @Resource
    private ArticleService articleService;

    @Resource
    private ArticleCategorySevice articleCategorySevice;

    @Resource
    private AccountUserService accountUserService;

    @Resource
    private FoodService foodService;

    @ApiOperation("分页查询产品")
    @PostMapping({"/food"})
    public ResultVO<PageInfo<Food>> pageDate(@RequestBody FoodQuery query){
        PageInfo info = foodService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @ApiOperation("产品详情")
    @GetMapping({"/food/{id}"})
    public ResultVO<Food> selectFood(@PathVariable String id) {
        Food employee = foodService.selectById(id);
        return this.success(employee, "查询id为:" + id + "商品成功");
    }

    @ApiOperation("注册接口")
    @PostMapping("/")
    public ResultVO insertData(@Valid @RequestBody AccountUserDTO form) {
        accountUserService.registerAccount(form);
        return this.success("注册成功");
    }

//    @AopOperation(
//            type = "分页查询"
//    )
    @ApiOperation("分页查询文章类别写100查全即可")
    @PostMapping({"/articleCategory"})
    public ResultVO<PageInfo<ArticleCategory>> pageDate(@RequestBody ArticleCategoryQuery query){
        PageInfo info = articleCategorySevice.pageData(query);
        return this.success(info,"分页查询成功");
    }

//    @AopOperation(
//            type = "分页查询"
//    )
    @ApiOperation("分页查询文章信息")
    @PostMapping({"/article"})
    public ResultVO<PageInfo<ArticleVO>> pageDate(@RequestBody ArticleQuery query){
        PageInfo info = articleService.pageData(query);
        return this.success(info,"分页查询成功");
    }

//    @AopOperation(
//            type = "详情"
//    )
    @ApiOperation("通过id查询文章详情")
    @GetMapping({"/article/{id}"})
    public ResultVO<ArticleVO> selectById(@PathVariable String id) {

        ArticleVO articleVO = articleService.selectById(id);
        return this.success(articleVO, "查询id为:" + id + "用户成功");
    }
}
