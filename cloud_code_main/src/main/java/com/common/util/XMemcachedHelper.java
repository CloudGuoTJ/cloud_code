package com.common.util;

import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class XMemcachedHelper implements Serializable {

    private static final long serialVersionUID = 1L;

    private static MemcachedClient memcachedClient;

    @Autowired
    public void setMemcachedClient(MemcachedClient memcachedClient) {
        XMemcachedHelper.memcachedClient = memcachedClient;
    }

    public static void addCache(String key, Object value, int expiry) throws Exception {
        if (StringUtils.isBlank(key) || value == null) {
            return;
        }

        try{
            memcachedClient.add(key, expiry * 60, value);
        } catch (Exception e){
            return;
        }
    }

    public static Object findCache(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        try{
            return memcachedClient.get(key);
        } catch (Exception e){
            return null;
        }
    }

    public static void deleteCache(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }

        try{
            memcachedClient.delete(key);
        } catch (Exception e){
            return;
        }
    }

    public static void replaceCache(String key, Object value, int expiry) {
        if (StringUtils.isBlank(key)) {
            return;
        }

        try{
            memcachedClient.replace(key, expiry * 60, value);
        } catch (Exception e){
            return;
        }
    }

    public static boolean set(String key, Object value, int expiry) {
        if (StringUtils.isBlank(key)) {
            return false;
        }

        try{
            return memcachedClient.set(key, expiry, value);
        } catch (Exception e){
            return false;
        }
    }

    public static boolean append(String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return false;
        }

        try{
            return memcachedClient.append(key, value);
        } catch (Exception e){
            return false;
        }
    }

    public static void flushAll() {
        try{
            memcachedClient.flushAll();
        } catch (Exception e){
            return;
        }
    }
}
