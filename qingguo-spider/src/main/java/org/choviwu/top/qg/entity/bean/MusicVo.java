package org.choviwu.top.qg.entity.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/***
 *
 **/
@Data
public class MusicVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String url;
    private String author;
    private String title;
    private String pic;
    private String br;
    private String size;
    private String md5;
    private String code;
    private String expi;
    private String type;
    private String gain;
    private String fee;
    private String uf;
    private String payed;
    private String flag;
    private String canExtend;
    private String freeTrialInfo;
    private String level;
    private String encodType;



}