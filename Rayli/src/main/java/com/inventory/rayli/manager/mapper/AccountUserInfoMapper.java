package com.inventory.rayli.manager.mapper;

import com.inventory.rayli.common.mapper.MapperCustom;
import com.inventory.rayli.manager.po.AccountUserInfo;
import com.inventory.rayli.manager.query.AccountUserInfoQuery;
import com.inventory.rayli.manager.vo.AccountUserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountUserInfoMapper extends MapperCustom<AccountUserInfo> {

    List<AccountUserInfoVO> selectList(AccountUserInfoQuery query);

    AccountUserInfoVO selectById(@Param("id") String id);

    int deleteData(@Param("ids") String[] ids);
}
