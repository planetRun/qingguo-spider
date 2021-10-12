package org.choviwu.top.qg.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.choviwu.top.qg.ex.CrudException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
@EnableWebMvc
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object exception(HttpServletResponse response,Exception e) {
        e.printStackTrace();
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        return ApiResponse.error("服务器异常,稍后再试");
    }

    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object exception(MaxUploadSizeExceededException e) {
//        return "文件上传过大，请上传10M以内的图片";
        return ApiResponse.error("文件上传过大,请上传10M以内的图片");
    }

    @ResponseBody
    @ExceptionHandler(CrudException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object exception(CrudException e, HttpServletResponse response) {
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        e.printStackTrace();
        return ApiResponse.error(e.getMsg(),e.getCode());

    }

    @ResponseBody
    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object exception(ServletException e) {
        return ApiResponse.error("服务器异常,稍后再试");
    }

}
