package com.inventory.rayli.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.inventory.rayli.common.bo.SessionUser;
import com.inventory.rayli.manager.dto.LoginFormDTO;
import com.inventory.rayli.manager.po.AccountUser;
import com.inventory.rayli.manager.vo.AccountUserVO;

import javax.servlet.http.HttpServletRequest;

public interface LoginService extends IService<AccountUser> {

    AccountUserVO login(LoginFormDTO dto, HttpServletRequest httpServletRequest);

    void loginOut(HttpServletRequest request);

    SessionUser stateRe(HttpServletRequest request);
}
