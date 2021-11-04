package com.inventory.rayli.manager.mapper;/*
 * @Author: zeng
 * @Data: 2021/11/4 19:35
 * @Description: TODO
 */

import com.inventory.rayli.common.mapper.MapperCustom;
import com.inventory.rayli.manager.po.Ranking;
import com.inventory.rayli.manager.vo.RankingVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankingMapper extends MapperCustom<Ranking> {

    List<RankingVO> selectList();

    RankingVO selectById(String id);

    void deleteByRank(Integer rank);

    void insertData(Ranking ranking);

    void updateData(Ranking ranking);

    Integer countRank(Integer rank);

    Integer countFoodId(String fkFoodId);
}
