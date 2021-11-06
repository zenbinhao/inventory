package com.inventory.nike.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.nike.common.em.ErrorCodeEnum;
import com.inventory.nike.common.util.BeanUtil;
import com.inventory.nike.common.util.ChangeType;
import com.inventory.nike.common.vo.BusinessException;
import com.inventory.nike.manager.dto.AccountUserDTO;
import com.inventory.nike.manager.dto.AccountUserUpdateDTO;
import com.inventory.nike.manager.mapper.AccountUserMapper;
import com.inventory.nike.manager.po.AccountUser;
import com.inventory.nike.manager.query.AccountUserQuery;
import com.inventory.nike.manager.service.AccountUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public void delete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        int row = accountUserMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }

    @Override
    public void updateData(AccountUserUpdateDTO formDTO) {
        AccountUser accountUser = new AccountUser();
        BeanUtil.copy(formDTO,accountUser);
        accountUserMapper.updateById(accountUser);
    }

    @Override
    public PageInfo<AccountUser> pageData(AccountUserQuery query) {
        //开启条件查询
        QueryWrapper<AccountUser> queryWrapper = new QueryWrapper<AccountUser>();
        queryWrapper.lambda().like(StringUtils.isNotEmpty(query.getUserName()),AccountUser::getUserName,query.getUserName()).orderByDesc(AccountUser::getGmtCreate);

        //开启分页
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<AccountUser> list = accountUserMapper.selectList(queryWrapper);
        return PageInfo.of(list);
    }

    @Override
    public AccountUser selectById(String id) {
        return accountUserMapper.selectById(id);
    }
}
