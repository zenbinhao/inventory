package com.inventory.rayli.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.rayli.common.bo.SessionUser;
import com.inventory.rayli.common.em.ErrorCodeEnum;
import com.inventory.rayli.common.service.AuthenticationService;
import com.inventory.rayli.common.service.CacheService;
import com.inventory.rayli.common.util.BeanUtil;
import com.inventory.rayli.common.util.ChangeType;
import com.inventory.rayli.common.util.RandomUtil;
import com.inventory.rayli.common.util.RsaUtils;
import com.inventory.rayli.common.vo.BusinessException;
import com.inventory.rayli.manager.dto.LoginFormDTO;
import com.inventory.rayli.manager.mapper.AccountUserMapper;
import com.inventory.rayli.manager.po.AccountUser;
import com.inventory.rayli.manager.service.LoginService;
import com.inventory.rayli.manager.vo.AccountUserVO;
import com.inventory.rayli.manager.vo.RsaKeys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class LoginServiceImpl extends ServiceImpl<AccountUserMapper, AccountUser> implements LoginService {

    @Resource
    CacheService cacheService;

    @Resource
    AccountUserMapper accountUserMapper;

    @Resource
    private AuthenticationService authenticationService;

    @Override
    public AccountUserVO login(LoginFormDTO form, HttpServletRequest httpServletRequest) {

        if (StringUtils.isEmpty(form.getUserAccount())) {
            throw new BusinessException("账号不允许为空");
        } else if (StringUtils.isEmpty(form.getPassword())) {
            throw new BusinessException("密码不允许为空");
        } else {
            if (form.getLoginType() == null) {
                // 0web  1微信  暂时不用枚举
                form.setLoginType(0);
            }

            //  rsa非对称 私钥解密
            String password = form.getPassword();
            if (StringUtils.isNotEmpty(form.getRsaKey())) {
                RsaKeys rsaKeys = (RsaKeys) this.cacheService.getObj(form.getRsaKey());
                if (rsaKeys == null) {
                    throw new BusinessException(ErrorCodeEnum.RSA_FAILURE);
                }
                try {
                    password = RsaUtils.decryptDataOnJava(form.getPassword(), rsaKeys.getPrivateKey());
                } catch (Exception e) {
                    throw new BusinessException(ErrorCodeEnum.LOGIN_PASSWORD);
                }
            }
            //  md5加密
                try {
                    password = ChangeType.EncoderByMd5(password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            AccountUser accountUser = this.accountUserMapper.selectOne((new QueryWrapper<AccountUser>()).lambda().eq(AccountUser::getUserPassword,password).eq(AccountUser::getUserAccount, form.getUserAccount()).orderByDesc(AccountUser::getGmtCreate).last("limit 1"));
            if (accountUser == null) {
                throw new BusinessException(ErrorCodeEnum.LOGIN_USER);
            }
            AccountUserVO userVO = new AccountUserVO();
            BeanUtil.copy(accountUser, userVO);
            String AuthToken = RandomUtil.uuid();
            userVO.setAuthToken(AuthToken);
            String key = "sessionUser";
            SessionUser sessionUser = new SessionUser();
            BeanUtil.copy(userVO, sessionUser);
            sessionUser.setUserId(userVO.getId());
            cacheService.setSessionCache(sessionUser.getAuthToken(),key,sessionUser);
            return userVO;
        }


    }

    @Override
    public void loginOut(HttpServletRequest request) {
        if (this.authenticationService.getAuthToken() == null) {
            return ;
        } else {
            if (request.getSession() != null) {
                request.getSession().invalidate();
            }
            this.cacheService.cleanSessionCache(this.authenticationService.getAuthToken(), "sessionUser");
        }
    }
}