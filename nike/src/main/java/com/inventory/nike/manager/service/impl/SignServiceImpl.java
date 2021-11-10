package com.inventory.nike.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/6 15:59
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.nike.common.bo.SessionUser;
import com.inventory.nike.common.service.AuthenticationService;
import com.inventory.nike.common.util.BeanUtil;
import com.inventory.nike.common.vo.BusinessException;
import com.inventory.nike.manager.dto.SignDTO;
import com.inventory.nike.manager.mapper.SignMapper;
import com.inventory.nike.manager.po.Sign;
import com.inventory.nike.manager.service.SignService;
import com.inventory.nike.manager.vo.SignVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements SignService {

    @Resource
    private SignMapper signMapper;

    @Resource
    private AuthenticationService authenticationService;

    @Override
    public void insertData(SignDTO formDTO) {
        Integer integer = signMapper.selectCount(new QueryWrapper<Sign>().lambda().eq(Sign::getFkFoodId, formDTO.getFkFoodId()).eq(Sign::getFkUserId, authenticationService.getSessionUser().getUserId()));
        if(integer>0){
            throw new BusinessException("1051","请勿重复收藏");
        }
        Sign sign = new Sign();
        BeanUtil.copy(formDTO,sign);
        sign.setFkUserId(authenticationService.getSessionUser().getUserId());
        signMapper.insert(sign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        int row = signMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }

    @Override
    public List<SignVO> selectList() {
        return signMapper.selectList(authenticationService.getSessionUser().getUserId());
    }

    @Override
    public void deleteBy(SignDTO formDTO) {
        if (StringUtils.isEmpty(formDTO.getFkFoodId())) {
            throw new BusinessException("请选择要删除的记录");
        }
        int row = signMapper.deleteBy(formDTO.getFkFoodId(),authenticationService.getSessionUser().getUserId());
        if (row <=0) {
            throw new BusinessException("请勿乱操作，这是不存在的数据");
        }
    }

    @Override
    public Integer checkState(SignDTO formDTO){
        Integer integer = signMapper.selectCount(new QueryWrapper<Sign>().lambda().eq(Sign::getFkFoodId, formDTO.getFkFoodId()).eq(Sign::getFkUserId, authenticationService.getSessionUser().getUserId()));
        if(integer>0){
            return 1;
        }
        return 0;
    }
}
