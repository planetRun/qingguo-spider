package org.choviwu.top.qg.service;

import org.choviwu.top.qg.entity.StudentScore;
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
public interface StudentScoreService extends IService<StudentScore> {

    String getStudentScore( String openId, String xqxn, String xn);

    List getCourseScores( String openId,  String xqxn, String xn);

    List getCourseScoreSchool( String studentId,String password,Integer school,  String xqxn, String xn);
}
