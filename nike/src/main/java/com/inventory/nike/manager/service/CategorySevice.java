package com.inventory.nike.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.inventory.nike.manager.dto.CategoryDTO;
import com.inventory.nike.manager.po.Category;
import com.inventory.nike.manager.query.CategoryQuery;

public interface CategorySevice extends IService<Category> {

    PageInfo<Category> pageData(CategoryQuery query);

    void updateData(CategoryDTO formDTO);

    Category selectById(String id);

    void insertData(CategoryDTO formDTO);

    void delete(String ids);
}
