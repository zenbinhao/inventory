package com.inventory.rayli.common.service;

public interface CacheService {
    String getAuthToken();

    void setSessionCache(String var1, String var2, Object var3);

    void cleanSessionCache(String var1, String var2);

    Object getSessionCache(String var1, String var2);

    void deleteKey(String var1);

    void setObj(String var1, Object var2, Long var3);

    void setObj(String var1, Object var2);

    void setObjTenMinutes(String var1, Object var2);

    Object getObj(String var1);

    void setTimeOut(String var1);

    void cleanTimeout();
}
