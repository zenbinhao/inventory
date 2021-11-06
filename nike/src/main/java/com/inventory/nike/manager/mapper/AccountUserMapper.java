package com.inventory.nike.manager.mapper;

import com.inventory.nike.common.mapper.MapperCustom;
import com.inventory.nike.manager.po.AccountUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface AccountUserMapper extends MapperCustom<AccountUser> {

    int deleteData(@Param("ids") String[] ids);
}
