package org.choviwu.top.qg.entity.bean;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/***
 *
 **/
@Builder
@Data
public class CatagoryVo implements Serializable {


    private String id ;

    private String catagory;

    private String alias;

    private Integer type;
}
