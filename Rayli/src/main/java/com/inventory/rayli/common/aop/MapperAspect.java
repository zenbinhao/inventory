package com.inventory.rayli.common.aop;

import com.inventory.rayli.common.bo.SessionUser;
import com.inventory.rayli.common.em.ErrorCodeEnum;
import com.inventory.rayli.common.po.BasePO;
import com.inventory.rayli.common.po.BusinessPO;
import com.inventory.rayli.common.service.AuthenticationService;
import com.inventory.rayli.common.util.RandomUtil;
import com.inventory.rayli.common.vo.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Aspect
public class MapperAspect {
    @Resource
    private AuthenticationService authenticationService;

    public MapperAspect() {
    }

    @Pointcut("execution(* com.inventory.rayli.common.mapper.MapperCustom.insert(..))")
    public void pointInsertService() {
    }

    @Pointcut("execution(* com.inventory.rayli.common.mapper.MapperCustom.update(..))")
    public void pointUpdateService() {
    }

    @Pointcut("execution(* com.inventory.rayli.common.mapper.MapperCustom.updateById(..))")
    public void pointUpdateVersionService() {
    }
    //初始化 基础表实体参数
    private void initInsertBase(BasePO basePo) {
        if (basePo.getGmtCreate() == null) {
            basePo.setGmtCreate(new Date());
        }

        if (basePo.getGmtModified() == null) {
            basePo.setGmtModified(new Date());
        }

    }
    //初始化 业务表实体参数
    private void initInsertBusiness(BusinessPO businessPo) {
        this.initInsertBase(businessPo);
        if (businessPo.getIsDeleted() == null) {
            businessPo.setIsDeleted(0);
        }

        if (businessPo.getVersion() == null) {
            businessPo.setVersion(1);
        }

        if (businessPo.getCreateUserId() == null) {
            SessionUser user = this.getSessionUser();
            businessPo.setCreateUserId(user.getUserId());
            businessPo.setCreateUserName(user.getUserName());
        }

        if (businessPo.getUpdateUserId() == null) {
            businessPo.setUpdateUserId(businessPo.getCreateUserId());
            businessPo.setUpdateUserName(businessPo.getCreateUserName());
        }

    }


    @Before("pointInsertService()")
    public void beforeInsertService(JoinPoint joinPoint) throws Exception {
        Object[] var2 = joinPoint.getArgs();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object object = var2[var4];
            if (object instanceof BusinessPO) {
                BusinessPO businessPo = (BusinessPO)object;
                if (StringUtils.isEmpty(businessPo.getId())) {
                    businessPo.setId(RandomUtil.uuid());
                }

                this.initInsertBusiness(businessPo);
            } else if (object instanceof BasePO) {
                BasePO basePo = (BasePO)object;
                if (StringUtils.isEmpty(basePo.getId())) {
                    basePo.setId(RandomUtil.uuid());
                }

                this.initInsertBase(basePo);
            }
        }

    }

    @Before("pointUpdateService()||pointUpdateVersionService()")
    public void beforeUpdate(JoinPoint joinPoint) {
        Object[] var2 = joinPoint.getArgs();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object object = var2[var4];
            SessionUser user;
            if (object instanceof BusinessPO) {
                BusinessPO businessPo = (BusinessPO)object;
                if (businessPo.getGmtModified() == null) {
                    businessPo.setGmtModified(new Date());
                }

                if (businessPo.getUpdateUserId() == null) {
                    user = this.getSessionUser();
                    businessPo.setUpdateUserId(user.getUserId());
                    businessPo.setUpdateUserName(user.getUserName());
                }
            } else if (object instanceof BasePO) {
                BasePO basePo = (BasePO)object;
                if (basePo.getGmtModified() == null) {
                    basePo.setGmtModified(new Date());
                }
            }
        }

    }

    @Around("pointUpdateVersionService()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        BusinessPO businessPo = null;
        Object[] var4 = pjp.getArgs();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Object object = var4[var6];
            if (object instanceof BusinessPO) {
                businessPo = (BusinessPO)object;
            }
        }

        Object res = pjp.proceed();
        if (res instanceof Long) {
            long result = (Long)res;
            if (result == 0L && businessPo != null && businessPo.getVersion() != null) {
                throw new BusinessException(ErrorCodeEnum.UPDATE_VES);
            }
        } else if (res instanceof Integer) {
            int result = (Integer)res;
            if (result == 0 && businessPo != null && businessPo.getVersion() != null) {
                throw new BusinessException(ErrorCodeEnum.UPDATE_VES);
            }
        }

        return res;
    }

    private SessionUser getSessionUser() {
        String authToken = this.authenticationService.getAuthToken();
        SessionUser user = this.authenticationService.getSessionUserByIgnoreFilter(authToken);
        return user;
    }
}