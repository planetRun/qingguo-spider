package org.choviwu.top.qg.redis;

import lombok.Getter;

public enum  RedisEnum implements CommonEnum{
    GARBAGE_HASH("gs:garbage:",100001),
    GARBAGE_LIST("gs:garbage:list:",100002),
    USER_ACCOUNT("gs:account:",100003)







    ;


    @Getter
    private String name;
    @Getter
    private int code;

    RedisEnum(String name,int code){
        this.name = name;
        this.code = code;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCode() {
        return code;
    }
}
