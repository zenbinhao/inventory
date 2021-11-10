package com.inventory.nike.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/6 15:20
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.nike.common.aop.AopOperation;
import com.inventory.nike.common.controller.BaseController;
import com.inventory.nike.common.vo.ResultVO;
import com.inventory.nike.manager.dto.ShoppingDTO;
import com.inventory.nike.manager.service.ShoppingService;
import com.inventory.nike.manager.vo.ShoppingVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(
        tags = {"用户端-购物车接口"}
)
@RestController
@RequestMapping("/shopping")
public class ShoppingController extends BaseController {

    @Resource
    private ShoppingService shoppingService;

    @AopOperation(
            type = "查看自己的购物车列表"
    )
    @ApiOperation("查看自己的购物车列表")
    @PostMapping({"/list"})
    public ResultVO<List<ShoppingVO>> pageDate(){
        List<ShoppingVO> shoppingVOS = shoppingService.selectList();
        return this.success(shoppingVOS,"查看自己的购物车列表成功");
    }


    @AopOperation(
            type = "商品加购"
    )
    @ApiOperation("商品加购")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody ShoppingDTO form) {
        shoppingService.insertData(form);
        return this.success("商品加购成功");
    }


    @AopOperation(
            type = "购物车商品批量删除"
    )
    @ApiOperation("购物车商品批量删除")
    @DeleteMapping({"/delete/{ids}"})
    public ResultVO delete(@PathVariable("ids") String ids) {
        shoppingService.delete(ids);
        return this.success("批量删除成功");
    }

}
