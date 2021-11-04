package com.inventory.rayli.manager.mapper;

import com.inventory.rayli.common.mapper.MapperCustom;
import com.inventory.rayli.manager.po.Article;
import com.inventory.rayli.manager.query.ArticleQuery;
import com.inventory.rayli.manager.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ArticleMapper extends MapperCustom<Article> {

    int deleteData(@Param("ids") String[] ids);

    List<ArticleVO> selectList(ArticleQuery courseQuery);

    ArticleVO selectById(String id);
}
