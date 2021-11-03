package com.inventory.rayli.common.util;

import com.alibaba.fastjson.JSONObject;
import com.inventory.rayli.common.vo.BusinessException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class BeanUtil {
    private static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap();

    public BeanUtil() {
    }

    public static void copy(Object srcObj, Object destObj) {
        String key = genKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier = null;
        if (!BEAN_COPIERS.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = (BeanCopier)BEAN_COPIERS.get(key);
        }

        copier.copy(srcObj, destObj, (Converter)null);
    }

    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + "_" + destClazz.getName();
    }

    public static void mapToEntity(Map<String, Object> map, Object entity) {
        if (map != null) {
            try {
                BeanUtils.populate(entity, map);
            } catch (Exception var3) {
                throw new BusinessException("数据格式异常", var3);
            }
        }
    }

    public static void jsonToEntity(JSONObject jsonObject, Object entity) {
        if (jsonObject != null) {
            Map<String, Object> map = new HashMap(2);
            Iterator var3 = jsonObject.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Object> entry = (Entry)var3.next();
                map.put(entry.getKey(), entry.getValue());
            }

            mapToEntity(map, entity);
        }
    }
}
