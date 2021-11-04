package com.inventory.rayli.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.inventory.rayli.manager.dto.ArticleDTO;
import com.inventory.rayli.manager.po.Article;
import com.inventory.rayli.manager.query.ArticleQuery;
import com.inventory.rayli.manager.vo.ArticleVO;

public interface ArticleService extends IService<Article> {

    PageInfo<ArticleVO> pageData(ArticleQuery query);

    void updateData(ArticleDTO formDTO);

    ArticleVO selectById(String id);

    void insertData(ArticleDTO formDTO);

    void delete(String ids);
}
