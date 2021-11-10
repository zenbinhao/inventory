package com.inventory.nike.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/4 17:15
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.nike.common.aop.AopOperation;
import com.inventory.nike.common.controller.BaseController;
import com.inventory.nike.common.vo.ResultVO;
import com.inventory.nike.manager.dto.FoodDTO;
import com.inventory.nike.manager.query.FoodQuery;
import com.inventory.nike.manager.service.FoodService;
import com.inventory.nike.manager.vo.FoodVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(
        tags = {"管理端-商品信息管理"}
)
@RestController
@RequestMapping("/food")
public class FoodController extends BaseController {

    @Resource
    private FoodService foodService;

    @AopOperation(
            type = "分页查询",
            checkPermission = true
    )
    @ApiOperation("分页查询")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<FoodVO>> pageDate(@RequestBody FoodQuery query){
        PageInfo info = foodService.pageData(query);
        return this.success(info,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过id查询")
    @GetMapping({"/{id}"})
    public ResultVO<FoodVO> selectById(@PathVariable String id) {

        FoodVO foodVO = foodService.selectById(id);
        return this.success(foodVO, "查询id为:" + id + "商品成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true
    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody FoodDTO form) {
        foodService.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@Valid @RequestBody FoodDTO form) {
        foodService.updateData(form);
        return this.success("修改信息成功");
    }


    @AopOperation(
            type = "批量删除",
            checkPermission = true
    )
    @ApiOperation("批量删除")
    @DeleteMapping({"/delete/{ids}"})
    public ResultVO delete(@PathVariable("ids") String ids) {
        foodService.delete(ids);
        return this.success("批量删除成功");
    }
}
