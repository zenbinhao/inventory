package com.inventory.nike.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.inventory.nike.manager.dto.FoodDTO;
import com.inventory.nike.manager.po.Food;
import com.inventory.nike.manager.query.FoodQuery;
import com.inventory.nike.manager.vo.FoodVO;

public interface FoodService extends IService<Food> {

    PageInfo<FoodVO> pageData(FoodQuery query);

    void updateData(FoodDTO formDTO);

    FoodVO selectById(String id);

    void insertData(FoodDTO formDTO);

    void delete(String ids);
}
