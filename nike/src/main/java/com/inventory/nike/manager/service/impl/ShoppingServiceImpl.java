package com.inventory.nike.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/6 15:06
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.nike.common.bo.SessionUser;
import com.inventory.nike.common.service.AuthenticationService;
import com.inventory.nike.common.util.BeanUtil;
import com.inventory.nike.common.vo.BusinessException;
import com.inventory.nike.manager.dto.ShoppingDTO;
import com.inventory.nike.manager.mapper.ShoppingMapper;
import com.inventory.nike.manager.po.Shopping;
import com.inventory.nike.manager.service.ShoppingService;
import com.inventory.nike.manager.vo.ShoppingVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShoppingServiceImpl extends ServiceImpl<ShoppingMapper, Shopping> implements ShoppingService {

    @Resource
    private ShoppingMapper shoppingMapper;

    @Resource
    private AuthenticationService authenticationService;

    @Override
    public void insertData(ShoppingDTO formDTO) {
        String userId = authenticationService.getSessionUser().getUserId();
        Integer integer = shoppingMapper.selectCount(new QueryWrapper<Shopping>().lambda().eq(Shopping::getFkFoodId, formDTO.getFkFoodId()).eq(Shopping::getFkUserId, userId));
        if(integer>0){
            throw new BusinessException("1050","请勿重复加入购物车");
        }
        Shopping shopping = new Shopping();
        BeanUtil.copy(formDTO,shopping);
        shopping.setFkUserId(userId);
        shoppingMapper.insert(shopping);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        int row = shoppingMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }

    @Override
    public List<ShoppingVO> selectList() {
        return shoppingMapper.selectList(authenticationService.getSessionUser().getUserId());
    }
}
