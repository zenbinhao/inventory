package com.inventory.rayli.common.service.impl;

import com.inventory.rayli.common.bo.SessionUser;
import com.inventory.rayli.common.em.ErrorCodeEnum;
import com.inventory.rayli.common.service.AuthenticationService;
import com.inventory.rayli.common.service.CacheService;
import com.inventory.rayli.common.vo.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    @Resource
    protected HttpServletRequest request;
    @Resource
    private CacheService cacheService;

    @Override
    public String getAuthToken() {
        String authToken = null;
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            authToken = this.request.getHeader("authToken");
        }

        return authToken;
    }

    @Override
    public SessionUser getSessionUser() {
        SessionUser sessionUser = null;
        String authToken = this.getAuthToken();
        if (StringUtils.isNotEmpty(authToken)) {
            sessionUser = (SessionUser)this.cacheService.getSessionCache(authToken, "sessionUser");
        }

        if (sessionUser == null) {
            if (StringUtils.isEmpty(authToken)) {
                throw new BusinessException(ErrorCodeEnum.NOT_AUTH_TOKEN);
            } else {
                String repeatLandingAccount = (String)this.cacheService.getSessionCache(authToken, "repeatLanding");
                if (repeatLandingAccount != null) {
                    log.info("异地登陆失效token:" + authToken);
                    throw new BusinessException(ErrorCodeEnum.LOGIN_SECONDARY);
                } else {
                    log.info("登录失效token:" + authToken);
                    throw new BusinessException(ErrorCodeEnum.NO_LOGIN);
                }
            }
        } else {
            return sessionUser;
        }
    }    public SessionUser getSessionUserByIgnoreFilter(String authToken) {
        SessionUser sessionUser = null;
        if (StringUtils.isNotEmpty(authToken)) {
            sessionUser = (SessionUser)this.cacheService.getSessionCache(authToken, "sessionUser");
        }

        if (sessionUser == null) {
            sessionUser = new SessionUser();
            sessionUser.setUserId("0");
            sessionUser.setUserName("-");
            sessionUser.setUserAccount("-");
        }

        return sessionUser;
    }
}
