package com.inventory.rayli.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.inventory.rayli.manager.dto.ArticleCategoryDTO;
import com.inventory.rayli.manager.po.ArticleCategory;
import com.inventory.rayli.manager.query.ArticleCategoryQuery;

public interface ArticleCategorySevice extends IService<ArticleCategory> {

    PageInfo<ArticleCategory> pageData(ArticleCategoryQuery query);

    void updateData(ArticleCategoryDTO formDTO);

    ArticleCategory selectById(String id);

    void insertData(ArticleCategoryDTO formDTO);

    void delete(String ids);
}
