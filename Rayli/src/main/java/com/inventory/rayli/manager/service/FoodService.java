package com.inventory.rayli.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.inventory.rayli.manager.dto.FoodDTO;
import com.inventory.rayli.manager.po.Food;
import com.inventory.rayli.manager.query.FoodQuery;

public interface FoodService extends IService<Food> {

    PageInfo<Food> pageData(FoodQuery query);

    void updateData(FoodDTO formDTO);

    Food selectById(String id);

    void insertData(FoodDTO formDTO);

    void delete(String ids);

}
