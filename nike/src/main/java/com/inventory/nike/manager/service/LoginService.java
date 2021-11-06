package com.inventory.nike.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.inventory.nike.common.bo.SessionUser;
import com.inventory.nike.manager.dto.LoginFormDTO;
import com.inventory.nike.manager.po.AccountUser;
import com.inventory.nike.manager.vo.AccountUserVO;

import javax.servlet.http.HttpServletRequest;

public interface LoginService extends IService<AccountUser> {

    AccountUserVO login(LoginFormDTO dto, HttpServletRequest httpServletRequest);

    void loginOut(HttpServletRequest request);

    SessionUser stateRe(HttpServletRequest request);
}
