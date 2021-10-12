package org.choviwu.top.qg.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
@Builder
@Data
public class StudentScore implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer xnxq;
    private Integer uid;

    private Integer xn;

    private String scoreImg;

    private BigDecimal addtime;

    @Override
    public String toString() {
        return "StudentScore{" +
                "id=" + id +
                ", xnxq=" + xnxq +
                ", xn=" + xn +
                ", scoreImg=" + scoreImg +
                ", addtime=" + addtime +
                "}";
    }
}
