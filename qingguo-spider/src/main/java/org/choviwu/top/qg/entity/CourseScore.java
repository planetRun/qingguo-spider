package org.choviwu.top.qg.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
@Accessors(chain = true)
@Data
public class CourseScore implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer uid;

    private String xnxq;
    private String courseName;

    private Double courseCredit;

    private String courseType;

    private String testType;

    private String natureRead;

    private Double score;

    private BigDecimal addtime;


    @Override
    public String toString() {
        return "CourseScore{" +
                "id=" + id +
                ", courseName=" + courseName +
                ", courseCredit=" + courseCredit +
                ", courseType=" + courseType +
                ", testType=" + testType +
                ", natureRead=" + natureRead +
                ", score=" + score +
                ", addtime=" + addtime +
                "}";
    }
}
