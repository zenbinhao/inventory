package com.inventory.nike.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.inventory.nike.manager.dto.ShoppingDTO;
import com.inventory.nike.manager.dto.SignDTO;
import com.inventory.nike.manager.po.Sign;
import com.inventory.nike.manager.vo.ShoppingVO;
import com.inventory.nike.manager.vo.SignVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignService extends IService<Sign> {

    void insertData(SignDTO formDTO);

    void delete(String ids);

    List<SignVO> selectList();

    void deleteBy(SignDTO formDTO);

    Integer checkState(SignDTO formDTO);

}
