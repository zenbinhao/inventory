package com.inventory.nike.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/6 16:22
 * @Description: TODO
 */

import com.inventory.nike.common.aop.AopOperation;
import com.inventory.nike.common.controller.BaseController;
import com.inventory.nike.common.vo.ResultVO;
import com.inventory.nike.manager.dto.ShoppingDTO;
import com.inventory.nike.manager.dto.SignDTO;
import com.inventory.nike.manager.service.SignService;
import com.inventory.nike.manager.vo.ShoppingVO;
import com.inventory.nike.manager.vo.SignVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(
        tags = {"用户端-收藏接口"}
)
@RestController
@RequestMapping("/sign")
public class SignController extends BaseController {

    @Resource
    private SignService signService;


    @AopOperation(
            type = "查看自己的收藏列表"
    )
    @ApiOperation("查看自己的收藏列表")
    @PostMapping({"/list"})
    public ResultVO<List<ShoppingVO>> pageDate(){
        List<SignVO> list = signService.selectList();
        return this.success(list,"查看自己的收藏列表成功");
    }

    @AopOperation(
            type = "点击收藏"
    )
    @ApiOperation("点击收藏")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody SignDTO form) {
        signService.insertData(form);
        return this.success("商品加购成功");
    }


    @AopOperation(
            type = "收藏的商品批量删除"
    )
    @ApiOperation("收藏的商品批量删除")
    @DeleteMapping({"/delete/{ids}"})
    public ResultVO delete(@PathVariable("ids") String ids) {
        signService.delete(ids);
        return this.success("批量删除成功");
    }

    @AopOperation(
            type = "取消收藏"
    )
    @ApiOperation("取消收藏")
    @DeleteMapping({"/delete"})
    public ResultVO delete(@Valid @RequestBody SignDTO form) {
        signService.deleteBy(form);
        return this.success("成功取消收藏");
    }



    @AopOperation(
            type = "查看收藏状态"
    )
    @ApiOperation("查看收藏状态")
    @PostMapping({"/state"})
    public ResultVO checkState(@Valid @RequestBody SignDTO form){
        Integer integer = signService.checkState(form);
        if(integer == 0){
            return this.success("7777","您未收藏该商品");

        }
        return this.success("6666","您已收藏该商品");
    }
}
