package com.wolve.libhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

public class JsonConvert implements Convert {
    //默认的Json转 Java Bean的转换器
    @Override
    public Object convert(String response, Type type) {
        JSONObject jsonObject = JSON.parseObject(response);
        JSONArray data = jsonObject.getJSONArray("data");
        if (data != null) {
            return JSON.parseObject(data.toString(), type);
        }
        return null;
    }
}
