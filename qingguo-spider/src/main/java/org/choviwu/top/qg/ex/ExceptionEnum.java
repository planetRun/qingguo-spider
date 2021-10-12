package org.choviwu.top.qg.ex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/***
 *
 **/
@AllArgsConstructor
@Getter
public enum  ExceptionEnum {

    sys_error(501,"系统异常"),



    user_not_bind_account(40001,"未绑定学号和密码"),
    user_already_bind_account(40002,"不能重复绑定账号,绑定学号为{0}"),
    user_score_not_public(40003,"该学年成绩还未公布哦"),
    account_or_password_error(40004,"学号或密码不正确"),


    ;

    private int code;
    private String msg;

}
