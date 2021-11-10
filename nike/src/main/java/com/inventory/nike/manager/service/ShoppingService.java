package com.inventory.nike.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.inventory.nike.manager.dto.ShoppingDTO;
import com.inventory.nike.manager.po.Shopping;
import com.inventory.nike.manager.vo.ShoppingVO;

import java.util.List;

public interface ShoppingService extends IService<Shopping> {

    void insertData(ShoppingDTO formDTO);

    void delete(String ids);

    List<ShoppingVO> selectList();

}
