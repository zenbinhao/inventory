package com.inventory.nike.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.inventory.nike.manager.dto.AccountUserDTO;
import com.inventory.nike.manager.dto.AccountUserUpdateDTO;
import com.inventory.nike.manager.po.AccountUser;
import com.inventory.nike.manager.query.AccountUserQuery;

public interface AccountUserService extends IService<AccountUser> {
    //注册  新增
    Integer registerAccount(AccountUserDTO from);
    //删
    void delete(String ids);
    //改
    void updateData(AccountUserUpdateDTO formDTO);
    //查
    PageInfo<AccountUser> pageData(AccountUserQuery query);
    //详情
    AccountUser selectById(String id);
}
