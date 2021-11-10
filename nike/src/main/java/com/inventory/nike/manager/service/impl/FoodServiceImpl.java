package com.inventory.nike.manager.service.impl;
/*
 * @Author: zeng
 * @Data: 2021/11/4 16:57
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.nike.common.util.BeanUtil;
import com.inventory.nike.common.vo.BusinessException;
import com.inventory.nike.manager.dto.FoodDTO;
import com.inventory.nike.manager.mapper.FoodMapper;
import com.inventory.nike.manager.po.Food;
import com.inventory.nike.manager.query.FoodQuery;
import com.inventory.nike.manager.service.FoodService;
import com.inventory.nike.manager.vo.FoodVO;
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
    public PageInfo<FoodVO> pageData(FoodQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<FoodVO> list = foodMapper.selectList(query);
        return PageInfo.of(list);
    }

    @Override
    public void updateData(FoodDTO formDTO) {
        Food article = new Food();
        BeanUtil.copy(formDTO,article);
        foodMapper.updateById(article);
    }

    @Override
    public FoodVO selectById(String id) {
        return foodMapper.selectById(id);
    }

    @Override
    public void insertData(FoodDTO formDTO) {
        Food article = new Food();
        BeanUtil.copy(formDTO,article);
        foodMapper.insert(article);
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
