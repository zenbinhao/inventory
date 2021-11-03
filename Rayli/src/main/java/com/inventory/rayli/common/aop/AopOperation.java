package com.inventory.rayli.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AopOperation {

    // 什么操作
    String type();

    // 是否保存操作记录
    boolean saveLog() default true;
    // 是否保存操作参数
    boolean saveLogParams() default true;

    // 是否是管理员操作
    boolean checkPermission() default false;
}
