package com.inventory.nike.manager.service.impl;/*
 * @Author: zeng
 * @Data: 2021/11/6 12:28
 * @Description: TODO
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.nike.common.util.BeanUtil;
import com.inventory.nike.common.vo.BusinessException;
import com.inventory.nike.manager.dto.CategoryDTO;
import com.inventory.nike.manager.mapper.CategoryMapper;
import com.inventory.nike.manager.po.Category;
import com.inventory.nike.manager.query.CategoryQuery;
import com.inventory.nike.manager.service.CategorySevice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategorySeviceImpl extends ServiceImpl<CategoryMapper, Category> implements CategorySevice {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public PageInfo<Category> pageData(CategoryQuery query) {
        QueryWrapper<Category> articleCategoryQueryWrapper = new QueryWrapper<>();
        articleCategoryQueryWrapper.lambda().like(StringUtils.isNotEmpty(query.getCategoryName()),Category::getCategoryName,query.getCategoryName()).orderByDesc(Category::getGmtCreate);
        List<Category> list = categoryMapper.selectList(articleCategoryQueryWrapper);

        PageHelper.startPage(query.getPageNum(),query.getPageSize());

        return PageInfo.of(list);
    }

    @Override
    public void updateData(CategoryDTO formDTO) {
        Category category = new Category();
        BeanUtil.copy(formDTO,category);
        categoryMapper.updateById(category);

    }

    @Override
    public Category selectById(String id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public void insertData(CategoryDTO formDTO) {
        Category category = new Category();
        BeanUtil.copy(formDTO,category);
        categoryMapper.insert(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessException("请选择要删除的记录");
        }
        String []id =ids.split(",");
        int row = categoryMapper.deleteData(id);
        if (row <=0|| ids.length()<=0) {
            throw new BusinessException("批量删除操作失败");
        }
    }
}
