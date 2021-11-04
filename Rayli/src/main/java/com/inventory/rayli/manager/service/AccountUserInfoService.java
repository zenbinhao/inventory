package com.inventory.rayli.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.inventory.rayli.manager.dto.AccountUserInfoDTO;
import com.inventory.rayli.manager.po.AccountUserInfo;
import com.inventory.rayli.manager.query.AccountUserInfoQuery;
import com.inventory.rayli.manager.vo.AccountUserInfoVO;

public interface AccountUserInfoService extends IService<AccountUserInfo> {

    PageInfo<AccountUserInfoVO> pageData(AccountUserInfoQuery query);

    void updateData(AccountUserInfoDTO formDTO);

    AccountUserInfoVO selectById(String id);

    void insertData(AccountUserInfoDTO formDTO);

    void delete(String ids);
}
