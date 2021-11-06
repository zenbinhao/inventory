package com.inventory.nike.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.inventory.nike.manager.dto.AccountUserInfoDTO;
import com.inventory.nike.manager.po.AccountUserInfo;
import com.inventory.nike.manager.query.AccountUserInfoQuery;
import com.inventory.nike.manager.vo.AccountUserInfoVO;

public interface AccountUserInfoService extends IService<AccountUserInfo> {

    PageInfo<AccountUserInfoVO> pageData(AccountUserInfoQuery query);

    void updateData(AccountUserInfoDTO formDTO);

    AccountUserInfoVO selectById(String id);

    void insertData(AccountUserInfoDTO formDTO);

    void delete(String ids);
}
