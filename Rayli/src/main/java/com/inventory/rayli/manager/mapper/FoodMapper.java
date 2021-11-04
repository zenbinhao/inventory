package com.inventory.rayli.manager.mapper;

import com.inventory.rayli.common.mapper.MapperCustom;
import com.inventory.rayli.manager.po.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FoodMapper extends MapperCustom<Food> {

    int deleteData(@Param("ids") String[] ids);

}
