package com.inventory.rayli.manager.service;/*
 * @Author: zeng
 * @Data: 2021/11/4 20:41
 * @Description: TODO
 */

import com.github.pagehelper.PageInfo;
import com.inventory.rayli.manager.po.Ranking;
import com.inventory.rayli.manager.vo.RankingVO;

import java.util.List;

public interface RankingService {

    List<RankingVO> selectList();

    RankingVO selectById(String id);

    void deleteByRank(Integer rank);

    void insertData(Ranking ranking);

    void updateData(Ranking ranking);
}
