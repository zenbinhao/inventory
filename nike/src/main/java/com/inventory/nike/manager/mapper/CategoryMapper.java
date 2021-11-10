package com.inventory.nike.manager.mapper;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:17
 * @Description: TODO
 */

import com.inventory.nike.common.mapper.MapperCustom;
import com.inventory.nike.manager.po.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper extends MapperCustom<Category> {

    int deleteData(@Param("ids") String[] ids);

}
