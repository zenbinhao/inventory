package com.inventory.rayli.manager.mapper;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:17
 * @Description: TODO
 */

import com.inventory.rayli.common.mapper.MapperCustom;
import com.inventory.rayli.manager.po.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleCategoryMapper extends MapperCustom<ArticleCategory> {

    int deleteData(@Param("ids") String[] ids);

}
