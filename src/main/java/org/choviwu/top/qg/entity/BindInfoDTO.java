package org.choviwu.top.qg.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BindInfoDTO implements Serializable {

    private String userNo;

    private String password;

    private String openId;

    private Integer schoolId = 13683;

    private String type;


}
