package com.inventory.rayli.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/4 16:24
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.rayli.common.util.BeanUtil;
import com.inventory.rayli.common.vo.BusinessException;
import com.inventory.rayli.manager.dto.ArticleCategoryDTO;
import com.inventory.rayli.manager.mapper.ArticleCategoryMapper;
import com.inventory.rayli.manager.po.ArticleCategory;
import com.inventory.rayli.manager.query.ArticleCategoryQuery;
import com.inventory.rayli.manager.service.ArticleCategorySevice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleCategorySeviceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategorySevice {

    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public PageInfo<ArticleCategory> pageData(ArticleCategoryQuery query) {
        QueryWrapper<ArticleCategory> articleCategoryQueryWrapper = new QueryWrapper<>();
        articleCategoryQueryWrapper.lambda().like(StringUtils.isNotEmpty(query.getCategoryName()),ArticleCategory::getCategoryName,query.getCategoryName()).orderByDesc(ArticleCategory::getGmtCreate);
        List<ArticleCategory> list = articleCategoryMapper.selectList(articleCategoryQueryWrapper);

        PageHelper.startPage(query.getPageNum(),query.getPageSize());

        return PageInfo.of(list);
    }

    @Override
    public void updateData(ArticleCategoryDTO formDTO) {
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtil.copy(formDTO,articleCategory);
        articleCategoryMapper.updateById(articleCategory);
    }

    @Override
    public ArticleCategory selectById(String id) {
        return articleCategoryMapper.selectById(id);
    }

    @Override
    public void insertData(ArticleCategoryDTO formDTO) {
        ArticleCategory articleCategory = new ArticleCategory();
        BeanUtil.copy(formDTO,articleCategory);
        articleCategoryMapper.insert(articleCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        int row = articleCategoryMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }
}
