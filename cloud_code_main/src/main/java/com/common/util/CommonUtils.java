package com.common.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by Atlantique on 2015/9/5.
 */
public class CommonUtils {

    /**
     * @param size : 返回list的大小
     * @param length ： list里面每一个字符串的长度
     * @param bitSet
     * @return ： 生成一个list，list的大小为size，list里面每一个字符串的长度为length
     */
    public static LinkedList<String> getRandomStrList(int size, int length, BitSet bitSet) {
        LinkedList<String> list = new LinkedList<String>();
        bitSet.set(0, 62 * 62 * 62, false);
        while (list.size() < size) {
            String str = RandomStringUtils.randomAlphanumeric(length);
            int i = Radix.convert62To10(str);
            if (!bitSet.get(i)) {
                bitSet.set(i, true);
                list.add(str);
            }
        }
        return list;
    }

    /**
     * 生成一串不重复的字符串的list，list中字符串的个数为size，每个字符串的长度为length，字符串的每一个字符为62进制的字符
     * @param size : 返回list的大小
     * @param length ： list里面每一个字符串的长度， length的大小不要超过5，超过5会出错
     * @return ： 生成一个list，list的大小为size，list里面每一个字符串的长度为length
     */
    public static LinkedList<String> getStrList(int size, int length) {
        if (Math.pow(62, length) < size) {
            return null;
        }
        LinkedList<String> list = new LinkedList<String>();
        BitSet bitSet = new BitSet((int) Math.pow(62, length));
        bitSet.set(0, false);
        while (list.size() < size) {
            String str = RandomStringUtils.randomAlphanumeric(length);
            int i = Radix.convert62To10(str);
            if (!bitSet.get(i)) {
                bitSet.set(i, true);
                list.add(str);
            }
        }
        return list;
    }

    /**
     * 是否重复请求（处理并发、重复提交等问题，第一次请求为false，之后为true。缓存时长：1min）
     * @param key 键
     * @param expiry 缓存时间（秒）
     * @return
     */
    public static boolean isRepeat(String key, int expiry){
        if(StringUtils.isBlank(key)){
            return true;
        }

        try {
            if(XMemcachedHelper.findCache(key) != null){
                return true;
            }

            XMemcachedHelper.set(key, true, expiry);
        } catch (Exception e){
            return true;
        }

        return false;
    }
}
