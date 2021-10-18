package org.choviwu.top.qg.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentUser implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String studentId;

    private String password;

    @TableField("openId")
    private String openId;

    @TableField("schoolId")
    private Integer schoolId;

    @TableField("state")
    private Integer state;

    private BigDecimal addtime;

    @Override
    public String toString() {
        return "StudentUser{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", password=" + password +
                ", openId=" + openId +
                ", schoolId=" + schoolId +
                ", addtime=" + addtime +
                "}";
    }
}
