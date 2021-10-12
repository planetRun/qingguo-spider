package org.choviwu.top.qg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.choviwu.top.qg.constant.RedisConstant;
import org.choviwu.top.qg.entity.CourseScore;
import org.choviwu.top.qg.entity.StudentScore;
import org.choviwu.top.qg.entity.StudentUser;
import org.choviwu.top.qg.ex.CrudException;
import org.choviwu.top.qg.ex.ExceptionEnum;
import org.choviwu.top.qg.mapper.StudentScoreMapper;
import org.choviwu.top.qg.mapper.StudentUserMapper;
import org.choviwu.top.qg.redis.RedisRepository;
import org.choviwu.top.qg.score.JwcRequest;
import org.choviwu.top.qg.service.CourseScoreService;
import org.choviwu.top.qg.service.StudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
@Service
public class StudentScoreServiceImpl extends ServiceImpl<StudentScoreMapper, StudentScore> implements StudentScoreService {

    @Autowired
    private StudentScoreMapper scoreMapper;
    @Autowired
    private StudentUserMapper studentUserMapper;
    @Autowired
    private CourseScoreService courseScoreService;
//    @Autowired
//    private RedisRepository redisRepository;


    @Override
    public String getStudentScore( String openId,  String xnxq,  String xn) {

//        StudentUser studentUser = studentUserMapper.selectOne(new QueryWrapper<StudentUser>().eq("openId", openId));
//        if (studentUser != null) {
//            StudentScore studentScore = scoreMapper.selectOne(new QueryWrapper<StudentScore>().eq("uid", studentUser.getId()).eq("xnxq", xnxq)
//                    .eq("xn", xn));
//            if (studentScore != null) {
//                return studentScore.getScoreImg();
//            } else {
//                String imageUrl = JwcRequest.getScore(JwcRequest.login(studentUser.getStudentId(), studentUser.getPassword(),studentUser.getSchoolId().toString()), xnxq, xn);
//                studentScore = StudentScore.builder().scoreImg(imageUrl)
//                        .addtime(new BigDecimal(System.currentTimeMillis()))
//                        .uid(studentUser.getId())
//                        .xn(Integer.valueOf(xn))
//                        .xnxq(Integer.valueOf(xnxq))
//                        .build();
//                scoreMapper.insert(studentScore);
//                return studentScore.getScoreImg();
//            }
//        }
        return null;
    }

    @Override
    public List<CourseScore> getCourseScores(String openId, String xnxq, String xn) {

        StudentUser studentUser = studentUserMapper.selectOne(new QueryWrapper<StudentUser>().eq("openId", openId));
        if (studentUser != null) {
            List<CourseScore> courseScores = courseScoreService.list(new QueryWrapper<CourseScore>().eq("uid", studentUser.getId()).eq("xnxq", xnxq));
            if (!courseScores.isEmpty()) {

                courseScores.forEach(c -> {
                    String courseName = c.getCourseName();
                    c.setCourseName(courseName.substring(courseName.indexOf("]") + 1));
                });
                return courseScores;
            } else {
                List<CourseScore> list = JwcRequest.getCourseScores(JwcRequest.login(studentUser.getStudentId(), studentUser.getPassword(),studentUser.getSchoolId().toString()), xnxq, xn);
                if (list.isEmpty()) {
                    throw new CrudException(ExceptionEnum.user_score_not_public);
                }
                list.forEach(c -> c.setUid(studentUser.getId()));
                courseScoreService.saveBatch(list);
                list.forEach(c -> {
                    String courseName = c.getCourseName();
                    c.setCourseName(courseName.substring(courseName.indexOf("]") + 1));
                });
                return list;
            }
        }
        return null;
    }

    /**
     * @param xqxn  学期 20171
     * @param xn    学年 2017
     * @return
     */
    @Override
    public List getCourseScoreSchool( String studentId,  String password,Integer school,
                                      String xqxn,  String xn) {
        List<CourseScore> list = JwcRequest.getCourseScores(JwcRequest.login(studentId, password,school.toString()), xqxn, xn);
        if (list.isEmpty()) {
            throw new CrudException(ExceptionEnum.user_score_not_public);
        }
        StudentUser user = studentUserMapper.selectOne(new QueryWrapper<StudentUser>().eq("student_id", studentId));
        if (user == null) {
            user = StudentUser.builder().studentId(studentId).password(password).addtime(new BigDecimal(System.currentTimeMillis())).schoolId(school).build();
//            String userKey = MessageFormat.format(RedisConstant.SCHOOL_STUDENT_INFO,school);
//            redisRepository.hset(userKey,studentId,user);
        }
        int uid = user.getId();
        list.forEach(c -> c.setUid(uid));

        String format = MessageFormat.format(RedisConstant.SCHOOL_STUDENT_SCORE,school.toString(),studentId,xqxn);
        list.forEach(c -> {
            String courseName = c.getCourseName();
            c.setCourseName(courseName.substring(courseName.indexOf("]") + 1));
//            if(!redisRepository.hHasKey(format,c.getCourseName())){
//                redisRepository.hset(format,c.getCourseName(),c);
//            }
        });


        return list;
    }


}
