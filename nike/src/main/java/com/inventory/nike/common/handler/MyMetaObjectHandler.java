package com.inventory.nike.common.handler;
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///*
//* 自动填充组件
//* */
//@Component
//public class MyMetaObjectHandler implements MetaObjectHandler {
//
//    //mp 进行添加操作时  加强方法
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        this.setFieldValByName("version",0,metaObject);
//        this.setFieldValByName("isDeleted",0,metaObject);
//        this.setFieldValByName("gmtCreate",new Date(),metaObject);
//        this.setFieldValByName("gmtModified",new Date(),metaObject);
//    }
//
//    //mp 进行修改操作时  加强方法
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        this.setFieldValByName("gmtModified",new Date(),metaObject);
//
//    }
//}
