package com.inventory.nike.manager.mapper;/*
 * @Author: zeng
 * @Data: 2021/11/6 14:17
 * @Description: TODO
 */

import com.inventory.nike.common.mapper.MapperCustom;
import com.inventory.nike.manager.po.Shopping;
import com.inventory.nike.manager.vo.ShoppingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShoppingMapper extends MapperCustom<Shopping> {

    int deleteData(@Param("ids") String[] ids);

    List<ShoppingVO> selectList(String id);
}
