package com.inventory.rayli.manager.controller;/*
 * @Author: zeng
 * @Data: 2021/11/4 20:47
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.rayli.common.aop.AopOperation;
import com.inventory.rayli.common.controller.BaseController;
import com.inventory.rayli.common.vo.ResultVO;
import com.inventory.rayli.manager.po.Ranking;
import com.inventory.rayli.manager.service.RankingService;
import com.inventory.rayli.manager.vo.RankingVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(
        tags = {"管理端-排行榜管理"}
)
@RestController
@RequestMapping("/rank")
public class RankingController extends BaseController {

    @Resource
    private RankingService rankingService;

    @AopOperation(
            type = "查全",
            checkPermission = true
    )
    @ApiOperation("查全")
    @PostMapping({"/page"})
    public ResultVO<PageInfo<RankingVO>> pageDate(){
        List<RankingVO> rankingVOS = rankingService.selectList();
        return this.success(rankingVOS,"分页查询成功");
    }

    @AopOperation(
            type = "详情",
            checkPermission = true
    )
    @ApiOperation("通过产品id查询")
    @GetMapping({"/{id}"})
    public ResultVO<RankingVO> selectById(@PathVariable String id) {
        RankingVO rankingVO = rankingService.selectById(id);
        return this.success(rankingVO, "查询id为:" + id + "用户成功");
    }


    @AopOperation(
            type = "新增",
            checkPermission = true
    )
    @ApiOperation("新增信息")
    @PostMapping({"/"})
    public ResultVO insertData(@Valid @RequestBody Ranking form) {
        rankingService.insertData(form);
        return this.success("新增信息成功");
    }

    @AopOperation(
            type = "修改",
            checkPermission = true
    )
    @ApiOperation("修改信息")
    @PutMapping({"/"})
    public ResultVO updateData(@Valid @RequestBody Ranking form) {
        rankingService.updateData(form);
        return this.success("修改信息成功");
    }

    @AopOperation(
            type = "单删",
            checkPermission = true
    )
    @ApiOperation("根据排行数删除")
    @DeleteMapping({"/{rank}"})
    public ResultVO deleteData(@PathVariable("rank") Integer rank) {
        rankingService.deleteByRank(rank);
        return this.success("根据排行数成功");
    }

}
