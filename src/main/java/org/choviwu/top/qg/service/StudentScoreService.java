package org.choviwu.top.qg.service;

import org.choviwu.top.qg.entity.CourseScore;
import org.choviwu.top.qg.entity.CourseScoreDTO;
import org.choviwu.top.qg.entity.StudentScore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
public interface StudentScoreService extends IService<StudentScore> {

    List getCourseScoreSchool( String studentId,String password,Integer school,  String xqxn, String xn);

    List<CourseScoreDTO>  getCourseScore(String openId);
}
