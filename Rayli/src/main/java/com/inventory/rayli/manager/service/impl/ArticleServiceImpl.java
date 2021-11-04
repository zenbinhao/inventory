package com.inventory.rayli.manager.service.impl;
/*
 * @Author: zeng
 * @Data: 2021/11/4 16:57
 * @Description: TODO
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.rayli.common.util.BeanUtil;
import com.inventory.rayli.common.vo.BusinessException;
import com.inventory.rayli.manager.dto.ArticleDTO;
import com.inventory.rayli.manager.mapper.ArticleMapper;
import com.inventory.rayli.manager.po.Article;
import com.inventory.rayli.manager.query.ArticleQuery;
import com.inventory.rayli.manager.service.ArticleService;
import com.inventory.rayli.manager.vo.ArticleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    @Resource
    private ArticleMapper articleMapper;

    @Override
    public PageInfo<ArticleVO> pageData(ArticleQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<ArticleVO> list = articleMapper.selectList(query);
        return PageInfo.of(list);
    }

    @Override
    public void updateData(ArticleDTO formDTO) {
        Article article = new Article();
        BeanUtil.copy(formDTO,article);
        articleMapper.updateById(article);
    }

    @Override
    public ArticleVO selectById(String id) {
        return articleMapper.selectById(id);
    }

    @Override
    public void insertData(ArticleDTO formDTO) {
        Article article = new Article();
        BeanUtil.copy(formDTO,article);
        articleMapper.insert(article);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        int row = articleMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }
}
