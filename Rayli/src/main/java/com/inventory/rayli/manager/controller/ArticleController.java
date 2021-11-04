package com.inventory.rayli.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/4 17:15
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.rayli.common.aop.AopOperation;
import com.inventory.rayli.common.controller.BaseController;
import com.inventory.rayli.common.vo.ResultVO;
import com.inventory.rayli.manager.dto.ArticleDTO;
import com.inventory.rayli.manager.query.ArticleQuery;
import com.inventory.rayli.manager.service.ArticleService;
import com.inventory.rayli.manager.vo.ArticleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-文章信息管理"}
)
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Resource
    private ArticleService articleService;

    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<ArticleVO>> pageDate(@RequestBody ArticleQuery query){
        PageInfo info = articleService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<ArticleVO> selectById(@PathVariable String id) {

        ArticleVO articleVO = articleService.selectById(id);
        return this.success(articleVO, "查询id为:" + id + "用户成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true
    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody ArticleDTO form) {
        articleService.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@Valid @RequestBody ArticleDTO form) {
        articleService.updateData(form);
        return this.success("修改信息成功");
    }


    @AopOperation(
            type = "批量删除",
            checkPermission = true
    )
    @ApiOperation("批量删除")
    @DeleteMapping({"/delete/{ids}"})
    public ResultVO delete(@PathVariable("ids") String ids) {
        articleService.delete(ids);
        return this.success("批量删除成功");
    }
}
