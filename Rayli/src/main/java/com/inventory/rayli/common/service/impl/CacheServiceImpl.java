package com.inventory.rayli.common.service.impl;


import com.inventory.rayli.common.bo.CacheBean;
import com.inventory.rayli.common.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {
    private Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);
    private static Map<String, CacheBean> mapCache = new HashMap(4);
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    protected HttpServletRequest request;
    @Value("${spring.redis.start:false}")
    private boolean startRedis = false;
    public static final String AUTH_TOKEN = "authToken";
    private static Long TIMEOUT_TEN_MINUTES = 6000L;
    @Value("${spring.redis.timeout:86400}")
    private long timeout = 86400L;

    public CacheServiceImpl() {
    }

    public String getAuthToken() {
        String authToken = null;
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            authToken = this.request.getHeader("authToken");
        }

        return authToken;
    }

    public void setSessionCache(String authCode, String key, Object value) {
        if (authCode != null && !"".equals(authCode)) {
            this.setObj(authCode + "_" + key, value, this.timeout);
        } else {
            this.request.getSession().setAttribute(key, value);
        }

    }

    public void cleanSessionCache(String authToken, String key) {
        this.deleteKey(authToken + "_" + key);
    }

    public Object getSessionCache(String authCode, String key) {
        return authCode != null && !"".equals(authCode) ? this.getObj(authCode + "_" + key) : this.request.getSession().getAttribute(key);
    }

    private boolean isStartRedis() {
        if (this.startRedis) {
            try {
                this.redisTemplate.getConnectionFactory().getConnection();
            } catch (Exception var2) {
                this.startRedis = false;
                this.log.error("获得redis连接失败,请确认是否已打开redis", var2);
            }
        }

        return this.startRedis;
    }

    public void deleteKey(String key) {
        if (this.isStartRedis()) {
            this.redisTemplate.delete(key);
        } else {
            mapCache.remove(key);
        }

    }

    public void setObj(String key, Object obj, Long seconds) {
        if (this.isStartRedis()) {
            if (seconds != null) {
                this.redisTemplate.opsForValue().set(key, obj, seconds, TimeUnit.SECONDS);
            } else {
                this.redisTemplate.opsForValue().set(key, obj);
            }
        } else {
            Calendar calendar = Calendar.getInstance();
            CacheBean cacheBean = new CacheBean();
            cacheBean.setCreate(calendar.getTimeInMillis());
            cacheBean.setFlag(true);
            cacheBean.setData(obj);
            if (seconds != null) {
                cacheBean.setAutoUpdate(true);
                cacheBean.setValidTimeSecond(seconds);
                calendar.add(13, new Integer(seconds + ""));
                cacheBean.setTimeout(calendar.getTimeInMillis());
            } else {
                cacheBean.setAutoUpdate(false);
            }

            mapCache.put(key, cacheBean);
        }

    }

    public void setObj(String key, Object obj) {
        this.setObj(key, obj, (Long)null);
    }

    public void setObjTenMinutes(String key, Object obj) {
        this.setObj(key, obj, TIMEOUT_TEN_MINUTES);
    }

    public Object getObj(String key) {
        if (key != null && !"".equals(key)) {
            if (this.isStartRedis()) {
                return this.redisTemplate.opsForValue().get(key);
            } else {
                CacheBean cacheBean = (CacheBean)mapCache.get(key);
                if (cacheBean != null) {
                    Calendar calendar = Calendar.getInstance();
                    if (cacheBean.getTimeout() != null && cacheBean.getTimeout() < calendar.getTimeInMillis()) {
                        mapCache.remove(key);
                        return null;
                    } else {
                        if (cacheBean.isAutoUpdate() && cacheBean.getValidTimeSecond() != null) {
                            calendar.add(13, new Integer(cacheBean.getValidTimeSecond() + ""));
                            cacheBean.setTimeout(calendar.getTimeInMillis());
                            mapCache.put(key, cacheBean);
                        }

                        return cacheBean.getData();
                    }
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public void cleanTimeout() {
        List<String> timeoutKey = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        Iterator var3 = mapCache.entrySet().iterator();

        while(true) {
            while(var3.hasNext()) {
                Entry<String, CacheBean> entry = (Entry)var3.next();
                if (((CacheBean)entry.getValue()).getTimeout() != null && ((CacheBean)entry.getValue()).getTimeout() < calendar.getTimeInMillis()) {
                    timeoutKey.add(entry.getKey());
                } else if (!((CacheBean)entry.getValue()).isFlag()) {
                    timeoutKey.add(entry.getKey());
                }
            }

            var3 = timeoutKey.iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                mapCache.remove(key);
            }

            return;
        }
    }

    public void setTimeOut(String key) {
        this.deleteKey(key);
    }
}
