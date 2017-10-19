package com.common.util;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_EMPTY)
public class ResultObject implements Serializable {

    private static final long serialVersionUID = 3149206066903085674L;

    private long errorCode = 0;
    private String msg = "";
    private Object data;

    private JSONObject map = new JSONObject();

    public ResultObject() {}

    public ResultObject(Object data) {
        this.data = data;
    }

    public ResultObject(long errorCode, String errMsg) {
        this.errorCode = errorCode;
        this.setMsg(errMsg);
    }

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultObject createErrorInstance(long errorCode, String errMsg) {
        ResultObject result = new ResultObject(errorCode, errMsg);
        return result;
    }

    public static ResultObject createInstance(long errorCode, String errMsg) {
        ResultObject result = new ResultObject(errorCode, errMsg);
        return result;
    }

    public static ResultObject createInstance(Object object) {
        ResultObject result = new ResultObject();
        result.data = object;
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getMap() {
        return map;
    }

    public void set(long errorCode, String msg){
        this.setErrorCode(errorCode);
        this.setMsg(msg);
    }

    public void set(long errorCode, String msg, Object data){
        this.setErrorCode(errorCode);
        this.setMsg(msg);
        this.setData(data);

        this.map.put("data", data);
    }

    /**
     * 返回JSON格式数据，需要显示data字段必须调用set(errorCode, msg, data)函数
     * @return
     */
    public String toString(){
        this.map.put("errorCode", this.errorCode);
        this.map.put("msg", this.msg);

        return JSONObject.toJSONString(this.map, SerializerFeature.WriteMapNullValue);
    }
}
