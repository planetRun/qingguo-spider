package org.choviwu.top.qg.redis;

import java.util.Set;

public interface Cached<T> {

    String GARBAGE_HASH = RedisEnum.GARBAGE_HASH.getName();

    String GARBAGE_LIST = RedisEnum.GARBAGE_HASH.getName();

    <T> T getValue(String key);


    <T> T getHashValue(String hash,String item,Class<T> tClass);


    Set<T> getSetValues(String item);

    boolean hset(String key,String item,Object value);
}
