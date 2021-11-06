package com.inventory.nike.manager.mapper;

import com.inventory.nike.manager.vo.AccountUserInfoVO;
import com.inventory.nike.common.mapper.MapperCustom;
import com.inventory.nike.manager.po.AccountUserInfo;
import com.inventory.nike.manager.query.AccountUserInfoQuery;
import com.inventory.nike.manager.vo.AccountUserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountUserInfoMapper extends MapperCustom<AccountUserInfo> {

    List<AccountUserInfoVO> selectList(AccountUserInfoQuery query);

    AccountUserInfoVO selectById(@Param("id") String id);

    int deleteData(@Param("ids") String[] ids);
}
