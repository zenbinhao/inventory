package com.inventory.rayli.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/4 19:51
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.rayli.common.util.BeanUtil;
import com.inventory.rayli.common.vo.BusinessException;
import com.inventory.rayli.manager.dto.FoodDTO;
import com.inventory.rayli.manager.mapper.FoodMapper;
import com.inventory.rayli.manager.po.Food;
import com.inventory.rayli.manager.query.FoodQuery;
import com.inventory.rayli.manager.service.FoodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {

    @Resource
    private FoodMapper foodMapper;

    @Override
    public PageInfo<Food> pageData(FoodQuery query) {
        //开启条件查询
        QueryWrapper<Food> queryWrapper = new QueryWrapper<Food>();
        queryWrapper.lambda().like(StringUtils.isNotEmpty(query.getFoodName()),Food::getFoodName,query.getFoodName()).orderByDesc(Food::getGmtCreate);

        //开启分页
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<Food> list = foodMapper.selectList(queryWrapper);
        return PageInfo.of(list);
    }

    @Override
    public void updateData(FoodDTO formDTO) {
        Food food = new Food();
        BeanUtil.copy(formDTO,food);
        foodMapper.updateById(food);
    }

    @Override
    public Food selectById(String id) {
        return foodMapper.selectById(id);
    }

    @Override
    public void insertData(FoodDTO formDTO) {
        Food food = new Food();
        BeanUtil.copy(formDTO,food);
        foodMapper.insert(food);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        int row = foodMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }
}
