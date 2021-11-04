package com.inventory.rayli.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:33
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.rayli.common.aop.AopOperation;
import com.inventory.rayli.common.controller.BaseController;
import com.inventory.rayli.common.vo.ResultVO;
import com.inventory.rayli.manager.dto.ArticleCategoryDTO;
import com.inventory.rayli.manager.po.ArticleCategory;
import com.inventory.rayli.manager.query.ArticleCategoryQuery;
import com.inventory.rayli.manager.service.ArticleCategorySevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-文章分类管理"}
)
@RestController
@RequestMapping("/articleCategory")
public class ArticleCategoryController extends BaseController {

    @Resource
    private ArticleCategorySevice articleCategorySevice;


    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<ArticleCategory>> pageDate(@RequestBody ArticleCategoryQuery query){
        PageInfo info = articleCategorySevice.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<ArticleCategory> selectById(@PathVariable String id) {

        ArticleCategory employee = articleCategorySevice.selectById(id);
        return this.success(employee, "查询id为:" + id + "用户成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true
    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody ArticleCategoryDTO form) {
        articleCategorySevice.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@Valid @RequestBody ArticleCategoryDTO form) {
        articleCategorySevice.updateData(form);
        return this.success("修改信息成功");
    }


    @AopOperation(
            type = "批量删除",
            checkPermission = true
    )
    @ApiOperation("批量删除")
    @DeleteMapping({"/delete/{ids}"})
    public ResultVO delete(@PathVariable("ids") String ids) {
        articleCategorySevice.delete(ids);
        return this.success("批量删除成功");
    }
}
