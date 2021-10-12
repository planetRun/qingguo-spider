package org.choviwu.top.qg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Config {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String param;

    private String result;

}
