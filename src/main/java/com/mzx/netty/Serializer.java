package com.mzx.netty;

public interface Serializer {
    byte getSerizlizerAlg();

    byte[] serializer(Object object);

    <T> T deserializer(Class<T> clazz, byte[] bytes);

    Serializer DEFAULT = new JSONSerializer();

}
