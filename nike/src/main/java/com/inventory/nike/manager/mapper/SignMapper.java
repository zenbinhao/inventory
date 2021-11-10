package com.inventory.nike.manager.mapper;

import com.inventory.nike.common.mapper.MapperCustom;
import com.inventory.nike.manager.po.Sign;
import com.inventory.nike.manager.vo.SignVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SignMapper extends MapperCustom<Sign> {

    int deleteData(@Param("ids") String[] ids);

    List<SignVO> selectList(String id);

    int deleteBy(@Param("foodId")String v1,@Param("userId") String v2);
}
