package com.inventory.rayli.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.rayli.common.em.ErrorCodeEnum;
import com.inventory.rayli.common.util.BeanUtil;
import com.inventory.rayli.common.util.ChangeType;
import com.inventory.rayli.common.vo.BusinessException;
import com.inventory.rayli.manager.dto.AccountUserDTO;
import com.inventory.rayli.manager.mapper.AccountUserMapper;
import com.inventory.rayli.manager.po.AccountUser;
import com.inventory.rayli.manager.service.AccountUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountUserServiceImpl extends ServiceImpl<AccountUserMapper, AccountUser> implements AccountUserService {

    @Resource
    AccountUserMapper accountUserMapper;

    @Override
    public Integer registerAccount(AccountUserDTO from) {
        AccountUser accountUser1 = accountUserMapper.selectOne(new QueryWrapper<AccountUser>().lambda().eq(AccountUser::getUserAccount, from.getUserAccount()).orderByDesc(AccountUser::getGmtCreate).last("limit 1"));
        if(accountUser1!=null){
            throw new BusinessException(ErrorCodeEnum.LOGIN_EXIST);
        }

        String password= null;
        try {
            password = ChangeType.EncoderByMd5(from.getUserPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        AccountUser accountUser = new AccountUser();
        BeanUtil.copy(from,accountUser);
        //默认激活
        accountUser.setActiveStatus(0);
        //默认无管理员权限
        accountUser.setUserType(0);
        accountUser.setUserPassword(password);
        accountUserMapper.insert(accountUser);
        return 0;
    }
}
