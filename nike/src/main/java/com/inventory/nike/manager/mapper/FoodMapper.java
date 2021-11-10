package com.inventory.nike.manager.mapper;

import com.inventory.nike.common.mapper.MapperCustom;
import com.inventory.nike.manager.po.Food;
import com.inventory.nike.manager.query.FoodQuery;
import com.inventory.nike.manager.vo.FoodVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodMapper extends MapperCustom<Food> {

    int deleteData(@Param("ids") String[] ids);

    List<FoodVO> selectList(FoodQuery courseQuery);

    FoodVO selectById(String id);
}
