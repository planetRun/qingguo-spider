package org.choviwu.top.qg.controller.web;

import lombok.*;
import lombok.experimental.Accessors;
import org.choviwu.top.qg.ex.ExceptionEnum;

@Data
@Builder
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private Object data;
    private String msg;
    private int code;


    public static ApiResponse error(String msg){
        return error(msg,ExceptionEnum.sys_error.getCode());
    }


    public static ApiResponse error(ExceptionEnum exceptionEnum){
        return error(exceptionEnum.getMsg(),exceptionEnum.getCode());
    }

    public static ApiResponse error(String msg,int code){
        ApiResponse response = new ApiResponse();
        response.setCode(code);
        response.setData(msg);
        response.setMsg(Flag.FAIL.getStr());
        return response;
    }
    enum Flag{
        SUCCESS("success"),
        FAIL("fail")

        ;
        @Getter
        @Setter
        private String str;
        Flag(String str){
            this.str = str;
        }

    }

}
