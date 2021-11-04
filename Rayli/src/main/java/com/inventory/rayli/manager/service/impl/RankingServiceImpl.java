package com.inventory.rayli.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/4 20:45
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.inventory.rayli.common.vo.BusinessException;
import com.inventory.rayli.manager.mapper.RankingMapper;
import com.inventory.rayli.manager.po.Ranking;
import com.inventory.rayli.manager.service.RankingService;
import com.inventory.rayli.manager.vo.RankingVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {

    @Resource
    private RankingMapper rankingMapper;

    @Override
    public List<RankingVO> selectList() {
        return rankingMapper.selectList();
    }

    @Override
    public RankingVO selectById(String id) {
        return rankingMapper.selectById(id);
    }

    @Override
    public void deleteByRank(Integer rank) {
        rankingMapper.deleteByRank(rank);
    }

    @Override
    public void insertData(Ranking ranking) {
        if(ranking.getRank()<=0){
            throw new BusinessException("1237","您输入的排名有问题");
        }
        checkRe(ranking);
        Integer integer = rankingMapper.countRank(ranking.getRank());
        if(integer>0){
            throw new BusinessException("1239","该排名已存在");
        }
        rankingMapper.insertData(ranking);
    }

    @Override
    public void updateData(Ranking ranking) {
        checkRe(ranking);
        rankingMapper.updateData(ranking);
    }

    void checkRe(Ranking ranking){
        Integer integer = rankingMapper.countFoodId(ranking.getFkFoodId());
//        QueryWrapper<Ranking> rankingQueryWrapper = new QueryWrapper<>();
//        rankingQueryWrapper.lambda().eq(Ranking::getRank,ranking.getRank());
//        Integer integer = rankingMapper.selectCount(rankingQueryWrapper);
        if(integer>0){
            throw new BusinessException("1238","该商品已存在其他排名");
        }
    }

}
