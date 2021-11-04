package com.inventory.rayli.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/4 15:46
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.rayli.common.util.BeanUtil;
import com.inventory.rayli.common.vo.BusinessException;
import com.inventory.rayli.manager.dto.AccountUserInfoDTO;
import com.inventory.rayli.manager.mapper.AccountUserInfoMapper;
import com.inventory.rayli.manager.po.AccountUserInfo;
import com.inventory.rayli.manager.query.AccountUserInfoQuery;
import com.inventory.rayli.manager.service.AccountUserInfoService;
import com.inventory.rayli.manager.vo.AccountUserInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AccountUserInfoServiceImpl extends ServiceImpl<AccountUserInfoMapper, AccountUserInfo> implements AccountUserInfoService {

    @Resource
    private AccountUserInfoMapper accountUserInfoMapper;

    @Override
    public PageInfo<AccountUserInfoVO> pageData(AccountUserInfoQuery query) {
        //开启分页
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<AccountUserInfoVO> list = accountUserInfoMapper.selectList(query);
        return PageInfo.of(list);
    }

    @Override
    public void updateData(AccountUserInfoDTO formDTO) {
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        BeanUtil.copy(formDTO,accountUserInfo);
        accountUserInfoMapper.updateById(accountUserInfo);
    }

    @Override
    public AccountUserInfoVO selectById(String id) {
        return accountUserInfoMapper.selectById(id);
    }

    @Override
    public void insertData(AccountUserInfoDTO formDTO) {
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        BeanUtil.copy(formDTO,accountUserInfo);
        accountUserInfoMapper.insert(accountUserInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        int row = accountUserInfoMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }
}
