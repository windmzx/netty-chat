package com.mzx;

public interface Serializer {
    byte getSerizlizerAlg();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);

    Serializer DEFAULT = new JSONSerializer();

}
