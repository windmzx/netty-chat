package com.mzx;

import com.alibaba.fastjson.JSON;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerizlizerAlg() {
//        1是json序列化
        return 1;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }


}
