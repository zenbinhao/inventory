package com.inventory.nike.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/6 12:32
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.nike.common.aop.AopOperation;
import com.inventory.nike.common.controller.BaseController;
import com.inventory.nike.common.vo.ResultVO;
import com.inventory.nike.manager.dto.CategoryDTO;
import com.inventory.nike.manager.po.Category;
import com.inventory.nike.manager.query.CategoryQuery;
import com.inventory.nike.manager.service.CategorySevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-商品分类管理"}
)
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Resource
    private CategorySevice categorySevice;

    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<Category>> pageDate(@RequestBody CategoryQuery query){
        PageInfo info = categorySevice.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<Category> selectById(@PathVariable String id) {

        Category category = categorySevice.selectById(id);
        return this.success(category, "查询id为:" + id + "分类成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true
    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody CategoryDTO form) {
        categorySevice.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@Valid @RequestBody CategoryDTO form) {
        categorySevice.updateData(form);
        return this.success("修改信息成功");
    }


    @AopOperation(
            type = "批量删除",
            checkPermission = true
    )
    @ApiOperation("批量删除")
    @DeleteMapping({"/delete/{ids}"})
    public ResultVO delete(@PathVariable("ids") String ids) {
        categorySevice.delete(ids);
        return this.success("批量删除成功");
    }
}
