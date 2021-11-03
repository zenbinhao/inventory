package com.inventory.rayli.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.inventory.rayli.manager.dto.AccountUserDTO;
import com.inventory.rayli.manager.po.AccountUser;

public interface AccountUserService extends IService<AccountUser> {
    //注册
    Integer registerAccount(AccountUserDTO from);
}
