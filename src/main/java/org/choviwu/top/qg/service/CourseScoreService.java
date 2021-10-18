package org.choviwu.top.qg.service;

import org.choviwu.top.qg.entity.CourseScore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
public interface CourseScoreService extends IService<CourseScore> {

    void asynUpdate(List<CourseScore> list);
}
