//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inventory.rayli.common.aop;

import com.inventory.rayli.common.bo.SessionUser;
import com.inventory.rayli.common.em.ErrorCodeEnum;
import com.inventory.rayli.common.service.AuthenticationService;
import com.inventory.rayli.common.vo.BusinessException;
import com.inventory.rayli.manager.mapper.AccountUserMapper;
import com.inventory.rayli.manager.po.AccountUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class PermissionAspect {
    @Resource
    protected HttpServletRequest request;
    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private AccountUserMapper accountUserMapper;
    public PermissionAspect() {
    }

    @Pointcut("execution(* com.inventory.rayli.manager.controller.*Controller.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()&&@annotation(operation)")
    public void beforeCheckPermission(JoinPoint joinPoint, AopOperation operation) {

        //获取 身份认证
        String authToken = this.authenticationService.getAuthToken();
        if(StringUtils.isEmpty(authToken)){
            throw new BusinessException(ErrorCodeEnum.NOT_AUTH_TOKEN);
        }

        SessionUser paramUser = this.authenticationService.getSessionUser();

        String type = operation.type();

        // 如果是管理员接口
        if(operation.checkPermission()){
            if(paramUser!=null){
                AccountUser accountUser = accountUserMapper.selectById(paramUser.getUserId());
                if(accountUser.getUserType()!=1){
                    throw new BusinessException(ErrorCodeEnum.NO_PERMISSIONS.getCode(),ErrorCodeEnum.NO_PERMISSIONS.getMsg().replace("{module}","管理员模块").replace("{type}",type));
                }
            }
        }

        if(operation.saveLog()&& paramUser!=null){
            //暂时保存到日志中
            log.info("保存"+paramUser.getUserName()+"的"+type+"操作的记录");
        }
    }
}
