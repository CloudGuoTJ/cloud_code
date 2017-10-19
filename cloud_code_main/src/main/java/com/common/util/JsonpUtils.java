package com.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * Jsonp跨域工具类
 * Created by qun on 2016/8/26.
 */
public class JsonpUtils {
    /**
     * 根据JSON获取Jsonp
     * @param callback
     * @param json JSON数据
     * @return
     */
    public static String get(String callback, String json) {
        if(StringUtils.isBlank(callback)){
            return json;
        }

        return callback + "(" + json + ")";
    }

    /**
     * 根据结果集获取Jsonp
     * @param callback
     * @param result 结果集
     * @return
     */
    public static String get(String callback, ResultObject result) {
        if(StringUtils.isBlank(callback)){
            return JSONObject.toJSONString(result);
        }

        return callback + "(" + JSONObject.toJSONString(result) + ")";
    }
}
