package org.choviwu.top.qg.ex;

import lombok.Data;

import java.text.MessageFormat;

@Data
public class CrudException extends RuntimeException {

    private int code;

    private String msg;

    public CrudException(String msg){
        super(msg);
        this.msg = msg;
    }

    public CrudException(ExceptionEnum msg){
        super(msg.getMsg());
        this.msg = msg.getMsg();
    }

    public CrudException(ExceptionEnum msg,Object...params){
        super(msg.getMsg());
        this.msg = MessageFormat.format(msg.getMsg(),params);

    }
}
