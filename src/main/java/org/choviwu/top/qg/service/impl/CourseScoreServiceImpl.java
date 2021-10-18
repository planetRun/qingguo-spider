package org.choviwu.top.qg.service.impl;

import org.choviwu.top.qg.entity.CourseScore;
import org.choviwu.top.qg.mapper.CourseScoreMapper;
import org.choviwu.top.qg.service.CourseScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
@Service
public class CourseScoreServiceImpl extends ServiceImpl<CourseScoreMapper, CourseScore> implements CourseScoreService {

    @Override
    @Async
    public void asynUpdate(List<CourseScore> list) {
        saveOrUpdateBatch(list);
    }
}
